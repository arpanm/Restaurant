package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuantityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuantityDTO.class);
        QuantityDTO quantityDTO1 = new QuantityDTO();
        quantityDTO1.setId(1L);
        QuantityDTO quantityDTO2 = new QuantityDTO();
        assertThat(quantityDTO1).isNotEqualTo(quantityDTO2);
        quantityDTO2.setId(quantityDTO1.getId());
        assertThat(quantityDTO1).isEqualTo(quantityDTO2);
        quantityDTO2.setId(2L);
        assertThat(quantityDTO1).isNotEqualTo(quantityDTO2);
        quantityDTO1.setId(null);
        assertThat(quantityDTO1).isNotEqualTo(quantityDTO2);
    }
}
