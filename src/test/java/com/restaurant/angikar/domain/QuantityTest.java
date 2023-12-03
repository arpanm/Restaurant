package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static com.restaurant.angikar.domain.QtyUnitTestSamples.*;
import static com.restaurant.angikar.domain.QuantityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quantity.class);
        Quantity quantity1 = getQuantitySample1();
        Quantity quantity2 = new Quantity();
        assertThat(quantity1).isNotEqualTo(quantity2);

        quantity2.setId(quantity1.getId());
        assertThat(quantity1).isEqualTo(quantity2);

        quantity2 = getQuantitySample2();
        assertThat(quantity1).isNotEqualTo(quantity2);
    }

    @Test
    void qtyUnitTest() throws Exception {
        Quantity quantity = getQuantityRandomSampleGenerator();
        QtyUnit qtyUnitBack = getQtyUnitRandomSampleGenerator();

        quantity.setQtyUnit(qtyUnitBack);
        assertThat(quantity.getQtyUnit()).isEqualTo(qtyUnitBack);

        quantity.qtyUnit(null);
        assertThat(quantity.getQtyUnit()).isNull();
    }

    @Test
    void itemTest() throws Exception {
        Quantity quantity = getQuantityRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        quantity.setItem(itemBack);
        assertThat(quantity.getItem()).isEqualTo(itemBack);
        assertThat(itemBack.getQuantity()).isEqualTo(quantity);

        quantity.item(null);
        assertThat(quantity.getItem()).isNull();
        assertThat(itemBack.getQuantity()).isNull();
    }
}
