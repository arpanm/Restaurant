package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.Avoid;
import com.restaurant.angikar.repository.AvoidRepository;
import com.restaurant.angikar.service.AvoidService;
import com.restaurant.angikar.service.dto.AvoidDTO;
import com.restaurant.angikar.service.mapper.AvoidMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.Avoid}.
 */
@Service
@Transactional
public class AvoidServiceImpl implements AvoidService {

    private final Logger log = LoggerFactory.getLogger(AvoidServiceImpl.class);

    private final AvoidRepository avoidRepository;

    private final AvoidMapper avoidMapper;

    public AvoidServiceImpl(AvoidRepository avoidRepository, AvoidMapper avoidMapper) {
        this.avoidRepository = avoidRepository;
        this.avoidMapper = avoidMapper;
    }

    @Override
    public AvoidDTO save(AvoidDTO avoidDTO) {
        log.debug("Request to save Avoid : {}", avoidDTO);
        Avoid avoid = avoidMapper.toEntity(avoidDTO);
        avoid = avoidRepository.save(avoid);
        return avoidMapper.toDto(avoid);
    }

    @Override
    public AvoidDTO update(AvoidDTO avoidDTO) {
        log.debug("Request to update Avoid : {}", avoidDTO);
        Avoid avoid = avoidMapper.toEntity(avoidDTO);
        avoid = avoidRepository.save(avoid);
        return avoidMapper.toDto(avoid);
    }

    @Override
    public Optional<AvoidDTO> partialUpdate(AvoidDTO avoidDTO) {
        log.debug("Request to partially update Avoid : {}", avoidDTO);

        return avoidRepository
            .findById(avoidDTO.getId())
            .map(existingAvoid -> {
                avoidMapper.partialUpdate(existingAvoid, avoidDTO);

                return existingAvoid;
            })
            .map(avoidRepository::save)
            .map(avoidMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvoidDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Avoids");
        return avoidRepository.findAll(pageable).map(avoidMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AvoidDTO> findOne(Long id) {
        log.debug("Request to get Avoid : {}", id);
        return avoidRepository.findById(id).map(avoidMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avoid : {}", id);
        avoidRepository.deleteById(id);
    }
}
