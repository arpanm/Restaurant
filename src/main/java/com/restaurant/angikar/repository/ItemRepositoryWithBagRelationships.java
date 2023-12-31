package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ItemRepositoryWithBagRelationships {
    Optional<Item> fetchBagRelationships(Optional<Item> item);

    List<Item> fetchBagRelationships(List<Item> items);

    Page<Item> fetchBagRelationships(Page<Item> items);
}
