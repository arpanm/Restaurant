package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.Price;
import com.restaurant.angikar.repository.PriceRepository;
import com.restaurant.angikar.service.PriceService;
import com.restaurant.angikar.service.dto.PriceDTO;
import com.restaurant.angikar.service.mapper.PriceMapper;
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
 * Service Implementation for managing {@link com.restaurant.angikar.domain.Price}.
 */
@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final PriceRepository priceRepository;

    private final PriceMapper priceMapper;

    public PriceServiceImpl(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public PriceDTO save(PriceDTO priceDTO) {
        log.debug("Request to save Price : {}", priceDTO);
        Price price = priceMapper.toEntity(priceDTO);
        price = priceRepository.save(price);
        return priceMapper.toDto(price);
    }

    @Override
    public PriceDTO update(PriceDTO priceDTO) {
        log.debug("Request to update Price : {}", priceDTO);
        Price price = priceMapper.toEntity(priceDTO);
        price = priceRepository.save(price);
        return priceMapper.toDto(price);
    }

    @Override
    public Optional<PriceDTO> partialUpdate(PriceDTO priceDTO) {
        log.debug("Request to partially update Price : {}", priceDTO);

        return priceRepository
            .findById(priceDTO.getId())
            .map(existingPrice -> {
                priceMapper.partialUpdate(existingPrice, priceDTO);

                return existingPrice;
            })
            .map(priceRepository::save)
            .map(priceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prices");
        return priceRepository.findAll(pageable).map(priceMapper::toDto);
    }

    /**
     *  Get all the prices where Item is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PriceDTO> findAllWhereItemIsNull() {
        log.debug("Request to get all prices where Item is null");
        return StreamSupport
            .stream(priceRepository.findAll().spliterator(), false)
            .filter(price -> price.getItem() == null)
            .map(priceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PriceDTO> findOne(Long id) {
        log.debug("Request to get Price : {}", id);
        return priceRepository.findById(id).map(priceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Price : {}", id);
        priceRepository.deleteById(id);
    }
}
