package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OtpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Otp getOtpSample1() {
        return new Otp().id(1L).email("email1").phone("phone1").otp(1);
    }

    public static Otp getOtpSample2() {
        return new Otp().id(2L).email("email2").phone("phone2").otp(2);
    }

    public static Otp getOtpRandomSampleGenerator() {
        return new Otp()
            .id(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .otp(intCount.incrementAndGet());
    }
}
