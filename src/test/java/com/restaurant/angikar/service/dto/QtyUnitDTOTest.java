package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QtyUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QtyUnitDTO.class);
        QtyUnitDTO qtyUnitDTO1 = new QtyUnitDTO();
        qtyUnitDTO1.setId(1L);
        QtyUnitDTO qtyUnitDTO2 = new QtyUnitDTO();
        assertThat(qtyUnitDTO1).isNotEqualTo(qtyUnitDTO2);
        qtyUnitDTO2.setId(qtyUnitDTO1.getId());
        assertThat(qtyUnitDTO1).isEqualTo(qtyUnitDTO2);
        qtyUnitDTO2.setId(2L);
        assertThat(qtyUnitDTO1).isNotEqualTo(qtyUnitDTO2);
        qtyUnitDTO1.setId(null);
        assertThat(qtyUnitDTO1).isNotEqualTo(qtyUnitDTO2);
    }
}
