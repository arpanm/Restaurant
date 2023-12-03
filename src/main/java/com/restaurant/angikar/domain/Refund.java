package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.PaymentVendor;
import com.restaurant.angikar.domain.enumeration.RefundStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Refund.
 */
@Entity
@Table(name = "refund")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Refund implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RefundStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "vendor")
    private PaymentVendor vendor;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "init_date")
    private Instant initDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "refunds", "order" }, allowSetters = true)
    private Payment payment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Refund id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefundStatus getStatus() {
        return this.status;
    }

    public Refund status(RefundStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RefundStatus status) {
        this.status = status;
    }

    public PaymentVendor getVendor() {
        return this.vendor;
    }

    public Refund vendor(PaymentVendor vendor) {
        this.setVendor(vendor);
        return this;
    }

    public void setVendor(PaymentVendor vendor) {
        this.vendor = vendor;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Refund amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getInitDate() {
        return this.initDate;
    }

    public Refund initDate(Instant initDate) {
        this.setInitDate(initDate);
        return this;
    }

    public void setInitDate(Instant initDate) {
        this.initDate = initDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Refund updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Refund payment(Payment payment) {
        this.setPayment(payment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Refund)) {
            return false;
        }
        return getId() != null && getId().equals(((Refund) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Refund{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", amount=" + getAmount() +
            ", initDate='" + getInitDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
