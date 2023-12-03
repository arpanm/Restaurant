package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.Quantity;
import com.restaurant.angikar.repository.QuantityRepository;
import com.restaurant.angikar.service.QuantityService;
import com.restaurant.angikar.service.dto.QuantityDTO;
import com.restaurant.angikar.service.mapper.QuantityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.Quantity}.
 */
@Service
@Transactional
public class QuantityServiceImpl implements QuantityService {

    private final Logger log = LoggerFactory.getLogger(QuantityServiceImpl.class);

    private final QuantityRepository quantityRepository;

    private final QuantityMapper quantityMapper;

    public QuantityServiceImpl(QuantityRepository quantityRepository, QuantityMapper quantityMapper) {
        this.quantityRepository = quantityRepository;
        this.quantityMapper = quantityMapper;
    }

    @Override
    public QuantityDTO save(QuantityDTO quantityDTO) {
        log.debug("Request to save Quantity : {}", quantityDTO);
        Quantity quantity = quantityMapper.toEntity(quantityDTO);
        quantity = quantityRepository.save(quantity);
        return quantityMapper.toDto(quantity);
    }

    @Override
    public QuantityDTO update(QuantityDTO quantityDTO) {
        log.debug("Request to update Quantity : {}", quantityDTO);
        Quantity quantity = quantityMapper.toEntity(quantityDTO);
        quantity = quantityRepository.save(quantity);
        return quantityMapper.toDto(quantity);
    }

    @Override
    public Optional<QuantityDTO> partialUpdate(QuantityDTO quantityDTO) {
        log.debug("Request to partially update Quantity : {}", quantityDTO);

        return quantityRepository
            .findById(quantityDTO.getId())
            .map(existingQuantity -> {
                quantityMapper.partialUpdate(existingQuantity, quantityDTO);

                return existingQuantity;
            })
            .map(quantityRepository::save)
            .map(quantityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuantityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quantities");
        return quantityRepository.findAll(pageable).map(quantityMapper::toDto);
    }

    /**
     *  Get all the quantities where Item is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QuantityDTO> findAllWhereItemIsNull() {
        log.debug("Request to get all quantities where Item is null");
        return StreamSupport
            .stream(quantityRepository.findAll().spliterator(), false)
            .filter(quantity -> quantity.getItem() == null)
            .map(quantityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuantityDTO> findOne(Long id) {
        log.debug("Request to get Quantity : {}", id);
        return quantityRepository.findById(id).map(quantityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quantity : {}", id);
        quantityRepository.deleteById(id);
    }
}
