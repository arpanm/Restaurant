package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.MealPlanItemTestSamples.*;
import static com.restaurant.angikar.domain.SkipDateTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkipDateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkipDate.class);
        SkipDate skipDate1 = getSkipDateSample1();
        SkipDate skipDate2 = new SkipDate();
        assertThat(skipDate1).isNotEqualTo(skipDate2);

        skipDate2.setId(skipDate1.getId());
        assertThat(skipDate1).isEqualTo(skipDate2);

        skipDate2 = getSkipDateSample2();
        assertThat(skipDate1).isNotEqualTo(skipDate2);
    }

    @Test
    void mealPlanItemTest() throws Exception {
        SkipDate skipDate = getSkipDateRandomSampleGenerator();
        MealPlanItem mealPlanItemBack = getMealPlanItemRandomSampleGenerator();

        skipDate.setMealPlanItem(mealPlanItemBack);
        assertThat(skipDate.getMealPlanItem()).isEqualTo(mealPlanItemBack);

        skipDate.mealPlanItem(null);
        assertThat(skipDate.getMealPlanItem()).isNull();
    }
}
