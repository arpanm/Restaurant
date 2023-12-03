package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.BannerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BannerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banner.class);
        Banner banner1 = getBannerSample1();
        Banner banner2 = new Banner();
        assertThat(banner1).isNotEqualTo(banner2);

        banner2.setId(banner1.getId());
        assertThat(banner1).isEqualTo(banner2);

        banner2 = getBannerSample2();
        assertThat(banner1).isNotEqualTo(banner2);
    }
}
