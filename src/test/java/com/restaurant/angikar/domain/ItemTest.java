package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.FoodCategoryTestSamples.*;
import static com.restaurant.angikar.domain.IngredienceMasterTestSamples.*;
import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanItemTestSamples.*;
import static com.restaurant.angikar.domain.NutritionTestSamples.*;
import static com.restaurant.angikar.domain.PriceTestSamples.*;
import static com.restaurant.angikar.domain.QuantityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Item.class);
        Item item1 = getItemSample1();
        Item item2 = new Item();
        assertThat(item1).isNotEqualTo(item2);

        item2.setId(item1.getId());
        assertThat(item1).isEqualTo(item2);

        item2 = getItemSample2();
        assertThat(item1).isNotEqualTo(item2);
    }

    @Test
    void nutritionTest() throws Exception {
        Item item = getItemRandomSampleGenerator();
        Nutrition nutritionBack = getNutritionRandomSampleGenerator();

        item.setNutrition(nutritionBack);
        assertThat(item.getNutrition()).isEqualTo(nutritionBack);

        item.nutrition(null);
        assertThat(item.getNutrition()).isNull();
    }

    @Test
    void priceTest() throws Exception {
        Item item = getItemRandomSampleGenerator();
        Price priceBack = getPriceRandomSampleGenerator();

        item.setPrice(priceBack);
        assertThat(item.getPrice()).isEqualTo(priceBack);

        item.price(null);
        assertThat(item.getPrice()).isNull();
    }

    @Test
    void quantityTest() throws Exception {
        Item item = getItemRandomSampleGenerator();
        Quantity quantityBack = getQuantityRandomSampleGenerator();

        item.setQuantity(quantityBack);
        assertThat(item.getQuantity()).isEqualTo(quantityBack);

        item.quantity(null);
        assertThat(item.getQuantity()).isNull();
    }

    @Test
    void ingredienceTest() throws Exception {
        Item item = getItemRandomSampleGenerator();
        IngredienceMaster ingredienceMasterBack = getIngredienceMasterRandomSampleGenerator();

        item.addIngredience(ingredienceMasterBack);
        assertThat(item.getIngrediences()).containsOnly(ingredienceMasterBack);

        item.removeIngredience(ingredienceMasterBack);
        assertThat(item.getIngrediences()).doesNotContain(ingredienceMasterBack);

        item.ingrediences(new HashSet<>(Set.of(ingredienceMasterBack)));
        assertThat(item.getIngrediences()).containsOnly(ingredienceMasterBack);

        item.setIngrediences(new HashSet<>());
        assertThat(item.getIngrediences()).doesNotContain(ingredienceMasterBack);
    }

    @Test
    void categoryTest() throws Exception {
        Item item = getItemRandomSampleGenerator();
        FoodCategory foodCategoryBack = getFoodCategoryRandomSampleGenerator();

        item.setCategory(foodCategoryBack);
        assertThat(item.getCategory()).isEqualTo(foodCategoryBack);

        item.category(null);
        assertThat(item.getCategory()).isNull();
    }

    @Test
    void mealPlanItemsTest() throws Exception {
        Item item = getItemRandomSampleGenerator();
        MealPlanItem mealPlanItemBack = getMealPlanItemRandomSampleGenerator();

        item.addMealPlanItems(mealPlanItemBack);
        assertThat(item.getMealPlanItems()).containsOnly(mealPlanItemBack);
        assertThat(mealPlanItemBack.getItems()).containsOnly(item);

        item.removeMealPlanItems(mealPlanItemBack);
        assertThat(item.getMealPlanItems()).doesNotContain(mealPlanItemBack);
        assertThat(mealPlanItemBack.getItems()).doesNotContain(item);

        item.mealPlanItems(new HashSet<>(Set.of(mealPlanItemBack)));
        assertThat(item.getMealPlanItems()).containsOnly(mealPlanItemBack);
        assertThat(mealPlanItemBack.getItems()).containsOnly(item);

        item.setMealPlanItems(new HashSet<>());
        assertThat(item.getMealPlanItems()).doesNotContain(mealPlanItemBack);
        assertThat(mealPlanItemBack.getItems()).doesNotContain(item);
    }
}
