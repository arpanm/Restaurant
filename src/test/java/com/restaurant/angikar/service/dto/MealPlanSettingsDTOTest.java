package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MealPlanSettingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MealPlanSettingsDTO.class);
        MealPlanSettingsDTO mealPlanSettingsDTO1 = new MealPlanSettingsDTO();
        mealPlanSettingsDTO1.setId(1L);
        MealPlanSettingsDTO mealPlanSettingsDTO2 = new MealPlanSettingsDTO();
        assertThat(mealPlanSettingsDTO1).isNotEqualTo(mealPlanSettingsDTO2);
        mealPlanSettingsDTO2.setId(mealPlanSettingsDTO1.getId());
        assertThat(mealPlanSettingsDTO1).isEqualTo(mealPlanSettingsDTO2);
        mealPlanSettingsDTO2.setId(2L);
        assertThat(mealPlanSettingsDTO1).isNotEqualTo(mealPlanSettingsDTO2);
        mealPlanSettingsDTO1.setId(null);
        assertThat(mealPlanSettingsDTO1).isNotEqualTo(mealPlanSettingsDTO2);
    }
}
