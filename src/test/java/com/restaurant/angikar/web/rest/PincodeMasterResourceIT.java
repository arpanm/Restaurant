package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.PincodeMaster;
import com.restaurant.angikar.repository.PincodeMasterRepository;
import com.restaurant.angikar.service.dto.PincodeMasterDTO;
import com.restaurant.angikar.service.mapper.PincodeMasterMapper;
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
 * Integration tests for the {@link PincodeMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PincodeMasterResourceIT {

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pincode-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PincodeMasterRepository pincodeMasterRepository;

    @Autowired
    private PincodeMasterMapper pincodeMasterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPincodeMasterMockMvc;

    private PincodeMaster pincodeMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PincodeMaster createEntity(EntityManager em) {
        PincodeMaster pincodeMaster = new PincodeMaster()
            .pincode(DEFAULT_PINCODE)
            .area(DEFAULT_AREA)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .country(DEFAULT_COUNTRY);
        return pincodeMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PincodeMaster createUpdatedEntity(EntityManager em) {
        PincodeMaster pincodeMaster = new PincodeMaster()
            .pincode(UPDATED_PINCODE)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY);
        return pincodeMaster;
    }

    @BeforeEach
    public void initTest() {
        pincodeMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createPincodeMaster() throws Exception {
        int databaseSizeBeforeCreate = pincodeMasterRepository.findAll().size();
        // Create the PincodeMaster
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(pincodeMaster);
        restPincodeMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        PincodeMaster testPincodeMaster = pincodeMasterList.get(pincodeMasterList.size() - 1);
        assertThat(testPincodeMaster.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testPincodeMaster.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testPincodeMaster.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPincodeMaster.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testPincodeMaster.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void createPincodeMasterWithExistingId() throws Exception {
        // Create the PincodeMaster with an existing ID
        pincodeMaster.setId(1L);
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(pincodeMaster);

        int databaseSizeBeforeCreate = pincodeMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPincodeMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPincodeMasters() throws Exception {
        // Initialize the database
        pincodeMasterRepository.saveAndFlush(pincodeMaster);

        // Get all the pincodeMasterList
        restPincodeMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincodeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }

    @Test
    @Transactional
    void getPincodeMaster() throws Exception {
        // Initialize the database
        pincodeMasterRepository.saveAndFlush(pincodeMaster);

        // Get the pincodeMaster
        restPincodeMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, pincodeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pincodeMaster.getId().intValue()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    void getNonExistingPincodeMaster() throws Exception {
        // Get the pincodeMaster
        restPincodeMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPincodeMaster() throws Exception {
        // Initialize the database
        pincodeMasterRepository.saveAndFlush(pincodeMaster);

        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();

        // Update the pincodeMaster
        PincodeMaster updatedPincodeMaster = pincodeMasterRepository.findById(pincodeMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPincodeMaster are not directly saved in db
        em.detach(updatedPincodeMaster);
        updatedPincodeMaster
            .pincode(UPDATED_PINCODE)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY);
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(updatedPincodeMaster);

        restPincodeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pincodeMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
        PincodeMaster testPincodeMaster = pincodeMasterList.get(pincodeMasterList.size() - 1);
        assertThat(testPincodeMaster.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testPincodeMaster.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testPincodeMaster.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPincodeMaster.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testPincodeMaster.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void putNonExistingPincodeMaster() throws Exception {
        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();
        pincodeMaster.setId(longCount.incrementAndGet());

        // Create the PincodeMaster
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(pincodeMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPincodeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pincodeMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPincodeMaster() throws Exception {
        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();
        pincodeMaster.setId(longCount.incrementAndGet());

        // Create the PincodeMaster
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(pincodeMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPincodeMaster() throws Exception {
        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();
        pincodeMaster.setId(longCount.incrementAndGet());

        // Create the PincodeMaster
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(pincodeMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePincodeMasterWithPatch() throws Exception {
        // Initialize the database
        pincodeMasterRepository.saveAndFlush(pincodeMaster);

        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();

        // Update the pincodeMaster using partial update
        PincodeMaster partialUpdatedPincodeMaster = new PincodeMaster();
        partialUpdatedPincodeMaster.setId(pincodeMaster.getId());

        partialUpdatedPincodeMaster.stateProvince(UPDATED_STATE_PROVINCE).country(UPDATED_COUNTRY);

        restPincodeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPincodeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPincodeMaster))
            )
            .andExpect(status().isOk());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
        PincodeMaster testPincodeMaster = pincodeMasterList.get(pincodeMasterList.size() - 1);
        assertThat(testPincodeMaster.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testPincodeMaster.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testPincodeMaster.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPincodeMaster.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testPincodeMaster.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdatePincodeMasterWithPatch() throws Exception {
        // Initialize the database
        pincodeMasterRepository.saveAndFlush(pincodeMaster);

        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();

        // Update the pincodeMaster using partial update
        PincodeMaster partialUpdatedPincodeMaster = new PincodeMaster();
        partialUpdatedPincodeMaster.setId(pincodeMaster.getId());

        partialUpdatedPincodeMaster
            .pincode(UPDATED_PINCODE)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY);

        restPincodeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPincodeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPincodeMaster))
            )
            .andExpect(status().isOk());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
        PincodeMaster testPincodeMaster = pincodeMasterList.get(pincodeMasterList.size() - 1);
        assertThat(testPincodeMaster.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testPincodeMaster.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testPincodeMaster.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPincodeMaster.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testPincodeMaster.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingPincodeMaster() throws Exception {
        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();
        pincodeMaster.setId(longCount.incrementAndGet());

        // Create the PincodeMaster
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(pincodeMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPincodeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pincodeMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPincodeMaster() throws Exception {
        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();
        pincodeMaster.setId(longCount.incrementAndGet());

        // Create the PincodeMaster
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(pincodeMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPincodeMaster() throws Exception {
        int databaseSizeBeforeUpdate = pincodeMasterRepository.findAll().size();
        pincodeMaster.setId(longCount.incrementAndGet());

        // Create the PincodeMaster
        PincodeMasterDTO pincodeMasterDTO = pincodeMasterMapper.toDto(pincodeMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pincodeMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PincodeMaster in the database
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePincodeMaster() throws Exception {
        // Initialize the database
        pincodeMasterRepository.saveAndFlush(pincodeMaster);

        int databaseSizeBeforeDelete = pincodeMasterRepository.findAll().size();

        // Delete the pincodeMaster
        restPincodeMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, pincodeMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PincodeMaster> pincodeMasterList = pincodeMasterRepository.findAll();
        assertThat(pincodeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
