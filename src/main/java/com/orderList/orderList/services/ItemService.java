package com.orderList.orderList.services;

import com.orderList.orderList.model.dto.request.item.CreateItemDTO;
import com.orderList.orderList.model.dto.request.item.UpdateItemQuantity;
import com.orderList.orderList.model.dto.response.ItemDTO;
import com.orderList.orderList.model.entities.Item;
import com.orderList.orderList.model.entities.Order;
import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.exceptions.customs.BadRequestException;
import com.orderList.orderList.exceptions.customs.NotFoundException;
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

    @Transactional
    public ItemDTO createItems(CreateItemDTO dto, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        Item item = itemMapper.toEntity(dto);
        item.setUnitaryPrice(dto.quantity() * product.getPrice());
        item.setProducts(itemRepository.addProduct(product));
        itemRepository.save(item);
        return itemMapper.toDTO(item);
    }

    @Transactional
    public void deleteById(Long itemsId){
        Item item = itemRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("Items not found"));

        itemRepository.delete(item);
    }

    public ItemDTO findById(Long itemsId){
        Item item = itemRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("Items not found"));

        return itemMapper.toDTO(item);
    }

    public List<ItemDTO> findAll(Long userId) {
        List<Item> items = itemRepository.findAllByUser(userId);
        return items.stream()
                .map(itemMapper::toDTO)
                .toList();
    }

    @Transactional
    public ItemDTO changeQuantity(Long itemsId, Long productId, Integer quantity){
        Item item = itemRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("Items not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        item.setQuantity(item.getQuantity() + quantity);
        product.setStock(product.getStock() - quantity);
        item.setUnitaryPrice(item.getUnitaryPrice() + (product.getPrice() * quantity));

        productRepository.save(product);
        itemRepository.save(item);
        return itemMapper.toDTO(item);
    }

    @Transactional
    public ItemDTO increaseQuantity(Long itemsId, Long productId, UpdateItemQuantity increase) {
        return changeQuantity(itemsId, productId, increase.quantity());
    }

    @Transactional
    public ItemDTO decreaseQuantity(Long itemsId, Long productId, UpdateItemQuantity decrease){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if(product.getStock() < decrease.quantity()){
            throw new BadRequestException("Product is not enough stock");
        }

        return changeQuantity(itemsId, productId, -decrease.quantity());
    }

    @Transactional
    public void addToOrder(Long itemsId, Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        Item item = itemRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("Items not found"));

        item.setOrder(order);
        itemRepository.save(item);
    }
}
