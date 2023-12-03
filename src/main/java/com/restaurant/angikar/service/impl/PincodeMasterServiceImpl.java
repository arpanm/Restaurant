package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.PincodeMaster;
import com.restaurant.angikar.repository.PincodeMasterRepository;
import com.restaurant.angikar.service.PincodeMasterService;
import com.restaurant.angikar.service.dto.PincodeMasterDTO;
import com.restaurant.angikar.service.mapper.PincodeMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.PincodeMaster}.
 */
@Service
@Transactional
public class PincodeMasterServiceImpl implements PincodeMasterService {

    private final Logger log = LoggerFactory.getLogger(PincodeMasterServiceImpl.class);

    private final PincodeMasterRepository pincodeMasterRepository;

    private final PincodeMasterMapper pincodeMasterMapper;

    public PincodeMasterServiceImpl(PincodeMasterRepository pincodeMasterRepository, PincodeMasterMapper pincodeMasterMapper) {
        this.pincodeMasterRepository = pincodeMasterRepository;
        this.pincodeMasterMapper = pincodeMasterMapper;
    }

    @Override
    public PincodeMasterDTO save(PincodeMasterDTO pincodeMasterDTO) {
        log.debug("Request to save PincodeMaster : {}", pincodeMasterDTO);
        PincodeMaster pincodeMaster = pincodeMasterMapper.toEntity(pincodeMasterDTO);
        pincodeMaster = pincodeMasterRepository.save(pincodeMaster);
        return pincodeMasterMapper.toDto(pincodeMaster);
    }

    @Override
    public PincodeMasterDTO update(PincodeMasterDTO pincodeMasterDTO) {
        log.debug("Request to update PincodeMaster : {}", pincodeMasterDTO);
        PincodeMaster pincodeMaster = pincodeMasterMapper.toEntity(pincodeMasterDTO);
        pincodeMaster = pincodeMasterRepository.save(pincodeMaster);
        return pincodeMasterMapper.toDto(pincodeMaster);
    }

    @Override
    public Optional<PincodeMasterDTO> partialUpdate(PincodeMasterDTO pincodeMasterDTO) {
        log.debug("Request to partially update PincodeMaster : {}", pincodeMasterDTO);

        return pincodeMasterRepository
            .findById(pincodeMasterDTO.getId())
            .map(existingPincodeMaster -> {
                pincodeMasterMapper.partialUpdate(existingPincodeMaster, pincodeMasterDTO);

                return existingPincodeMaster;
            })
            .map(pincodeMasterRepository::save)
            .map(pincodeMasterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PincodeMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PincodeMasters");
        return pincodeMasterRepository.findAll(pageable).map(pincodeMasterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PincodeMasterDTO> findOne(Long id) {
        log.debug("Request to get PincodeMaster : {}", id);
        return pincodeMasterRepository.findById(id).map(pincodeMasterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PincodeMaster : {}", id);
        pincodeMasterRepository.deleteById(id);
    }
}
