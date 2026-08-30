package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.ConflictException;
import com.orderlist.api.model.dto.request.item.CreateItemDTO;
import com.orderlist.api.model.dto.request.item.UpdateItemQuantity;
import com.orderlist.api.model.dto.response.ItemDTO;
import com.orderlist.api.model.entities.Item;
import com.orderlist.api.model.entities.Order;
import com.orderlist.api.model.entities.Product;
import com.orderlist.api.exceptions.customs.BadRequestException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.utils.mapper.ItemMapper;
import com.orderlist.api.repository.ItemRepository;
import com.orderlist.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final OrderService orderService;
    private final UserService userService;

    @Transactional
    @PreAuthorize("#userId == authentication.principal.user.id")
    public ItemDTO createItem(CreateItemDTO dto, Long productId, UUID userId) {
        Product product = findProductById(productId);
        if(product.getStock() < dto.quantity()){
            throw new BadRequestException("Insufficient stock for this product");
        }

        User user = userService.findUserById(userId);

        Item item = itemMapper.toEntity(dto);
        item.setProduct(product);
        item.setUser(user);
        item.setUnitaryPrice(product.getPrice().multiply(BigDecimal.valueOf(dto.quantity())));
        itemRepository.save(item);

        try {
            product.setStock(product.getStock() - dto.quantity());
            productRepository.save(product);
        } catch (ObjectOptimisticLockingFailureException e){
            throw new ConflictException("Operation conflict. Please try again");
        }

        return itemMapper.toDTO(item);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @itemSecurity.isOwner(#itemId, authentication.principal.user.id)")
    public void deleteById(Long itemId){
        Item item = findItemById(itemId);
        Product product = item.getProduct();

        try {
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        } catch (ObjectOptimisticLockingFailureException e){
            throw new ConflictException("Operation conflict. Please try again");
        }

        itemRepository.delete(item);
    }

    @PostAuthorize("hasRole('ADMIN') or returnObject.userId == authentication.principal.user.id")
    public ItemDTO findById(Long itemId){
        Item item = findItemById(itemId);
        return itemMapper.toDTO(item);
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.user.id")
    public Page<ItemDTO> findAll(UUID userId, Pageable pageable) {
        Page<Item> all = itemRepository.findAllByUserId(userId, pageable);
        return all.map(itemMapper::toDTO);
    }

    @Transactional
    @PreAuthorize("@itemSecurity.isOwner(#itemId, authentication.principal.user.id)")
    public ItemDTO increaseQuantity(Long itemId, UpdateItemQuantity dto) {
        Item item = findItemById(itemId);
        Product product = item.getProduct();

        if(item.getOrder() == null){
            throw new BadRequestException("The item is not associated with any order");
        }

        if(product.getStock() < dto.quantity()){
            throw new BadRequestException("Insufficient stock for this product");
        }

        try {
            product.setStock(product.getStock() - dto.quantity());
            productRepository.save(product);
        } catch (ObjectOptimisticLockingFailureException e){
            throw new ConflictException("Operation conflict. Please try again");
        }

        item.setQuantity(item.getQuantity() + dto.quantity());
        item.setUnitaryPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        itemRepository.save(item);
        return itemMapper.toDTO(item);
    }

    @Transactional
    @PreAuthorize("@itemSecurity.isOwner(#itemId, authentication.principal.user.id)")
    public ItemDTO decreaseQuantity(Long itemId, UpdateItemQuantity dto){
        Item item = findItemById(itemId);

        if(item.getOrder() == null){
            throw new BadRequestException("The item is not associated with any order");
        }

        Product product = item.getProduct();

        if(item.getQuantity() <= dto.quantity()){
            throw new BadRequestException("Cannot decrease more than the current item quantity");
        }

        try {
            product.setStock(product.getStock() + dto.quantity());
            productRepository.save(product);
        } catch (ObjectOptimisticLockingFailureException e){
            throw new ConflictException("Operation conflict. Please try again");
        }

        item.setQuantity(item.getQuantity() - dto.quantity());
        item.setUnitaryPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        itemRepository.save(item);
        return itemMapper.toDTO(item);
    }

    @Transactional
    @PreAuthorize("@itemSecurity.isOwner(#itemId, authentication.principal.user.id) && @orderSecurity.isOwner(orderId, authentication.principal.user.id)")
    public void addToOrder(Long itemId, Long orderId) {
        Item item = findItemById(itemId);

        if(item.getOrder() != null){
            throw new BadRequestException("The item is already in an order");
        }

        Order order = orderService.findOrderById(orderId);
        item.setOrder(order);
        itemRepository.save(item);
    }

    //Auxiliary methods
    Item findItemById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    Product findProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }
}

