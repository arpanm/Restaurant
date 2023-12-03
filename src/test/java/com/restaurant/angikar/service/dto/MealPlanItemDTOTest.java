package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MealPlanItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MealPlanItemDTO.class);
        MealPlanItemDTO mealPlanItemDTO1 = new MealPlanItemDTO();
        mealPlanItemDTO1.setId(1L);
        MealPlanItemDTO mealPlanItemDTO2 = new MealPlanItemDTO();
        assertThat(mealPlanItemDTO1).isNotEqualTo(mealPlanItemDTO2);
        mealPlanItemDTO2.setId(mealPlanItemDTO1.getId());
        assertThat(mealPlanItemDTO1).isEqualTo(mealPlanItemDTO2);
        mealPlanItemDTO2.setId(2L);
        assertThat(mealPlanItemDTO1).isNotEqualTo(mealPlanItemDTO2);
        mealPlanItemDTO1.setId(null);
        assertThat(mealPlanItemDTO1).isNotEqualTo(mealPlanItemDTO2);
    }
}
