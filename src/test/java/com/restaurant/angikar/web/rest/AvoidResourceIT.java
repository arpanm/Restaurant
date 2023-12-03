package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.Avoid;
import com.restaurant.angikar.repository.AvoidRepository;
import com.restaurant.angikar.service.dto.AvoidDTO;
import com.restaurant.angikar.service.mapper.AvoidMapper;
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
 * Integration tests for the {@link AvoidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvoidResourceIT {

    private static final String DEFAULT_AVOID_INGREDIENCE = "AAAAAAAAAA";
    private static final String UPDATED_AVOID_INGREDIENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/avoids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvoidRepository avoidRepository;

    @Autowired
    private AvoidMapper avoidMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvoidMockMvc;

    private Avoid avoid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avoid createEntity(EntityManager em) {
        Avoid avoid = new Avoid().avoidIngredience(DEFAULT_AVOID_INGREDIENCE);
        return avoid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avoid createUpdatedEntity(EntityManager em) {
        Avoid avoid = new Avoid().avoidIngredience(UPDATED_AVOID_INGREDIENCE);
        return avoid;
    }

    @BeforeEach
    public void initTest() {
        avoid = createEntity(em);
    }

    @Test
    @Transactional
    void createAvoid() throws Exception {
        int databaseSizeBeforeCreate = avoidRepository.findAll().size();
        // Create the Avoid
        AvoidDTO avoidDTO = avoidMapper.toDto(avoid);
        restAvoidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avoidDTO)))
            .andExpect(status().isCreated());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeCreate + 1);
        Avoid testAvoid = avoidList.get(avoidList.size() - 1);
        assertThat(testAvoid.getAvoidIngredience()).isEqualTo(DEFAULT_AVOID_INGREDIENCE);
    }

    @Test
    @Transactional
    void createAvoidWithExistingId() throws Exception {
        // Create the Avoid with an existing ID
        avoid.setId(1L);
        AvoidDTO avoidDTO = avoidMapper.toDto(avoid);

        int databaseSizeBeforeCreate = avoidRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvoidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avoidDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAvoids() throws Exception {
        // Initialize the database
        avoidRepository.saveAndFlush(avoid);

        // Get all the avoidList
        restAvoidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avoid.getId().intValue())))
            .andExpect(jsonPath("$.[*].avoidIngredience").value(hasItem(DEFAULT_AVOID_INGREDIENCE)));
    }

    @Test
    @Transactional
    void getAvoid() throws Exception {
        // Initialize the database
        avoidRepository.saveAndFlush(avoid);

        // Get the avoid
        restAvoidMockMvc
            .perform(get(ENTITY_API_URL_ID, avoid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avoid.getId().intValue()))
            .andExpect(jsonPath("$.avoidIngredience").value(DEFAULT_AVOID_INGREDIENCE));
    }

    @Test
    @Transactional
    void getNonExistingAvoid() throws Exception {
        // Get the avoid
        restAvoidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAvoid() throws Exception {
        // Initialize the database
        avoidRepository.saveAndFlush(avoid);

        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();

        // Update the avoid
        Avoid updatedAvoid = avoidRepository.findById(avoid.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAvoid are not directly saved in db
        em.detach(updatedAvoid);
        updatedAvoid.avoidIngredience(UPDATED_AVOID_INGREDIENCE);
        AvoidDTO avoidDTO = avoidMapper.toDto(updatedAvoid);

        restAvoidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avoidDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avoidDTO))
            )
            .andExpect(status().isOk());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
        Avoid testAvoid = avoidList.get(avoidList.size() - 1);
        assertThat(testAvoid.getAvoidIngredience()).isEqualTo(UPDATED_AVOID_INGREDIENCE);
    }

    @Test
    @Transactional
    void putNonExistingAvoid() throws Exception {
        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();
        avoid.setId(longCount.incrementAndGet());

        // Create the Avoid
        AvoidDTO avoidDTO = avoidMapper.toDto(avoid);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvoidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avoidDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avoidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvoid() throws Exception {
        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();
        avoid.setId(longCount.incrementAndGet());

        // Create the Avoid
        AvoidDTO avoidDTO = avoidMapper.toDto(avoid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvoidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avoidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvoid() throws Exception {
        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();
        avoid.setId(longCount.incrementAndGet());

        // Create the Avoid
        AvoidDTO avoidDTO = avoidMapper.toDto(avoid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvoidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avoidDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvoidWithPatch() throws Exception {
        // Initialize the database
        avoidRepository.saveAndFlush(avoid);

        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();

        // Update the avoid using partial update
        Avoid partialUpdatedAvoid = new Avoid();
        partialUpdatedAvoid.setId(avoid.getId());

        restAvoidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvoid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvoid))
            )
            .andExpect(status().isOk());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
        Avoid testAvoid = avoidList.get(avoidList.size() - 1);
        assertThat(testAvoid.getAvoidIngredience()).isEqualTo(DEFAULT_AVOID_INGREDIENCE);
    }

    @Test
    @Transactional
    void fullUpdateAvoidWithPatch() throws Exception {
        // Initialize the database
        avoidRepository.saveAndFlush(avoid);

        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();

        // Update the avoid using partial update
        Avoid partialUpdatedAvoid = new Avoid();
        partialUpdatedAvoid.setId(avoid.getId());

        partialUpdatedAvoid.avoidIngredience(UPDATED_AVOID_INGREDIENCE);

        restAvoidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvoid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvoid))
            )
            .andExpect(status().isOk());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
        Avoid testAvoid = avoidList.get(avoidList.size() - 1);
        assertThat(testAvoid.getAvoidIngredience()).isEqualTo(UPDATED_AVOID_INGREDIENCE);
    }

    @Test
    @Transactional
    void patchNonExistingAvoid() throws Exception {
        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();
        avoid.setId(longCount.incrementAndGet());

        // Create the Avoid
        AvoidDTO avoidDTO = avoidMapper.toDto(avoid);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvoidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avoidDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avoidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvoid() throws Exception {
        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();
        avoid.setId(longCount.incrementAndGet());

        // Create the Avoid
        AvoidDTO avoidDTO = avoidMapper.toDto(avoid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvoidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avoidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvoid() throws Exception {
        int databaseSizeBeforeUpdate = avoidRepository.findAll().size();
        avoid.setId(longCount.incrementAndGet());

        // Create the Avoid
        AvoidDTO avoidDTO = avoidMapper.toDto(avoid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvoidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avoidDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avoid in the database
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvoid() throws Exception {
        // Initialize the database
        avoidRepository.saveAndFlush(avoid);

        int databaseSizeBeforeDelete = avoidRepository.findAll().size();

        // Delete the avoid
        restAvoidMockMvc
            .perform(delete(ENTITY_API_URL_ID, avoid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avoid> avoidList = avoidRepository.findAll();
        assertThat(avoidList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
