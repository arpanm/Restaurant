package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.OrderStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Order} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    private OrderStatus status;

    private Instant orderDate;

    private Double amount;

    private Double couponValue;

    private LocationDTO billingLoc;

    private CouponDTO coupon;

    private ApplicationUserDTO usr;

    private ApplicationUserDTO statusUpdatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(Double couponValue) {
        this.couponValue = couponValue;
    }

    public LocationDTO getBillingLoc() {
        return billingLoc;
    }

    public void setBillingLoc(LocationDTO billingLoc) {
        this.billingLoc = billingLoc;
    }

    public CouponDTO getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponDTO coupon) {
        this.coupon = coupon;
    }

    public ApplicationUserDTO getUsr() {
        return usr;
    }

    public void setUsr(ApplicationUserDTO usr) {
        this.usr = usr;
    }

    public ApplicationUserDTO getStatusUpdatedBy() {
        return statusUpdatedBy;
    }

    public void setStatusUpdatedBy(ApplicationUserDTO statusUpdatedBy) {
        this.statusUpdatedBy = statusUpdatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", amount=" + getAmount() +
            ", couponValue=" + getCouponValue() +
            ", billingLoc=" + getBillingLoc() +
            ", coupon=" + getCoupon() +
            ", usr=" + getUsr() +
            ", statusUpdatedBy=" + getStatusUpdatedBy() +
            "}";
    }
}
