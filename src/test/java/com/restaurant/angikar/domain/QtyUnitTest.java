package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.QtyUnitTestSamples.*;
import static com.restaurant.angikar.domain.QuantityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QtyUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QtyUnit.class);
        QtyUnit qtyUnit1 = getQtyUnitSample1();
        QtyUnit qtyUnit2 = new QtyUnit();
        assertThat(qtyUnit1).isNotEqualTo(qtyUnit2);

        qtyUnit2.setId(qtyUnit1.getId());
        assertThat(qtyUnit1).isEqualTo(qtyUnit2);

        qtyUnit2 = getQtyUnitSample2();
        assertThat(qtyUnit1).isNotEqualTo(qtyUnit2);
    }

    @Test
    void quantityTest() throws Exception {
        QtyUnit qtyUnit = getQtyUnitRandomSampleGenerator();
        Quantity quantityBack = getQuantityRandomSampleGenerator();

        qtyUnit.setQuantity(quantityBack);
        assertThat(qtyUnit.getQuantity()).isEqualTo(quantityBack);
        assertThat(quantityBack.getQtyUnit()).isEqualTo(qtyUnit);

        qtyUnit.quantity(null);
        assertThat(qtyUnit.getQuantity()).isNull();
        assertThat(quantityBack.getQtyUnit()).isNull();
    }
}
