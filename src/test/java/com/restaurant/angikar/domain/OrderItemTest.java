package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static com.restaurant.angikar.domain.LocationTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanTestSamples.*;
import static com.restaurant.angikar.domain.OrderItemTestSamples.*;
import static com.restaurant.angikar.domain.OrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItem.class);
        OrderItem orderItem1 = getOrderItemSample1();
        OrderItem orderItem2 = new OrderItem();
        assertThat(orderItem1).isNotEqualTo(orderItem2);

        orderItem2.setId(orderItem1.getId());
        assertThat(orderItem1).isEqualTo(orderItem2);

        orderItem2 = getOrderItemSample2();
        assertThat(orderItem1).isNotEqualTo(orderItem2);
    }

    @Test
    void itemTest() throws Exception {
        OrderItem orderItem = getOrderItemRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        orderItem.setItem(itemBack);
        assertThat(orderItem.getItem()).isEqualTo(itemBack);

        orderItem.item(null);
        assertThat(orderItem.getItem()).isNull();
    }

    @Test
    void mealTest() throws Exception {
        OrderItem orderItem = getOrderItemRandomSampleGenerator();
        MealPlan mealPlanBack = getMealPlanRandomSampleGenerator();

        orderItem.setMeal(mealPlanBack);
        assertThat(orderItem.getMeal()).isEqualTo(mealPlanBack);

        orderItem.meal(null);
        assertThat(orderItem.getMeal()).isNull();
    }

    @Test
    void deliveryLocTest() throws Exception {
        OrderItem orderItem = getOrderItemRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        orderItem.setDeliveryLoc(locationBack);
        assertThat(orderItem.getDeliveryLoc()).isEqualTo(locationBack);

        orderItem.deliveryLoc(null);
        assertThat(orderItem.getDeliveryLoc()).isNull();
    }

    @Test
    void orderTest() throws Exception {
        OrderItem orderItem = getOrderItemRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        orderItem.setOrder(orderBack);
        assertThat(orderItem.getOrder()).isEqualTo(orderBack);

        orderItem.order(null);
        assertThat(orderItem.getOrder()).isNull();
    }
}
