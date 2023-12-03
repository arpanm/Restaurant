package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.ItemPincodeMappingRepository;
import com.restaurant.angikar.service.ItemPincodeMappingService;
import com.restaurant.angikar.service.dto.ItemPincodeMappingDTO;
import com.restaurant.angikar.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.restaurant.angikar.domain.ItemPincodeMapping}.
 */
@RestController
@RequestMapping("/api/item-pincode-mappings")
public class ItemPincodeMappingResource {

    private final Logger log = LoggerFactory.getLogger(ItemPincodeMappingResource.class);

    private static final String ENTITY_NAME = "itemPincodeMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemPincodeMappingService itemPincodeMappingService;

    private final ItemPincodeMappingRepository itemPincodeMappingRepository;

    public ItemPincodeMappingResource(
        ItemPincodeMappingService itemPincodeMappingService,
        ItemPincodeMappingRepository itemPincodeMappingRepository
    ) {
        this.itemPincodeMappingService = itemPincodeMappingService;
        this.itemPincodeMappingRepository = itemPincodeMappingRepository;
    }

    /**
     * {@code POST  /item-pincode-mappings} : Create a new itemPincodeMapping.
     *
     * @param itemPincodeMappingDTO the itemPincodeMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPincodeMappingDTO, or with status {@code 400 (Bad Request)} if the itemPincodeMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ItemPincodeMappingDTO> createItemPincodeMapping(@RequestBody ItemPincodeMappingDTO itemPincodeMappingDTO)
        throws URISyntaxException {
        log.debug("REST request to save ItemPincodeMapping : {}", itemPincodeMappingDTO);
        if (itemPincodeMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPincodeMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPincodeMappingDTO result = itemPincodeMappingService.save(itemPincodeMappingDTO);
        return ResponseEntity
            .created(new URI("/api/item-pincode-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-pincode-mappings/:id} : Updates an existing itemPincodeMapping.
     *
     * @param id the id of the itemPincodeMappingDTO to save.
     * @param itemPincodeMappingDTO the itemPincodeMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPincodeMappingDTO,
     * or with status {@code 400 (Bad Request)} if the itemPincodeMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPincodeMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ItemPincodeMappingDTO> updateItemPincodeMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemPincodeMappingDTO itemPincodeMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ItemPincodeMapping : {}, {}", id, itemPincodeMappingDTO);
        if (itemPincodeMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemPincodeMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemPincodeMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemPincodeMappingDTO result = itemPincodeMappingService.update(itemPincodeMappingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemPincodeMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-pincode-mappings/:id} : Partial updates given fields of an existing itemPincodeMapping, field will ignore if it is null
     *
     * @param id the id of the itemPincodeMappingDTO to save.
     * @param itemPincodeMappingDTO the itemPincodeMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPincodeMappingDTO,
     * or with status {@code 400 (Bad Request)} if the itemPincodeMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the itemPincodeMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemPincodeMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemPincodeMappingDTO> partialUpdateItemPincodeMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemPincodeMappingDTO itemPincodeMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemPincodeMapping partially : {}, {}", id, itemPincodeMappingDTO);
        if (itemPincodeMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemPincodeMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemPincodeMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemPincodeMappingDTO> result = itemPincodeMappingService.partialUpdate(itemPincodeMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemPincodeMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /item-pincode-mappings} : get all the itemPincodeMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPincodeMappings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ItemPincodeMappingDTO>> getAllItemPincodeMappings(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ItemPincodeMappings");
        Page<ItemPincodeMappingDTO> page = itemPincodeMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-pincode-mappings/:id} : get the "id" itemPincodeMapping.
     *
     * @param id the id of the itemPincodeMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPincodeMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemPincodeMappingDTO> getItemPincodeMapping(@PathVariable Long id) {
        log.debug("REST request to get ItemPincodeMapping : {}", id);
        Optional<ItemPincodeMappingDTO> itemPincodeMappingDTO = itemPincodeMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPincodeMappingDTO);
    }

    /**
     * {@code DELETE  /item-pincode-mappings/:id} : delete the "id" itemPincodeMapping.
     *
     * @param id the id of the itemPincodeMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemPincodeMapping(@PathVariable Long id) {
        log.debug("REST request to delete ItemPincodeMapping : {}", id);
        itemPincodeMappingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
