package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.FoodCategoryTestSamples.*;
import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FoodCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodCategory.class);
        FoodCategory foodCategory1 = getFoodCategorySample1();
        FoodCategory foodCategory2 = new FoodCategory();
        assertThat(foodCategory1).isNotEqualTo(foodCategory2);

        foodCategory2.setId(foodCategory1.getId());
        assertThat(foodCategory1).isEqualTo(foodCategory2);

        foodCategory2 = getFoodCategorySample2();
        assertThat(foodCategory1).isNotEqualTo(foodCategory2);
    }

    @Test
    void itemTest() throws Exception {
        FoodCategory foodCategory = getFoodCategoryRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        foodCategory.addItem(itemBack);
        assertThat(foodCategory.getItems()).containsOnly(itemBack);
        assertThat(itemBack.getCategory()).isEqualTo(foodCategory);

        foodCategory.removeItem(itemBack);
        assertThat(foodCategory.getItems()).doesNotContain(itemBack);
        assertThat(itemBack.getCategory()).isNull();

        foodCategory.items(new HashSet<>(Set.of(itemBack)));
        assertThat(foodCategory.getItems()).containsOnly(itemBack);
        assertThat(itemBack.getCategory()).isEqualTo(foodCategory);

        foodCategory.setItems(new HashSet<>());
        assertThat(foodCategory.getItems()).doesNotContain(itemBack);
        assertThat(itemBack.getCategory()).isNull();
    }
}
