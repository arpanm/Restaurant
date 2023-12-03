package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.Nutrition;
import com.restaurant.angikar.repository.NutritionRepository;
import com.restaurant.angikar.service.NutritionService;
import com.restaurant.angikar.service.dto.NutritionDTO;
import com.restaurant.angikar.service.mapper.NutritionMapper;
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
 * Service Implementation for managing {@link com.restaurant.angikar.domain.Nutrition}.
 */
@Service
@Transactional
public class NutritionServiceImpl implements NutritionService {

    private final Logger log = LoggerFactory.getLogger(NutritionServiceImpl.class);

    private final NutritionRepository nutritionRepository;

    private final NutritionMapper nutritionMapper;

    public NutritionServiceImpl(NutritionRepository nutritionRepository, NutritionMapper nutritionMapper) {
        this.nutritionRepository = nutritionRepository;
        this.nutritionMapper = nutritionMapper;
    }

    @Override
    public NutritionDTO save(NutritionDTO nutritionDTO) {
        log.debug("Request to save Nutrition : {}", nutritionDTO);
        Nutrition nutrition = nutritionMapper.toEntity(nutritionDTO);
        nutrition = nutritionRepository.save(nutrition);
        return nutritionMapper.toDto(nutrition);
    }

    @Override
    public NutritionDTO update(NutritionDTO nutritionDTO) {
        log.debug("Request to update Nutrition : {}", nutritionDTO);
        Nutrition nutrition = nutritionMapper.toEntity(nutritionDTO);
        nutrition = nutritionRepository.save(nutrition);
        return nutritionMapper.toDto(nutrition);
    }

    @Override
    public Optional<NutritionDTO> partialUpdate(NutritionDTO nutritionDTO) {
        log.debug("Request to partially update Nutrition : {}", nutritionDTO);

        return nutritionRepository
            .findById(nutritionDTO.getId())
            .map(existingNutrition -> {
                nutritionMapper.partialUpdate(existingNutrition, nutritionDTO);

                return existingNutrition;
            })
            .map(nutritionRepository::save)
            .map(nutritionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NutritionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nutritions");
        return nutritionRepository.findAll(pageable).map(nutritionMapper::toDto);
    }

    /**
     *  Get all the nutritions where Item is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NutritionDTO> findAllWhereItemIsNull() {
        log.debug("Request to get all nutritions where Item is null");
        return StreamSupport
            .stream(nutritionRepository.findAll().spliterator(), false)
            .filter(nutrition -> nutrition.getItem() == null)
            .map(nutritionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NutritionDTO> findOne(Long id) {
        log.debug("Request to get Nutrition : {}", id);
        return nutritionRepository.findById(id).map(nutritionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nutrition : {}", id);
        nutritionRepository.deleteById(id);
    }
}
