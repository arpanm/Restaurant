package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemPincodeMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPincodeMappingDTO.class);
        ItemPincodeMappingDTO itemPincodeMappingDTO1 = new ItemPincodeMappingDTO();
        itemPincodeMappingDTO1.setId(1L);
        ItemPincodeMappingDTO itemPincodeMappingDTO2 = new ItemPincodeMappingDTO();
        assertThat(itemPincodeMappingDTO1).isNotEqualTo(itemPincodeMappingDTO2);
        itemPincodeMappingDTO2.setId(itemPincodeMappingDTO1.getId());
        assertThat(itemPincodeMappingDTO1).isEqualTo(itemPincodeMappingDTO2);
        itemPincodeMappingDTO2.setId(2L);
        assertThat(itemPincodeMappingDTO1).isNotEqualTo(itemPincodeMappingDTO2);
        itemPincodeMappingDTO1.setId(null);
        assertThat(itemPincodeMappingDTO1).isNotEqualTo(itemPincodeMappingDTO2);
    }
}
