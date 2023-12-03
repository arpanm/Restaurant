package com.restaurant.angikar.domain;

import static com.restaurant.angikar.domain.PincodeMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.angikar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PincodeMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PincodeMaster.class);
        PincodeMaster pincodeMaster1 = getPincodeMasterSample1();
        PincodeMaster pincodeMaster2 = new PincodeMaster();
        assertThat(pincodeMaster1).isNotEqualTo(pincodeMaster2);

        pincodeMaster2.setId(pincodeMaster1.getId());
        assertThat(pincodeMaster1).isEqualTo(pincodeMaster2);

        pincodeMaster2 = getPincodeMasterSample2();
        assertThat(pincodeMaster1).isNotEqualTo(pincodeMaster2);
    }
}
