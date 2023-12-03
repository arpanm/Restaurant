package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SkipDate.
 */
@Entity
@Table(name = "skip_date")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkipDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "skip_date")
    private LocalDate skipDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "deliveryLocation", "skipDates", "items", "plan" }, allowSetters = true)
    private MealPlanItem mealPlanItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SkipDate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSkipDate() {
        return this.skipDate;
    }

    public SkipDate skipDate(LocalDate skipDate) {
        this.setSkipDate(skipDate);
        return this;
    }

    public void setSkipDate(LocalDate skipDate) {
        this.skipDate = skipDate;
    }

    public MealPlanItem getMealPlanItem() {
        return this.mealPlanItem;
    }

    public void setMealPlanItem(MealPlanItem mealPlanItem) {
        this.mealPlanItem = mealPlanItem;
    }

    public SkipDate mealPlanItem(MealPlanItem mealPlanItem) {
        this.setMealPlanItem(mealPlanItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkipDate)) {
            return false;
        }
        return getId() != null && getId().equals(((SkipDate) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkipDate{" +
            "id=" + getId() +
            ", skipDate='" + getSkipDate() + "'" +
            "}";
    }
}
