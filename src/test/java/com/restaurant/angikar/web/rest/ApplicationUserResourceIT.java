package com.restaurant.angikar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.angikar.IntegrationTest;
import com.restaurant.angikar.domain.ApplicationUser;
import com.restaurant.angikar.domain.enumeration.UserRole;
import com.restaurant.angikar.repository.ApplicationUserRepository;
import com.restaurant.angikar.service.dto.ApplicationUserDTO;
import com.restaurant.angikar.service.mapper.ApplicationUserMapper;
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
 * Integration tests for the {@link ApplicationUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final UserRole DEFAULT_ROLE = UserRole.Customer;
    private static final UserRole UPDATED_ROLE = UserRole.RestaurantAdmin;

    private static final Boolean DEFAULT_IS_PHONE_VALIDATED = false;
    private static final Boolean UPDATED_IS_PHONE_VALIDATED = true;

    private static final Boolean DEFAULT_IS_EMAIL_VALIDATED = false;
    private static final Boolean UPDATED_IS_EMAIL_VALIDATED = true;

    private static final Instant DEFAULT_PHONE_VALIDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PHONE_VALIDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EMAIL_VALIDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EMAIL_VALIDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/application-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ApplicationUserMapper applicationUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationUserMockMvc;

    private ApplicationUser applicationUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUser createEntity(EntityManager em) {
        ApplicationUser applicationUser = new ApplicationUser()
            .name(DEFAULT_NAME)
            .password(DEFAULT_PASSWORD)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .role(DEFAULT_ROLE)
            .isPhoneValidated(DEFAULT_IS_PHONE_VALIDATED)
            .isEmailValidated(DEFAULT_IS_EMAIL_VALIDATED)
            .phoneValidatedOn(DEFAULT_PHONE_VALIDATED_ON)
            .emailValidatedOn(DEFAULT_EMAIL_VALIDATED_ON);
        return applicationUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUser createUpdatedEntity(EntityManager em) {
        ApplicationUser applicationUser = new ApplicationUser()
            .name(UPDATED_NAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .role(UPDATED_ROLE)
            .isPhoneValidated(UPDATED_IS_PHONE_VALIDATED)
            .isEmailValidated(UPDATED_IS_EMAIL_VALIDATED)
            .phoneValidatedOn(UPDATED_PHONE_VALIDATED_ON)
            .emailValidatedOn(UPDATED_EMAIL_VALIDATED_ON);
        return applicationUser;
    }

    @BeforeEach
    public void initTest() {
        applicationUser = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationUser() throws Exception {
        int databaseSizeBeforeCreate = applicationUserRepository.findAll().size();
        // Create the ApplicationUser
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(applicationUser);
        restApplicationUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationUser testApplicationUser = applicationUserList.get(applicationUserList.size() - 1);
        assertThat(testApplicationUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testApplicationUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testApplicationUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testApplicationUser.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testApplicationUser.getIsPhoneValidated()).isEqualTo(DEFAULT_IS_PHONE_VALIDATED);
        assertThat(testApplicationUser.getIsEmailValidated()).isEqualTo(DEFAULT_IS_EMAIL_VALIDATED);
        assertThat(testApplicationUser.getPhoneValidatedOn()).isEqualTo(DEFAULT_PHONE_VALIDATED_ON);
        assertThat(testApplicationUser.getEmailValidatedOn()).isEqualTo(DEFAULT_EMAIL_VALIDATED_ON);
    }

    @Test
    @Transactional
    void createApplicationUserWithExistingId() throws Exception {
        // Create the ApplicationUser with an existing ID
        applicationUser.setId(1L);
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(applicationUser);

        int databaseSizeBeforeCreate = applicationUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicationUsers() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        // Get all the applicationUserList
        restApplicationUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].isPhoneValidated").value(hasItem(DEFAULT_IS_PHONE_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].isEmailValidated").value(hasItem(DEFAULT_IS_EMAIL_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].phoneValidatedOn").value(hasItem(DEFAULT_PHONE_VALIDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].emailValidatedOn").value(hasItem(DEFAULT_EMAIL_VALIDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getApplicationUser() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        // Get the applicationUser
        restApplicationUserMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.isPhoneValidated").value(DEFAULT_IS_PHONE_VALIDATED.booleanValue()))
            .andExpect(jsonPath("$.isEmailValidated").value(DEFAULT_IS_EMAIL_VALIDATED.booleanValue()))
            .andExpect(jsonPath("$.phoneValidatedOn").value(DEFAULT_PHONE_VALIDATED_ON.toString()))
            .andExpect(jsonPath("$.emailValidatedOn").value(DEFAULT_EMAIL_VALIDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApplicationUser() throws Exception {
        // Get the applicationUser
        restApplicationUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplicationUser() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();

        // Update the applicationUser
        ApplicationUser updatedApplicationUser = applicationUserRepository.findById(applicationUser.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApplicationUser are not directly saved in db
        em.detach(updatedApplicationUser);
        updatedApplicationUser
            .name(UPDATED_NAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .role(UPDATED_ROLE)
            .isPhoneValidated(UPDATED_IS_PHONE_VALIDATED)
            .isEmailValidated(UPDATED_IS_EMAIL_VALIDATED)
            .phoneValidatedOn(UPDATED_PHONE_VALIDATED_ON)
            .emailValidatedOn(UPDATED_EMAIL_VALIDATED_ON);
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(updatedApplicationUser);

        restApplicationUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUser testApplicationUser = applicationUserList.get(applicationUserList.size() - 1);
        assertThat(testApplicationUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testApplicationUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicationUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testApplicationUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testApplicationUser.getIsPhoneValidated()).isEqualTo(UPDATED_IS_PHONE_VALIDATED);
        assertThat(testApplicationUser.getIsEmailValidated()).isEqualTo(UPDATED_IS_EMAIL_VALIDATED);
        assertThat(testApplicationUser.getPhoneValidatedOn()).isEqualTo(UPDATED_PHONE_VALIDATED_ON);
        assertThat(testApplicationUser.getEmailValidatedOn()).isEqualTo(UPDATED_EMAIL_VALIDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(longCount.incrementAndGet());

        // Create the ApplicationUser
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(applicationUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(longCount.incrementAndGet());

        // Create the ApplicationUser
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(applicationUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(longCount.incrementAndGet());

        // Create the ApplicationUser
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(applicationUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationUserWithPatch() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();

        // Update the applicationUser using partial update
        ApplicationUser partialUpdatedApplicationUser = new ApplicationUser();
        partialUpdatedApplicationUser.setId(applicationUser.getId());

        partialUpdatedApplicationUser
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .role(UPDATED_ROLE)
            .emailValidatedOn(UPDATED_EMAIL_VALIDATED_ON);

        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUser))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUser testApplicationUser = applicationUserList.get(applicationUserList.size() - 1);
        assertThat(testApplicationUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testApplicationUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicationUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testApplicationUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testApplicationUser.getIsPhoneValidated()).isEqualTo(DEFAULT_IS_PHONE_VALIDATED);
        assertThat(testApplicationUser.getIsEmailValidated()).isEqualTo(DEFAULT_IS_EMAIL_VALIDATED);
        assertThat(testApplicationUser.getPhoneValidatedOn()).isEqualTo(DEFAULT_PHONE_VALIDATED_ON);
        assertThat(testApplicationUser.getEmailValidatedOn()).isEqualTo(UPDATED_EMAIL_VALIDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateApplicationUserWithPatch() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();

        // Update the applicationUser using partial update
        ApplicationUser partialUpdatedApplicationUser = new ApplicationUser();
        partialUpdatedApplicationUser.setId(applicationUser.getId());

        partialUpdatedApplicationUser
            .name(UPDATED_NAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .role(UPDATED_ROLE)
            .isPhoneValidated(UPDATED_IS_PHONE_VALIDATED)
            .isEmailValidated(UPDATED_IS_EMAIL_VALIDATED)
            .phoneValidatedOn(UPDATED_PHONE_VALIDATED_ON)
            .emailValidatedOn(UPDATED_EMAIL_VALIDATED_ON);

        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUser))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUser testApplicationUser = applicationUserList.get(applicationUserList.size() - 1);
        assertThat(testApplicationUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testApplicationUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicationUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testApplicationUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testApplicationUser.getIsPhoneValidated()).isEqualTo(UPDATED_IS_PHONE_VALIDATED);
        assertThat(testApplicationUser.getIsEmailValidated()).isEqualTo(UPDATED_IS_EMAIL_VALIDATED);
        assertThat(testApplicationUser.getPhoneValidatedOn()).isEqualTo(UPDATED_PHONE_VALIDATED_ON);
        assertThat(testApplicationUser.getEmailValidatedOn()).isEqualTo(UPDATED_EMAIL_VALIDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(longCount.incrementAndGet());

        // Create the ApplicationUser
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(applicationUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(longCount.incrementAndGet());

        // Create the ApplicationUser
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(applicationUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(longCount.incrementAndGet());

        // Create the ApplicationUser
        ApplicationUserDTO applicationUserDTO = applicationUserMapper.toDto(applicationUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationUser() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        int databaseSizeBeforeDelete = applicationUserRepository.findAll().size();

        // Delete the applicationUser
        restApplicationUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
