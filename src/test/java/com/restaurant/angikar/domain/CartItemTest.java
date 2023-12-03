package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.CartItemTestSamples.*;
import static com.restaurant.angikar.domain.CartTestSamples.*;
import static com.restaurant.angikar.domain.ItemTestSamples.*;
import static com.restaurant.angikar.domain.MealPlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CartItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartItem.class);
        CartItem cartItem1 = getCartItemSample1();
        CartItem cartItem2 = new CartItem();
        assertThat(cartItem1).isNotEqualTo(cartItem2);

        cartItem2.setId(cartItem1.getId());
        assertThat(cartItem1).isEqualTo(cartItem2);

        cartItem2 = getCartItemSample2();
        assertThat(cartItem1).isNotEqualTo(cartItem2);
    }

    @Test
    void itemTest() throws Exception {
        CartItem cartItem = getCartItemRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        cartItem.setItem(itemBack);
        assertThat(cartItem.getItem()).isEqualTo(itemBack);

        cartItem.item(null);
        assertThat(cartItem.getItem()).isNull();
    }

    @Test
    void mealTest() throws Exception {
        CartItem cartItem = getCartItemRandomSampleGenerator();
        MealPlan mealPlanBack = getMealPlanRandomSampleGenerator();

        cartItem.setMeal(mealPlanBack);
        assertThat(cartItem.getMeal()).isEqualTo(mealPlanBack);

        cartItem.meal(null);
        assertThat(cartItem.getMeal()).isNull();
    }

    @Test
    void cartTest() throws Exception {
        CartItem cartItem = getCartItemRandomSampleGenerator();
        Cart cartBack = getCartRandomSampleGenerator();

        cartItem.setCart(cartBack);
        assertThat(cartItem.getCart()).isEqualTo(cartBack);

        cartItem.cart(null);
        assertThat(cartItem.getCart()).isNull();
    }
}
