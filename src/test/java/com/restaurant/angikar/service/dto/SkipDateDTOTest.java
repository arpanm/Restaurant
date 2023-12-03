package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkipDateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkipDateDTO.class);
        SkipDateDTO skipDateDTO1 = new SkipDateDTO();
        skipDateDTO1.setId(1L);
        SkipDateDTO skipDateDTO2 = new SkipDateDTO();
        assertThat(skipDateDTO1).isNotEqualTo(skipDateDTO2);
        skipDateDTO2.setId(skipDateDTO1.getId());
        assertThat(skipDateDTO1).isEqualTo(skipDateDTO2);
        skipDateDTO2.setId(2L);
        assertThat(skipDateDTO1).isNotEqualTo(skipDateDTO2);
        skipDateDTO1.setId(null);
        assertThat(skipDateDTO1).isNotEqualTo(skipDateDTO2);
    }
}
