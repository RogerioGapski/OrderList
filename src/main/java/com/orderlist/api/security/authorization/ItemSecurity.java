package com.orderlist.api.security.authorization;

import com.orderlist.api.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("itemSecurity")
@RequiredArgsConstructor
public class ItemSecurity {

    private final ItemRepository itemRepository;

    public boolean isOwner(Long itemId, UUID userId) {
        return itemRepository.findById(itemId)
                .map(item -> item.getUser().getId().equals(userId))
                .orElse(false);
    }
}
