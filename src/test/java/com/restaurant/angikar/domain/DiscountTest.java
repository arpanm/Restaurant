package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.DiscountTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiscountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discount.class);
        Discount discount1 = getDiscountSample1();
        Discount discount2 = new Discount();
        assertThat(discount1).isNotEqualTo(discount2);

        discount2.setId(discount1.getId());
        assertThat(discount1).isEqualTo(discount2);

        discount2 = getDiscountSample2();
        assertThat(discount1).isNotEqualTo(discount2);
    }

    @Test
    void mealPlanTest() throws Exception {
        Discount discount = getDiscountRandomSampleGenerator();
        MealPlan mealPlanBack = getMealPlanRandomSampleGenerator();

        discount.setMealPlan(mealPlanBack);
        assertThat(discount.getMealPlan()).isEqualTo(mealPlanBack);
        assertThat(mealPlanBack.getDiscount()).isEqualTo(discount);

        discount.mealPlan(null);
        assertThat(discount.getMealPlan()).isNull();
        assertThat(mealPlanBack.getDiscount()).isNull();
    }
}
