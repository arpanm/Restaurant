package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.PaymentVendor;
import com.restaurant.angikar.domain.enumeration.RefundStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Refund} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RefundDTO implements Serializable {

    private Long id;

    private RefundStatus status;

    private PaymentVendor vendor;

    private Double amount;

    private Instant initDate;

    private Instant updatedDate;

    private PaymentDTO payment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefundStatus getStatus() {
        return status;
    }

    public void setStatus(RefundStatus status) {
        this.status = status;
    }

    public PaymentVendor getVendor() {
        return vendor;
    }

    public void setVendor(PaymentVendor vendor) {
        this.vendor = vendor;
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

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefundDTO)) {
            return false;
        }

        RefundDTO refundDTO = (RefundDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, refundDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefundDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", amount=" + getAmount() +
            ", initDate='" + getInitDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", payment=" + getPayment() +
            "}";
    }
}
