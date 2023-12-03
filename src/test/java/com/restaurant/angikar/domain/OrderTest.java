package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.CouponTestSamples.*;
import static com.restaurant.angikar.domain.LocationTestSamples.*;
import static com.restaurant.angikar.domain.OrderItemTestSamples.*;
import static com.restaurant.angikar.domain.OrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = getOrderSample1();
        Order order2 = new Order();
        assertThat(order1).isNotEqualTo(order2);

        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);

        order2 = getOrderSample2();
        assertThat(order1).isNotEqualTo(order2);
    }

    @Test
    void billingLocTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        order.setBillingLoc(locationBack);
        assertThat(order.getBillingLoc()).isEqualTo(locationBack);

        order.billingLoc(null);
        assertThat(order.getBillingLoc()).isNull();
    }

    @Test
    void itemsTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        OrderItem orderItemBack = getOrderItemRandomSampleGenerator();

        order.addItems(orderItemBack);
        assertThat(order.getItems()).containsOnly(orderItemBack);
        assertThat(orderItemBack.getOrder()).isEqualTo(order);

        order.removeItems(orderItemBack);
        assertThat(order.getItems()).doesNotContain(orderItemBack);
        assertThat(orderItemBack.getOrder()).isNull();

        order.items(new HashSet<>(Set.of(orderItemBack)));
        assertThat(order.getItems()).containsOnly(orderItemBack);
        assertThat(orderItemBack.getOrder()).isEqualTo(order);

        order.setItems(new HashSet<>());
        assertThat(order.getItems()).doesNotContain(orderItemBack);
        assertThat(orderItemBack.getOrder()).isNull();
    }

    @Test
    void couponTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        Coupon couponBack = getCouponRandomSampleGenerator();

        order.setCoupon(couponBack);
        assertThat(order.getCoupon()).isEqualTo(couponBack);

        order.coupon(null);
        assertThat(order.getCoupon()).isNull();
    }
}
