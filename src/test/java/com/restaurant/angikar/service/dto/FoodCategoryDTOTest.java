package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoodCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodCategoryDTO.class);
        FoodCategoryDTO foodCategoryDTO1 = new FoodCategoryDTO();
        foodCategoryDTO1.setId(1L);
        FoodCategoryDTO foodCategoryDTO2 = new FoodCategoryDTO();
        assertThat(foodCategoryDTO1).isNotEqualTo(foodCategoryDTO2);
        foodCategoryDTO2.setId(foodCategoryDTO1.getId());
        assertThat(foodCategoryDTO1).isEqualTo(foodCategoryDTO2);
        foodCategoryDTO2.setId(2L);
        assertThat(foodCategoryDTO1).isNotEqualTo(foodCategoryDTO2);
        foodCategoryDTO1.setId(null);
        assertThat(foodCategoryDTO1).isNotEqualTo(foodCategoryDTO2);
    }
}
