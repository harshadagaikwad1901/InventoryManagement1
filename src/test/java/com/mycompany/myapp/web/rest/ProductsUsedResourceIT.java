package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ProductsUsed;
import com.mycompany.myapp.repository.ProductsUsedRepository;
import com.mycompany.myapp.service.criteria.ProductsUsedCriteria;
import com.mycompany.myapp.service.dto.ProductsUsedDTO;
import com.mycompany.myapp.service.mapper.ProductsUsedMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductsUsedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductsUsedResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;
    private static final Long SMALLER_PRODUCT_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCT_CONSUMED = 1L;
    private static final Long UPDATED_PRODUCT_CONSUMED = 2L;
    private static final Long SMALLER_PRODUCT_CONSUMED = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/products-useds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductsUsedRepository productsUsedRepository;

    @Autowired
    private ProductsUsedMapper productsUsedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductsUsedMockMvc;

    private ProductsUsed productsUsed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductsUsed createEntity(EntityManager em) {
        ProductsUsed productsUsed = new ProductsUsed().productId(DEFAULT_PRODUCT_ID).productConsumed(DEFAULT_PRODUCT_CONSUMED);
        return productsUsed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductsUsed createUpdatedEntity(EntityManager em) {
        ProductsUsed productsUsed = new ProductsUsed().productId(UPDATED_PRODUCT_ID).productConsumed(UPDATED_PRODUCT_CONSUMED);
        return productsUsed;
    }

    @BeforeEach
    public void initTest() {
        productsUsed = createEntity(em);
    }

    @Test
    @Transactional
    void createProductsUsed() throws Exception {
        int databaseSizeBeforeCreate = productsUsedRepository.findAll().size();
        // Create the ProductsUsed
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(productsUsed);
        restProductsUsedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeCreate + 1);
        ProductsUsed testProductsUsed = productsUsedList.get(productsUsedList.size() - 1);
        assertThat(testProductsUsed.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProductsUsed.getProductConsumed()).isEqualTo(DEFAULT_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void createProductsUsedWithExistingId() throws Exception {
        // Create the ProductsUsed with an existing ID
        productsUsed.setId(1L);
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(productsUsed);

        int databaseSizeBeforeCreate = productsUsedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsUsedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductsUseds() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList
        restProductsUsedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsUsed.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productConsumed").value(hasItem(DEFAULT_PRODUCT_CONSUMED.intValue())));
    }

    @Test
    @Transactional
    void getProductsUsed() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get the productsUsed
        restProductsUsedMockMvc
            .perform(get(ENTITY_API_URL_ID, productsUsed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productsUsed.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.productConsumed").value(DEFAULT_PRODUCT_CONSUMED.intValue()));
    }

    @Test
    @Transactional
    void getProductsUsedsByIdFiltering() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        Long id = productsUsed.getId();

        defaultProductsUsedShouldBeFound("id.equals=" + id);
        defaultProductsUsedShouldNotBeFound("id.notEquals=" + id);

        defaultProductsUsedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductsUsedShouldNotBeFound("id.greaterThan=" + id);

        defaultProductsUsedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductsUsedShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productId equals to DEFAULT_PRODUCT_ID
        defaultProductsUsedShouldBeFound("productId.equals=" + DEFAULT_PRODUCT_ID);

        // Get all the productsUsedList where productId equals to UPDATED_PRODUCT_ID
        defaultProductsUsedShouldNotBeFound("productId.equals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productId in DEFAULT_PRODUCT_ID or UPDATED_PRODUCT_ID
        defaultProductsUsedShouldBeFound("productId.in=" + DEFAULT_PRODUCT_ID + "," + UPDATED_PRODUCT_ID);

        // Get all the productsUsedList where productId equals to UPDATED_PRODUCT_ID
        defaultProductsUsedShouldNotBeFound("productId.in=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productId is not null
        defaultProductsUsedShouldBeFound("productId.specified=true");

        // Get all the productsUsedList where productId is null
        defaultProductsUsedShouldNotBeFound("productId.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productId is greater than or equal to DEFAULT_PRODUCT_ID
        defaultProductsUsedShouldBeFound("productId.greaterThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the productsUsedList where productId is greater than or equal to UPDATED_PRODUCT_ID
        defaultProductsUsedShouldNotBeFound("productId.greaterThanOrEqual=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productId is less than or equal to DEFAULT_PRODUCT_ID
        defaultProductsUsedShouldBeFound("productId.lessThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the productsUsedList where productId is less than or equal to SMALLER_PRODUCT_ID
        defaultProductsUsedShouldNotBeFound("productId.lessThanOrEqual=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductIdIsLessThanSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productId is less than DEFAULT_PRODUCT_ID
        defaultProductsUsedShouldNotBeFound("productId.lessThan=" + DEFAULT_PRODUCT_ID);

        // Get all the productsUsedList where productId is less than UPDATED_PRODUCT_ID
        defaultProductsUsedShouldBeFound("productId.lessThan=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productId is greater than DEFAULT_PRODUCT_ID
        defaultProductsUsedShouldNotBeFound("productId.greaterThan=" + DEFAULT_PRODUCT_ID);

        // Get all the productsUsedList where productId is greater than SMALLER_PRODUCT_ID
        defaultProductsUsedShouldBeFound("productId.greaterThan=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductConsumedIsEqualToSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productConsumed equals to DEFAULT_PRODUCT_CONSUMED
        defaultProductsUsedShouldBeFound("productConsumed.equals=" + DEFAULT_PRODUCT_CONSUMED);

        // Get all the productsUsedList where productConsumed equals to UPDATED_PRODUCT_CONSUMED
        defaultProductsUsedShouldNotBeFound("productConsumed.equals=" + UPDATED_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductConsumedIsInShouldWork() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productConsumed in DEFAULT_PRODUCT_CONSUMED or UPDATED_PRODUCT_CONSUMED
        defaultProductsUsedShouldBeFound("productConsumed.in=" + DEFAULT_PRODUCT_CONSUMED + "," + UPDATED_PRODUCT_CONSUMED);

        // Get all the productsUsedList where productConsumed equals to UPDATED_PRODUCT_CONSUMED
        defaultProductsUsedShouldNotBeFound("productConsumed.in=" + UPDATED_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductConsumedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productConsumed is not null
        defaultProductsUsedShouldBeFound("productConsumed.specified=true");

        // Get all the productsUsedList where productConsumed is null
        defaultProductsUsedShouldNotBeFound("productConsumed.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductConsumedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productConsumed is greater than or equal to DEFAULT_PRODUCT_CONSUMED
        defaultProductsUsedShouldBeFound("productConsumed.greaterThanOrEqual=" + DEFAULT_PRODUCT_CONSUMED);

        // Get all the productsUsedList where productConsumed is greater than or equal to UPDATED_PRODUCT_CONSUMED
        defaultProductsUsedShouldNotBeFound("productConsumed.greaterThanOrEqual=" + UPDATED_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductConsumedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productConsumed is less than or equal to DEFAULT_PRODUCT_CONSUMED
        defaultProductsUsedShouldBeFound("productConsumed.lessThanOrEqual=" + DEFAULT_PRODUCT_CONSUMED);

        // Get all the productsUsedList where productConsumed is less than or equal to SMALLER_PRODUCT_CONSUMED
        defaultProductsUsedShouldNotBeFound("productConsumed.lessThanOrEqual=" + SMALLER_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductConsumedIsLessThanSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productConsumed is less than DEFAULT_PRODUCT_CONSUMED
        defaultProductsUsedShouldNotBeFound("productConsumed.lessThan=" + DEFAULT_PRODUCT_CONSUMED);

        // Get all the productsUsedList where productConsumed is less than UPDATED_PRODUCT_CONSUMED
        defaultProductsUsedShouldBeFound("productConsumed.lessThan=" + UPDATED_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void getAllProductsUsedsByProductConsumedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        // Get all the productsUsedList where productConsumed is greater than DEFAULT_PRODUCT_CONSUMED
        defaultProductsUsedShouldNotBeFound("productConsumed.greaterThan=" + DEFAULT_PRODUCT_CONSUMED);

        // Get all the productsUsedList where productConsumed is greater than SMALLER_PRODUCT_CONSUMED
        defaultProductsUsedShouldBeFound("productConsumed.greaterThan=" + SMALLER_PRODUCT_CONSUMED);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductsUsedShouldBeFound(String filter) throws Exception {
        restProductsUsedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsUsed.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productConsumed").value(hasItem(DEFAULT_PRODUCT_CONSUMED.intValue())));

        // Check, that the count call also returns 1
        restProductsUsedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductsUsedShouldNotBeFound(String filter) throws Exception {
        restProductsUsedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductsUsedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductsUsed() throws Exception {
        // Get the productsUsed
        restProductsUsedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductsUsed() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();

        // Update the productsUsed
        ProductsUsed updatedProductsUsed = productsUsedRepository.findById(productsUsed.getId()).get();
        // Disconnect from session so that the updates on updatedProductsUsed are not directly saved in db
        em.detach(updatedProductsUsed);
        updatedProductsUsed.productId(UPDATED_PRODUCT_ID).productConsumed(UPDATED_PRODUCT_CONSUMED);
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(updatedProductsUsed);

        restProductsUsedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productsUsedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
        ProductsUsed testProductsUsed = productsUsedList.get(productsUsedList.size() - 1);
        assertThat(testProductsUsed.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProductsUsed.getProductConsumed()).isEqualTo(UPDATED_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void putNonExistingProductsUsed() throws Exception {
        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();
        productsUsed.setId(count.incrementAndGet());

        // Create the ProductsUsed
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(productsUsed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsUsedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productsUsedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductsUsed() throws Exception {
        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();
        productsUsed.setId(count.incrementAndGet());

        // Create the ProductsUsed
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(productsUsed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsUsedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductsUsed() throws Exception {
        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();
        productsUsed.setId(count.incrementAndGet());

        // Create the ProductsUsed
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(productsUsed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsUsedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductsUsedWithPatch() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();

        // Update the productsUsed using partial update
        ProductsUsed partialUpdatedProductsUsed = new ProductsUsed();
        partialUpdatedProductsUsed.setId(productsUsed.getId());

        restProductsUsedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductsUsed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductsUsed))
            )
            .andExpect(status().isOk());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
        ProductsUsed testProductsUsed = productsUsedList.get(productsUsedList.size() - 1);
        assertThat(testProductsUsed.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProductsUsed.getProductConsumed()).isEqualTo(DEFAULT_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void fullUpdateProductsUsedWithPatch() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();

        // Update the productsUsed using partial update
        ProductsUsed partialUpdatedProductsUsed = new ProductsUsed();
        partialUpdatedProductsUsed.setId(productsUsed.getId());

        partialUpdatedProductsUsed.productId(UPDATED_PRODUCT_ID).productConsumed(UPDATED_PRODUCT_CONSUMED);

        restProductsUsedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductsUsed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductsUsed))
            )
            .andExpect(status().isOk());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
        ProductsUsed testProductsUsed = productsUsedList.get(productsUsedList.size() - 1);
        assertThat(testProductsUsed.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProductsUsed.getProductConsumed()).isEqualTo(UPDATED_PRODUCT_CONSUMED);
    }

    @Test
    @Transactional
    void patchNonExistingProductsUsed() throws Exception {
        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();
        productsUsed.setId(count.incrementAndGet());

        // Create the ProductsUsed
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(productsUsed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsUsedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productsUsedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductsUsed() throws Exception {
        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();
        productsUsed.setId(count.incrementAndGet());

        // Create the ProductsUsed
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(productsUsed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsUsedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductsUsed() throws Exception {
        int databaseSizeBeforeUpdate = productsUsedRepository.findAll().size();
        productsUsed.setId(count.incrementAndGet());

        // Create the ProductsUsed
        ProductsUsedDTO productsUsedDTO = productsUsedMapper.toDto(productsUsed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsUsedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productsUsedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductsUsed in the database
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductsUsed() throws Exception {
        // Initialize the database
        productsUsedRepository.saveAndFlush(productsUsed);

        int databaseSizeBeforeDelete = productsUsedRepository.findAll().size();

        // Delete the productsUsed
        restProductsUsedMockMvc
            .perform(delete(ENTITY_API_URL_ID, productsUsed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductsUsed> productsUsedList = productsUsedRepository.findAll();
        assertThat(productsUsedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
