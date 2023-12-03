package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BannerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Banner getBannerSample1() {
        return new Banner().id(1L).title("title1").imageUrl("imageUrl1").bannerLink("bannerLink1").description("description1");
    }

    public static Banner getBannerSample2() {
        return new Banner().id(2L).title("title2").imageUrl("imageUrl2").bannerLink("bannerLink2").description("description2");
    }

    public static Banner getBannerRandomSampleGenerator() {
        return new Banner()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .imageUrl(UUID.randomUUID().toString())
            .bannerLink(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
