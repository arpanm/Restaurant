package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.Refund;
import com.restaurant.angikar.repository.RefundRepository;
import com.restaurant.angikar.service.RefundService;
import com.restaurant.angikar.service.dto.RefundDTO;
import com.restaurant.angikar.service.mapper.RefundMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.restaurant.angikar.domain.Refund}.
 */
@Service
@Transactional
public class RefundServiceImpl implements RefundService {

    private final Logger log = LoggerFactory.getLogger(RefundServiceImpl.class);

    private final RefundRepository refundRepository;

    private final RefundMapper refundMapper;

    public RefundServiceImpl(RefundRepository refundRepository, RefundMapper refundMapper) {
        this.refundRepository = refundRepository;
        this.refundMapper = refundMapper;
    }

    @Override
    public RefundDTO save(RefundDTO refundDTO) {
        log.debug("Request to save Refund : {}", refundDTO);
        Refund refund = refundMapper.toEntity(refundDTO);
        refund = refundRepository.save(refund);
        return refundMapper.toDto(refund);
    }

    @Override
    public RefundDTO update(RefundDTO refundDTO) {
        log.debug("Request to update Refund : {}", refundDTO);
        Refund refund = refundMapper.toEntity(refundDTO);
        refund = refundRepository.save(refund);
        return refundMapper.toDto(refund);
    }

    @Override
    public Optional<RefundDTO> partialUpdate(RefundDTO refundDTO) {
        log.debug("Request to partially update Refund : {}", refundDTO);

        return refundRepository
            .findById(refundDTO.getId())
            .map(existingRefund -> {
                refundMapper.partialUpdate(existingRefund, refundDTO);

                return existingRefund;
            })
            .map(refundRepository::save)
            .map(refundMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RefundDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Refunds");
        return refundRepository.findAll(pageable).map(refundMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefundDTO> findOne(Long id) {
        log.debug("Request to get Refund : {}", id);
        return refundRepository.findById(id).map(refundMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Refund : {}", id);
        refundRepository.deleteById(id);
    }
}
