package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Location getLocationSample1() {
        return new Location()
            .id(1L)
            .saveAs("saveAs1")
            .gst("gst1")
            .pan("pan1")
            .email("email1")
            .phone("phone1")
            .streetAddress("streetAddress1")
            .postalCode("postalCode1")
            .area("area1")
            .city("city1")
            .stateProvince("stateProvince1")
            .country("country1");
    }

    public static Location getLocationSample2() {
        return new Location()
            .id(2L)
            .saveAs("saveAs2")
            .gst("gst2")
            .pan("pan2")
            .email("email2")
            .phone("phone2")
            .streetAddress("streetAddress2")
            .postalCode("postalCode2")
            .area("area2")
            .city("city2")
            .stateProvince("stateProvince2")
            .country("country2");
    }

    public static Location getLocationRandomSampleGenerator() {
        return new Location()
            .id(longCount.incrementAndGet())
            .saveAs(UUID.randomUUID().toString())
            .gst(UUID.randomUUID().toString())
            .pan(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .streetAddress(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString())
            .area(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .stateProvince(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString());
    }
}
