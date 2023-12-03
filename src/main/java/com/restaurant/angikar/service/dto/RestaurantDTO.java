package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.RestaurantType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Restaurant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RestaurantDTO implements Serializable {

    private Long id;

    private String restaurantName;

    private String title;

    private String logo;

    private RestaurantType type;

    private LocationDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public RestaurantType getType() {
        return type;
    }

    public void setType(RestaurantType type) {
        this.type = type;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurantDTO)) {
            return false;
        }

        RestaurantDTO restaurantDTO = (RestaurantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, restaurantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantDTO{" +
            "id=" + getId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            ", title='" + getTitle() + "'" +
            ", logo='" + getLogo() + "'" +
            ", type='" + getType() + "'" +
            ", location=" + getLocation() +
            "}";
    }
}
