package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.QtyUnit;
import com.restaurant.angikar.repository.QtyUnitRepository;
import com.restaurant.angikar.service.dto.QtyUnitDTO;
import com.restaurant.angikar.service.mapper.QtyUnitMapper;
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
 * Integration tests for the {@link QtyUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QtyUnitResourceIT {

    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/qty-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QtyUnitRepository qtyUnitRepository;

    @Autowired
    private QtyUnitMapper qtyUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQtyUnitMockMvc;

    private QtyUnit qtyUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QtyUnit createEntity(EntityManager em) {
        QtyUnit qtyUnit = new QtyUnit().unitName(DEFAULT_UNIT_NAME);
        return qtyUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QtyUnit createUpdatedEntity(EntityManager em) {
        QtyUnit qtyUnit = new QtyUnit().unitName(UPDATED_UNIT_NAME);
        return qtyUnit;
    }

    @BeforeEach
    public void initTest() {
        qtyUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createQtyUnit() throws Exception {
        int databaseSizeBeforeCreate = qtyUnitRepository.findAll().size();
        // Create the QtyUnit
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(qtyUnit);
        restQtyUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeCreate + 1);
        QtyUnit testQtyUnit = qtyUnitList.get(qtyUnitList.size() - 1);
        assertThat(testQtyUnit.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
    }

    @Test
    @Transactional
    void createQtyUnitWithExistingId() throws Exception {
        // Create the QtyUnit with an existing ID
        qtyUnit.setId(1L);
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(qtyUnit);

        int databaseSizeBeforeCreate = qtyUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQtyUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQtyUnits() throws Exception {
        // Initialize the database
        qtyUnitRepository.saveAndFlush(qtyUnit);

        // Get all the qtyUnitList
        restQtyUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qtyUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)));
    }

    @Test
    @Transactional
    void getQtyUnit() throws Exception {
        // Initialize the database
        qtyUnitRepository.saveAndFlush(qtyUnit);

        // Get the qtyUnit
        restQtyUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, qtyUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qtyUnit.getId().intValue()))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingQtyUnit() throws Exception {
        // Get the qtyUnit
        restQtyUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQtyUnit() throws Exception {
        // Initialize the database
        qtyUnitRepository.saveAndFlush(qtyUnit);

        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();

        // Update the qtyUnit
        QtyUnit updatedQtyUnit = qtyUnitRepository.findById(qtyUnit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQtyUnit are not directly saved in db
        em.detach(updatedQtyUnit);
        updatedQtyUnit.unitName(UPDATED_UNIT_NAME);
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(updatedQtyUnit);

        restQtyUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qtyUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
        QtyUnit testQtyUnit = qtyUnitList.get(qtyUnitList.size() - 1);
        assertThat(testQtyUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingQtyUnit() throws Exception {
        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();
        qtyUnit.setId(longCount.incrementAndGet());

        // Create the QtyUnit
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(qtyUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQtyUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qtyUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQtyUnit() throws Exception {
        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();
        qtyUnit.setId(longCount.incrementAndGet());

        // Create the QtyUnit
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(qtyUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQtyUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQtyUnit() throws Exception {
        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();
        qtyUnit.setId(longCount.incrementAndGet());

        // Create the QtyUnit
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(qtyUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQtyUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQtyUnitWithPatch() throws Exception {
        // Initialize the database
        qtyUnitRepository.saveAndFlush(qtyUnit);

        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();

        // Update the qtyUnit using partial update
        QtyUnit partialUpdatedQtyUnit = new QtyUnit();
        partialUpdatedQtyUnit.setId(qtyUnit.getId());

        restQtyUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQtyUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQtyUnit))
            )
            .andExpect(status().isOk());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
        QtyUnit testQtyUnit = qtyUnitList.get(qtyUnitList.size() - 1);
        assertThat(testQtyUnit.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateQtyUnitWithPatch() throws Exception {
        // Initialize the database
        qtyUnitRepository.saveAndFlush(qtyUnit);

        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();

        // Update the qtyUnit using partial update
        QtyUnit partialUpdatedQtyUnit = new QtyUnit();
        partialUpdatedQtyUnit.setId(qtyUnit.getId());

        partialUpdatedQtyUnit.unitName(UPDATED_UNIT_NAME);

        restQtyUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQtyUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQtyUnit))
            )
            .andExpect(status().isOk());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
        QtyUnit testQtyUnit = qtyUnitList.get(qtyUnitList.size() - 1);
        assertThat(testQtyUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingQtyUnit() throws Exception {
        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();
        qtyUnit.setId(longCount.incrementAndGet());

        // Create the QtyUnit
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(qtyUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQtyUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qtyUnitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQtyUnit() throws Exception {
        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();
        qtyUnit.setId(longCount.incrementAndGet());

        // Create the QtyUnit
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(qtyUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQtyUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQtyUnit() throws Exception {
        int databaseSizeBeforeUpdate = qtyUnitRepository.findAll().size();
        qtyUnit.setId(longCount.incrementAndGet());

        // Create the QtyUnit
        QtyUnitDTO qtyUnitDTO = qtyUnitMapper.toDto(qtyUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQtyUnitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qtyUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QtyUnit in the database
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQtyUnit() throws Exception {
        // Initialize the database
        qtyUnitRepository.saveAndFlush(qtyUnit);

        int databaseSizeBeforeDelete = qtyUnitRepository.findAll().size();

        // Delete the qtyUnit
        restQtyUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, qtyUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QtyUnit> qtyUnitList = qtyUnitRepository.findAll();
        assertThat(qtyUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
