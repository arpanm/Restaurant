package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.MealPlanSettings;
import com.restaurant.angikar.domain.enumeration.DietType;
import com.restaurant.angikar.domain.enumeration.FoodType;
import com.restaurant.angikar.repository.MealPlanSettingsRepository;
import com.restaurant.angikar.service.MealPlanSettingsService;
import com.restaurant.angikar.service.dto.MealPlanSettingsDTO;
import com.restaurant.angikar.service.mapper.MealPlanSettingsMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MealPlanSettingsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MealPlanSettingsResourceIT {

    private static final DietType DEFAULT_DIET_TYPE = DietType.Keto;
    private static final DietType UPDATED_DIET_TYPE = DietType.IntermittentFasting;

    private static final FoodType DEFAULT_FOOD_TYPE = FoodType.Veg;
    private static final FoodType UPDATED_FOOD_TYPE = FoodType.NonVeg;

    private static final String ENTITY_API_URL = "/api/meal-plan-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MealPlanSettingsRepository mealPlanSettingsRepository;

    @Mock
    private MealPlanSettingsRepository mealPlanSettingsRepositoryMock;

    @Autowired
    private MealPlanSettingsMapper mealPlanSettingsMapper;

    @Mock
    private MealPlanSettingsService mealPlanSettingsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMealPlanSettingsMockMvc;

    private MealPlanSettings mealPlanSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MealPlanSettings createEntity(EntityManager em) {
        MealPlanSettings mealPlanSettings = new MealPlanSettings().dietType(DEFAULT_DIET_TYPE).foodType(DEFAULT_FOOD_TYPE);
        return mealPlanSettings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MealPlanSettings createUpdatedEntity(EntityManager em) {
        MealPlanSettings mealPlanSettings = new MealPlanSettings().dietType(UPDATED_DIET_TYPE).foodType(UPDATED_FOOD_TYPE);
        return mealPlanSettings;
    }

    @BeforeEach
    public void initTest() {
        mealPlanSettings = createEntity(em);
    }

    @Test
    @Transactional
    void createMealPlanSettings() throws Exception {
        int databaseSizeBeforeCreate = mealPlanSettingsRepository.findAll().size();
        // Create the MealPlanSettings
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(mealPlanSettings);
        restMealPlanSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        MealPlanSettings testMealPlanSettings = mealPlanSettingsList.get(mealPlanSettingsList.size() - 1);
        assertThat(testMealPlanSettings.getDietType()).isEqualTo(DEFAULT_DIET_TYPE);
        assertThat(testMealPlanSettings.getFoodType()).isEqualTo(DEFAULT_FOOD_TYPE);
    }

    @Test
    @Transactional
    void createMealPlanSettingsWithExistingId() throws Exception {
        // Create the MealPlanSettings with an existing ID
        mealPlanSettings.setId(1L);
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(mealPlanSettings);

        int databaseSizeBeforeCreate = mealPlanSettingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMealPlanSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMealPlanSettings() throws Exception {
        // Initialize the database
        mealPlanSettingsRepository.saveAndFlush(mealPlanSettings);

        // Get all the mealPlanSettingsList
        restMealPlanSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mealPlanSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].dietType").value(hasItem(DEFAULT_DIET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].foodType").value(hasItem(DEFAULT_FOOD_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMealPlanSettingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(mealPlanSettingsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMealPlanSettingsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mealPlanSettingsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMealPlanSettingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mealPlanSettingsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMealPlanSettingsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(mealPlanSettingsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMealPlanSettings() throws Exception {
        // Initialize the database
        mealPlanSettingsRepository.saveAndFlush(mealPlanSettings);

        // Get the mealPlanSettings
        restMealPlanSettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, mealPlanSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mealPlanSettings.getId().intValue()))
            .andExpect(jsonPath("$.dietType").value(DEFAULT_DIET_TYPE.toString()))
            .andExpect(jsonPath("$.foodType").value(DEFAULT_FOOD_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMealPlanSettings() throws Exception {
        // Get the mealPlanSettings
        restMealPlanSettingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMealPlanSettings() throws Exception {
        // Initialize the database
        mealPlanSettingsRepository.saveAndFlush(mealPlanSettings);

        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();

        // Update the mealPlanSettings
        MealPlanSettings updatedMealPlanSettings = mealPlanSettingsRepository.findById(mealPlanSettings.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMealPlanSettings are not directly saved in db
        em.detach(updatedMealPlanSettings);
        updatedMealPlanSettings.dietType(UPDATED_DIET_TYPE).foodType(UPDATED_FOOD_TYPE);
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(updatedMealPlanSettings);

        restMealPlanSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mealPlanSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
        MealPlanSettings testMealPlanSettings = mealPlanSettingsList.get(mealPlanSettingsList.size() - 1);
        assertThat(testMealPlanSettings.getDietType()).isEqualTo(UPDATED_DIET_TYPE);
        assertThat(testMealPlanSettings.getFoodType()).isEqualTo(UPDATED_FOOD_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMealPlanSettings() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();
        mealPlanSettings.setId(longCount.incrementAndGet());

        // Create the MealPlanSettings
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(mealPlanSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMealPlanSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mealPlanSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMealPlanSettings() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();
        mealPlanSettings.setId(longCount.incrementAndGet());

        // Create the MealPlanSettings
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(mealPlanSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMealPlanSettings() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();
        mealPlanSettings.setId(longCount.incrementAndGet());

        // Create the MealPlanSettings
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(mealPlanSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanSettingsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMealPlanSettingsWithPatch() throws Exception {
        // Initialize the database
        mealPlanSettingsRepository.saveAndFlush(mealPlanSettings);

        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();

        // Update the mealPlanSettings using partial update
        MealPlanSettings partialUpdatedMealPlanSettings = new MealPlanSettings();
        partialUpdatedMealPlanSettings.setId(mealPlanSettings.getId());

        partialUpdatedMealPlanSettings.dietType(UPDATED_DIET_TYPE);

        restMealPlanSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMealPlanSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMealPlanSettings))
            )
            .andExpect(status().isOk());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
        MealPlanSettings testMealPlanSettings = mealPlanSettingsList.get(mealPlanSettingsList.size() - 1);
        assertThat(testMealPlanSettings.getDietType()).isEqualTo(UPDATED_DIET_TYPE);
        assertThat(testMealPlanSettings.getFoodType()).isEqualTo(DEFAULT_FOOD_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMealPlanSettingsWithPatch() throws Exception {
        // Initialize the database
        mealPlanSettingsRepository.saveAndFlush(mealPlanSettings);

        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();

        // Update the mealPlanSettings using partial update
        MealPlanSettings partialUpdatedMealPlanSettings = new MealPlanSettings();
        partialUpdatedMealPlanSettings.setId(mealPlanSettings.getId());

        partialUpdatedMealPlanSettings.dietType(UPDATED_DIET_TYPE).foodType(UPDATED_FOOD_TYPE);

        restMealPlanSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMealPlanSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMealPlanSettings))
            )
            .andExpect(status().isOk());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
        MealPlanSettings testMealPlanSettings = mealPlanSettingsList.get(mealPlanSettingsList.size() - 1);
        assertThat(testMealPlanSettings.getDietType()).isEqualTo(UPDATED_DIET_TYPE);
        assertThat(testMealPlanSettings.getFoodType()).isEqualTo(UPDATED_FOOD_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMealPlanSettings() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();
        mealPlanSettings.setId(longCount.incrementAndGet());

        // Create the MealPlanSettings
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(mealPlanSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMealPlanSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mealPlanSettingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMealPlanSettings() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();
        mealPlanSettings.setId(longCount.incrementAndGet());

        // Create the MealPlanSettings
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(mealPlanSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMealPlanSettings() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanSettingsRepository.findAll().size();
        mealPlanSettings.setId(longCount.incrementAndGet());

        // Create the MealPlanSettings
        MealPlanSettingsDTO mealPlanSettingsDTO = mealPlanSettingsMapper.toDto(mealPlanSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MealPlanSettings in the database
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMealPlanSettings() throws Exception {
        // Initialize the database
        mealPlanSettingsRepository.saveAndFlush(mealPlanSettings);

        int databaseSizeBeforeDelete = mealPlanSettingsRepository.findAll().size();

        // Delete the mealPlanSettings
        restMealPlanSettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, mealPlanSettings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MealPlanSettings> mealPlanSettingsList = mealPlanSettingsRepository.findAll();
        assertThat(mealPlanSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
