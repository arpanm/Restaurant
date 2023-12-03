package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.MealPlanSettingsTestSamples.*;
import static com.restaurant.angikar.domain.WeightInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WeightInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeightInfo.class);
        WeightInfo weightInfo1 = getWeightInfoSample1();
        WeightInfo weightInfo2 = new WeightInfo();
        assertThat(weightInfo1).isNotEqualTo(weightInfo2);

        weightInfo2.setId(weightInfo1.getId());
        assertThat(weightInfo1).isEqualTo(weightInfo2);

        weightInfo2 = getWeightInfoSample2();
        assertThat(weightInfo1).isNotEqualTo(weightInfo2);
    }

    @Test
    void mealPlanSettingsTest() throws Exception {
        WeightInfo weightInfo = getWeightInfoRandomSampleGenerator();
        MealPlanSettings mealPlanSettingsBack = getMealPlanSettingsRandomSampleGenerator();

        weightInfo.setMealPlanSettings(mealPlanSettingsBack);
        assertThat(weightInfo.getMealPlanSettings()).isEqualTo(mealPlanSettingsBack);
        assertThat(mealPlanSettingsBack.getWeightInfo()).isEqualTo(weightInfo);

        weightInfo.mealPlanSettings(null);
        assertThat(weightInfo.getMealPlanSettings()).isNull();
        assertThat(mealPlanSettingsBack.getWeightInfo()).isNull();
    }
}
