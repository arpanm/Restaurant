package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.PaymentStatus;
import com.restaurant.angikar.domain.enumeration.PaymentType;
import com.restaurant.angikar.domain.enumeration.PaymentVendor;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Payment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDTO implements Serializable {

    private Long id;

    private PaymentStatus status;

    private PaymentVendor vendor;

    private PaymentType type;

    private Double amount;

    private Instant initDate;

    private Instant updatedDate;

    private Double totalRefundAmount;

    private OrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentVendor getVendor() {
        return vendor;
    }

    public void setVendor(PaymentVendor vendor) {
        this.vendor = vendor;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getInitDate() {
        return initDate;
    }

    public void setInitDate(Instant initDate) {
        this.initDate = initDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(Double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        PaymentDTO paymentDTO = (PaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", type='" + getType() + "'" +
            ", amount=" + getAmount() +
            ", initDate='" + getInitDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", totalRefundAmount=" + getTotalRefundAmount() +
            ", order=" + getOrder() +
            "}";
    }
}
