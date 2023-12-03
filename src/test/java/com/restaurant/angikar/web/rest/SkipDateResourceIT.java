package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.SkipDate;
import com.restaurant.angikar.repository.SkipDateRepository;
import com.restaurant.angikar.service.dto.SkipDateDTO;
import com.restaurant.angikar.service.mapper.SkipDateMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link SkipDateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkipDateResourceIT {

    private static final LocalDate DEFAULT_SKIP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SKIP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/skip-dates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SkipDateRepository skipDateRepository;

    @Autowired
    private SkipDateMapper skipDateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkipDateMockMvc;

    private SkipDate skipDate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkipDate createEntity(EntityManager em) {
        SkipDate skipDate = new SkipDate().skipDate(DEFAULT_SKIP_DATE);
        return skipDate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkipDate createUpdatedEntity(EntityManager em) {
        SkipDate skipDate = new SkipDate().skipDate(UPDATED_SKIP_DATE);
        return skipDate;
    }

    @BeforeEach
    public void initTest() {
        skipDate = createEntity(em);
    }

    @Test
    @Transactional
    void createSkipDate() throws Exception {
        int databaseSizeBeforeCreate = skipDateRepository.findAll().size();
        // Create the SkipDate
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(skipDate);
        restSkipDateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skipDateDTO)))
            .andExpect(status().isCreated());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeCreate + 1);
        SkipDate testSkipDate = skipDateList.get(skipDateList.size() - 1);
        assertThat(testSkipDate.getSkipDate()).isEqualTo(DEFAULT_SKIP_DATE);
    }

    @Test
    @Transactional
    void createSkipDateWithExistingId() throws Exception {
        // Create the SkipDate with an existing ID
        skipDate.setId(1L);
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(skipDate);

        int databaseSizeBeforeCreate = skipDateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkipDateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skipDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSkipDates() throws Exception {
        // Initialize the database
        skipDateRepository.saveAndFlush(skipDate);

        // Get all the skipDateList
        restSkipDateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skipDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].skipDate").value(hasItem(DEFAULT_SKIP_DATE.toString())));
    }

    @Test
    @Transactional
    void getSkipDate() throws Exception {
        // Initialize the database
        skipDateRepository.saveAndFlush(skipDate);

        // Get the skipDate
        restSkipDateMockMvc
            .perform(get(ENTITY_API_URL_ID, skipDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skipDate.getId().intValue()))
            .andExpect(jsonPath("$.skipDate").value(DEFAULT_SKIP_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSkipDate() throws Exception {
        // Get the skipDate
        restSkipDateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSkipDate() throws Exception {
        // Initialize the database
        skipDateRepository.saveAndFlush(skipDate);

        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();

        // Update the skipDate
        SkipDate updatedSkipDate = skipDateRepository.findById(skipDate.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSkipDate are not directly saved in db
        em.detach(updatedSkipDate);
        updatedSkipDate.skipDate(UPDATED_SKIP_DATE);
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(updatedSkipDate);

        restSkipDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skipDateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skipDateDTO))
            )
            .andExpect(status().isOk());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
        SkipDate testSkipDate = skipDateList.get(skipDateList.size() - 1);
        assertThat(testSkipDate.getSkipDate()).isEqualTo(UPDATED_SKIP_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSkipDate() throws Exception {
        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();
        skipDate.setId(longCount.incrementAndGet());

        // Create the SkipDate
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(skipDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkipDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skipDateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skipDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSkipDate() throws Exception {
        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();
        skipDate.setId(longCount.incrementAndGet());

        // Create the SkipDate
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(skipDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkipDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skipDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkipDate() throws Exception {
        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();
        skipDate.setId(longCount.incrementAndGet());

        // Create the SkipDate
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(skipDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkipDateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skipDateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkipDateWithPatch() throws Exception {
        // Initialize the database
        skipDateRepository.saveAndFlush(skipDate);

        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();

        // Update the skipDate using partial update
        SkipDate partialUpdatedSkipDate = new SkipDate();
        partialUpdatedSkipDate.setId(skipDate.getId());

        restSkipDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkipDate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkipDate))
            )
            .andExpect(status().isOk());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
        SkipDate testSkipDate = skipDateList.get(skipDateList.size() - 1);
        assertThat(testSkipDate.getSkipDate()).isEqualTo(DEFAULT_SKIP_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSkipDateWithPatch() throws Exception {
        // Initialize the database
        skipDateRepository.saveAndFlush(skipDate);

        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();

        // Update the skipDate using partial update
        SkipDate partialUpdatedSkipDate = new SkipDate();
        partialUpdatedSkipDate.setId(skipDate.getId());

        partialUpdatedSkipDate.skipDate(UPDATED_SKIP_DATE);

        restSkipDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkipDate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkipDate))
            )
            .andExpect(status().isOk());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
        SkipDate testSkipDate = skipDateList.get(skipDateList.size() - 1);
        assertThat(testSkipDate.getSkipDate()).isEqualTo(UPDATED_SKIP_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSkipDate() throws Exception {
        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();
        skipDate.setId(longCount.incrementAndGet());

        // Create the SkipDate
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(skipDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkipDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skipDateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skipDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkipDate() throws Exception {
        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();
        skipDate.setId(longCount.incrementAndGet());

        // Create the SkipDate
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(skipDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkipDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skipDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkipDate() throws Exception {
        int databaseSizeBeforeUpdate = skipDateRepository.findAll().size();
        skipDate.setId(longCount.incrementAndGet());

        // Create the SkipDate
        SkipDateDTO skipDateDTO = skipDateMapper.toDto(skipDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkipDateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(skipDateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkipDate in the database
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSkipDate() throws Exception {
        // Initialize the database
        skipDateRepository.saveAndFlush(skipDate);

        int databaseSizeBeforeDelete = skipDateRepository.findAll().size();

        // Delete the skipDate
        restSkipDateMockMvc
            .perform(delete(ENTITY_API_URL_ID, skipDate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkipDate> skipDateList = skipDateRepository.findAll();
        assertThat(skipDateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
