package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.IngredienceMasterTestSamples.*;
import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class IngredienceMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredienceMaster.class);
        IngredienceMaster ingredienceMaster1 = getIngredienceMasterSample1();
        IngredienceMaster ingredienceMaster2 = new IngredienceMaster();
        assertThat(ingredienceMaster1).isNotEqualTo(ingredienceMaster2);

        ingredienceMaster2.setId(ingredienceMaster1.getId());
        assertThat(ingredienceMaster1).isEqualTo(ingredienceMaster2);

        ingredienceMaster2 = getIngredienceMasterSample2();
        assertThat(ingredienceMaster1).isNotEqualTo(ingredienceMaster2);
    }

    @Test
    void itemsTest() throws Exception {
        IngredienceMaster ingredienceMaster = getIngredienceMasterRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        ingredienceMaster.addItems(itemBack);
        assertThat(ingredienceMaster.getItems()).containsOnly(itemBack);
        assertThat(itemBack.getIngrediences()).containsOnly(ingredienceMaster);

        ingredienceMaster.removeItems(itemBack);
        assertThat(ingredienceMaster.getItems()).doesNotContain(itemBack);
        assertThat(itemBack.getIngrediences()).doesNotContain(ingredienceMaster);

        ingredienceMaster.items(new HashSet<>(Set.of(itemBack)));
        assertThat(ingredienceMaster.getItems()).containsOnly(itemBack);
        assertThat(itemBack.getIngrediences()).containsOnly(ingredienceMaster);

        ingredienceMaster.setItems(new HashSet<>());
        assertThat(ingredienceMaster.getItems()).doesNotContain(itemBack);
        assertThat(itemBack.getIngrediences()).doesNotContain(ingredienceMaster);
    }
}
