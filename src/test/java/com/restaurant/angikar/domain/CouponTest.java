package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.CartTestSamples.*;
import static com.restaurant.angikar.domain.CouponTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coupon.class);
        Coupon coupon1 = getCouponSample1();
        Coupon coupon2 = new Coupon();
        assertThat(coupon1).isNotEqualTo(coupon2);

        coupon2.setId(coupon1.getId());
        assertThat(coupon1).isEqualTo(coupon2);

        coupon2 = getCouponSample2();
        assertThat(coupon1).isNotEqualTo(coupon2);
    }

    @Test
    void cartTest() throws Exception {
        Coupon coupon = getCouponRandomSampleGenerator();
        Cart cartBack = getCartRandomSampleGenerator();

        coupon.setCart(cartBack);
        assertThat(coupon.getCart()).isEqualTo(cartBack);
        assertThat(cartBack.getCoupon()).isEqualTo(coupon);

        coupon.cart(null);
        assertThat(coupon.getCart()).isNull();
        assertThat(cartBack.getCoupon()).isNull();
    }
}
