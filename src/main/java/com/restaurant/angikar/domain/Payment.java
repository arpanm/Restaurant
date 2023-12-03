package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.PaymentStatus;
import com.restaurant.angikar.domain.enumeration.PaymentType;
import com.restaurant.angikar.domain.enumeration.PaymentVendor;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "vendor")
    private PaymentVendor vendor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PaymentType type;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "init_date")
    private Instant initDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "total_refund_amount")
    private Double totalRefundAmount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "payment" }, allowSetters = true)
    private Set<Refund> refunds = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "billingLoc", "items", "payments", "coupon", "usr", "statusUpdatedBy" }, allowSetters = true)
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public Payment status(PaymentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentVendor getVendor() {
        return this.vendor;
    }

    public Payment vendor(PaymentVendor vendor) {
        this.setVendor(vendor);
        return this;
    }

    public void setVendor(PaymentVendor vendor) {
        this.vendor = vendor;
    }

    public PaymentType getType() {
        return this.type;
    }

    public Payment type(PaymentType type) {
        this.setType(type);
        return this;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Payment amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getInitDate() {
        return this.initDate;
    }

    public Payment initDate(Instant initDate) {
        this.setInitDate(initDate);
        return this;
    }

    public void setInitDate(Instant initDate) {
        this.initDate = initDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Payment updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Double getTotalRefundAmount() {
        return this.totalRefundAmount;
    }

    public Payment totalRefundAmount(Double totalRefundAmount) {
        this.setTotalRefundAmount(totalRefundAmount);
        return this;
    }

    public void setTotalRefundAmount(Double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public Set<Refund> getRefunds() {
        return this.refunds;
    }

    public void setRefunds(Set<Refund> refunds) {
        if (this.refunds != null) {
            this.refunds.forEach(i -> i.setPayment(null));
        }
        if (refunds != null) {
            refunds.forEach(i -> i.setPayment(this));
        }
        this.refunds = refunds;
    }

    public Payment refunds(Set<Refund> refunds) {
        this.setRefunds(refunds);
        return this;
    }

    public Payment addRefunds(Refund refund) {
        this.refunds.add(refund);
        refund.setPayment(this);
        return this;
    }

    public Payment removeRefunds(Refund refund) {
        this.refunds.remove(refund);
        refund.setPayment(null);
        return this;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Payment order(Order order) {
        this.setOrder(order);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return getId() != null && getId().equals(((Payment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", type='" + getType() + "'" +
            ", amount=" + getAmount() +
            ", initDate='" + getInitDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", totalRefundAmount=" + getTotalRefundAmount() +
            "}";
    }
}
