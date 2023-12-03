package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.IngredienceMaster;
import com.restaurant.angikar.repository.IngredienceMasterRepository;
import com.restaurant.angikar.service.dto.IngredienceMasterDTO;
import com.restaurant.angikar.service.mapper.IngredienceMasterMapper;
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
 * Integration tests for the {@link IngredienceMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IngredienceMasterResourceIT {

    private static final String DEFAULT_INGREDIENCE = "AAAAAAAAAA";
    private static final String UPDATED_INGREDIENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ingredience-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IngredienceMasterRepository ingredienceMasterRepository;

    @Autowired
    private IngredienceMasterMapper ingredienceMasterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredienceMasterMockMvc;

    private IngredienceMaster ingredienceMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredienceMaster createEntity(EntityManager em) {
        IngredienceMaster ingredienceMaster = new IngredienceMaster().ingredience(DEFAULT_INGREDIENCE);
        return ingredienceMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredienceMaster createUpdatedEntity(EntityManager em) {
        IngredienceMaster ingredienceMaster = new IngredienceMaster().ingredience(UPDATED_INGREDIENCE);
        return ingredienceMaster;
    }

    @BeforeEach
    public void initTest() {
        ingredienceMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createIngredienceMaster() throws Exception {
        int databaseSizeBeforeCreate = ingredienceMasterRepository.findAll().size();
        // Create the IngredienceMaster
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(ingredienceMaster);
        restIngredienceMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeCreate + 1);
        IngredienceMaster testIngredienceMaster = ingredienceMasterList.get(ingredienceMasterList.size() - 1);
        assertThat(testIngredienceMaster.getIngredience()).isEqualTo(DEFAULT_INGREDIENCE);
    }

    @Test
    @Transactional
    void createIngredienceMasterWithExistingId() throws Exception {
        // Create the IngredienceMaster with an existing ID
        ingredienceMaster.setId(1L);
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(ingredienceMaster);

        int databaseSizeBeforeCreate = ingredienceMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredienceMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIngredienceMasters() throws Exception {
        // Initialize the database
        ingredienceMasterRepository.saveAndFlush(ingredienceMaster);

        // Get all the ingredienceMasterList
        restIngredienceMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredienceMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].ingredience").value(hasItem(DEFAULT_INGREDIENCE)));
    }

    @Test
    @Transactional
    void getIngredienceMaster() throws Exception {
        // Initialize the database
        ingredienceMasterRepository.saveAndFlush(ingredienceMaster);

        // Get the ingredienceMaster
        restIngredienceMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, ingredienceMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredienceMaster.getId().intValue()))
            .andExpect(jsonPath("$.ingredience").value(DEFAULT_INGREDIENCE));
    }

    @Test
    @Transactional
    void getNonExistingIngredienceMaster() throws Exception {
        // Get the ingredienceMaster
        restIngredienceMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIngredienceMaster() throws Exception {
        // Initialize the database
        ingredienceMasterRepository.saveAndFlush(ingredienceMaster);

        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();

        // Update the ingredienceMaster
        IngredienceMaster updatedIngredienceMaster = ingredienceMasterRepository.findById(ingredienceMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIngredienceMaster are not directly saved in db
        em.detach(updatedIngredienceMaster);
        updatedIngredienceMaster.ingredience(UPDATED_INGREDIENCE);
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(updatedIngredienceMaster);

        restIngredienceMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingredienceMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
        IngredienceMaster testIngredienceMaster = ingredienceMasterList.get(ingredienceMasterList.size() - 1);
        assertThat(testIngredienceMaster.getIngredience()).isEqualTo(UPDATED_INGREDIENCE);
    }

    @Test
    @Transactional
    void putNonExistingIngredienceMaster() throws Exception {
        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();
        ingredienceMaster.setId(longCount.incrementAndGet());

        // Create the IngredienceMaster
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(ingredienceMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredienceMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingredienceMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIngredienceMaster() throws Exception {
        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();
        ingredienceMaster.setId(longCount.incrementAndGet());

        // Create the IngredienceMaster
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(ingredienceMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredienceMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIngredienceMaster() throws Exception {
        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();
        ingredienceMaster.setId(longCount.incrementAndGet());

        // Create the IngredienceMaster
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(ingredienceMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredienceMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIngredienceMasterWithPatch() throws Exception {
        // Initialize the database
        ingredienceMasterRepository.saveAndFlush(ingredienceMaster);

        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();

        // Update the ingredienceMaster using partial update
        IngredienceMaster partialUpdatedIngredienceMaster = new IngredienceMaster();
        partialUpdatedIngredienceMaster.setId(ingredienceMaster.getId());

        restIngredienceMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredienceMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIngredienceMaster))
            )
            .andExpect(status().isOk());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
        IngredienceMaster testIngredienceMaster = ingredienceMasterList.get(ingredienceMasterList.size() - 1);
        assertThat(testIngredienceMaster.getIngredience()).isEqualTo(DEFAULT_INGREDIENCE);
    }

    @Test
    @Transactional
    void fullUpdateIngredienceMasterWithPatch() throws Exception {
        // Initialize the database
        ingredienceMasterRepository.saveAndFlush(ingredienceMaster);

        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();

        // Update the ingredienceMaster using partial update
        IngredienceMaster partialUpdatedIngredienceMaster = new IngredienceMaster();
        partialUpdatedIngredienceMaster.setId(ingredienceMaster.getId());

        partialUpdatedIngredienceMaster.ingredience(UPDATED_INGREDIENCE);

        restIngredienceMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredienceMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIngredienceMaster))
            )
            .andExpect(status().isOk());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
        IngredienceMaster testIngredienceMaster = ingredienceMasterList.get(ingredienceMasterList.size() - 1);
        assertThat(testIngredienceMaster.getIngredience()).isEqualTo(UPDATED_INGREDIENCE);
    }

    @Test
    @Transactional
    void patchNonExistingIngredienceMaster() throws Exception {
        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();
        ingredienceMaster.setId(longCount.incrementAndGet());

        // Create the IngredienceMaster
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(ingredienceMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredienceMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ingredienceMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIngredienceMaster() throws Exception {
        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();
        ingredienceMaster.setId(longCount.incrementAndGet());

        // Create the IngredienceMaster
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(ingredienceMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredienceMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIngredienceMaster() throws Exception {
        int databaseSizeBeforeUpdate = ingredienceMasterRepository.findAll().size();
        ingredienceMaster.setId(longCount.incrementAndGet());

        // Create the IngredienceMaster
        IngredienceMasterDTO ingredienceMasterDTO = ingredienceMasterMapper.toDto(ingredienceMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredienceMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredienceMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IngredienceMaster in the database
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIngredienceMaster() throws Exception {
        // Initialize the database
        ingredienceMasterRepository.saveAndFlush(ingredienceMaster);

        int databaseSizeBeforeDelete = ingredienceMasterRepository.findAll().size();

        // Delete the ingredienceMaster
        restIngredienceMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, ingredienceMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IngredienceMaster> ingredienceMasterList = ingredienceMasterRepository.findAll();
        assertThat(ingredienceMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
