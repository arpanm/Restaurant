package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.MealPlanItem;
import com.restaurant.angikar.repository.MealPlanItemRepository;
import com.restaurant.angikar.service.MealPlanItemService;
import com.restaurant.angikar.service.dto.MealPlanItemDTO;
import com.restaurant.angikar.service.mapper.MealPlanItemMapper;
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
 * Integration tests for the {@link MealPlanItemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MealPlanItemResourceIT {

    private static final String DEFAULT_PLAN_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_ITEM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_HOUR_VALUE = 1;
    private static final Integer UPDATED_HOUR_VALUE = 2;

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meal-plan-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MealPlanItemRepository mealPlanItemRepository;

    @Mock
    private MealPlanItemRepository mealPlanItemRepositoryMock;

    @Autowired
    private MealPlanItemMapper mealPlanItemMapper;

    @Mock
    private MealPlanItemService mealPlanItemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMealPlanItemMockMvc;

    private MealPlanItem mealPlanItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MealPlanItem createEntity(EntityManager em) {
        MealPlanItem mealPlanItem = new MealPlanItem()
            .planItemName(DEFAULT_PLAN_ITEM_NAME)
            .hourValue(DEFAULT_HOUR_VALUE)
            .pincode(DEFAULT_PINCODE);
        return mealPlanItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MealPlanItem createUpdatedEntity(EntityManager em) {
        MealPlanItem mealPlanItem = new MealPlanItem()
            .planItemName(UPDATED_PLAN_ITEM_NAME)
            .hourValue(UPDATED_HOUR_VALUE)
            .pincode(UPDATED_PINCODE);
        return mealPlanItem;
    }

    @BeforeEach
    public void initTest() {
        mealPlanItem = createEntity(em);
    }

    @Test
    @Transactional
    void createMealPlanItem() throws Exception {
        int databaseSizeBeforeCreate = mealPlanItemRepository.findAll().size();
        // Create the MealPlanItem
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(mealPlanItem);
        restMealPlanItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeCreate + 1);
        MealPlanItem testMealPlanItem = mealPlanItemList.get(mealPlanItemList.size() - 1);
        assertThat(testMealPlanItem.getPlanItemName()).isEqualTo(DEFAULT_PLAN_ITEM_NAME);
        assertThat(testMealPlanItem.getHourValue()).isEqualTo(DEFAULT_HOUR_VALUE);
        assertThat(testMealPlanItem.getPincode()).isEqualTo(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    void createMealPlanItemWithExistingId() throws Exception {
        // Create the MealPlanItem with an existing ID
        mealPlanItem.setId(1L);
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(mealPlanItem);

        int databaseSizeBeforeCreate = mealPlanItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMealPlanItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMealPlanItems() throws Exception {
        // Initialize the database
        mealPlanItemRepository.saveAndFlush(mealPlanItem);

        // Get all the mealPlanItemList
        restMealPlanItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mealPlanItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].planItemName").value(hasItem(DEFAULT_PLAN_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].hourValue").value(hasItem(DEFAULT_HOUR_VALUE)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMealPlanItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(mealPlanItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMealPlanItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mealPlanItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMealPlanItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mealPlanItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMealPlanItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(mealPlanItemRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMealPlanItem() throws Exception {
        // Initialize the database
        mealPlanItemRepository.saveAndFlush(mealPlanItem);

        // Get the mealPlanItem
        restMealPlanItemMockMvc
            .perform(get(ENTITY_API_URL_ID, mealPlanItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mealPlanItem.getId().intValue()))
            .andExpect(jsonPath("$.planItemName").value(DEFAULT_PLAN_ITEM_NAME))
            .andExpect(jsonPath("$.hourValue").value(DEFAULT_HOUR_VALUE))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE));
    }

    @Test
    @Transactional
    void getNonExistingMealPlanItem() throws Exception {
        // Get the mealPlanItem
        restMealPlanItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMealPlanItem() throws Exception {
        // Initialize the database
        mealPlanItemRepository.saveAndFlush(mealPlanItem);

        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();

        // Update the mealPlanItem
        MealPlanItem updatedMealPlanItem = mealPlanItemRepository.findById(mealPlanItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMealPlanItem are not directly saved in db
        em.detach(updatedMealPlanItem);
        updatedMealPlanItem.planItemName(UPDATED_PLAN_ITEM_NAME).hourValue(UPDATED_HOUR_VALUE).pincode(UPDATED_PINCODE);
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(updatedMealPlanItem);

        restMealPlanItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mealPlanItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
        MealPlanItem testMealPlanItem = mealPlanItemList.get(mealPlanItemList.size() - 1);
        assertThat(testMealPlanItem.getPlanItemName()).isEqualTo(UPDATED_PLAN_ITEM_NAME);
        assertThat(testMealPlanItem.getHourValue()).isEqualTo(UPDATED_HOUR_VALUE);
        assertThat(testMealPlanItem.getPincode()).isEqualTo(UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void putNonExistingMealPlanItem() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();
        mealPlanItem.setId(longCount.incrementAndGet());

        // Create the MealPlanItem
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(mealPlanItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMealPlanItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mealPlanItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMealPlanItem() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();
        mealPlanItem.setId(longCount.incrementAndGet());

        // Create the MealPlanItem
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(mealPlanItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMealPlanItem() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();
        mealPlanItem.setId(longCount.incrementAndGet());

        // Create the MealPlanItem
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(mealPlanItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMealPlanItemWithPatch() throws Exception {
        // Initialize the database
        mealPlanItemRepository.saveAndFlush(mealPlanItem);

        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();

        // Update the mealPlanItem using partial update
        MealPlanItem partialUpdatedMealPlanItem = new MealPlanItem();
        partialUpdatedMealPlanItem.setId(mealPlanItem.getId());

        partialUpdatedMealPlanItem.planItemName(UPDATED_PLAN_ITEM_NAME).hourValue(UPDATED_HOUR_VALUE);

        restMealPlanItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMealPlanItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMealPlanItem))
            )
            .andExpect(status().isOk());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
        MealPlanItem testMealPlanItem = mealPlanItemList.get(mealPlanItemList.size() - 1);
        assertThat(testMealPlanItem.getPlanItemName()).isEqualTo(UPDATED_PLAN_ITEM_NAME);
        assertThat(testMealPlanItem.getHourValue()).isEqualTo(UPDATED_HOUR_VALUE);
        assertThat(testMealPlanItem.getPincode()).isEqualTo(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    void fullUpdateMealPlanItemWithPatch() throws Exception {
        // Initialize the database
        mealPlanItemRepository.saveAndFlush(mealPlanItem);

        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();

        // Update the mealPlanItem using partial update
        MealPlanItem partialUpdatedMealPlanItem = new MealPlanItem();
        partialUpdatedMealPlanItem.setId(mealPlanItem.getId());

        partialUpdatedMealPlanItem.planItemName(UPDATED_PLAN_ITEM_NAME).hourValue(UPDATED_HOUR_VALUE).pincode(UPDATED_PINCODE);

        restMealPlanItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMealPlanItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMealPlanItem))
            )
            .andExpect(status().isOk());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
        MealPlanItem testMealPlanItem = mealPlanItemList.get(mealPlanItemList.size() - 1);
        assertThat(testMealPlanItem.getPlanItemName()).isEqualTo(UPDATED_PLAN_ITEM_NAME);
        assertThat(testMealPlanItem.getHourValue()).isEqualTo(UPDATED_HOUR_VALUE);
        assertThat(testMealPlanItem.getPincode()).isEqualTo(UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void patchNonExistingMealPlanItem() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();
        mealPlanItem.setId(longCount.incrementAndGet());

        // Create the MealPlanItem
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(mealPlanItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMealPlanItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mealPlanItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMealPlanItem() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();
        mealPlanItem.setId(longCount.incrementAndGet());

        // Create the MealPlanItem
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(mealPlanItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMealPlanItem() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanItemRepository.findAll().size();
        mealPlanItem.setId(longCount.incrementAndGet());

        // Create the MealPlanItem
        MealPlanItemDTO mealPlanItemDTO = mealPlanItemMapper.toDto(mealPlanItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MealPlanItem in the database
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMealPlanItem() throws Exception {
        // Initialize the database
        mealPlanItemRepository.saveAndFlush(mealPlanItem);

        int databaseSizeBeforeDelete = mealPlanItemRepository.findAll().size();

        // Delete the mealPlanItem
        restMealPlanItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, mealPlanItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MealPlanItem> mealPlanItemList = mealPlanItemRepository.findAll();
        assertThat(mealPlanItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
