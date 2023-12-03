package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalorieInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalorieInfoDTO.class);
        CalorieInfoDTO calorieInfoDTO1 = new CalorieInfoDTO();
        calorieInfoDTO1.setId(1L);
        CalorieInfoDTO calorieInfoDTO2 = new CalorieInfoDTO();
        assertThat(calorieInfoDTO1).isNotEqualTo(calorieInfoDTO2);
        calorieInfoDTO2.setId(calorieInfoDTO1.getId());
        assertThat(calorieInfoDTO1).isEqualTo(calorieInfoDTO2);
        calorieInfoDTO2.setId(2L);
        assertThat(calorieInfoDTO1).isNotEqualTo(calorieInfoDTO2);
        calorieInfoDTO1.setId(null);
        assertThat(calorieInfoDTO1).isNotEqualTo(calorieInfoDTO2);
    }
}
