package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ApplicationUserTestSamples.*;
import static com.restaurant.angikar.domain.LocationTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanItemTestSamples.*;
import static com.restaurant.angikar.domain.OrderItemTestSamples.*;
import static com.restaurant.angikar.domain.OrderTestSamples.*;
import static com.restaurant.angikar.domain.RestaurantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void usrTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        location.setUsr(applicationUserBack);
        assertThat(location.getUsr()).isEqualTo(applicationUserBack);

        location.usr(null);
        assertThat(location.getUsr()).isNull();
    }

    @Test
    void restaurantTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Restaurant restaurantBack = getRestaurantRandomSampleGenerator();

        location.setRestaurant(restaurantBack);
        assertThat(location.getRestaurant()).isEqualTo(restaurantBack);
        assertThat(restaurantBack.getLocation()).isEqualTo(location);

        location.restaurant(null);
        assertThat(location.getRestaurant()).isNull();
        assertThat(restaurantBack.getLocation()).isNull();
    }

    @Test
    void mealPlanItemTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        MealPlanItem mealPlanItemBack = getMealPlanItemRandomSampleGenerator();

        location.setMealPlanItem(mealPlanItemBack);
        assertThat(location.getMealPlanItem()).isEqualTo(mealPlanItemBack);
        assertThat(mealPlanItemBack.getDeliveryLocation()).isEqualTo(location);

        location.mealPlanItem(null);
        assertThat(location.getMealPlanItem()).isNull();
        assertThat(mealPlanItemBack.getDeliveryLocation()).isNull();
    }

    @Test
    void orderItemTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        OrderItem orderItemBack = getOrderItemRandomSampleGenerator();

        location.setOrderItem(orderItemBack);
        assertThat(location.getOrderItem()).isEqualTo(orderItemBack);
        assertThat(orderItemBack.getDeliveryLoc()).isEqualTo(location);

        location.orderItem(null);
        assertThat(location.getOrderItem()).isNull();
        assertThat(orderItemBack.getDeliveryLoc()).isNull();
    }

    @Test
    void orderTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        location.setOrder(orderBack);
        assertThat(location.getOrder()).isEqualTo(orderBack);
        assertThat(orderBack.getBillingLoc()).isEqualTo(location);

        location.order(null);
        assertThat(location.getOrder()).isNull();
        assertThat(orderBack.getBillingLoc()).isNull();
    }
}
