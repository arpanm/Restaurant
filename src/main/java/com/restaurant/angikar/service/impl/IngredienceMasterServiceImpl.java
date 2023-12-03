package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.IngredienceMaster;
import com.restaurant.angikar.repository.IngredienceMasterRepository;
import com.restaurant.angikar.service.IngredienceMasterService;
import com.restaurant.angikar.service.dto.IngredienceMasterDTO;
import com.restaurant.angikar.service.mapper.IngredienceMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.IngredienceMaster}.
 */
@Service
@Transactional
public class IngredienceMasterServiceImpl implements IngredienceMasterService {

    private final Logger log = LoggerFactory.getLogger(IngredienceMasterServiceImpl.class);

    private final IngredienceMasterRepository ingredienceMasterRepository;

    private final IngredienceMasterMapper ingredienceMasterMapper;

    public IngredienceMasterServiceImpl(
        IngredienceMasterRepository ingredienceMasterRepository,
        IngredienceMasterMapper ingredienceMasterMapper
    ) {
        this.ingredienceMasterRepository = ingredienceMasterRepository;
        this.ingredienceMasterMapper = ingredienceMasterMapper;
    }

    @Override
    public IngredienceMasterDTO save(IngredienceMasterDTO ingredienceMasterDTO) {
        log.debug("Request to save IngredienceMaster : {}", ingredienceMasterDTO);
        IngredienceMaster ingredienceMaster = ingredienceMasterMapper.toEntity(ingredienceMasterDTO);
        ingredienceMaster = ingredienceMasterRepository.save(ingredienceMaster);
        return ingredienceMasterMapper.toDto(ingredienceMaster);
    }

    @Override
    public IngredienceMasterDTO update(IngredienceMasterDTO ingredienceMasterDTO) {
        log.debug("Request to update IngredienceMaster : {}", ingredienceMasterDTO);
        IngredienceMaster ingredienceMaster = ingredienceMasterMapper.toEntity(ingredienceMasterDTO);
        ingredienceMaster = ingredienceMasterRepository.save(ingredienceMaster);
        return ingredienceMasterMapper.toDto(ingredienceMaster);
    }

    @Override
    public Optional<IngredienceMasterDTO> partialUpdate(IngredienceMasterDTO ingredienceMasterDTO) {
        log.debug("Request to partially update IngredienceMaster : {}", ingredienceMasterDTO);

        return ingredienceMasterRepository
            .findById(ingredienceMasterDTO.getId())
            .map(existingIngredienceMaster -> {
                ingredienceMasterMapper.partialUpdate(existingIngredienceMaster, ingredienceMasterDTO);

                return existingIngredienceMaster;
            })
            .map(ingredienceMasterRepository::save)
            .map(ingredienceMasterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IngredienceMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IngredienceMasters");
        return ingredienceMasterRepository.findAll(pageable).map(ingredienceMasterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IngredienceMasterDTO> findOne(Long id) {
        log.debug("Request to get IngredienceMaster : {}", id);
        return ingredienceMasterRepository.findById(id).map(ingredienceMasterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IngredienceMaster : {}", id);
        ingredienceMasterRepository.deleteById(id);
    }
}
