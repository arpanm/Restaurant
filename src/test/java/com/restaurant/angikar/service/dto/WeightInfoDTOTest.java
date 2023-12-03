package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WeightInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeightInfoDTO.class);
        WeightInfoDTO weightInfoDTO1 = new WeightInfoDTO();
        weightInfoDTO1.setId(1L);
        WeightInfoDTO weightInfoDTO2 = new WeightInfoDTO();
        assertThat(weightInfoDTO1).isNotEqualTo(weightInfoDTO2);
        weightInfoDTO2.setId(weightInfoDTO1.getId());
        assertThat(weightInfoDTO1).isEqualTo(weightInfoDTO2);
        weightInfoDTO2.setId(2L);
        assertThat(weightInfoDTO1).isNotEqualTo(weightInfoDTO2);
        weightInfoDTO1.setId(null);
        assertThat(weightInfoDTO1).isNotEqualTo(weightInfoDTO2);
    }
}
