package com.restaurant.angikar.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IngredienceMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredienceMasterDTO.class);
        IngredienceMasterDTO ingredienceMasterDTO1 = new IngredienceMasterDTO();
        ingredienceMasterDTO1.setId(1L);
        IngredienceMasterDTO ingredienceMasterDTO2 = new IngredienceMasterDTO();
        assertThat(ingredienceMasterDTO1).isNotEqualTo(ingredienceMasterDTO2);
        ingredienceMasterDTO2.setId(ingredienceMasterDTO1.getId());
        assertThat(ingredienceMasterDTO1).isEqualTo(ingredienceMasterDTO2);
        ingredienceMasterDTO2.setId(2L);
        assertThat(ingredienceMasterDTO1).isNotEqualTo(ingredienceMasterDTO2);
        ingredienceMasterDTO1.setId(null);
        assertThat(ingredienceMasterDTO1).isNotEqualTo(ingredienceMasterDTO2);
    }
}
