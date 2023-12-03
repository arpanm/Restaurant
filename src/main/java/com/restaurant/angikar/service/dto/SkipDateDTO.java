package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.SkipDate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkipDateDTO implements Serializable {

    private Long id;

    private LocalDate skipDate;

    private MealPlanItemDTO mealPlanItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSkipDate() {
        return skipDate;
    }

    public void setSkipDate(LocalDate skipDate) {
        this.skipDate = skipDate;
    }

    public MealPlanItemDTO getMealPlanItem() {
        return mealPlanItem;
    }

    public void setMealPlanItem(MealPlanItemDTO mealPlanItem) {
        this.mealPlanItem = mealPlanItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkipDateDTO)) {
            return false;
        }

        SkipDateDTO skipDateDTO = (SkipDateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, skipDateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkipDateDTO{" +
            "id=" + getId() +
            ", skipDate='" + getSkipDate() + "'" +
            ", mealPlanItem=" + getMealPlanItem() +
            "}";
    }
}
