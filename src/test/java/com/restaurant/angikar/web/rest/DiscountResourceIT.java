package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.Discount;
import com.restaurant.angikar.domain.enumeration.DiscountSlab;
import com.restaurant.angikar.repository.DiscountRepository;
import com.restaurant.angikar.service.dto.DiscountDTO;
import com.restaurant.angikar.service.mapper.DiscountMapper;
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
 * Integration tests for the {@link DiscountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiscountResourceIT {

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final DiscountSlab DEFAULT_SLAB = DiscountSlab.OneDay;
    private static final DiscountSlab UPDATED_SLAB = DiscountSlab.SevenDay;

    private static final String ENTITY_API_URL = "/api/discounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscountMockMvc;

    private Discount discount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discount createEntity(EntityManager em) {
        Discount discount = new Discount().discount(DEFAULT_DISCOUNT).slab(DEFAULT_SLAB);
        return discount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discount createUpdatedEntity(EntityManager em) {
        Discount discount = new Discount().discount(UPDATED_DISCOUNT).slab(UPDATED_SLAB);
        return discount;
    }

    @BeforeEach
    public void initTest() {
        discount = createEntity(em);
    }

    @Test
    @Transactional
    void createDiscount() throws Exception {
        int databaseSizeBeforeCreate = discountRepository.findAll().size();
        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);
        restDiscountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isCreated());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeCreate + 1);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testDiscount.getSlab()).isEqualTo(DEFAULT_SLAB);
    }

    @Test
    @Transactional
    void createDiscountWithExistingId() throws Exception {
        // Create the Discount with an existing ID
        discount.setId(1L);
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        int databaseSizeBeforeCreate = discountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiscounts() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList
        restDiscountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discount.getId().intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].slab").value(hasItem(DEFAULT_SLAB.toString())));
    }

    @Test
    @Transactional
    void getDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get the discount
        restDiscountMockMvc
            .perform(get(ENTITY_API_URL_ID, discount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discount.getId().intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.slab").value(DEFAULT_SLAB.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDiscount() throws Exception {
        // Get the discount
        restDiscountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Update the discount
        Discount updatedDiscount = discountRepository.findById(discount.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDiscount are not directly saved in db
        em.detach(updatedDiscount);
        updatedDiscount.discount(UPDATED_DISCOUNT).slab(UPDATED_SLAB);
        DiscountDTO discountDTO = discountMapper.toDto(updatedDiscount);

        restDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, discountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(discountDTO))
            )
            .andExpect(status().isOk());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testDiscount.getSlab()).isEqualTo(UPDATED_SLAB);
    }

    @Test
    @Transactional
    void putNonExistingDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();
        discount.setId(longCount.incrementAndGet());

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, discountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(discountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();
        discount.setId(longCount.incrementAndGet());

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(discountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();
        discount.setId(longCount.incrementAndGet());

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiscountWithPatch() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Update the discount using partial update
        Discount partialUpdatedDiscount = new Discount();
        partialUpdatedDiscount.setId(discount.getId());

        partialUpdatedDiscount.slab(UPDATED_SLAB);

        restDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiscount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiscount))
            )
            .andExpect(status().isOk());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testDiscount.getSlab()).isEqualTo(UPDATED_SLAB);
    }

    @Test
    @Transactional
    void fullUpdateDiscountWithPatch() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Update the discount using partial update
        Discount partialUpdatedDiscount = new Discount();
        partialUpdatedDiscount.setId(discount.getId());

        partialUpdatedDiscount.discount(UPDATED_DISCOUNT).slab(UPDATED_SLAB);

        restDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiscount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiscount))
            )
            .andExpect(status().isOk());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testDiscount.getSlab()).isEqualTo(UPDATED_SLAB);
    }

    @Test
    @Transactional
    void patchNonExistingDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();
        discount.setId(longCount.incrementAndGet());

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, discountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(discountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();
        discount.setId(longCount.incrementAndGet());

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(discountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();
        discount.setId(longCount.incrementAndGet());

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(discountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeDelete = discountRepository.findAll().size();

        // Delete the discount
        restDiscountMockMvc
            .perform(delete(ENTITY_API_URL_ID, discount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
