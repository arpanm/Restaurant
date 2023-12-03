package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvoidDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvoidDTO.class);
        AvoidDTO avoidDTO1 = new AvoidDTO();
        avoidDTO1.setId(1L);
        AvoidDTO avoidDTO2 = new AvoidDTO();
        assertThat(avoidDTO1).isNotEqualTo(avoidDTO2);
        avoidDTO2.setId(avoidDTO1.getId());
        assertThat(avoidDTO1).isEqualTo(avoidDTO2);
        avoidDTO2.setId(2L);
        assertThat(avoidDTO1).isNotEqualTo(avoidDTO2);
        avoidDTO1.setId(null);
        assertThat(avoidDTO1).isNotEqualTo(avoidDTO2);
    }
}
