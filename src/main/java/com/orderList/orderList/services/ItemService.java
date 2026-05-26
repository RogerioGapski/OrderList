package com.orderList.orderList.services;

import com.orderList.orderList.exceptions.customs.UnauthorizedException;
import com.orderList.orderList.model.dto.request.item.CreateItemDTO;
import com.orderList.orderList.model.dto.request.item.UpdateItemQuantity;
import com.orderList.orderList.model.dto.response.ItemDTO;
import com.orderList.orderList.model.entities.Item;
import com.orderList.orderList.model.entities.Order;
import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.exceptions.customs.BadRequestException;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.model.entities.User;
import com.orderList.orderList.repository.OrderRepository;
import com.orderList.orderList.utils.mapper.ItemMapper;
import com.orderList.orderList.repository.ItemRepository;
import com.orderList.orderList.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Transactional
    public ItemDTO createItems(CreateItemDTO dto, Long productId, Long userId) {
        Product product = findProductById(productId);
        if(product.getStock() < dto.quantity()){
            throw new BadRequestException("Insufficient stock for this product.");
        }

        User user = userService.findUserById(userId);

        Item item = itemMapper.toEntity(dto);
        item.setProduct(product);
        item.setUser(user);
        item.setUnitaryPrice(dto.quantity() * product.getPrice());

        product.setStock(product.getStock() - dto.quantity());
        productRepository.save(product);

        itemRepository.save(item);
        return itemMapper.toDTO(item);
    }

    @Transactional
    public void deleteById(Long itemId, Long userId){
        Item item = findItemById(itemId);
        checkOwnership(item, userId);

        Product product = item.getProduct();
        product.setStock(product.getStock() + item.getQuantity());
        productRepository.save(product);

        itemRepository.delete(item);
    }

    public ItemDTO findById(Long itemId, Long userId){
        Item item = findItemById(itemId);
        checkOwnership(item, userId);
        return itemMapper.toDTO(item);
    }

    public List<ItemDTO> findAll(Long userId) {
        return itemRepository.findAllByUserId(userId).stream()
                .map(itemMapper::toDTO)
                .toList();
    }

    @Transactional
    public ItemDTO increaseQuantity(Long itemId, Long userId, UpdateItemQuantity dto) {
        Item item = findItemById(itemId);
        checkOwnership(item, userId);
        Product product = item.getProduct();

        if(product.getStock() < dto.quantity()){
            throw new BadRequestException("Insufficient stock for this product.");
        }

        product.setStock(product.getStock() - dto.quantity());
        item.setQuantity(item.getQuantity() + dto.quantity());
        item.setUnitaryPrice(item.getQuantity() * product.getPrice());

        productRepository.save(product);
        itemRepository.save(item);
        return itemMapper.toDTO(item);
    }

    @Transactional
    public ItemDTO decreaseQuantity(Long itemId, Long userId, UpdateItemQuantity dto){
        Item item = findItemById(itemId);
        checkOwnership(item, userId);
        Product product = item.getProduct();

        if(item.getQuantity() < dto.quantity()){
            throw new BadRequestException("Cannot decrease more than the current item quantity.");
        }

        product.setStock(product.getStock() + dto.quantity());
        item.setQuantity(item.getQuantity() - dto.quantity());
        item.setUnitaryPrice(item.getQuantity() * product.getPrice());

        productRepository.save(product);
        itemRepository.save(item);
        return itemMapper.toDTO(item);
    }

    @Transactional
    public void addToOrder(Long itemId, Long orderId, Long userId) {
        Item item = findItemById(itemId);
        checkOwnership(item, userId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found."));

        if(!order.getUser().getId().equals(userId)){
            throw new UnauthorizedException("Order does not belong to this user.");
        }

        item.setOrder(order);
        itemRepository.save(item);
    }

    private Item findItemById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found."));
    }

    private Product findProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found."));
    }

    private void checkOwnership(Item item, Long userId){
        if(!item.getUser().getId().equals(userId)){
            throw new UnauthorizedException("Item does not belong to this user.");
        }
    }
}
