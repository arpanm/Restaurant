package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static com.restaurant.angikar.domain.NutritionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NutritionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nutrition.class);
        Nutrition nutrition1 = getNutritionSample1();
        Nutrition nutrition2 = new Nutrition();
        assertThat(nutrition1).isNotEqualTo(nutrition2);

        nutrition2.setId(nutrition1.getId());
        assertThat(nutrition1).isEqualTo(nutrition2);

        nutrition2 = getNutritionSample2();
        assertThat(nutrition1).isNotEqualTo(nutrition2);
    }

    @Test
    void itemTest() throws Exception {
        Nutrition nutrition = getNutritionRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        nutrition.setItem(itemBack);
        assertThat(nutrition.getItem()).isEqualTo(itemBack);
        assertThat(itemBack.getNutrition()).isEqualTo(nutrition);

        nutrition.item(null);
        assertThat(nutrition.getItem()).isNull();
        assertThat(itemBack.getNutrition()).isNull();
    }
}
