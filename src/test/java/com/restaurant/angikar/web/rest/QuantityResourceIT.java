package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.Quantity;
import com.restaurant.angikar.repository.QuantityRepository;
import com.restaurant.angikar.service.dto.QuantityDTO;
import com.restaurant.angikar.service.mapper.QuantityMapper;
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
 * Integration tests for the {@link QuantityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuantityResourceIT {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Double DEFAULT_MIN_QUANTITY = 1D;
    private static final Double UPDATED_MIN_QUANTITY = 2D;

    private static final String ENTITY_API_URL = "/api/quantities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private QuantityMapper quantityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuantityMockMvc;

    private Quantity quantity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quantity createEntity(EntityManager em) {
        Quantity quantity = new Quantity().quantity(DEFAULT_QUANTITY).minQuantity(DEFAULT_MIN_QUANTITY);
        return quantity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quantity createUpdatedEntity(EntityManager em) {
        Quantity quantity = new Quantity().quantity(UPDATED_QUANTITY).minQuantity(UPDATED_MIN_QUANTITY);
        return quantity;
    }

    @BeforeEach
    public void initTest() {
        quantity = createEntity(em);
    }

    @Test
    @Transactional
    void createQuantity() throws Exception {
        int databaseSizeBeforeCreate = quantityRepository.findAll().size();
        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);
        restQuantityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quantityDTO)))
            .andExpect(status().isCreated());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeCreate + 1);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testQuantity.getMinQuantity()).isEqualTo(DEFAULT_MIN_QUANTITY);
    }

    @Test
    @Transactional
    void createQuantityWithExistingId() throws Exception {
        // Create the Quantity with an existing ID
        quantity.setId(1L);
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        int databaseSizeBeforeCreate = quantityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuantityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quantityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuantities() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        // Get all the quantityList
        restQuantityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quantity.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].minQuantity").value(hasItem(DEFAULT_MIN_QUANTITY.doubleValue())));
    }

    @Test
    @Transactional
    void getQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        // Get the quantity
        restQuantityMockMvc
            .perform(get(ENTITY_API_URL_ID, quantity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quantity.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.minQuantity").value(DEFAULT_MIN_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingQuantity() throws Exception {
        // Get the quantity
        restQuantityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();

        // Update the quantity
        Quantity updatedQuantity = quantityRepository.findById(quantity.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQuantity are not directly saved in db
        em.detach(updatedQuantity);
        updatedQuantity.quantity(UPDATED_QUANTITY).minQuantity(UPDATED_MIN_QUANTITY);
        QuantityDTO quantityDTO = quantityMapper.toDto(updatedQuantity);

        restQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quantityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quantityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testQuantity.getMinQuantity()).isEqualTo(UPDATED_MIN_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(longCount.incrementAndGet());

        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quantityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quantityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(longCount.incrementAndGet());

        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quantityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(longCount.incrementAndGet());

        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quantityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuantityWithPatch() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();

        // Update the quantity using partial update
        Quantity partialUpdatedQuantity = new Quantity();
        partialUpdatedQuantity.setId(quantity.getId());

        partialUpdatedQuantity.quantity(UPDATED_QUANTITY);

        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuantity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuantity))
            )
            .andExpect(status().isOk());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testQuantity.getMinQuantity()).isEqualTo(DEFAULT_MIN_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateQuantityWithPatch() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();

        // Update the quantity using partial update
        Quantity partialUpdatedQuantity = new Quantity();
        partialUpdatedQuantity.setId(quantity.getId());

        partialUpdatedQuantity.quantity(UPDATED_QUANTITY).minQuantity(UPDATED_MIN_QUANTITY);

        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuantity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuantity))
            )
            .andExpect(status().isOk());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testQuantity.getMinQuantity()).isEqualTo(UPDATED_MIN_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(longCount.incrementAndGet());

        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quantityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quantityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(longCount.incrementAndGet());

        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quantityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(longCount.incrementAndGet());

        // Create the Quantity
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quantityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        int databaseSizeBeforeDelete = quantityRepository.findAll().size();

        // Delete the quantity
        restQuantityMockMvc
            .perform(delete(ENTITY_API_URL_ID, quantity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
