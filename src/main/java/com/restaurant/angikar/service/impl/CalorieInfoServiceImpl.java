package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.CalorieInfo;
import com.restaurant.angikar.repository.CalorieInfoRepository;
import com.restaurant.angikar.service.CalorieInfoService;
import com.restaurant.angikar.service.dto.CalorieInfoDTO;
import com.restaurant.angikar.service.mapper.CalorieInfoMapper;
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
 * Service Implementation for managing {@link com.restaurant.angikar.domain.CalorieInfo}.
 */
@Service
@Transactional
public class CalorieInfoServiceImpl implements CalorieInfoService {

    private final Logger log = LoggerFactory.getLogger(CalorieInfoServiceImpl.class);

    private final CalorieInfoRepository calorieInfoRepository;

    private final CalorieInfoMapper calorieInfoMapper;

    public CalorieInfoServiceImpl(CalorieInfoRepository calorieInfoRepository, CalorieInfoMapper calorieInfoMapper) {
        this.calorieInfoRepository = calorieInfoRepository;
        this.calorieInfoMapper = calorieInfoMapper;
    }

    @Override
    public CalorieInfoDTO save(CalorieInfoDTO calorieInfoDTO) {
        log.debug("Request to save CalorieInfo : {}", calorieInfoDTO);
        CalorieInfo calorieInfo = calorieInfoMapper.toEntity(calorieInfoDTO);
        calorieInfo = calorieInfoRepository.save(calorieInfo);
        return calorieInfoMapper.toDto(calorieInfo);
    }

    @Override
    public CalorieInfoDTO update(CalorieInfoDTO calorieInfoDTO) {
        log.debug("Request to update CalorieInfo : {}", calorieInfoDTO);
        CalorieInfo calorieInfo = calorieInfoMapper.toEntity(calorieInfoDTO);
        calorieInfo = calorieInfoRepository.save(calorieInfo);
        return calorieInfoMapper.toDto(calorieInfo);
    }

    @Override
    public Optional<CalorieInfoDTO> partialUpdate(CalorieInfoDTO calorieInfoDTO) {
        log.debug("Request to partially update CalorieInfo : {}", calorieInfoDTO);

        return calorieInfoRepository
            .findById(calorieInfoDTO.getId())
            .map(existingCalorieInfo -> {
                calorieInfoMapper.partialUpdate(existingCalorieInfo, calorieInfoDTO);

                return existingCalorieInfo;
            })
            .map(calorieInfoRepository::save)
            .map(calorieInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CalorieInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CalorieInfos");
        return calorieInfoRepository.findAll(pageable).map(calorieInfoMapper::toDto);
    }

    /**
     *  Get all the calorieInfos where MealPlanSettings is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CalorieInfoDTO> findAllWhereMealPlanSettingsIsNull() {
        log.debug("Request to get all calorieInfos where MealPlanSettings is null");
        return StreamSupport
            .stream(calorieInfoRepository.findAll().spliterator(), false)
            .filter(calorieInfo -> calorieInfo.getMealPlanSettings() == null)
            .map(calorieInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CalorieInfoDTO> findOne(Long id) {
        log.debug("Request to get CalorieInfo : {}", id);
        return calorieInfoRepository.findById(id).map(calorieInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CalorieInfo : {}", id);
        calorieInfoRepository.deleteById(id);
    }
}
