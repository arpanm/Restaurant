package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.QtyUnit;
import com.restaurant.angikar.repository.QtyUnitRepository;
import com.restaurant.angikar.service.QtyUnitService;
import com.restaurant.angikar.service.dto.QtyUnitDTO;
import com.restaurant.angikar.service.mapper.QtyUnitMapper;
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
 * Service Implementation for managing {@link com.restaurant.angikar.domain.QtyUnit}.
 */
@Service
@Transactional
public class QtyUnitServiceImpl implements QtyUnitService {

    private final Logger log = LoggerFactory.getLogger(QtyUnitServiceImpl.class);

    private final QtyUnitRepository qtyUnitRepository;

    private final QtyUnitMapper qtyUnitMapper;

    public QtyUnitServiceImpl(QtyUnitRepository qtyUnitRepository, QtyUnitMapper qtyUnitMapper) {
        this.qtyUnitRepository = qtyUnitRepository;
        this.qtyUnitMapper = qtyUnitMapper;
    }

    @Override
    public QtyUnitDTO save(QtyUnitDTO qtyUnitDTO) {
        log.debug("Request to save QtyUnit : {}", qtyUnitDTO);
        QtyUnit qtyUnit = qtyUnitMapper.toEntity(qtyUnitDTO);
        qtyUnit = qtyUnitRepository.save(qtyUnit);
        return qtyUnitMapper.toDto(qtyUnit);
    }

    @Override
    public QtyUnitDTO update(QtyUnitDTO qtyUnitDTO) {
        log.debug("Request to update QtyUnit : {}", qtyUnitDTO);
        QtyUnit qtyUnit = qtyUnitMapper.toEntity(qtyUnitDTO);
        qtyUnit = qtyUnitRepository.save(qtyUnit);
        return qtyUnitMapper.toDto(qtyUnit);
    }

    @Override
    public Optional<QtyUnitDTO> partialUpdate(QtyUnitDTO qtyUnitDTO) {
        log.debug("Request to partially update QtyUnit : {}", qtyUnitDTO);

        return qtyUnitRepository
            .findById(qtyUnitDTO.getId())
            .map(existingQtyUnit -> {
                qtyUnitMapper.partialUpdate(existingQtyUnit, qtyUnitDTO);

                return existingQtyUnit;
            })
            .map(qtyUnitRepository::save)
            .map(qtyUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QtyUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QtyUnits");
        return qtyUnitRepository.findAll(pageable).map(qtyUnitMapper::toDto);
    }

    /**
     *  Get all the qtyUnits where Quantity is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QtyUnitDTO> findAllWhereQuantityIsNull() {
        log.debug("Request to get all qtyUnits where Quantity is null");
        return StreamSupport
            .stream(qtyUnitRepository.findAll().spliterator(), false)
            .filter(qtyUnit -> qtyUnit.getQuantity() == null)
            .map(qtyUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QtyUnitDTO> findOne(Long id) {
        log.debug("Request to get QtyUnit : {}", id);
        return qtyUnitRepository.findById(id).map(qtyUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QtyUnit : {}", id);
        qtyUnitRepository.deleteById(id);
    }
}
