package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.ItemPincodeMapping;
import com.restaurant.angikar.repository.ItemPincodeMappingRepository;
import com.restaurant.angikar.service.ItemPincodeMappingService;
import com.restaurant.angikar.service.dto.ItemPincodeMappingDTO;
import com.restaurant.angikar.service.mapper.ItemPincodeMappingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.ItemPincodeMapping}.
 */
@Service
@Transactional
public class ItemPincodeMappingServiceImpl implements ItemPincodeMappingService {

    private final Logger log = LoggerFactory.getLogger(ItemPincodeMappingServiceImpl.class);

    private final ItemPincodeMappingRepository itemPincodeMappingRepository;

    private final ItemPincodeMappingMapper itemPincodeMappingMapper;

    public ItemPincodeMappingServiceImpl(
        ItemPincodeMappingRepository itemPincodeMappingRepository,
        ItemPincodeMappingMapper itemPincodeMappingMapper
    ) {
        this.itemPincodeMappingRepository = itemPincodeMappingRepository;
        this.itemPincodeMappingMapper = itemPincodeMappingMapper;
    }

    @Override
    public ItemPincodeMappingDTO save(ItemPincodeMappingDTO itemPincodeMappingDTO) {
        log.debug("Request to save ItemPincodeMapping : {}", itemPincodeMappingDTO);
        ItemPincodeMapping itemPincodeMapping = itemPincodeMappingMapper.toEntity(itemPincodeMappingDTO);
        itemPincodeMapping = itemPincodeMappingRepository.save(itemPincodeMapping);
        return itemPincodeMappingMapper.toDto(itemPincodeMapping);
    }

    @Override
    public ItemPincodeMappingDTO update(ItemPincodeMappingDTO itemPincodeMappingDTO) {
        log.debug("Request to update ItemPincodeMapping : {}", itemPincodeMappingDTO);
        ItemPincodeMapping itemPincodeMapping = itemPincodeMappingMapper.toEntity(itemPincodeMappingDTO);
        itemPincodeMapping = itemPincodeMappingRepository.save(itemPincodeMapping);
        return itemPincodeMappingMapper.toDto(itemPincodeMapping);
    }

    @Override
    public Optional<ItemPincodeMappingDTO> partialUpdate(ItemPincodeMappingDTO itemPincodeMappingDTO) {
        log.debug("Request to partially update ItemPincodeMapping : {}", itemPincodeMappingDTO);

        return itemPincodeMappingRepository
            .findById(itemPincodeMappingDTO.getId())
            .map(existingItemPincodeMapping -> {
                itemPincodeMappingMapper.partialUpdate(existingItemPincodeMapping, itemPincodeMappingDTO);

                return existingItemPincodeMapping;
            })
            .map(itemPincodeMappingRepository::save)
            .map(itemPincodeMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemPincodeMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPincodeMappings");
        return itemPincodeMappingRepository.findAll(pageable).map(itemPincodeMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPincodeMappingDTO> findOne(Long id) {
        log.debug("Request to get ItemPincodeMapping : {}", id);
        return itemPincodeMappingRepository.findById(id).map(itemPincodeMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemPincodeMapping : {}", id);
        itemPincodeMappingRepository.deleteById(id);
    }
}
