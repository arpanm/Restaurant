package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PincodeMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PincodeMasterDTO.class);
        PincodeMasterDTO pincodeMasterDTO1 = new PincodeMasterDTO();
        pincodeMasterDTO1.setId(1L);
        PincodeMasterDTO pincodeMasterDTO2 = new PincodeMasterDTO();
        assertThat(pincodeMasterDTO1).isNotEqualTo(pincodeMasterDTO2);
        pincodeMasterDTO2.setId(pincodeMasterDTO1.getId());
        assertThat(pincodeMasterDTO1).isEqualTo(pincodeMasterDTO2);
        pincodeMasterDTO2.setId(2L);
        assertThat(pincodeMasterDTO1).isNotEqualTo(pincodeMasterDTO2);
        pincodeMasterDTO1.setId(null);
        assertThat(pincodeMasterDTO1).isNotEqualTo(pincodeMasterDTO2);
    }
}
