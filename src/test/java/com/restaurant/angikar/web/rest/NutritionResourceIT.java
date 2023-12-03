package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.Nutrition;
import com.restaurant.angikar.domain.enumeration.NutritionType;
import com.restaurant.angikar.repository.NutritionRepository;
import com.restaurant.angikar.service.dto.NutritionDTO;
import com.restaurant.angikar.service.mapper.NutritionMapper;
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
 * Integration tests for the {@link NutritionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NutritionResourceIT {

    private static final Double DEFAULT_NUTRITION_VALUE = 1D;
    private static final Double UPDATED_NUTRITION_VALUE = 2D;

    private static final NutritionType DEFAULT_NUTRI_TYPE = NutritionType.Calory;
    private static final NutritionType UPDATED_NUTRI_TYPE = NutritionType.Fat;

    private static final String ENTITY_API_URL = "/api/nutritions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NutritionRepository nutritionRepository;

    @Autowired
    private NutritionMapper nutritionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNutritionMockMvc;

    private Nutrition nutrition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nutrition createEntity(EntityManager em) {
        Nutrition nutrition = new Nutrition().nutritionValue(DEFAULT_NUTRITION_VALUE).nutriType(DEFAULT_NUTRI_TYPE);
        return nutrition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nutrition createUpdatedEntity(EntityManager em) {
        Nutrition nutrition = new Nutrition().nutritionValue(UPDATED_NUTRITION_VALUE).nutriType(UPDATED_NUTRI_TYPE);
        return nutrition;
    }

    @BeforeEach
    public void initTest() {
        nutrition = createEntity(em);
    }

    @Test
    @Transactional
    void createNutrition() throws Exception {
        int databaseSizeBeforeCreate = nutritionRepository.findAll().size();
        // Create the Nutrition
        NutritionDTO nutritionDTO = nutritionMapper.toDto(nutrition);
        restNutritionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionDTO)))
            .andExpect(status().isCreated());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeCreate + 1);
        Nutrition testNutrition = nutritionList.get(nutritionList.size() - 1);
        assertThat(testNutrition.getNutritionValue()).isEqualTo(DEFAULT_NUTRITION_VALUE);
        assertThat(testNutrition.getNutriType()).isEqualTo(DEFAULT_NUTRI_TYPE);
    }

    @Test
    @Transactional
    void createNutritionWithExistingId() throws Exception {
        // Create the Nutrition with an existing ID
        nutrition.setId(1L);
        NutritionDTO nutritionDTO = nutritionMapper.toDto(nutrition);

        int databaseSizeBeforeCreate = nutritionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNutritionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNutritions() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList
        restNutritionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutrition.getId().intValue())))
            .andExpect(jsonPath("$.[*].nutritionValue").value(hasItem(DEFAULT_NUTRITION_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].nutriType").value(hasItem(DEFAULT_NUTRI_TYPE.toString())));
    }

    @Test
    @Transactional
    void getNutrition() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get the nutrition
        restNutritionMockMvc
            .perform(get(ENTITY_API_URL_ID, nutrition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nutrition.getId().intValue()))
            .andExpect(jsonPath("$.nutritionValue").value(DEFAULT_NUTRITION_VALUE.doubleValue()))
            .andExpect(jsonPath("$.nutriType").value(DEFAULT_NUTRI_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNutrition() throws Exception {
        // Get the nutrition
        restNutritionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNutrition() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();

        // Update the nutrition
        Nutrition updatedNutrition = nutritionRepository.findById(nutrition.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNutrition are not directly saved in db
        em.detach(updatedNutrition);
        updatedNutrition.nutritionValue(UPDATED_NUTRITION_VALUE).nutriType(UPDATED_NUTRI_TYPE);
        NutritionDTO nutritionDTO = nutritionMapper.toDto(updatedNutrition);

        restNutritionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nutritionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
        Nutrition testNutrition = nutritionList.get(nutritionList.size() - 1);
        assertThat(testNutrition.getNutritionValue()).isEqualTo(UPDATED_NUTRITION_VALUE);
        assertThat(testNutrition.getNutriType()).isEqualTo(UPDATED_NUTRI_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(longCount.incrementAndGet());

        // Create the Nutrition
        NutritionDTO nutritionDTO = nutritionMapper.toDto(nutrition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nutritionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(longCount.incrementAndGet());

        // Create the Nutrition
        NutritionDTO nutritionDTO = nutritionMapper.toDto(nutrition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(longCount.incrementAndGet());

        // Create the Nutrition
        NutritionDTO nutritionDTO = nutritionMapper.toDto(nutrition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNutritionWithPatch() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();

        // Update the nutrition using partial update
        Nutrition partialUpdatedNutrition = new Nutrition();
        partialUpdatedNutrition.setId(nutrition.getId());

        partialUpdatedNutrition.nutriType(UPDATED_NUTRI_TYPE);

        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutrition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutrition))
            )
            .andExpect(status().isOk());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
        Nutrition testNutrition = nutritionList.get(nutritionList.size() - 1);
        assertThat(testNutrition.getNutritionValue()).isEqualTo(DEFAULT_NUTRITION_VALUE);
        assertThat(testNutrition.getNutriType()).isEqualTo(UPDATED_NUTRI_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateNutritionWithPatch() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();

        // Update the nutrition using partial update
        Nutrition partialUpdatedNutrition = new Nutrition();
        partialUpdatedNutrition.setId(nutrition.getId());

        partialUpdatedNutrition.nutritionValue(UPDATED_NUTRITION_VALUE).nutriType(UPDATED_NUTRI_TYPE);

        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutrition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutrition))
            )
            .andExpect(status().isOk());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
        Nutrition testNutrition = nutritionList.get(nutritionList.size() - 1);
        assertThat(testNutrition.getNutritionValue()).isEqualTo(UPDATED_NUTRITION_VALUE);
        assertThat(testNutrition.getNutriType()).isEqualTo(UPDATED_NUTRI_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(longCount.incrementAndGet());

        // Create the Nutrition
        NutritionDTO nutritionDTO = nutritionMapper.toDto(nutrition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nutritionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(longCount.incrementAndGet());

        // Create the Nutrition
        NutritionDTO nutritionDTO = nutritionMapper.toDto(nutrition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(longCount.incrementAndGet());

        // Create the Nutrition
        NutritionDTO nutritionDTO = nutritionMapper.toDto(nutrition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nutritionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNutrition() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeDelete = nutritionRepository.findAll().size();

        // Delete the nutrition
        restNutritionMockMvc
            .perform(delete(ENTITY_API_URL_ID, nutrition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
