package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ItemPincodeMappingTestSamples.*;
import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static com.restaurant.angikar.domain.RestaurantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemPincodeMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPincodeMapping.class);
        ItemPincodeMapping itemPincodeMapping1 = getItemPincodeMappingSample1();
        ItemPincodeMapping itemPincodeMapping2 = new ItemPincodeMapping();
        assertThat(itemPincodeMapping1).isNotEqualTo(itemPincodeMapping2);

        itemPincodeMapping2.setId(itemPincodeMapping1.getId());
        assertThat(itemPincodeMapping1).isEqualTo(itemPincodeMapping2);

        itemPincodeMapping2 = getItemPincodeMappingSample2();
        assertThat(itemPincodeMapping1).isNotEqualTo(itemPincodeMapping2);
    }

    @Test
    void itemTest() throws Exception {
        ItemPincodeMapping itemPincodeMapping = getItemPincodeMappingRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        itemPincodeMapping.setItem(itemBack);
        assertThat(itemPincodeMapping.getItem()).isEqualTo(itemBack);

        itemPincodeMapping.item(null);
        assertThat(itemPincodeMapping.getItem()).isNull();
    }

    @Test
    void restaurantTest() throws Exception {
        ItemPincodeMapping itemPincodeMapping = getItemPincodeMappingRandomSampleGenerator();
        Restaurant restaurantBack = getRestaurantRandomSampleGenerator();

        itemPincodeMapping.setRestaurant(restaurantBack);
        assertThat(itemPincodeMapping.getRestaurant()).isEqualTo(restaurantBack);

        itemPincodeMapping.restaurant(null);
        assertThat(itemPincodeMapping.getRestaurant()).isNull();
    }
}
