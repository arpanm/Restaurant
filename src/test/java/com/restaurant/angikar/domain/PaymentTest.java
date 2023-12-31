package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.OrderTestSamples.*;
import static com.restaurant.angikar.domain.PaymentTestSamples.*;
import static com.restaurant.angikar.domain.RefundTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = getPaymentSample1();
        Payment payment2 = new Payment();
        assertThat(payment1).isNotEqualTo(payment2);

        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);

        payment2 = getPaymentSample2();
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    void refundsTest() throws Exception {
        Payment payment = getPaymentRandomSampleGenerator();
        Refund refundBack = getRefundRandomSampleGenerator();

        payment.addRefunds(refundBack);
        assertThat(payment.getRefunds()).containsOnly(refundBack);
        assertThat(refundBack.getPayment()).isEqualTo(payment);

        payment.removeRefunds(refundBack);
        assertThat(payment.getRefunds()).doesNotContain(refundBack);
        assertThat(refundBack.getPayment()).isNull();

        payment.refunds(new HashSet<>(Set.of(refundBack)));
        assertThat(payment.getRefunds()).containsOnly(refundBack);
        assertThat(refundBack.getPayment()).isEqualTo(payment);

        payment.setRefunds(new HashSet<>());
        assertThat(payment.getRefunds()).doesNotContain(refundBack);
        assertThat(refundBack.getPayment()).isNull();
    }

    @Test
    void orderTest() throws Exception {
        Payment payment = getPaymentRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        payment.setOrder(orderBack);
        assertThat(payment.getOrder()).isEqualTo(orderBack);

        payment.order(null);
        assertThat(payment.getOrder()).isNull();
    }
}
