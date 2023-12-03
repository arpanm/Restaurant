package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.CalorieInfoTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanSettingsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalorieInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalorieInfo.class);
        CalorieInfo calorieInfo1 = getCalorieInfoSample1();
        CalorieInfo calorieInfo2 = new CalorieInfo();
        assertThat(calorieInfo1).isNotEqualTo(calorieInfo2);

        calorieInfo2.setId(calorieInfo1.getId());
        assertThat(calorieInfo1).isEqualTo(calorieInfo2);

        calorieInfo2 = getCalorieInfoSample2();
        assertThat(calorieInfo1).isNotEqualTo(calorieInfo2);
    }

    @Test
    void mealPlanSettingsTest() throws Exception {
        CalorieInfo calorieInfo = getCalorieInfoRandomSampleGenerator();
        MealPlanSettings mealPlanSettingsBack = getMealPlanSettingsRandomSampleGenerator();

        calorieInfo.setMealPlanSettings(mealPlanSettingsBack);
        assertThat(calorieInfo.getMealPlanSettings()).isEqualTo(mealPlanSettingsBack);
        assertThat(mealPlanSettingsBack.getCalorieInfo()).isEqualTo(calorieInfo);

        calorieInfo.mealPlanSettings(null);
        assertThat(calorieInfo.getMealPlanSettings()).isNull();
        assertThat(mealPlanSettingsBack.getCalorieInfo()).isNull();
    }
}
