package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.WeightInfo;
import com.restaurant.angikar.repository.WeightInfoRepository;
import com.restaurant.angikar.service.WeightInfoService;
import com.restaurant.angikar.service.dto.WeightInfoDTO;
import com.restaurant.angikar.service.mapper.WeightInfoMapper;
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
 * Service Implementation for managing {@link com.restaurant.angikar.domain.WeightInfo}.
 */
@Service
@Transactional
public class WeightInfoServiceImpl implements WeightInfoService {

    private final Logger log = LoggerFactory.getLogger(WeightInfoServiceImpl.class);

    private final WeightInfoRepository weightInfoRepository;

    private final WeightInfoMapper weightInfoMapper;

    public WeightInfoServiceImpl(WeightInfoRepository weightInfoRepository, WeightInfoMapper weightInfoMapper) {
        this.weightInfoRepository = weightInfoRepository;
        this.weightInfoMapper = weightInfoMapper;
    }

    @Override
    public WeightInfoDTO save(WeightInfoDTO weightInfoDTO) {
        log.debug("Request to save WeightInfo : {}", weightInfoDTO);
        WeightInfo weightInfo = weightInfoMapper.toEntity(weightInfoDTO);
        weightInfo = weightInfoRepository.save(weightInfo);
        return weightInfoMapper.toDto(weightInfo);
    }

    @Override
    public WeightInfoDTO update(WeightInfoDTO weightInfoDTO) {
        log.debug("Request to update WeightInfo : {}", weightInfoDTO);
        WeightInfo weightInfo = weightInfoMapper.toEntity(weightInfoDTO);
        weightInfo = weightInfoRepository.save(weightInfo);
        return weightInfoMapper.toDto(weightInfo);
    }

    @Override
    public Optional<WeightInfoDTO> partialUpdate(WeightInfoDTO weightInfoDTO) {
        log.debug("Request to partially update WeightInfo : {}", weightInfoDTO);

        return weightInfoRepository
            .findById(weightInfoDTO.getId())
            .map(existingWeightInfo -> {
                weightInfoMapper.partialUpdate(existingWeightInfo, weightInfoDTO);

                return existingWeightInfo;
            })
            .map(weightInfoRepository::save)
            .map(weightInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WeightInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WeightInfos");
        return weightInfoRepository.findAll(pageable).map(weightInfoMapper::toDto);
    }

    /**
     *  Get all the weightInfos where MealPlanSettings is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WeightInfoDTO> findAllWhereMealPlanSettingsIsNull() {
        log.debug("Request to get all weightInfos where MealPlanSettings is null");
        return StreamSupport
            .stream(weightInfoRepository.findAll().spliterator(), false)
            .filter(weightInfo -> weightInfo.getMealPlanSettings() == null)
            .map(weightInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WeightInfoDTO> findOne(Long id) {
        log.debug("Request to get WeightInfo : {}", id);
        return weightInfoRepository.findById(id).map(weightInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeightInfo : {}", id);
        weightInfoRepository.deleteById(id);
    }
}
