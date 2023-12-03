package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.AvoidTestSamples.*;
import static com.restaurant.angikar.domain.CalorieInfoTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanSettingsTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanTestSamples.*;
import static com.restaurant.angikar.domain.WeightInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MealPlanSettingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MealPlanSettings.class);
        MealPlanSettings mealPlanSettings1 = getMealPlanSettingsSample1();
        MealPlanSettings mealPlanSettings2 = new MealPlanSettings();
        assertThat(mealPlanSettings1).isNotEqualTo(mealPlanSettings2);

        mealPlanSettings2.setId(mealPlanSettings1.getId());
        assertThat(mealPlanSettings1).isEqualTo(mealPlanSettings2);

        mealPlanSettings2 = getMealPlanSettingsSample2();
        assertThat(mealPlanSettings1).isNotEqualTo(mealPlanSettings2);
    }

    @Test
    void weightInfoTest() throws Exception {
        MealPlanSettings mealPlanSettings = getMealPlanSettingsRandomSampleGenerator();
        WeightInfo weightInfoBack = getWeightInfoRandomSampleGenerator();

        mealPlanSettings.setWeightInfo(weightInfoBack);
        assertThat(mealPlanSettings.getWeightInfo()).isEqualTo(weightInfoBack);

        mealPlanSettings.weightInfo(null);
        assertThat(mealPlanSettings.getWeightInfo()).isNull();
    }

    @Test
    void calorieInfoTest() throws Exception {
        MealPlanSettings mealPlanSettings = getMealPlanSettingsRandomSampleGenerator();
        CalorieInfo calorieInfoBack = getCalorieInfoRandomSampleGenerator();

        mealPlanSettings.setCalorieInfo(calorieInfoBack);
        assertThat(mealPlanSettings.getCalorieInfo()).isEqualTo(calorieInfoBack);

        mealPlanSettings.calorieInfo(null);
        assertThat(mealPlanSettings.getCalorieInfo()).isNull();
    }

    @Test
    void plansTest() throws Exception {
        MealPlanSettings mealPlanSettings = getMealPlanSettingsRandomSampleGenerator();
        MealPlan mealPlanBack = getMealPlanRandomSampleGenerator();

        mealPlanSettings.addPlans(mealPlanBack);
        assertThat(mealPlanSettings.getPlans()).containsOnly(mealPlanBack);
        assertThat(mealPlanBack.getMealPlanSetting()).isEqualTo(mealPlanSettings);

        mealPlanSettings.removePlans(mealPlanBack);
        assertThat(mealPlanSettings.getPlans()).doesNotContain(mealPlanBack);
        assertThat(mealPlanBack.getMealPlanSetting()).isNull();

        mealPlanSettings.plans(new HashSet<>(Set.of(mealPlanBack)));
        assertThat(mealPlanSettings.getPlans()).containsOnly(mealPlanBack);
        assertThat(mealPlanBack.getMealPlanSetting()).isEqualTo(mealPlanSettings);

        mealPlanSettings.setPlans(new HashSet<>());
        assertThat(mealPlanSettings.getPlans()).doesNotContain(mealPlanBack);
        assertThat(mealPlanBack.getMealPlanSetting()).isNull();
    }

    @Test
    void avoidListTest() throws Exception {
        MealPlanSettings mealPlanSettings = getMealPlanSettingsRandomSampleGenerator();
        Avoid avoidBack = getAvoidRandomSampleGenerator();

        mealPlanSettings.addAvoidList(avoidBack);
        assertThat(mealPlanSettings.getAvoidLists()).containsOnly(avoidBack);

        mealPlanSettings.removeAvoidList(avoidBack);
        assertThat(mealPlanSettings.getAvoidLists()).doesNotContain(avoidBack);

        mealPlanSettings.avoidLists(new HashSet<>(Set.of(avoidBack)));
        assertThat(mealPlanSettings.getAvoidLists()).containsOnly(avoidBack);

        mealPlanSettings.setAvoidLists(new HashSet<>());
        assertThat(mealPlanSettings.getAvoidLists()).doesNotContain(avoidBack);
    }
}
