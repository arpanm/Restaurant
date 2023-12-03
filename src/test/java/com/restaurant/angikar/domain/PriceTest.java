package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static com.restaurant.angikar.domain.PriceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PriceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Price.class);
        Price price1 = getPriceSample1();
        Price price2 = new Price();
        assertThat(price1).isNotEqualTo(price2);

        price2.setId(price1.getId());
        assertThat(price1).isEqualTo(price2);

        price2 = getPriceSample2();
        assertThat(price1).isNotEqualTo(price2);
    }

    @Test
    void itemTest() throws Exception {
        Price price = getPriceRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        price.setItem(itemBack);
        assertThat(price.getItem()).isEqualTo(itemBack);
        assertThat(itemBack.getPrice()).isEqualTo(price);

        price.item(null);
        assertThat(price.getItem()).isNull();
        assertThat(itemBack.getPrice()).isNull();
    }
}
