package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.Discount;
import com.restaurant.angikar.repository.DiscountRepository;
import com.restaurant.angikar.service.DiscountService;
import com.restaurant.angikar.service.dto.DiscountDTO;
import com.restaurant.angikar.service.mapper.DiscountMapper;
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
 * Service Implementation for managing {@link com.restaurant.angikar.domain.Discount}.
 */
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountRepository discountRepository;

    private final DiscountMapper discountMapper;

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
    }

    @Override
    public DiscountDTO save(DiscountDTO discountDTO) {
        log.debug("Request to save Discount : {}", discountDTO);
        Discount discount = discountMapper.toEntity(discountDTO);
        discount = discountRepository.save(discount);
        return discountMapper.toDto(discount);
    }

    @Override
    public DiscountDTO update(DiscountDTO discountDTO) {
        log.debug("Request to update Discount : {}", discountDTO);
        Discount discount = discountMapper.toEntity(discountDTO);
        discount = discountRepository.save(discount);
        return discountMapper.toDto(discount);
    }

    @Override
    public Optional<DiscountDTO> partialUpdate(DiscountDTO discountDTO) {
        log.debug("Request to partially update Discount : {}", discountDTO);

        return discountRepository
            .findById(discountDTO.getId())
            .map(existingDiscount -> {
                discountMapper.partialUpdate(existingDiscount, discountDTO);

                return existingDiscount;
            })
            .map(discountRepository::save)
            .map(discountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiscountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Discounts");
        return discountRepository.findAll(pageable).map(discountMapper::toDto);
    }

    /**
     *  Get all the discounts where MealPlan is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DiscountDTO> findAllWhereMealPlanIsNull() {
        log.debug("Request to get all discounts where MealPlan is null");
        return StreamSupport
            .stream(discountRepository.findAll().spliterator(), false)
            .filter(discount -> discount.getMealPlan() == null)
            .map(discountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountDTO> findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        return discountRepository.findById(id).map(discountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discount : {}", id);
        discountRepository.deleteById(id);
    }
}
