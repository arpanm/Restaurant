package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.WeightInfo;
import com.restaurant.angikar.repository.WeightInfoRepository;
import com.restaurant.angikar.service.dto.WeightInfoDTO;
import com.restaurant.angikar.service.mapper.WeightInfoMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link WeightInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WeightInfoResourceIT {

    private static final Double DEFAULT_CURRENT_WEIGHT = 1D;
    private static final Double UPDATED_CURRENT_WEIGHT = 2D;

    private static final Double DEFAULT_EXPECTED_WEIGHT = 1D;
    private static final Double UPDATED_EXPECTED_WEIGHT = 2D;

    private static final Double DEFAULT_HEIGHT_IN_INCH = 1D;
    private static final Double UPDATED_HEIGHT_IN_INCH = 2D;

    private static final Integer DEFAULT_NUMBER_OF_DAYS = 1;
    private static final Integer UPDATED_NUMBER_OF_DAYS = 2;

    private static final String ENTITY_API_URL = "/api/weight-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WeightInfoRepository weightInfoRepository;

    @Autowired
    private WeightInfoMapper weightInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeightInfoMockMvc;

    private WeightInfo weightInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeightInfo createEntity(EntityManager em) {
        WeightInfo weightInfo = new WeightInfo()
            .currentWeight(DEFAULT_CURRENT_WEIGHT)
            .expectedWeight(DEFAULT_EXPECTED_WEIGHT)
            .heightInInch(DEFAULT_HEIGHT_IN_INCH)
            .numberOfDays(DEFAULT_NUMBER_OF_DAYS);
        return weightInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeightInfo createUpdatedEntity(EntityManager em) {
        WeightInfo weightInfo = new WeightInfo()
            .currentWeight(UPDATED_CURRENT_WEIGHT)
            .expectedWeight(UPDATED_EXPECTED_WEIGHT)
            .heightInInch(UPDATED_HEIGHT_IN_INCH)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS);
        return weightInfo;
    }

    @BeforeEach
    public void initTest() {
        weightInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createWeightInfo() throws Exception {
        int databaseSizeBeforeCreate = weightInfoRepository.findAll().size();
        // Create the WeightInfo
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(weightInfo);
        restWeightInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weightInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeCreate + 1);
        WeightInfo testWeightInfo = weightInfoList.get(weightInfoList.size() - 1);
        assertThat(testWeightInfo.getCurrentWeight()).isEqualTo(DEFAULT_CURRENT_WEIGHT);
        assertThat(testWeightInfo.getExpectedWeight()).isEqualTo(DEFAULT_EXPECTED_WEIGHT);
        assertThat(testWeightInfo.getHeightInInch()).isEqualTo(DEFAULT_HEIGHT_IN_INCH);
        assertThat(testWeightInfo.getNumberOfDays()).isEqualTo(DEFAULT_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    void createWeightInfoWithExistingId() throws Exception {
        // Create the WeightInfo with an existing ID
        weightInfo.setId(1L);
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(weightInfo);

        int databaseSizeBeforeCreate = weightInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeightInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weightInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWeightInfos() throws Exception {
        // Initialize the database
        weightInfoRepository.saveAndFlush(weightInfo);

        // Get all the weightInfoList
        restWeightInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weightInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].currentWeight").value(hasItem(DEFAULT_CURRENT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].expectedWeight").value(hasItem(DEFAULT_EXPECTED_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].heightInInch").value(hasItem(DEFAULT_HEIGHT_IN_INCH.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)));
    }

    @Test
    @Transactional
    void getWeightInfo() throws Exception {
        // Initialize the database
        weightInfoRepository.saveAndFlush(weightInfo);

        // Get the weightInfo
        restWeightInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, weightInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weightInfo.getId().intValue()))
            .andExpect(jsonPath("$.currentWeight").value(DEFAULT_CURRENT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.expectedWeight").value(DEFAULT_EXPECTED_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.heightInInch").value(DEFAULT_HEIGHT_IN_INCH.doubleValue()))
            .andExpect(jsonPath("$.numberOfDays").value(DEFAULT_NUMBER_OF_DAYS));
    }

    @Test
    @Transactional
    void getNonExistingWeightInfo() throws Exception {
        // Get the weightInfo
        restWeightInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWeightInfo() throws Exception {
        // Initialize the database
        weightInfoRepository.saveAndFlush(weightInfo);

        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();

        // Update the weightInfo
        WeightInfo updatedWeightInfo = weightInfoRepository.findById(weightInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWeightInfo are not directly saved in db
        em.detach(updatedWeightInfo);
        updatedWeightInfo
            .currentWeight(UPDATED_CURRENT_WEIGHT)
            .expectedWeight(UPDATED_EXPECTED_WEIGHT)
            .heightInInch(UPDATED_HEIGHT_IN_INCH)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS);
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(updatedWeightInfo);

        restWeightInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weightInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
        WeightInfo testWeightInfo = weightInfoList.get(weightInfoList.size() - 1);
        assertThat(testWeightInfo.getCurrentWeight()).isEqualTo(UPDATED_CURRENT_WEIGHT);
        assertThat(testWeightInfo.getExpectedWeight()).isEqualTo(UPDATED_EXPECTED_WEIGHT);
        assertThat(testWeightInfo.getHeightInInch()).isEqualTo(UPDATED_HEIGHT_IN_INCH);
        assertThat(testWeightInfo.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    void putNonExistingWeightInfo() throws Exception {
        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();
        weightInfo.setId(longCount.incrementAndGet());

        // Create the WeightInfo
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(weightInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeightInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weightInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWeightInfo() throws Exception {
        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();
        weightInfo.setId(longCount.incrementAndGet());

        // Create the WeightInfo
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(weightInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWeightInfo() throws Exception {
        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();
        weightInfo.setId(longCount.incrementAndGet());

        // Create the WeightInfo
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(weightInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weightInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWeightInfoWithPatch() throws Exception {
        // Initialize the database
        weightInfoRepository.saveAndFlush(weightInfo);

        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();

        // Update the weightInfo using partial update
        WeightInfo partialUpdatedWeightInfo = new WeightInfo();
        partialUpdatedWeightInfo.setId(weightInfo.getId());

        partialUpdatedWeightInfo.heightInInch(UPDATED_HEIGHT_IN_INCH).numberOfDays(UPDATED_NUMBER_OF_DAYS);

        restWeightInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeightInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeightInfo))
            )
            .andExpect(status().isOk());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
        WeightInfo testWeightInfo = weightInfoList.get(weightInfoList.size() - 1);
        assertThat(testWeightInfo.getCurrentWeight()).isEqualTo(DEFAULT_CURRENT_WEIGHT);
        assertThat(testWeightInfo.getExpectedWeight()).isEqualTo(DEFAULT_EXPECTED_WEIGHT);
        assertThat(testWeightInfo.getHeightInInch()).isEqualTo(UPDATED_HEIGHT_IN_INCH);
        assertThat(testWeightInfo.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    void fullUpdateWeightInfoWithPatch() throws Exception {
        // Initialize the database
        weightInfoRepository.saveAndFlush(weightInfo);

        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();

        // Update the weightInfo using partial update
        WeightInfo partialUpdatedWeightInfo = new WeightInfo();
        partialUpdatedWeightInfo.setId(weightInfo.getId());

        partialUpdatedWeightInfo
            .currentWeight(UPDATED_CURRENT_WEIGHT)
            .expectedWeight(UPDATED_EXPECTED_WEIGHT)
            .heightInInch(UPDATED_HEIGHT_IN_INCH)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS);

        restWeightInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeightInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeightInfo))
            )
            .andExpect(status().isOk());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
        WeightInfo testWeightInfo = weightInfoList.get(weightInfoList.size() - 1);
        assertThat(testWeightInfo.getCurrentWeight()).isEqualTo(UPDATED_CURRENT_WEIGHT);
        assertThat(testWeightInfo.getExpectedWeight()).isEqualTo(UPDATED_EXPECTED_WEIGHT);
        assertThat(testWeightInfo.getHeightInInch()).isEqualTo(UPDATED_HEIGHT_IN_INCH);
        assertThat(testWeightInfo.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    void patchNonExistingWeightInfo() throws Exception {
        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();
        weightInfo.setId(longCount.incrementAndGet());

        // Create the WeightInfo
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(weightInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeightInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, weightInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weightInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWeightInfo() throws Exception {
        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();
        weightInfo.setId(longCount.incrementAndGet());

        // Create the WeightInfo
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(weightInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weightInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWeightInfo() throws Exception {
        int databaseSizeBeforeUpdate = weightInfoRepository.findAll().size();
        weightInfo.setId(longCount.incrementAndGet());

        // Create the WeightInfo
        WeightInfoDTO weightInfoDTO = weightInfoMapper.toDto(weightInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(weightInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WeightInfo in the database
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWeightInfo() throws Exception {
        // Initialize the database
        weightInfoRepository.saveAndFlush(weightInfo);

        int databaseSizeBeforeDelete = weightInfoRepository.findAll().size();

        // Delete the weightInfo
        restWeightInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, weightInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WeightInfo> weightInfoList = weightInfoRepository.findAll();
        assertThat(weightInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
