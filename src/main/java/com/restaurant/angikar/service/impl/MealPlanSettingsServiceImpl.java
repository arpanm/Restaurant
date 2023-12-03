package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.MealPlanSettings;
import com.restaurant.angikar.repository.MealPlanSettingsRepository;
import com.restaurant.angikar.service.MealPlanSettingsService;
import com.restaurant.angikar.service.dto.MealPlanSettingsDTO;
import com.restaurant.angikar.service.mapper.MealPlanSettingsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.MealPlanSettings}.
 */
@Service
@Transactional
public class MealPlanSettingsServiceImpl implements MealPlanSettingsService {

    private final Logger log = LoggerFactory.getLogger(MealPlanSettingsServiceImpl.class);

    private final MealPlanSettingsRepository mealPlanSettingsRepository;

    private final MealPlanSettingsMapper mealPlanSettingsMapper;

    public MealPlanSettingsServiceImpl(
        MealPlanSettingsRepository mealPlanSettingsRepository,
        MealPlanSettingsMapper mealPlanSettingsMapper
    ) {
        this.mealPlanSettingsRepository = mealPlanSettingsRepository;
        this.mealPlanSettingsMapper = mealPlanSettingsMapper;
    }

    @Override
    public MealPlanSettingsDTO save(MealPlanSettingsDTO mealPlanSettingsDTO) {
        log.debug("Request to save MealPlanSettings : {}", mealPlanSettingsDTO);
        MealPlanSettings mealPlanSettings = mealPlanSettingsMapper.toEntity(mealPlanSettingsDTO);
        mealPlanSettings = mealPlanSettingsRepository.save(mealPlanSettings);
        return mealPlanSettingsMapper.toDto(mealPlanSettings);
    }

    @Override
    public MealPlanSettingsDTO update(MealPlanSettingsDTO mealPlanSettingsDTO) {
        log.debug("Request to update MealPlanSettings : {}", mealPlanSettingsDTO);
        MealPlanSettings mealPlanSettings = mealPlanSettingsMapper.toEntity(mealPlanSettingsDTO);
        mealPlanSettings = mealPlanSettingsRepository.save(mealPlanSettings);
        return mealPlanSettingsMapper.toDto(mealPlanSettings);
    }

    @Override
    public Optional<MealPlanSettingsDTO> partialUpdate(MealPlanSettingsDTO mealPlanSettingsDTO) {
        log.debug("Request to partially update MealPlanSettings : {}", mealPlanSettingsDTO);

        return mealPlanSettingsRepository
            .findById(mealPlanSettingsDTO.getId())
            .map(existingMealPlanSettings -> {
                mealPlanSettingsMapper.partialUpdate(existingMealPlanSettings, mealPlanSettingsDTO);

                return existingMealPlanSettings;
            })
            .map(mealPlanSettingsRepository::save)
            .map(mealPlanSettingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MealPlanSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MealPlanSettings");
        return mealPlanSettingsRepository.findAll(pageable).map(mealPlanSettingsMapper::toDto);
    }

    public Page<MealPlanSettingsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mealPlanSettingsRepository.findAllWithEagerRelationships(pageable).map(mealPlanSettingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MealPlanSettingsDTO> findOne(Long id) {
        log.debug("Request to get MealPlanSettings : {}", id);
        return mealPlanSettingsRepository.findOneWithEagerRelationships(id).map(mealPlanSettingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MealPlanSettings : {}", id);
        mealPlanSettingsRepository.deleteById(id);
    }
}
