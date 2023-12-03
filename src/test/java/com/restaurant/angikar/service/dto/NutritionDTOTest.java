package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NutritionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NutritionDTO.class);
        NutritionDTO nutritionDTO1 = new NutritionDTO();
        nutritionDTO1.setId(1L);
        NutritionDTO nutritionDTO2 = new NutritionDTO();
        assertThat(nutritionDTO1).isNotEqualTo(nutritionDTO2);
        nutritionDTO2.setId(nutritionDTO1.getId());
        assertThat(nutritionDTO1).isEqualTo(nutritionDTO2);
        nutritionDTO2.setId(2L);
        assertThat(nutritionDTO1).isNotEqualTo(nutritionDTO2);
        nutritionDTO1.setId(null);
        assertThat(nutritionDTO1).isNotEqualTo(nutritionDTO2);
    }
}
