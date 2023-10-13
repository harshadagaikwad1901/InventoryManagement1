package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ProductsUsedRepository;
import com.mycompany.myapp.service.ProductsUsedQueryService;
import com.mycompany.myapp.service.ProductsUsedService;
import com.mycompany.myapp.service.criteria.ProductsUsedCriteria;
import com.mycompany.myapp.service.dto.ProductsUsedDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProductsUsed}.
 */
@RestController
@RequestMapping("/api")
public class ProductsUsedResource {

    private final Logger log = LoggerFactory.getLogger(ProductsUsedResource.class);

    private static final String ENTITY_NAME = "productsUsed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductsUsedService productsUsedService;

    private final ProductsUsedRepository productsUsedRepository;

    private final ProductsUsedQueryService productsUsedQueryService;

    public ProductsUsedResource(
        ProductsUsedService productsUsedService,
        ProductsUsedRepository productsUsedRepository,
        ProductsUsedQueryService productsUsedQueryService
    ) {
        this.productsUsedService = productsUsedService;
        this.productsUsedRepository = productsUsedRepository;
        this.productsUsedQueryService = productsUsedQueryService;
    }

    /**
     * {@code POST  /products-useds} : Create a new productsUsed.
     *
     * @param productsUsedDTO the productsUsedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productsUsedDTO, or with status {@code 400 (Bad Request)} if the productsUsed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products-useds")
    public ResponseEntity<ProductsUsedDTO> createProductsUsed(@RequestBody ProductsUsedDTO productsUsedDTO) throws URISyntaxException {
        log.debug("REST request to save ProductsUsed : {}", productsUsedDTO);
        if (productsUsedDTO.getId() != null) {
            throw new BadRequestAlertException("A new productsUsed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductsUsedDTO result = productsUsedService.save(productsUsedDTO);
        return ResponseEntity
            .created(new URI("/api/products-useds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /products-useds/:id} : Updates an existing productsUsed.
     *
     * @param id the id of the productsUsedDTO to save.
     * @param productsUsedDTO the productsUsedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsUsedDTO,
     * or with status {@code 400 (Bad Request)} if the productsUsedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productsUsedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products-useds/{id}")
    public ResponseEntity<ProductsUsedDTO> updateProductsUsed(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductsUsedDTO productsUsedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductsUsed : {}, {}", id, productsUsedDTO);
        if (productsUsedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productsUsedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productsUsedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductsUsedDTO result = productsUsedService.update(productsUsedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productsUsedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /products-useds/:id} : Partial updates given fields of an existing productsUsed, field will ignore if it is null
     *
     * @param id the id of the productsUsedDTO to save.
     * @param productsUsedDTO the productsUsedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsUsedDTO,
     * or with status {@code 400 (Bad Request)} if the productsUsedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productsUsedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productsUsedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/products-useds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductsUsedDTO> partialUpdateProductsUsed(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductsUsedDTO productsUsedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductsUsed partially : {}, {}", id, productsUsedDTO);
        if (productsUsedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productsUsedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productsUsedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductsUsedDTO> result = productsUsedService.partialUpdate(productsUsedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productsUsedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /products-useds} : get all the productsUseds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productsUseds in body.
     */
    @GetMapping("/products-useds")
    public ResponseEntity<List<ProductsUsedDTO>> getAllProductsUseds(
        ProductsUsedCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProductsUseds by criteria: {}", criteria);
        Page<ProductsUsedDTO> page = productsUsedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /products-useds/count} : count all the productsUseds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/products-useds/count")
    public ResponseEntity<Long> countProductsUseds(ProductsUsedCriteria criteria) {
        log.debug("REST request to count ProductsUseds by criteria: {}", criteria);
        return ResponseEntity.ok().body(productsUsedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /products-useds/:id} : get the "id" productsUsed.
     *
     * @param id the id of the productsUsedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productsUsedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products-useds/{id}")
    public ResponseEntity<ProductsUsedDTO> getProductsUsed(@PathVariable Long id) {
        log.debug("REST request to get ProductsUsed : {}", id);
        Optional<ProductsUsedDTO> productsUsedDTO = productsUsedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productsUsedDTO);
    }

    /**
     * {@code DELETE  /products-useds/:id} : delete the "id" productsUsed.
     *
     * @param id the id of the productsUsedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products-useds/{id}")
    public ResponseEntity<Void> deleteProductsUsed(@PathVariable Long id) {
        log.debug("REST request to delete ProductsUsed : {}", id);
        productsUsedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
