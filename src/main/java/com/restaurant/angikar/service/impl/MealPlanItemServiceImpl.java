package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.MealPlanItem;
import com.restaurant.angikar.repository.MealPlanItemRepository;
import com.restaurant.angikar.service.MealPlanItemService;
import com.restaurant.angikar.service.dto.MealPlanItemDTO;
import com.restaurant.angikar.service.mapper.MealPlanItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.MealPlanItem}.
 */
@Service
@Transactional
public class MealPlanItemServiceImpl implements MealPlanItemService {

    private final Logger log = LoggerFactory.getLogger(MealPlanItemServiceImpl.class);

    private final MealPlanItemRepository mealPlanItemRepository;

    private final MealPlanItemMapper mealPlanItemMapper;

    public MealPlanItemServiceImpl(MealPlanItemRepository mealPlanItemRepository, MealPlanItemMapper mealPlanItemMapper) {
        this.mealPlanItemRepository = mealPlanItemRepository;
        this.mealPlanItemMapper = mealPlanItemMapper;
    }

    @Override
    public MealPlanItemDTO save(MealPlanItemDTO mealPlanItemDTO) {
        log.debug("Request to save MealPlanItem : {}", mealPlanItemDTO);
        MealPlanItem mealPlanItem = mealPlanItemMapper.toEntity(mealPlanItemDTO);
        mealPlanItem = mealPlanItemRepository.save(mealPlanItem);
        return mealPlanItemMapper.toDto(mealPlanItem);
    }

    @Override
    public MealPlanItemDTO update(MealPlanItemDTO mealPlanItemDTO) {
        log.debug("Request to update MealPlanItem : {}", mealPlanItemDTO);
        MealPlanItem mealPlanItem = mealPlanItemMapper.toEntity(mealPlanItemDTO);
        mealPlanItem = mealPlanItemRepository.save(mealPlanItem);
        return mealPlanItemMapper.toDto(mealPlanItem);
    }

    @Override
    public Optional<MealPlanItemDTO> partialUpdate(MealPlanItemDTO mealPlanItemDTO) {
        log.debug("Request to partially update MealPlanItem : {}", mealPlanItemDTO);

        return mealPlanItemRepository
            .findById(mealPlanItemDTO.getId())
            .map(existingMealPlanItem -> {
                mealPlanItemMapper.partialUpdate(existingMealPlanItem, mealPlanItemDTO);

                return existingMealPlanItem;
            })
            .map(mealPlanItemRepository::save)
            .map(mealPlanItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MealPlanItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MealPlanItems");
        return mealPlanItemRepository.findAll(pageable).map(mealPlanItemMapper::toDto);
    }

    public Page<MealPlanItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mealPlanItemRepository.findAllWithEagerRelationships(pageable).map(mealPlanItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MealPlanItemDTO> findOne(Long id) {
        log.debug("Request to get MealPlanItem : {}", id);
        return mealPlanItemRepository.findOneWithEagerRelationships(id).map(mealPlanItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MealPlanItem : {}", id);
        mealPlanItemRepository.deleteById(id);
    }
}
