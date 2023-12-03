package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.ItemPincodeMapping;
import com.restaurant.angikar.repository.ItemPincodeMappingRepository;
import com.restaurant.angikar.service.dto.ItemPincodeMappingDTO;
import com.restaurant.angikar.service.mapper.ItemPincodeMappingMapper;
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
 * Integration tests for the {@link ItemPincodeMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItemPincodeMappingResourceIT {

    private static final String DEFAULT_SERVICEABILITY = "AAAAAAAAAA";
    private static final String UPDATED_SERVICEABILITY = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/item-pincode-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemPincodeMappingRepository itemPincodeMappingRepository;

    @Autowired
    private ItemPincodeMappingMapper itemPincodeMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemPincodeMappingMockMvc;

    private ItemPincodeMapping itemPincodeMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPincodeMapping createEntity(EntityManager em) {
        ItemPincodeMapping itemPincodeMapping = new ItemPincodeMapping().serviceability(DEFAULT_SERVICEABILITY).pincode(DEFAULT_PINCODE);
        return itemPincodeMapping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPincodeMapping createUpdatedEntity(EntityManager em) {
        ItemPincodeMapping itemPincodeMapping = new ItemPincodeMapping().serviceability(UPDATED_SERVICEABILITY).pincode(UPDATED_PINCODE);
        return itemPincodeMapping;
    }

    @BeforeEach
    public void initTest() {
        itemPincodeMapping = createEntity(em);
    }

    @Test
    @Transactional
    void createItemPincodeMapping() throws Exception {
        int databaseSizeBeforeCreate = itemPincodeMappingRepository.findAll().size();
        // Create the ItemPincodeMapping
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(itemPincodeMapping);
        restItemPincodeMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPincodeMapping testItemPincodeMapping = itemPincodeMappingList.get(itemPincodeMappingList.size() - 1);
        assertThat(testItemPincodeMapping.getServiceability()).isEqualTo(DEFAULT_SERVICEABILITY);
        assertThat(testItemPincodeMapping.getPincode()).isEqualTo(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    void createItemPincodeMappingWithExistingId() throws Exception {
        // Create the ItemPincodeMapping with an existing ID
        itemPincodeMapping.setId(1L);
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(itemPincodeMapping);

        int databaseSizeBeforeCreate = itemPincodeMappingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPincodeMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllItemPincodeMappings() throws Exception {
        // Initialize the database
        itemPincodeMappingRepository.saveAndFlush(itemPincodeMapping);

        // Get all the itemPincodeMappingList
        restItemPincodeMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPincodeMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceability").value(hasItem(DEFAULT_SERVICEABILITY)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)));
    }

    @Test
    @Transactional
    void getItemPincodeMapping() throws Exception {
        // Initialize the database
        itemPincodeMappingRepository.saveAndFlush(itemPincodeMapping);

        // Get the itemPincodeMapping
        restItemPincodeMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, itemPincodeMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemPincodeMapping.getId().intValue()))
            .andExpect(jsonPath("$.serviceability").value(DEFAULT_SERVICEABILITY))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE));
    }

    @Test
    @Transactional
    void getNonExistingItemPincodeMapping() throws Exception {
        // Get the itemPincodeMapping
        restItemPincodeMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingItemPincodeMapping() throws Exception {
        // Initialize the database
        itemPincodeMappingRepository.saveAndFlush(itemPincodeMapping);

        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();

        // Update the itemPincodeMapping
        ItemPincodeMapping updatedItemPincodeMapping = itemPincodeMappingRepository.findById(itemPincodeMapping.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedItemPincodeMapping are not directly saved in db
        em.detach(updatedItemPincodeMapping);
        updatedItemPincodeMapping.serviceability(UPDATED_SERVICEABILITY).pincode(UPDATED_PINCODE);
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(updatedItemPincodeMapping);

        restItemPincodeMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemPincodeMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
        ItemPincodeMapping testItemPincodeMapping = itemPincodeMappingList.get(itemPincodeMappingList.size() - 1);
        assertThat(testItemPincodeMapping.getServiceability()).isEqualTo(UPDATED_SERVICEABILITY);
        assertThat(testItemPincodeMapping.getPincode()).isEqualTo(UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void putNonExistingItemPincodeMapping() throws Exception {
        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();
        itemPincodeMapping.setId(longCount.incrementAndGet());

        // Create the ItemPincodeMapping
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(itemPincodeMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPincodeMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemPincodeMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemPincodeMapping() throws Exception {
        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();
        itemPincodeMapping.setId(longCount.incrementAndGet());

        // Create the ItemPincodeMapping
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(itemPincodeMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemPincodeMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemPincodeMapping() throws Exception {
        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();
        itemPincodeMapping.setId(longCount.incrementAndGet());

        // Create the ItemPincodeMapping
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(itemPincodeMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemPincodeMappingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemPincodeMappingWithPatch() throws Exception {
        // Initialize the database
        itemPincodeMappingRepository.saveAndFlush(itemPincodeMapping);

        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();

        // Update the itemPincodeMapping using partial update
        ItemPincodeMapping partialUpdatedItemPincodeMapping = new ItemPincodeMapping();
        partialUpdatedItemPincodeMapping.setId(itemPincodeMapping.getId());

        partialUpdatedItemPincodeMapping.serviceability(UPDATED_SERVICEABILITY);

        restItemPincodeMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemPincodeMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemPincodeMapping))
            )
            .andExpect(status().isOk());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
        ItemPincodeMapping testItemPincodeMapping = itemPincodeMappingList.get(itemPincodeMappingList.size() - 1);
        assertThat(testItemPincodeMapping.getServiceability()).isEqualTo(UPDATED_SERVICEABILITY);
        assertThat(testItemPincodeMapping.getPincode()).isEqualTo(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    void fullUpdateItemPincodeMappingWithPatch() throws Exception {
        // Initialize the database
        itemPincodeMappingRepository.saveAndFlush(itemPincodeMapping);

        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();

        // Update the itemPincodeMapping using partial update
        ItemPincodeMapping partialUpdatedItemPincodeMapping = new ItemPincodeMapping();
        partialUpdatedItemPincodeMapping.setId(itemPincodeMapping.getId());

        partialUpdatedItemPincodeMapping.serviceability(UPDATED_SERVICEABILITY).pincode(UPDATED_PINCODE);

        restItemPincodeMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemPincodeMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemPincodeMapping))
            )
            .andExpect(status().isOk());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
        ItemPincodeMapping testItemPincodeMapping = itemPincodeMappingList.get(itemPincodeMappingList.size() - 1);
        assertThat(testItemPincodeMapping.getServiceability()).isEqualTo(UPDATED_SERVICEABILITY);
        assertThat(testItemPincodeMapping.getPincode()).isEqualTo(UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void patchNonExistingItemPincodeMapping() throws Exception {
        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();
        itemPincodeMapping.setId(longCount.incrementAndGet());

        // Create the ItemPincodeMapping
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(itemPincodeMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPincodeMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemPincodeMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemPincodeMapping() throws Exception {
        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();
        itemPincodeMapping.setId(longCount.incrementAndGet());

        // Create the ItemPincodeMapping
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(itemPincodeMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemPincodeMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemPincodeMapping() throws Exception {
        int databaseSizeBeforeUpdate = itemPincodeMappingRepository.findAll().size();
        itemPincodeMapping.setId(longCount.incrementAndGet());

        // Create the ItemPincodeMapping
        ItemPincodeMappingDTO itemPincodeMappingDTO = itemPincodeMappingMapper.toDto(itemPincodeMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemPincodeMappingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemPincodeMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemPincodeMapping in the database
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemPincodeMapping() throws Exception {
        // Initialize the database
        itemPincodeMappingRepository.saveAndFlush(itemPincodeMapping);

        int databaseSizeBeforeDelete = itemPincodeMappingRepository.findAll().size();

        // Delete the itemPincodeMapping
        restItemPincodeMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemPincodeMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemPincodeMapping> itemPincodeMappingList = itemPincodeMappingRepository.findAll();
        assertThat(itemPincodeMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
