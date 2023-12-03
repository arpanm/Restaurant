package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ApplicationUserTestSamples.*;
import static com.restaurant.angikar.domain.CartTestSamples.*;
import static com.restaurant.angikar.domain.LocationTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanTestSamples.*;
import static com.restaurant.angikar.domain.OtpTestSamples.*;
import static com.restaurant.angikar.domain.RestaurantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUser.class);
        ApplicationUser applicationUser1 = getApplicationUserSample1();
        ApplicationUser applicationUser2 = new ApplicationUser();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);

        applicationUser2.setId(applicationUser1.getId());
        assertThat(applicationUser1).isEqualTo(applicationUser2);

        applicationUser2 = getApplicationUserSample2();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);
    }

    @Test
    void addressesTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        applicationUser.addAddresses(locationBack);
        assertThat(applicationUser.getAddresses()).containsOnly(locationBack);
        assertThat(locationBack.getUsr()).isEqualTo(applicationUser);

        applicationUser.removeAddresses(locationBack);
        assertThat(applicationUser.getAddresses()).doesNotContain(locationBack);
        assertThat(locationBack.getUsr()).isNull();

        applicationUser.addresses(new HashSet<>(Set.of(locationBack)));
        assertThat(applicationUser.getAddresses()).containsOnly(locationBack);
        assertThat(locationBack.getUsr()).isEqualTo(applicationUser);

        applicationUser.setAddresses(new HashSet<>());
        assertThat(applicationUser.getAddresses()).doesNotContain(locationBack);
        assertThat(locationBack.getUsr()).isNull();
    }

    @Test
    void otpTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Otp otpBack = getOtpRandomSampleGenerator();

        applicationUser.addOtp(otpBack);
        assertThat(applicationUser.getOtps()).containsOnly(otpBack);
        assertThat(otpBack.getUsr()).isEqualTo(applicationUser);

        applicationUser.removeOtp(otpBack);
        assertThat(applicationUser.getOtps()).doesNotContain(otpBack);
        assertThat(otpBack.getUsr()).isNull();

        applicationUser.otps(new HashSet<>(Set.of(otpBack)));
        assertThat(applicationUser.getOtps()).containsOnly(otpBack);
        assertThat(otpBack.getUsr()).isEqualTo(applicationUser);

        applicationUser.setOtps(new HashSet<>());
        assertThat(applicationUser.getOtps()).doesNotContain(otpBack);
        assertThat(otpBack.getUsr()).isNull();
    }

    @Test
    void mealPlanTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        MealPlan mealPlanBack = getMealPlanRandomSampleGenerator();

        applicationUser.addMealPlan(mealPlanBack);
        assertThat(applicationUser.getMealPlans()).containsOnly(mealPlanBack);
        assertThat(mealPlanBack.getUsr()).isEqualTo(applicationUser);

        applicationUser.removeMealPlan(mealPlanBack);
        assertThat(applicationUser.getMealPlans()).doesNotContain(mealPlanBack);
        assertThat(mealPlanBack.getUsr()).isNull();

        applicationUser.mealPlans(new HashSet<>(Set.of(mealPlanBack)));
        assertThat(applicationUser.getMealPlans()).containsOnly(mealPlanBack);
        assertThat(mealPlanBack.getUsr()).isEqualTo(applicationUser);

        applicationUser.setMealPlans(new HashSet<>());
        assertThat(applicationUser.getMealPlans()).doesNotContain(mealPlanBack);
        assertThat(mealPlanBack.getUsr()).isNull();
    }

    @Test
    void cartTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Cart cartBack = getCartRandomSampleGenerator();

        applicationUser.addCart(cartBack);
        assertThat(applicationUser.getCarts()).containsOnly(cartBack);
        assertThat(cartBack.getUsr()).isEqualTo(applicationUser);

        applicationUser.removeCart(cartBack);
        assertThat(applicationUser.getCarts()).doesNotContain(cartBack);
        assertThat(cartBack.getUsr()).isNull();

        applicationUser.carts(new HashSet<>(Set.of(cartBack)));
        assertThat(applicationUser.getCarts()).containsOnly(cartBack);
        assertThat(cartBack.getUsr()).isEqualTo(applicationUser);

        applicationUser.setCarts(new HashSet<>());
        assertThat(applicationUser.getCarts()).doesNotContain(cartBack);
        assertThat(cartBack.getUsr()).isNull();
    }

    @Test
    void restaurantTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Restaurant restaurantBack = getRestaurantRandomSampleGenerator();

        applicationUser.setRestaurant(restaurantBack);
        assertThat(applicationUser.getRestaurant()).isEqualTo(restaurantBack);

        applicationUser.restaurant(null);
        assertThat(applicationUser.getRestaurant()).isNull();
    }
}
