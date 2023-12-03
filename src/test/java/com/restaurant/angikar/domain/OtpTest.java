package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.ApplicationUserTestSamples.*;
import static com.restaurant.angikar.domain.OtpTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Otp.class);
        Otp otp1 = getOtpSample1();
        Otp otp2 = new Otp();
        assertThat(otp1).isNotEqualTo(otp2);

        otp2.setId(otp1.getId());
        assertThat(otp1).isEqualTo(otp2);

        otp2 = getOtpSample2();
        assertThat(otp1).isNotEqualTo(otp2);
    }

    @Test
    void usrTest() throws Exception {
        Otp otp = getOtpRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        otp.setUsr(applicationUserBack);
        assertThat(otp.getUsr()).isEqualTo(applicationUserBack);

        otp.usr(null);
        assertThat(otp.getUsr()).isNull();
    }
}
