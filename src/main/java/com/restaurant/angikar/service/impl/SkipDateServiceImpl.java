package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.SkipDate;
import com.restaurant.angikar.repository.SkipDateRepository;
import com.restaurant.angikar.service.SkipDateService;
import com.restaurant.angikar.service.dto.SkipDateDTO;
import com.restaurant.angikar.service.mapper.SkipDateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.SkipDate}.
 */
@Service
@Transactional
public class SkipDateServiceImpl implements SkipDateService {

    private final Logger log = LoggerFactory.getLogger(SkipDateServiceImpl.class);

    private final SkipDateRepository skipDateRepository;

    private final SkipDateMapper skipDateMapper;

    public SkipDateServiceImpl(SkipDateRepository skipDateRepository, SkipDateMapper skipDateMapper) {
        this.skipDateRepository = skipDateRepository;
        this.skipDateMapper = skipDateMapper;
    }

    @Override
    public SkipDateDTO save(SkipDateDTO skipDateDTO) {
        log.debug("Request to save SkipDate : {}", skipDateDTO);
        SkipDate skipDate = skipDateMapper.toEntity(skipDateDTO);
        skipDate = skipDateRepository.save(skipDate);
        return skipDateMapper.toDto(skipDate);
    }

    @Override
    public SkipDateDTO update(SkipDateDTO skipDateDTO) {
        log.debug("Request to update SkipDate : {}", skipDateDTO);
        SkipDate skipDate = skipDateMapper.toEntity(skipDateDTO);
        skipDate = skipDateRepository.save(skipDate);
        return skipDateMapper.toDto(skipDate);
    }

    @Override
    public Optional<SkipDateDTO> partialUpdate(SkipDateDTO skipDateDTO) {
        log.debug("Request to partially update SkipDate : {}", skipDateDTO);

        return skipDateRepository
            .findById(skipDateDTO.getId())
            .map(existingSkipDate -> {
                skipDateMapper.partialUpdate(existingSkipDate, skipDateDTO);

                return existingSkipDate;
            })
            .map(skipDateRepository::save)
            .map(skipDateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SkipDateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkipDates");
        return skipDateRepository.findAll(pageable).map(skipDateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SkipDateDTO> findOne(Long id) {
        log.debug("Request to get SkipDate : {}", id);
        return skipDateRepository.findById(id).map(skipDateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkipDate : {}", id);
        skipDateRepository.deleteById(id);
    }
}
