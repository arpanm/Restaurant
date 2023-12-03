package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ApplicationUserTestSamples.*;
import static com.restaurant.angikar.domain.CartItemTestSamples.*;
import static com.restaurant.angikar.domain.DiscountTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanItemTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanSettingsTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MealPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MealPlan.class);
        MealPlan mealPlan1 = getMealPlanSample1();
        MealPlan mealPlan2 = new MealPlan();
        assertThat(mealPlan1).isNotEqualTo(mealPlan2);

        mealPlan2.setId(mealPlan1.getId());
        assertThat(mealPlan1).isEqualTo(mealPlan2);

        mealPlan2 = getMealPlanSample2();
        assertThat(mealPlan1).isNotEqualTo(mealPlan2);
    }

    @Test
    void discountTest() throws Exception {
        MealPlan mealPlan = getMealPlanRandomSampleGenerator();
        Discount discountBack = getDiscountRandomSampleGenerator();

        mealPlan.setDiscount(discountBack);
        assertThat(mealPlan.getDiscount()).isEqualTo(discountBack);

        mealPlan.discount(null);
        assertThat(mealPlan.getDiscount()).isNull();
    }

    @Test
    void mealsTest() throws Exception {
        MealPlan mealPlan = getMealPlanRandomSampleGenerator();
        MealPlanItem mealPlanItemBack = getMealPlanItemRandomSampleGenerator();

        mealPlan.addMeals(mealPlanItemBack);
        assertThat(mealPlan.getMeals()).containsOnly(mealPlanItemBack);
        assertThat(mealPlanItemBack.getPlan()).isEqualTo(mealPlan);

        mealPlan.removeMeals(mealPlanItemBack);
        assertThat(mealPlan.getMeals()).doesNotContain(mealPlanItemBack);
        assertThat(mealPlanItemBack.getPlan()).isNull();

        mealPlan.meals(new HashSet<>(Set.of(mealPlanItemBack)));
        assertThat(mealPlan.getMeals()).containsOnly(mealPlanItemBack);
        assertThat(mealPlanItemBack.getPlan()).isEqualTo(mealPlan);

        mealPlan.setMeals(new HashSet<>());
        assertThat(mealPlan.getMeals()).doesNotContain(mealPlanItemBack);
        assertThat(mealPlanItemBack.getPlan()).isNull();
    }

    @Test
    void mealPlanSettingTest() throws Exception {
        MealPlan mealPlan = getMealPlanRandomSampleGenerator();
        MealPlanSettings mealPlanSettingsBack = getMealPlanSettingsRandomSampleGenerator();

        mealPlan.setMealPlanSetting(mealPlanSettingsBack);
        assertThat(mealPlan.getMealPlanSetting()).isEqualTo(mealPlanSettingsBack);

        mealPlan.mealPlanSetting(null);
        assertThat(mealPlan.getMealPlanSetting()).isNull();
    }

    @Test
    void usrTest() throws Exception {
        MealPlan mealPlan = getMealPlanRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        mealPlan.setUsr(applicationUserBack);
        assertThat(mealPlan.getUsr()).isEqualTo(applicationUserBack);

        mealPlan.usr(null);
        assertThat(mealPlan.getUsr()).isNull();
    }

    @Test
    void cartItemTest() throws Exception {
        MealPlan mealPlan = getMealPlanRandomSampleGenerator();
        CartItem cartItemBack = getCartItemRandomSampleGenerator();

        mealPlan.setCartItem(cartItemBack);
        assertThat(mealPlan.getCartItem()).isEqualTo(cartItemBack);
        assertThat(cartItemBack.getMeal()).isEqualTo(mealPlan);

        mealPlan.cartItem(null);
        assertThat(mealPlan.getCartItem()).isNull();
        assertThat(cartItemBack.getMeal()).isNull();
    }
}
