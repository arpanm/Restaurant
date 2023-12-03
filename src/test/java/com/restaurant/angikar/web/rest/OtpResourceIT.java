package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.Otp;
import com.restaurant.angikar.repository.OtpRepository;
import com.restaurant.angikar.service.dto.OtpDTO;
import com.restaurant.angikar.service.mapper.OtpMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OtpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OtpResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_OTP = 1;
    private static final Integer UPDATED_OTP = 2;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Boolean DEFAULT_IS_VALIDATED = false;
    private static final Boolean UPDATED_IS_VALIDATED = true;

    private static final Instant DEFAULT_EXPIRY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/otps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private OtpMapper otpMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtpMockMvc;

    private Otp otp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Otp createEntity(EntityManager em) {
        Otp otp = new Otp()
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .otp(DEFAULT_OTP)
            .isActive(DEFAULT_IS_ACTIVE)
            .isValidated(DEFAULT_IS_VALIDATED)
            .expiry(DEFAULT_EXPIRY);
        return otp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Otp createUpdatedEntity(EntityManager em) {
        Otp otp = new Otp()
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .otp(UPDATED_OTP)
            .isActive(UPDATED_IS_ACTIVE)
            .isValidated(UPDATED_IS_VALIDATED)
            .expiry(UPDATED_EXPIRY);
        return otp;
    }

    @BeforeEach
    public void initTest() {
        otp = createEntity(em);
    }

    @Test
    @Transactional
    void createOtp() throws Exception {
        int databaseSizeBeforeCreate = otpRepository.findAll().size();
        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);
        restOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otpDTO)))
            .andExpect(status().isCreated());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeCreate + 1);
        Otp testOtp = otpList.get(otpList.size() - 1);
        assertThat(testOtp.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOtp.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOtp.getOtp()).isEqualTo(DEFAULT_OTP);
        assertThat(testOtp.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testOtp.getIsValidated()).isEqualTo(DEFAULT_IS_VALIDATED);
        assertThat(testOtp.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
    }

    @Test
    @Transactional
    void createOtpWithExistingId() throws Exception {
        // Create the Otp with an existing ID
        otp.setId(1L);
        OtpDTO otpDTO = otpMapper.toDto(otp);

        int databaseSizeBeforeCreate = otpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOtps() throws Exception {
        // Initialize the database
        otpRepository.saveAndFlush(otp);

        // Get all the otpList
        restOtpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otp.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].otp").value(hasItem(DEFAULT_OTP)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].expiry").value(hasItem(DEFAULT_EXPIRY.toString())));
    }

    @Test
    @Transactional
    void getOtp() throws Exception {
        // Initialize the database
        otpRepository.saveAndFlush(otp);

        // Get the otp
        restOtpMockMvc
            .perform(get(ENTITY_API_URL_ID, otp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otp.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.otp").value(DEFAULT_OTP))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.isValidated").value(DEFAULT_IS_VALIDATED.booleanValue()))
            .andExpect(jsonPath("$.expiry").value(DEFAULT_EXPIRY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOtp() throws Exception {
        // Get the otp
        restOtpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOtp() throws Exception {
        // Initialize the database
        otpRepository.saveAndFlush(otp);

        int databaseSizeBeforeUpdate = otpRepository.findAll().size();

        // Update the otp
        Otp updatedOtp = otpRepository.findById(otp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOtp are not directly saved in db
        em.detach(updatedOtp);
        updatedOtp
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .otp(UPDATED_OTP)
            .isActive(UPDATED_IS_ACTIVE)
            .isValidated(UPDATED_IS_VALIDATED)
            .expiry(UPDATED_EXPIRY);
        OtpDTO otpDTO = otpMapper.toDto(updatedOtp);

        restOtpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otpDTO))
            )
            .andExpect(status().isOk());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
        Otp testOtp = otpList.get(otpList.size() - 1);
        assertThat(testOtp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOtp.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOtp.getOtp()).isEqualTo(UPDATED_OTP);
        assertThat(testOtp.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOtp.getIsValidated()).isEqualTo(UPDATED_IS_VALIDATED);
        assertThat(testOtp.getExpiry()).isEqualTo(UPDATED_EXPIRY);
    }

    @Test
    @Transactional
    void putNonExistingOtp() throws Exception {
        int databaseSizeBeforeUpdate = otpRepository.findAll().size();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOtp() throws Exception {
        int databaseSizeBeforeUpdate = otpRepository.findAll().size();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOtp() throws Exception {
        int databaseSizeBeforeUpdate = otpRepository.findAll().size();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOtpWithPatch() throws Exception {
        // Initialize the database
        otpRepository.saveAndFlush(otp);

        int databaseSizeBeforeUpdate = otpRepository.findAll().size();

        // Update the otp using partial update
        Otp partialUpdatedOtp = new Otp();
        partialUpdatedOtp.setId(otp.getId());

        partialUpdatedOtp.email(UPDATED_EMAIL).phone(UPDATED_PHONE).isActive(UPDATED_IS_ACTIVE).expiry(UPDATED_EXPIRY);

        restOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtp))
            )
            .andExpect(status().isOk());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
        Otp testOtp = otpList.get(otpList.size() - 1);
        assertThat(testOtp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOtp.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOtp.getOtp()).isEqualTo(DEFAULT_OTP);
        assertThat(testOtp.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOtp.getIsValidated()).isEqualTo(DEFAULT_IS_VALIDATED);
        assertThat(testOtp.getExpiry()).isEqualTo(UPDATED_EXPIRY);
    }

    @Test
    @Transactional
    void fullUpdateOtpWithPatch() throws Exception {
        // Initialize the database
        otpRepository.saveAndFlush(otp);

        int databaseSizeBeforeUpdate = otpRepository.findAll().size();

        // Update the otp using partial update
        Otp partialUpdatedOtp = new Otp();
        partialUpdatedOtp.setId(otp.getId());

        partialUpdatedOtp
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .otp(UPDATED_OTP)
            .isActive(UPDATED_IS_ACTIVE)
            .isValidated(UPDATED_IS_VALIDATED)
            .expiry(UPDATED_EXPIRY);

        restOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtp))
            )
            .andExpect(status().isOk());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
        Otp testOtp = otpList.get(otpList.size() - 1);
        assertThat(testOtp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOtp.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOtp.getOtp()).isEqualTo(UPDATED_OTP);
        assertThat(testOtp.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOtp.getIsValidated()).isEqualTo(UPDATED_IS_VALIDATED);
        assertThat(testOtp.getExpiry()).isEqualTo(UPDATED_EXPIRY);
    }

    @Test
    @Transactional
    void patchNonExistingOtp() throws Exception {
        int databaseSizeBeforeUpdate = otpRepository.findAll().size();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, otpDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOtp() throws Exception {
        int databaseSizeBeforeUpdate = otpRepository.findAll().size();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOtp() throws Exception {
        int databaseSizeBeforeUpdate = otpRepository.findAll().size();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(otpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Otp in the database
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOtp() throws Exception {
        // Initialize the database
        otpRepository.saveAndFlush(otp);

        int databaseSizeBeforeDelete = otpRepository.findAll().size();

        // Delete the otp
        restOtpMockMvc.perform(delete(ENTITY_API_URL_ID, otp.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Otp> otpList = otpRepository.findAll();
        assertThat(otpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
