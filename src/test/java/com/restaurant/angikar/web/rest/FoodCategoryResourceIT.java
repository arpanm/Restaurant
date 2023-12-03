package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.FoodCategory;
import com.restaurant.angikar.repository.FoodCategoryRepository;
import com.restaurant.angikar.service.dto.FoodCategoryDTO;
import com.restaurant.angikar.service.mapper.FoodCategoryMapper;
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
 * Integration tests for the {@link FoodCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FoodCategoryResourceIT {

    private static final String DEFAULT_CAT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/food-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private FoodCategoryMapper foodCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoodCategoryMockMvc;

    private FoodCategory foodCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodCategory createEntity(EntityManager em) {
        FoodCategory foodCategory = new FoodCategory()
            .catName(DEFAULT_CAT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .imageUrl(DEFAULT_IMAGE_URL);
        return foodCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodCategory createUpdatedEntity(EntityManager em) {
        FoodCategory foodCategory = new FoodCategory()
            .catName(UPDATED_CAT_NAME)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL);
        return foodCategory;
    }

    @BeforeEach
    public void initTest() {
        foodCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createFoodCategory() throws Exception {
        int databaseSizeBeforeCreate = foodCategoryRepository.findAll().size();
        // Create the FoodCategory
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(foodCategory);
        restFoodCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        FoodCategory testFoodCategory = foodCategoryList.get(foodCategoryList.size() - 1);
        assertThat(testFoodCategory.getCatName()).isEqualTo(DEFAULT_CAT_NAME);
        assertThat(testFoodCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFoodCategory.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void createFoodCategoryWithExistingId() throws Exception {
        // Create the FoodCategory with an existing ID
        foodCategory.setId(1L);
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(foodCategory);

        int databaseSizeBeforeCreate = foodCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFoodCategories() throws Exception {
        // Initialize the database
        foodCategoryRepository.saveAndFlush(foodCategory);

        // Get all the foodCategoryList
        restFoodCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].catName").value(hasItem(DEFAULT_CAT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }

    @Test
    @Transactional
    void getFoodCategory() throws Exception {
        // Initialize the database
        foodCategoryRepository.saveAndFlush(foodCategory);

        // Get the foodCategory
        restFoodCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, foodCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foodCategory.getId().intValue()))
            .andExpect(jsonPath("$.catName").value(DEFAULT_CAT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingFoodCategory() throws Exception {
        // Get the foodCategory
        restFoodCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFoodCategory() throws Exception {
        // Initialize the database
        foodCategoryRepository.saveAndFlush(foodCategory);

        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();

        // Update the foodCategory
        FoodCategory updatedFoodCategory = foodCategoryRepository.findById(foodCategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFoodCategory are not directly saved in db
        em.detach(updatedFoodCategory);
        updatedFoodCategory.catName(UPDATED_CAT_NAME).description(UPDATED_DESCRIPTION).imageUrl(UPDATED_IMAGE_URL);
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(updatedFoodCategory);

        restFoodCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
        FoodCategory testFoodCategory = foodCategoryList.get(foodCategoryList.size() - 1);
        assertThat(testFoodCategory.getCatName()).isEqualTo(UPDATED_CAT_NAME);
        assertThat(testFoodCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoodCategory.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void putNonExistingFoodCategory() throws Exception {
        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();
        foodCategory.setId(longCount.incrementAndGet());

        // Create the FoodCategory
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(foodCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFoodCategory() throws Exception {
        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();
        foodCategory.setId(longCount.incrementAndGet());

        // Create the FoodCategory
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(foodCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFoodCategory() throws Exception {
        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();
        foodCategory.setId(longCount.incrementAndGet());

        // Create the FoodCategory
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(foodCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFoodCategoryWithPatch() throws Exception {
        // Initialize the database
        foodCategoryRepository.saveAndFlush(foodCategory);

        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();

        // Update the foodCategory using partial update
        FoodCategory partialUpdatedFoodCategory = new FoodCategory();
        partialUpdatedFoodCategory.setId(foodCategory.getId());

        partialUpdatedFoodCategory.catName(UPDATED_CAT_NAME).description(UPDATED_DESCRIPTION);

        restFoodCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoodCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoodCategory))
            )
            .andExpect(status().isOk());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
        FoodCategory testFoodCategory = foodCategoryList.get(foodCategoryList.size() - 1);
        assertThat(testFoodCategory.getCatName()).isEqualTo(UPDATED_CAT_NAME);
        assertThat(testFoodCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoodCategory.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void fullUpdateFoodCategoryWithPatch() throws Exception {
        // Initialize the database
        foodCategoryRepository.saveAndFlush(foodCategory);

        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();

        // Update the foodCategory using partial update
        FoodCategory partialUpdatedFoodCategory = new FoodCategory();
        partialUpdatedFoodCategory.setId(foodCategory.getId());

        partialUpdatedFoodCategory.catName(UPDATED_CAT_NAME).description(UPDATED_DESCRIPTION).imageUrl(UPDATED_IMAGE_URL);

        restFoodCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoodCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoodCategory))
            )
            .andExpect(status().isOk());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
        FoodCategory testFoodCategory = foodCategoryList.get(foodCategoryList.size() - 1);
        assertThat(testFoodCategory.getCatName()).isEqualTo(UPDATED_CAT_NAME);
        assertThat(testFoodCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoodCategory.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingFoodCategory() throws Exception {
        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();
        foodCategory.setId(longCount.incrementAndGet());

        // Create the FoodCategory
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(foodCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, foodCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFoodCategory() throws Exception {
        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();
        foodCategory.setId(longCount.incrementAndGet());

        // Create the FoodCategory
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(foodCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFoodCategory() throws Exception {
        int databaseSizeBeforeUpdate = foodCategoryRepository.findAll().size();
        foodCategory.setId(longCount.incrementAndGet());

        // Create the FoodCategory
        FoodCategoryDTO foodCategoryDTO = foodCategoryMapper.toDto(foodCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoodCategory in the database
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFoodCategory() throws Exception {
        // Initialize the database
        foodCategoryRepository.saveAndFlush(foodCategory);

        int databaseSizeBeforeDelete = foodCategoryRepository.findAll().size();

        // Delete the foodCategory
        restFoodCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, foodCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        assertThat(foodCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
