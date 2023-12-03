package com.restaurant.angikar.service.impl;

import com.restaurant.angikar.domain.Coupon;
import com.restaurant.angikar.repository.CouponRepository;
import com.restaurant.angikar.service.CouponService;
import com.restaurant.angikar.service.dto.CouponDTO;
import com.restaurant.angikar.service.mapper.CouponMapper;
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
 * Service Implementation for managing {@link com.restaurant.angikar.domain.Coupon}.
 */
@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    private final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    public CouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    public CouponDTO save(CouponDTO couponDTO) {
        log.debug("Request to save Coupon : {}", couponDTO);
        Coupon coupon = couponMapper.toEntity(couponDTO);
        coupon = couponRepository.save(coupon);
        return couponMapper.toDto(coupon);
    }

    @Override
    public CouponDTO update(CouponDTO couponDTO) {
        log.debug("Request to update Coupon : {}", couponDTO);
        Coupon coupon = couponMapper.toEntity(couponDTO);
        coupon = couponRepository.save(coupon);
        return couponMapper.toDto(coupon);
    }

    @Override
    public Optional<CouponDTO> partialUpdate(CouponDTO couponDTO) {
        log.debug("Request to partially update Coupon : {}", couponDTO);

        return couponRepository
            .findById(couponDTO.getId())
            .map(existingCoupon -> {
                couponMapper.partialUpdate(existingCoupon, couponDTO);

                return existingCoupon;
            })
            .map(couponRepository::save)
            .map(couponMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coupons");
        return couponRepository.findAll(pageable).map(couponMapper::toDto);
    }

    /**
     *  Get all the coupons where Cart is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CouponDTO> findAllWhereCartIsNull() {
        log.debug("Request to get all coupons where Cart is null");
        return StreamSupport
            .stream(couponRepository.findAll().spliterator(), false)
            .filter(coupon -> coupon.getCart() == null)
            .map(couponMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponDTO> findOne(Long id) {
        log.debug("Request to get Coupon : {}", id);
        return couponRepository.findById(id).map(couponMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coupon : {}", id);
        couponRepository.deleteById(id);
    }
}
