package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.CalorieInfo;
import com.restaurant.angikar.repository.CalorieInfoRepository;
import com.restaurant.angikar.service.dto.CalorieInfoDTO;
import com.restaurant.angikar.service.mapper.CalorieInfoMapper;
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
 * Integration tests for the {@link CalorieInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CalorieInfoResourceIT {

    private static final Double DEFAULT_DAILY_CALORIE_LIMIT = 1D;
    private static final Double UPDATED_DAILY_CALORIE_LIMIT = 2D;

    private static final String ENTITY_API_URL = "/api/calorie-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CalorieInfoRepository calorieInfoRepository;

    @Autowired
    private CalorieInfoMapper calorieInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalorieInfoMockMvc;

    private CalorieInfo calorieInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalorieInfo createEntity(EntityManager em) {
        CalorieInfo calorieInfo = new CalorieInfo().dailyCalorieLimit(DEFAULT_DAILY_CALORIE_LIMIT);
        return calorieInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalorieInfo createUpdatedEntity(EntityManager em) {
        CalorieInfo calorieInfo = new CalorieInfo().dailyCalorieLimit(UPDATED_DAILY_CALORIE_LIMIT);
        return calorieInfo;
    }

    @BeforeEach
    public void initTest() {
        calorieInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createCalorieInfo() throws Exception {
        int databaseSizeBeforeCreate = calorieInfoRepository.findAll().size();
        // Create the CalorieInfo
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(calorieInfo);
        restCalorieInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CalorieInfo testCalorieInfo = calorieInfoList.get(calorieInfoList.size() - 1);
        assertThat(testCalorieInfo.getDailyCalorieLimit()).isEqualTo(DEFAULT_DAILY_CALORIE_LIMIT);
    }

    @Test
    @Transactional
    void createCalorieInfoWithExistingId() throws Exception {
        // Create the CalorieInfo with an existing ID
        calorieInfo.setId(1L);
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(calorieInfo);

        int databaseSizeBeforeCreate = calorieInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalorieInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCalorieInfos() throws Exception {
        // Initialize the database
        calorieInfoRepository.saveAndFlush(calorieInfo);

        // Get all the calorieInfoList
        restCalorieInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calorieInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dailyCalorieLimit").value(hasItem(DEFAULT_DAILY_CALORIE_LIMIT.doubleValue())));
    }

    @Test
    @Transactional
    void getCalorieInfo() throws Exception {
        // Initialize the database
        calorieInfoRepository.saveAndFlush(calorieInfo);

        // Get the calorieInfo
        restCalorieInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, calorieInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calorieInfo.getId().intValue()))
            .andExpect(jsonPath("$.dailyCalorieLimit").value(DEFAULT_DAILY_CALORIE_LIMIT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCalorieInfo() throws Exception {
        // Get the calorieInfo
        restCalorieInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCalorieInfo() throws Exception {
        // Initialize the database
        calorieInfoRepository.saveAndFlush(calorieInfo);

        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();

        // Update the calorieInfo
        CalorieInfo updatedCalorieInfo = calorieInfoRepository.findById(calorieInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCalorieInfo are not directly saved in db
        em.detach(updatedCalorieInfo);
        updatedCalorieInfo.dailyCalorieLimit(UPDATED_DAILY_CALORIE_LIMIT);
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(updatedCalorieInfo);

        restCalorieInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calorieInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
        CalorieInfo testCalorieInfo = calorieInfoList.get(calorieInfoList.size() - 1);
        assertThat(testCalorieInfo.getDailyCalorieLimit()).isEqualTo(UPDATED_DAILY_CALORIE_LIMIT);
    }

    @Test
    @Transactional
    void putNonExistingCalorieInfo() throws Exception {
        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();
        calorieInfo.setId(longCount.incrementAndGet());

        // Create the CalorieInfo
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(calorieInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalorieInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calorieInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCalorieInfo() throws Exception {
        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();
        calorieInfo.setId(longCount.incrementAndGet());

        // Create the CalorieInfo
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(calorieInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalorieInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCalorieInfo() throws Exception {
        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();
        calorieInfo.setId(longCount.incrementAndGet());

        // Create the CalorieInfo
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(calorieInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalorieInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCalorieInfoWithPatch() throws Exception {
        // Initialize the database
        calorieInfoRepository.saveAndFlush(calorieInfo);

        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();

        // Update the calorieInfo using partial update
        CalorieInfo partialUpdatedCalorieInfo = new CalorieInfo();
        partialUpdatedCalorieInfo.setId(calorieInfo.getId());

        partialUpdatedCalorieInfo.dailyCalorieLimit(UPDATED_DAILY_CALORIE_LIMIT);

        restCalorieInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalorieInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalorieInfo))
            )
            .andExpect(status().isOk());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
        CalorieInfo testCalorieInfo = calorieInfoList.get(calorieInfoList.size() - 1);
        assertThat(testCalorieInfo.getDailyCalorieLimit()).isEqualTo(UPDATED_DAILY_CALORIE_LIMIT);
    }

    @Test
    @Transactional
    void fullUpdateCalorieInfoWithPatch() throws Exception {
        // Initialize the database
        calorieInfoRepository.saveAndFlush(calorieInfo);

        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();

        // Update the calorieInfo using partial update
        CalorieInfo partialUpdatedCalorieInfo = new CalorieInfo();
        partialUpdatedCalorieInfo.setId(calorieInfo.getId());

        partialUpdatedCalorieInfo.dailyCalorieLimit(UPDATED_DAILY_CALORIE_LIMIT);

        restCalorieInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalorieInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalorieInfo))
            )
            .andExpect(status().isOk());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
        CalorieInfo testCalorieInfo = calorieInfoList.get(calorieInfoList.size() - 1);
        assertThat(testCalorieInfo.getDailyCalorieLimit()).isEqualTo(UPDATED_DAILY_CALORIE_LIMIT);
    }

    @Test
    @Transactional
    void patchNonExistingCalorieInfo() throws Exception {
        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();
        calorieInfo.setId(longCount.incrementAndGet());

        // Create the CalorieInfo
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(calorieInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalorieInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calorieInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCalorieInfo() throws Exception {
        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();
        calorieInfo.setId(longCount.incrementAndGet());

        // Create the CalorieInfo
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(calorieInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalorieInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCalorieInfo() throws Exception {
        int databaseSizeBeforeUpdate = calorieInfoRepository.findAll().size();
        calorieInfo.setId(longCount.incrementAndGet());

        // Create the CalorieInfo
        CalorieInfoDTO calorieInfoDTO = calorieInfoMapper.toDto(calorieInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalorieInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(calorieInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalorieInfo in the database
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCalorieInfo() throws Exception {
        // Initialize the database
        calorieInfoRepository.saveAndFlush(calorieInfo);

        int databaseSizeBeforeDelete = calorieInfoRepository.findAll().size();

        // Delete the calorieInfo
        restCalorieInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, calorieInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CalorieInfo> calorieInfoList = calorieInfoRepository.findAll();
        assertThat(calorieInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
