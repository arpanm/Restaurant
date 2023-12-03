package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.Refund;
import com.restaurant.angikar.domain.enumeration.PaymentVendor;
import com.restaurant.angikar.domain.enumeration.RefundStatus;
import com.restaurant.angikar.repository.RefundRepository;
import com.restaurant.angikar.service.dto.RefundDTO;
import com.restaurant.angikar.service.mapper.RefundMapper;
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
 * Integration tests for the {@link RefundResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RefundResourceIT {

    private static final RefundStatus DEFAULT_STATUS = RefundStatus.Success;
    private static final RefundStatus UPDATED_STATUS = RefundStatus.InProcess;

    private static final PaymentVendor DEFAULT_VENDOR = PaymentVendor.RazorPay;
    private static final PaymentVendor UPDATED_VENDOR = PaymentVendor.RazorPay;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Instant DEFAULT_INIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/refunds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private RefundMapper refundMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefundMockMvc;

    private Refund refund;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createEntity(EntityManager em) {
        Refund refund = new Refund()
            .status(DEFAULT_STATUS)
            .vendor(DEFAULT_VENDOR)
            .amount(DEFAULT_AMOUNT)
            .initDate(DEFAULT_INIT_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return refund;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createUpdatedEntity(EntityManager em) {
        Refund refund = new Refund()
            .status(UPDATED_STATUS)
            .vendor(UPDATED_VENDOR)
            .amount(UPDATED_AMOUNT)
            .initDate(UPDATED_INIT_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return refund;
    }

    @BeforeEach
    public void initTest() {
        refund = createEntity(em);
    }

    @Test
    @Transactional
    void createRefund() throws Exception {
        int databaseSizeBeforeCreate = refundRepository.findAll().size();
        // Create the Refund
        RefundDTO refundDTO = refundMapper.toDto(refund);
        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refundDTO)))
            .andExpect(status().isCreated());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeCreate + 1);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRefund.getVendor()).isEqualTo(DEFAULT_VENDOR);
        assertThat(testRefund.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRefund.getInitDate()).isEqualTo(DEFAULT_INIT_DATE);
        assertThat(testRefund.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createRefundWithExistingId() throws Exception {
        // Create the Refund with an existing ID
        refund.setId(1L);
        RefundDTO refundDTO = refundMapper.toDto(refund);

        int databaseSizeBeforeCreate = refundRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refundDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRefunds() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        // Get all the refundList
        restRefundMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refund.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        // Get the refund
        restRefundMockMvc
            .perform(get(ENTITY_API_URL_ID, refund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refund.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.vendor").value(DEFAULT_VENDOR.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.initDate").value(DEFAULT_INIT_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRefund() throws Exception {
        // Get the refund
        restRefundMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund
        Refund updatedRefund = refundRepository.findById(refund.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRefund are not directly saved in db
        em.detach(updatedRefund);
        updatedRefund
            .status(UPDATED_STATUS)
            .vendor(UPDATED_VENDOR)
            .amount(UPDATED_AMOUNT)
            .initDate(UPDATED_INIT_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        RefundDTO refundDTO = refundMapper.toDto(updatedRefund);

        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refundDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refundDTO))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRefund.getVendor()).isEqualTo(UPDATED_VENDOR);
        assertThat(testRefund.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRefund.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testRefund.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(longCount.incrementAndGet());

        // Create the Refund
        RefundDTO refundDTO = refundMapper.toDto(refund);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refundDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refundDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(longCount.incrementAndGet());

        // Create the Refund
        RefundDTO refundDTO = refundMapper.toDto(refund);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refundDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(longCount.incrementAndGet());

        // Create the Refund
        RefundDTO refundDTO = refundMapper.toDto(refund);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refundDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRefundWithPatch() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund using partial update
        Refund partialUpdatedRefund = new Refund();
        partialUpdatedRefund.setId(refund.getId());

        partialUpdatedRefund.vendor(UPDATED_VENDOR).initDate(UPDATED_INIT_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefund.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRefund.getVendor()).isEqualTo(UPDATED_VENDOR);
        assertThat(testRefund.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRefund.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testRefund.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRefundWithPatch() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund using partial update
        Refund partialUpdatedRefund = new Refund();
        partialUpdatedRefund.setId(refund.getId());

        partialUpdatedRefund
            .status(UPDATED_STATUS)
            .vendor(UPDATED_VENDOR)
            .amount(UPDATED_AMOUNT)
            .initDate(UPDATED_INIT_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefund.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRefund.getVendor()).isEqualTo(UPDATED_VENDOR);
        assertThat(testRefund.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRefund.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testRefund.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(longCount.incrementAndGet());

        // Create the Refund
        RefundDTO refundDTO = refundMapper.toDto(refund);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, refundDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refundDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(longCount.incrementAndGet());

        // Create the Refund
        RefundDTO refundDTO = refundMapper.toDto(refund);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refundDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(longCount.incrementAndGet());

        // Create the Refund
        RefundDTO refundDTO = refundMapper.toDto(refund);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(refundDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeDelete = refundRepository.findAll().size();

        // Delete the refund
        restRefundMockMvc
            .perform(delete(ENTITY_API_URL_ID, refund.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
