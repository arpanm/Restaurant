package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static com.restaurant.angikar.domain.LocationTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanItemTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanTestSamples.*;
import static com.restaurant.angikar.domain.SkipDateTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MealPlanItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MealPlanItem.class);
        MealPlanItem mealPlanItem1 = getMealPlanItemSample1();
        MealPlanItem mealPlanItem2 = new MealPlanItem();
        assertThat(mealPlanItem1).isNotEqualTo(mealPlanItem2);

        mealPlanItem2.setId(mealPlanItem1.getId());
        assertThat(mealPlanItem1).isEqualTo(mealPlanItem2);

        mealPlanItem2 = getMealPlanItemSample2();
        assertThat(mealPlanItem1).isNotEqualTo(mealPlanItem2);
    }

    @Test
    void deliveryLocationTest() throws Exception {
        MealPlanItem mealPlanItem = getMealPlanItemRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        mealPlanItem.setDeliveryLocation(locationBack);
        assertThat(mealPlanItem.getDeliveryLocation()).isEqualTo(locationBack);

        mealPlanItem.deliveryLocation(null);
        assertThat(mealPlanItem.getDeliveryLocation()).isNull();
    }

    @Test
    void skipDatesTest() throws Exception {
        MealPlanItem mealPlanItem = getMealPlanItemRandomSampleGenerator();
        SkipDate skipDateBack = getSkipDateRandomSampleGenerator();

        mealPlanItem.addSkipDates(skipDateBack);
        assertThat(mealPlanItem.getSkipDates()).containsOnly(skipDateBack);
        assertThat(skipDateBack.getMealPlanItem()).isEqualTo(mealPlanItem);

        mealPlanItem.removeSkipDates(skipDateBack);
        assertThat(mealPlanItem.getSkipDates()).doesNotContain(skipDateBack);
        assertThat(skipDateBack.getMealPlanItem()).isNull();

        mealPlanItem.skipDates(new HashSet<>(Set.of(skipDateBack)));
        assertThat(mealPlanItem.getSkipDates()).containsOnly(skipDateBack);
        assertThat(skipDateBack.getMealPlanItem()).isEqualTo(mealPlanItem);

        mealPlanItem.setSkipDates(new HashSet<>());
        assertThat(mealPlanItem.getSkipDates()).doesNotContain(skipDateBack);
        assertThat(skipDateBack.getMealPlanItem()).isNull();
    }

    @Test
    void itemsTest() throws Exception {
        MealPlanItem mealPlanItem = getMealPlanItemRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        mealPlanItem.addItems(itemBack);
        assertThat(mealPlanItem.getItems()).containsOnly(itemBack);

        mealPlanItem.removeItems(itemBack);
        assertThat(mealPlanItem.getItems()).doesNotContain(itemBack);

        mealPlanItem.items(new HashSet<>(Set.of(itemBack)));
        assertThat(mealPlanItem.getItems()).containsOnly(itemBack);

        mealPlanItem.setItems(new HashSet<>());
        assertThat(mealPlanItem.getItems()).doesNotContain(itemBack);
    }

    @Test
    void planTest() throws Exception {
        MealPlanItem mealPlanItem = getMealPlanItemRandomSampleGenerator();
        MealPlan mealPlanBack = getMealPlanRandomSampleGenerator();

        mealPlanItem.setPlan(mealPlanBack);
        assertThat(mealPlanItem.getPlan()).isEqualTo(mealPlanBack);

        mealPlanItem.plan(null);
        assertThat(mealPlanItem.getPlan()).isNull();
    }
}
