package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.WeightInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WeightInfoDTO implements Serializable {

    private Long id;

    private Double currentWeight;

    private Double expectedWeight;

    private Double heightInInch;

    private Integer numberOfDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Double getExpectedWeight() {
        return expectedWeight;
    }

    public void setExpectedWeight(Double expectedWeight) {
        this.expectedWeight = expectedWeight;
    }

    public Double getHeightInInch() {
        return heightInInch;
    }

    public void setHeightInInch(Double heightInInch) {
        this.heightInInch = heightInInch;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeightInfoDTO)) {
            return false;
        }

        WeightInfoDTO weightInfoDTO = (WeightInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, weightInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WeightInfoDTO{" +
            "id=" + getId() +
            ", currentWeight=" + getCurrentWeight() +
            ", expectedWeight=" + getExpectedWeight() +
            ", heightInInch=" + getHeightInInch() +
            ", numberOfDays=" + getNumberOfDays() +
            "}";
    }
}
