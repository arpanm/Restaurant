package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.AvoidTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanSettingsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AvoidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avoid.class);
        Avoid avoid1 = getAvoidSample1();
        Avoid avoid2 = new Avoid();
        assertThat(avoid1).isNotEqualTo(avoid2);

        avoid2.setId(avoid1.getId());
        assertThat(avoid1).isEqualTo(avoid2);

        avoid2 = getAvoidSample2();
        assertThat(avoid1).isNotEqualTo(avoid2);
    }

    @Test
    void mealPlanSettingsTest() throws Exception {
        Avoid avoid = getAvoidRandomSampleGenerator();
        MealPlanSettings mealPlanSettingsBack = getMealPlanSettingsRandomSampleGenerator();

        avoid.addMealPlanSettings(mealPlanSettingsBack);
        assertThat(avoid.getMealPlanSettings()).containsOnly(mealPlanSettingsBack);
        assertThat(mealPlanSettingsBack.getAvoidLists()).containsOnly(avoid);

        avoid.removeMealPlanSettings(mealPlanSettingsBack);
        assertThat(avoid.getMealPlanSettings()).doesNotContain(mealPlanSettingsBack);
        assertThat(mealPlanSettingsBack.getAvoidLists()).doesNotContain(avoid);

        avoid.mealPlanSettings(new HashSet<>(Set.of(mealPlanSettingsBack)));
        assertThat(avoid.getMealPlanSettings()).containsOnly(mealPlanSettingsBack);
        assertThat(mealPlanSettingsBack.getAvoidLists()).containsOnly(avoid);

        avoid.setMealPlanSettings(new HashSet<>());
        assertThat(avoid.getMealPlanSettings()).doesNotContain(mealPlanSettingsBack);
        assertThat(mealPlanSettingsBack.getAvoidLists()).doesNotContain(avoid);
    }
}
