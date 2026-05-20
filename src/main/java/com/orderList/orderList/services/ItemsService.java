package com.orderList.orderList.services;

import com.orderList.orderList.model.dto.response.ItemsDTO;
import com.orderList.orderList.model.entities.Items;
import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.model.dto.request.CreateItemsDTO;
import com.orderList.orderList.exceptions.customs.BadRequestException;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.utils.mapper.OrderItemMapper;
import com.orderList.orderList.repository.ItemsRepository;
import com.orderList.orderList.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ProductRepository productRepository;
    private final ItemsRepository itemsRepository;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    public ItemsDTO createOrderItem(CreateItemsDTO dto) {
        Items items = orderItemMapper.toEntity(dto);
        itemsRepository.save(items);
        return orderItemMapper.toDTO(items);
    }

    @Transactional
    public void deleteById(Long itemsId){
        Items items = itemsRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("Items not found"));

        itemsRepository.delete(items);
    }

    public ItemsDTO findById(Long itemsId){
        Items items = itemsRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("Items not found"));

        return orderItemMapper.toDTO(items);
    }

    @Transactional
    public ItemsDTO changeQuantity(Long itemsId, Long productId, Integer quantity){
        Items items = itemsRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("Items not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        items.setQuantity(items.getQuantity() + quantity);
        product.setStock(product.getStock() - quantity);
        items.setUnitaryPrice(items.getUnitaryPrice() + (product.getPrice() * quantity));

        productRepository.save(product);
        itemsRepository.save(items);
        return orderItemMapper.toDTO(items);
    }

    @Transactional
    public ItemsDTO increaseQuantity(Long itemsId, Long productId, Integer quantity) {
        return changeQuantity(itemsId, productId, quantity);
    }

    @Transactional
    public ItemsDTO decreaseQuantity(Long itemsId, Long productId, Integer quantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if(product.getStock() < quantity){
            throw new BadRequestException("Product is not enough stock");
        }

        return changeQuantity(itemsId, productId, -quantity);
    }
}
