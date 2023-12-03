package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.FoodCategory;
import com.restaurant.angikar.repository.FoodCategoryRepository;
import com.restaurant.angikar.service.FoodCategoryService;
import com.restaurant.angikar.service.dto.FoodCategoryDTO;
import com.restaurant.angikar.service.mapper.FoodCategoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.FoodCategory}.
 */
@Service
@Transactional
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final Logger log = LoggerFactory.getLogger(FoodCategoryServiceImpl.class);

    private final FoodCategoryRepository foodCategoryRepository;

    private final FoodCategoryMapper foodCategoryMapper;

    public FoodCategoryServiceImpl(FoodCategoryRepository foodCategoryRepository, FoodCategoryMapper foodCategoryMapper) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.foodCategoryMapper = foodCategoryMapper;
    }

    @Override
    public FoodCategoryDTO save(FoodCategoryDTO foodCategoryDTO) {
        log.debug("Request to save FoodCategory : {}", foodCategoryDTO);
        FoodCategory foodCategory = foodCategoryMapper.toEntity(foodCategoryDTO);
        foodCategory = foodCategoryRepository.save(foodCategory);
        return foodCategoryMapper.toDto(foodCategory);
    }

    @Override
    public FoodCategoryDTO update(FoodCategoryDTO foodCategoryDTO) {
        log.debug("Request to update FoodCategory : {}", foodCategoryDTO);
        FoodCategory foodCategory = foodCategoryMapper.toEntity(foodCategoryDTO);
        foodCategory = foodCategoryRepository.save(foodCategory);
        return foodCategoryMapper.toDto(foodCategory);
    }

    @Override
    public Optional<FoodCategoryDTO> partialUpdate(FoodCategoryDTO foodCategoryDTO) {
        log.debug("Request to partially update FoodCategory : {}", foodCategoryDTO);

        return foodCategoryRepository
            .findById(foodCategoryDTO.getId())
            .map(existingFoodCategory -> {
                foodCategoryMapper.partialUpdate(existingFoodCategory, foodCategoryDTO);

                return existingFoodCategory;
            })
            .map(foodCategoryRepository::save)
            .map(foodCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FoodCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FoodCategories");
        return foodCategoryRepository.findAll(pageable).map(foodCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FoodCategoryDTO> findOne(Long id) {
        log.debug("Request to get FoodCategory : {}", id);
        return foodCategoryRepository.findById(id).map(foodCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FoodCategory : {}", id);
        foodCategoryRepository.deleteById(id);
    }
}
