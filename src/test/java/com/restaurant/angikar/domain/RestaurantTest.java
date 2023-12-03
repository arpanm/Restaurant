package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ApplicationUserTestSamples.*;
import static com.restaurant.angikar.domain.LocationTestSamples.*;
import static com.restaurant.angikar.domain.RestaurantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RestaurantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Restaurant.class);
        Restaurant restaurant1 = getRestaurantSample1();
        Restaurant restaurant2 = new Restaurant();
        assertThat(restaurant1).isNotEqualTo(restaurant2);

        restaurant2.setId(restaurant1.getId());
        assertThat(restaurant1).isEqualTo(restaurant2);

        restaurant2 = getRestaurantSample2();
        assertThat(restaurant1).isNotEqualTo(restaurant2);
    }

    @Test
    void locationTest() throws Exception {
        Restaurant restaurant = getRestaurantRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        restaurant.setLocation(locationBack);
        assertThat(restaurant.getLocation()).isEqualTo(locationBack);

        restaurant.location(null);
        assertThat(restaurant.getLocation()).isNull();
    }

    @Test
    void adminsTest() throws Exception {
        Restaurant restaurant = getRestaurantRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        restaurant.addAdmins(applicationUserBack);
        assertThat(restaurant.getAdmins()).containsOnly(applicationUserBack);
        assertThat(applicationUserBack.getRestaurant()).isEqualTo(restaurant);

        restaurant.removeAdmins(applicationUserBack);
        assertThat(restaurant.getAdmins()).doesNotContain(applicationUserBack);
        assertThat(applicationUserBack.getRestaurant()).isNull();

        restaurant.admins(new HashSet<>(Set.of(applicationUserBack)));
        assertThat(restaurant.getAdmins()).containsOnly(applicationUserBack);
        assertThat(applicationUserBack.getRestaurant()).isEqualTo(restaurant);

        restaurant.setAdmins(new HashSet<>());
        assertThat(restaurant.getAdmins()).doesNotContain(applicationUserBack);
        assertThat(applicationUserBack.getRestaurant()).isNull();
    }
}
