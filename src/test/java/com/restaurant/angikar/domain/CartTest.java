package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ApplicationUserTestSamples.*;
import static com.restaurant.angikar.domain.CartItemTestSamples.*;
import static com.restaurant.angikar.domain.CartTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cart.class);
        Cart cart1 = getCartSample1();
        Cart cart2 = new Cart();
        assertThat(cart1).isNotEqualTo(cart2);

        cart2.setId(cart1.getId());
        assertThat(cart1).isEqualTo(cart2);

        cart2 = getCartSample2();
        assertThat(cart1).isNotEqualTo(cart2);
    }

    @Test
    void itemsTest() throws Exception {
        Cart cart = getCartRandomSampleGenerator();
        CartItem cartItemBack = getCartItemRandomSampleGenerator();

        cart.addItems(cartItemBack);
        assertThat(cart.getItems()).containsOnly(cartItemBack);
        assertThat(cartItemBack.getCart()).isEqualTo(cart);

        cart.removeItems(cartItemBack);
        assertThat(cart.getItems()).doesNotContain(cartItemBack);
        assertThat(cartItemBack.getCart()).isNull();

        cart.items(new HashSet<>(Set.of(cartItemBack)));
        assertThat(cart.getItems()).containsOnly(cartItemBack);
        assertThat(cartItemBack.getCart()).isEqualTo(cart);

        cart.setItems(new HashSet<>());
        assertThat(cart.getItems()).doesNotContain(cartItemBack);
        assertThat(cartItemBack.getCart()).isNull();
    }

    @Test
    void usrTest() throws Exception {
        Cart cart = getCartRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        cart.setUsr(applicationUserBack);
        assertThat(cart.getUsr()).isEqualTo(applicationUserBack);

        cart.usr(null);
        assertThat(cart.getUsr()).isNull();
    }
}
