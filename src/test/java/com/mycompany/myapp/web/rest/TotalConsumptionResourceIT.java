package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TotalConsumption;
import com.mycompany.myapp.repository.TotalConsumptionRepository;
import com.mycompany.myapp.service.criteria.TotalConsumptionCriteria;
import com.mycompany.myapp.service.dto.TotalConsumptionDTO;
import com.mycompany.myapp.service.mapper.TotalConsumptionMapper;
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
 * Integration tests for the {@link TotalConsumptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TotalConsumptionResourceIT {

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;
    private static final Long SMALLER_PROJECT_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;
    private static final Long SMALLER_PRODUCT_ID = 1L - 1L;

    private static final Double DEFAULT_TOTAL_MATERIAL_COST = 1D;
    private static final Double UPDATED_TOTAL_MATERIAL_COST = 2D;
    private static final Double SMALLER_TOTAL_MATERIAL_COST = 1D - 1D;

    private static final Double DEFAULT_TOTAL_PRODUCTS_COST = 1D;
    private static final Double UPDATED_TOTAL_PRODUCTS_COST = 2D;
    private static final Double SMALLER_TOTAL_PRODUCTS_COST = 1D - 1D;

    private static final Double DEFAULT_FINAL_COST = 1D;
    private static final Double UPDATED_FINAL_COST = 2D;
    private static final Double SMALLER_FINAL_COST = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/total-consumptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TotalConsumptionRepository totalConsumptionRepository;

    @Autowired
    private TotalConsumptionMapper totalConsumptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTotalConsumptionMockMvc;

    private TotalConsumption totalConsumption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TotalConsumption createEntity(EntityManager em) {
        TotalConsumption totalConsumption = new TotalConsumption()
            .projectId(DEFAULT_PROJECT_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .totalMaterialCost(DEFAULT_TOTAL_MATERIAL_COST)
            .totalProductsCost(DEFAULT_TOTAL_PRODUCTS_COST)
            .finalCost(DEFAULT_FINAL_COST);
        return totalConsumption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TotalConsumption createUpdatedEntity(EntityManager em) {
        TotalConsumption totalConsumption = new TotalConsumption()
            .projectId(UPDATED_PROJECT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST)
            .totalProductsCost(UPDATED_TOTAL_PRODUCTS_COST)
            .finalCost(UPDATED_FINAL_COST);
        return totalConsumption;
    }

    @BeforeEach
    public void initTest() {
        totalConsumption = createEntity(em);
    }

    @Test
    @Transactional
    void createTotalConsumption() throws Exception {
        int databaseSizeBeforeCreate = totalConsumptionRepository.findAll().size();
        // Create the TotalConsumption
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(totalConsumption);
        restTotalConsumptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeCreate + 1);
        TotalConsumption testTotalConsumption = totalConsumptionList.get(totalConsumptionList.size() - 1);
        assertThat(testTotalConsumption.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testTotalConsumption.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testTotalConsumption.getTotalMaterialCost()).isEqualTo(DEFAULT_TOTAL_MATERIAL_COST);
        assertThat(testTotalConsumption.getTotalProductsCost()).isEqualTo(DEFAULT_TOTAL_PRODUCTS_COST);
        assertThat(testTotalConsumption.getFinalCost()).isEqualTo(DEFAULT_FINAL_COST);
    }

    @Test
    @Transactional
    void createTotalConsumptionWithExistingId() throws Exception {
        // Create the TotalConsumption with an existing ID
        totalConsumption.setId(1L);
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(totalConsumption);

        int databaseSizeBeforeCreate = totalConsumptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTotalConsumptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTotalConsumptions() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList
        restTotalConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(totalConsumption.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalMaterialCost").value(hasItem(DEFAULT_TOTAL_MATERIAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].totalProductsCost").value(hasItem(DEFAULT_TOTAL_PRODUCTS_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].finalCost").value(hasItem(DEFAULT_FINAL_COST.doubleValue())));
    }

    @Test
    @Transactional
    void getTotalConsumption() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get the totalConsumption
        restTotalConsumptionMockMvc
            .perform(get(ENTITY_API_URL_ID, totalConsumption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(totalConsumption.getId().intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.totalMaterialCost").value(DEFAULT_TOTAL_MATERIAL_COST.doubleValue()))
            .andExpect(jsonPath("$.totalProductsCost").value(DEFAULT_TOTAL_PRODUCTS_COST.doubleValue()))
            .andExpect(jsonPath("$.finalCost").value(DEFAULT_FINAL_COST.doubleValue()));
    }

    @Test
    @Transactional
    void getTotalConsumptionsByIdFiltering() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        Long id = totalConsumption.getId();

        defaultTotalConsumptionShouldBeFound("id.equals=" + id);
        defaultTotalConsumptionShouldNotBeFound("id.notEquals=" + id);

        defaultTotalConsumptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTotalConsumptionShouldNotBeFound("id.greaterThan=" + id);

        defaultTotalConsumptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTotalConsumptionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProjectIdIsEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where projectId equals to DEFAULT_PROJECT_ID
        defaultTotalConsumptionShouldBeFound("projectId.equals=" + DEFAULT_PROJECT_ID);

        // Get all the totalConsumptionList where projectId equals to UPDATED_PROJECT_ID
        defaultTotalConsumptionShouldNotBeFound("projectId.equals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProjectIdIsInShouldWork() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where projectId in DEFAULT_PROJECT_ID or UPDATED_PROJECT_ID
        defaultTotalConsumptionShouldBeFound("projectId.in=" + DEFAULT_PROJECT_ID + "," + UPDATED_PROJECT_ID);

        // Get all the totalConsumptionList where projectId equals to UPDATED_PROJECT_ID
        defaultTotalConsumptionShouldNotBeFound("projectId.in=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProjectIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where projectId is not null
        defaultTotalConsumptionShouldBeFound("projectId.specified=true");

        // Get all the totalConsumptionList where projectId is null
        defaultTotalConsumptionShouldNotBeFound("projectId.specified=false");
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProjectIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where projectId is greater than or equal to DEFAULT_PROJECT_ID
        defaultTotalConsumptionShouldBeFound("projectId.greaterThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the totalConsumptionList where projectId is greater than or equal to UPDATED_PROJECT_ID
        defaultTotalConsumptionShouldNotBeFound("projectId.greaterThanOrEqual=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProjectIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where projectId is less than or equal to DEFAULT_PROJECT_ID
        defaultTotalConsumptionShouldBeFound("projectId.lessThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the totalConsumptionList where projectId is less than or equal to SMALLER_PROJECT_ID
        defaultTotalConsumptionShouldNotBeFound("projectId.lessThanOrEqual=" + SMALLER_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProjectIdIsLessThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where projectId is less than DEFAULT_PROJECT_ID
        defaultTotalConsumptionShouldNotBeFound("projectId.lessThan=" + DEFAULT_PROJECT_ID);

        // Get all the totalConsumptionList where projectId is less than UPDATED_PROJECT_ID
        defaultTotalConsumptionShouldBeFound("projectId.lessThan=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProjectIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where projectId is greater than DEFAULT_PROJECT_ID
        defaultTotalConsumptionShouldNotBeFound("projectId.greaterThan=" + DEFAULT_PROJECT_ID);

        // Get all the totalConsumptionList where projectId is greater than SMALLER_PROJECT_ID
        defaultTotalConsumptionShouldBeFound("projectId.greaterThan=" + SMALLER_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where productId equals to DEFAULT_PRODUCT_ID
        defaultTotalConsumptionShouldBeFound("productId.equals=" + DEFAULT_PRODUCT_ID);

        // Get all the totalConsumptionList where productId equals to UPDATED_PRODUCT_ID
        defaultTotalConsumptionShouldNotBeFound("productId.equals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where productId in DEFAULT_PRODUCT_ID or UPDATED_PRODUCT_ID
        defaultTotalConsumptionShouldBeFound("productId.in=" + DEFAULT_PRODUCT_ID + "," + UPDATED_PRODUCT_ID);

        // Get all the totalConsumptionList where productId equals to UPDATED_PRODUCT_ID
        defaultTotalConsumptionShouldNotBeFound("productId.in=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where productId is not null
        defaultTotalConsumptionShouldBeFound("productId.specified=true");

        // Get all the totalConsumptionList where productId is null
        defaultTotalConsumptionShouldNotBeFound("productId.specified=false");
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProductIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where productId is greater than or equal to DEFAULT_PRODUCT_ID
        defaultTotalConsumptionShouldBeFound("productId.greaterThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the totalConsumptionList where productId is greater than or equal to UPDATED_PRODUCT_ID
        defaultTotalConsumptionShouldNotBeFound("productId.greaterThanOrEqual=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProductIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where productId is less than or equal to DEFAULT_PRODUCT_ID
        defaultTotalConsumptionShouldBeFound("productId.lessThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the totalConsumptionList where productId is less than or equal to SMALLER_PRODUCT_ID
        defaultTotalConsumptionShouldNotBeFound("productId.lessThanOrEqual=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProductIdIsLessThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where productId is less than DEFAULT_PRODUCT_ID
        defaultTotalConsumptionShouldNotBeFound("productId.lessThan=" + DEFAULT_PRODUCT_ID);

        // Get all the totalConsumptionList where productId is less than UPDATED_PRODUCT_ID
        defaultTotalConsumptionShouldBeFound("productId.lessThan=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByProductIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where productId is greater than DEFAULT_PRODUCT_ID
        defaultTotalConsumptionShouldNotBeFound("productId.greaterThan=" + DEFAULT_PRODUCT_ID);

        // Get all the totalConsumptionList where productId is greater than SMALLER_PRODUCT_ID
        defaultTotalConsumptionShouldBeFound("productId.greaterThan=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalMaterialCostIsEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalMaterialCost equals to DEFAULT_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldBeFound("totalMaterialCost.equals=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the totalConsumptionList where totalMaterialCost equals to UPDATED_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldNotBeFound("totalMaterialCost.equals=" + UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalMaterialCostIsInShouldWork() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalMaterialCost in DEFAULT_TOTAL_MATERIAL_COST or UPDATED_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldBeFound("totalMaterialCost.in=" + DEFAULT_TOTAL_MATERIAL_COST + "," + UPDATED_TOTAL_MATERIAL_COST);

        // Get all the totalConsumptionList where totalMaterialCost equals to UPDATED_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldNotBeFound("totalMaterialCost.in=" + UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalMaterialCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalMaterialCost is not null
        defaultTotalConsumptionShouldBeFound("totalMaterialCost.specified=true");

        // Get all the totalConsumptionList where totalMaterialCost is null
        defaultTotalConsumptionShouldNotBeFound("totalMaterialCost.specified=false");
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalMaterialCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalMaterialCost is greater than or equal to DEFAULT_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldBeFound("totalMaterialCost.greaterThanOrEqual=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the totalConsumptionList where totalMaterialCost is greater than or equal to UPDATED_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldNotBeFound("totalMaterialCost.greaterThanOrEqual=" + UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalMaterialCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalMaterialCost is less than or equal to DEFAULT_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldBeFound("totalMaterialCost.lessThanOrEqual=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the totalConsumptionList where totalMaterialCost is less than or equal to SMALLER_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldNotBeFound("totalMaterialCost.lessThanOrEqual=" + SMALLER_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalMaterialCostIsLessThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalMaterialCost is less than DEFAULT_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldNotBeFound("totalMaterialCost.lessThan=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the totalConsumptionList where totalMaterialCost is less than UPDATED_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldBeFound("totalMaterialCost.lessThan=" + UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalMaterialCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalMaterialCost is greater than DEFAULT_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldNotBeFound("totalMaterialCost.greaterThan=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the totalConsumptionList where totalMaterialCost is greater than SMALLER_TOTAL_MATERIAL_COST
        defaultTotalConsumptionShouldBeFound("totalMaterialCost.greaterThan=" + SMALLER_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalProductsCostIsEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalProductsCost equals to DEFAULT_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldBeFound("totalProductsCost.equals=" + DEFAULT_TOTAL_PRODUCTS_COST);

        // Get all the totalConsumptionList where totalProductsCost equals to UPDATED_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldNotBeFound("totalProductsCost.equals=" + UPDATED_TOTAL_PRODUCTS_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalProductsCostIsInShouldWork() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalProductsCost in DEFAULT_TOTAL_PRODUCTS_COST or UPDATED_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldBeFound("totalProductsCost.in=" + DEFAULT_TOTAL_PRODUCTS_COST + "," + UPDATED_TOTAL_PRODUCTS_COST);

        // Get all the totalConsumptionList where totalProductsCost equals to UPDATED_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldNotBeFound("totalProductsCost.in=" + UPDATED_TOTAL_PRODUCTS_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalProductsCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalProductsCost is not null
        defaultTotalConsumptionShouldBeFound("totalProductsCost.specified=true");

        // Get all the totalConsumptionList where totalProductsCost is null
        defaultTotalConsumptionShouldNotBeFound("totalProductsCost.specified=false");
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalProductsCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalProductsCost is greater than or equal to DEFAULT_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldBeFound("totalProductsCost.greaterThanOrEqual=" + DEFAULT_TOTAL_PRODUCTS_COST);

        // Get all the totalConsumptionList where totalProductsCost is greater than or equal to UPDATED_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldNotBeFound("totalProductsCost.greaterThanOrEqual=" + UPDATED_TOTAL_PRODUCTS_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalProductsCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalProductsCost is less than or equal to DEFAULT_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldBeFound("totalProductsCost.lessThanOrEqual=" + DEFAULT_TOTAL_PRODUCTS_COST);

        // Get all the totalConsumptionList where totalProductsCost is less than or equal to SMALLER_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldNotBeFound("totalProductsCost.lessThanOrEqual=" + SMALLER_TOTAL_PRODUCTS_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalProductsCostIsLessThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalProductsCost is less than DEFAULT_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldNotBeFound("totalProductsCost.lessThan=" + DEFAULT_TOTAL_PRODUCTS_COST);

        // Get all the totalConsumptionList where totalProductsCost is less than UPDATED_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldBeFound("totalProductsCost.lessThan=" + UPDATED_TOTAL_PRODUCTS_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByTotalProductsCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where totalProductsCost is greater than DEFAULT_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldNotBeFound("totalProductsCost.greaterThan=" + DEFAULT_TOTAL_PRODUCTS_COST);

        // Get all the totalConsumptionList where totalProductsCost is greater than SMALLER_TOTAL_PRODUCTS_COST
        defaultTotalConsumptionShouldBeFound("totalProductsCost.greaterThan=" + SMALLER_TOTAL_PRODUCTS_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByFinalCostIsEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where finalCost equals to DEFAULT_FINAL_COST
        defaultTotalConsumptionShouldBeFound("finalCost.equals=" + DEFAULT_FINAL_COST);

        // Get all the totalConsumptionList where finalCost equals to UPDATED_FINAL_COST
        defaultTotalConsumptionShouldNotBeFound("finalCost.equals=" + UPDATED_FINAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByFinalCostIsInShouldWork() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where finalCost in DEFAULT_FINAL_COST or UPDATED_FINAL_COST
        defaultTotalConsumptionShouldBeFound("finalCost.in=" + DEFAULT_FINAL_COST + "," + UPDATED_FINAL_COST);

        // Get all the totalConsumptionList where finalCost equals to UPDATED_FINAL_COST
        defaultTotalConsumptionShouldNotBeFound("finalCost.in=" + UPDATED_FINAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByFinalCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where finalCost is not null
        defaultTotalConsumptionShouldBeFound("finalCost.specified=true");

        // Get all the totalConsumptionList where finalCost is null
        defaultTotalConsumptionShouldNotBeFound("finalCost.specified=false");
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByFinalCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where finalCost is greater than or equal to DEFAULT_FINAL_COST
        defaultTotalConsumptionShouldBeFound("finalCost.greaterThanOrEqual=" + DEFAULT_FINAL_COST);

        // Get all the totalConsumptionList where finalCost is greater than or equal to UPDATED_FINAL_COST
        defaultTotalConsumptionShouldNotBeFound("finalCost.greaterThanOrEqual=" + UPDATED_FINAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByFinalCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where finalCost is less than or equal to DEFAULT_FINAL_COST
        defaultTotalConsumptionShouldBeFound("finalCost.lessThanOrEqual=" + DEFAULT_FINAL_COST);

        // Get all the totalConsumptionList where finalCost is less than or equal to SMALLER_FINAL_COST
        defaultTotalConsumptionShouldNotBeFound("finalCost.lessThanOrEqual=" + SMALLER_FINAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByFinalCostIsLessThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where finalCost is less than DEFAULT_FINAL_COST
        defaultTotalConsumptionShouldNotBeFound("finalCost.lessThan=" + DEFAULT_FINAL_COST);

        // Get all the totalConsumptionList where finalCost is less than UPDATED_FINAL_COST
        defaultTotalConsumptionShouldBeFound("finalCost.lessThan=" + UPDATED_FINAL_COST);
    }

    @Test
    @Transactional
    void getAllTotalConsumptionsByFinalCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        // Get all the totalConsumptionList where finalCost is greater than DEFAULT_FINAL_COST
        defaultTotalConsumptionShouldNotBeFound("finalCost.greaterThan=" + DEFAULT_FINAL_COST);

        // Get all the totalConsumptionList where finalCost is greater than SMALLER_FINAL_COST
        defaultTotalConsumptionShouldBeFound("finalCost.greaterThan=" + SMALLER_FINAL_COST);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTotalConsumptionShouldBeFound(String filter) throws Exception {
        restTotalConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(totalConsumption.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalMaterialCost").value(hasItem(DEFAULT_TOTAL_MATERIAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].totalProductsCost").value(hasItem(DEFAULT_TOTAL_PRODUCTS_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].finalCost").value(hasItem(DEFAULT_FINAL_COST.doubleValue())));

        // Check, that the count call also returns 1
        restTotalConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTotalConsumptionShouldNotBeFound(String filter) throws Exception {
        restTotalConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTotalConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTotalConsumption() throws Exception {
        // Get the totalConsumption
        restTotalConsumptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTotalConsumption() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();

        // Update the totalConsumption
        TotalConsumption updatedTotalConsumption = totalConsumptionRepository.findById(totalConsumption.getId()).get();
        // Disconnect from session so that the updates on updatedTotalConsumption are not directly saved in db
        em.detach(updatedTotalConsumption);
        updatedTotalConsumption
            .projectId(UPDATED_PROJECT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST)
            .totalProductsCost(UPDATED_TOTAL_PRODUCTS_COST)
            .finalCost(UPDATED_FINAL_COST);
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(updatedTotalConsumption);

        restTotalConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, totalConsumptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
        TotalConsumption testTotalConsumption = totalConsumptionList.get(totalConsumptionList.size() - 1);
        assertThat(testTotalConsumption.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testTotalConsumption.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testTotalConsumption.getTotalMaterialCost()).isEqualTo(UPDATED_TOTAL_MATERIAL_COST);
        assertThat(testTotalConsumption.getTotalProductsCost()).isEqualTo(UPDATED_TOTAL_PRODUCTS_COST);
        assertThat(testTotalConsumption.getFinalCost()).isEqualTo(UPDATED_FINAL_COST);
    }

    @Test
    @Transactional
    void putNonExistingTotalConsumption() throws Exception {
        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();
        totalConsumption.setId(count.incrementAndGet());

        // Create the TotalConsumption
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(totalConsumption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, totalConsumptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTotalConsumption() throws Exception {
        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();
        totalConsumption.setId(count.incrementAndGet());

        // Create the TotalConsumption
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(totalConsumption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTotalConsumption() throws Exception {
        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();
        totalConsumption.setId(count.incrementAndGet());

        // Create the TotalConsumption
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(totalConsumption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTotalConsumptionWithPatch() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();

        // Update the totalConsumption using partial update
        TotalConsumption partialUpdatedTotalConsumption = new TotalConsumption();
        partialUpdatedTotalConsumption.setId(totalConsumption.getId());

        partialUpdatedTotalConsumption
            .productId(UPDATED_PRODUCT_ID)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST)
            .totalProductsCost(UPDATED_TOTAL_PRODUCTS_COST)
            .finalCost(UPDATED_FINAL_COST);

        restTotalConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotalConsumption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotalConsumption))
            )
            .andExpect(status().isOk());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
        TotalConsumption testTotalConsumption = totalConsumptionList.get(totalConsumptionList.size() - 1);
        assertThat(testTotalConsumption.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testTotalConsumption.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testTotalConsumption.getTotalMaterialCost()).isEqualTo(UPDATED_TOTAL_MATERIAL_COST);
        assertThat(testTotalConsumption.getTotalProductsCost()).isEqualTo(UPDATED_TOTAL_PRODUCTS_COST);
        assertThat(testTotalConsumption.getFinalCost()).isEqualTo(UPDATED_FINAL_COST);
    }

    @Test
    @Transactional
    void fullUpdateTotalConsumptionWithPatch() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();

        // Update the totalConsumption using partial update
        TotalConsumption partialUpdatedTotalConsumption = new TotalConsumption();
        partialUpdatedTotalConsumption.setId(totalConsumption.getId());

        partialUpdatedTotalConsumption
            .projectId(UPDATED_PROJECT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST)
            .totalProductsCost(UPDATED_TOTAL_PRODUCTS_COST)
            .finalCost(UPDATED_FINAL_COST);

        restTotalConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotalConsumption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotalConsumption))
            )
            .andExpect(status().isOk());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
        TotalConsumption testTotalConsumption = totalConsumptionList.get(totalConsumptionList.size() - 1);
        assertThat(testTotalConsumption.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testTotalConsumption.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testTotalConsumption.getTotalMaterialCost()).isEqualTo(UPDATED_TOTAL_MATERIAL_COST);
        assertThat(testTotalConsumption.getTotalProductsCost()).isEqualTo(UPDATED_TOTAL_PRODUCTS_COST);
        assertThat(testTotalConsumption.getFinalCost()).isEqualTo(UPDATED_FINAL_COST);
    }

    @Test
    @Transactional
    void patchNonExistingTotalConsumption() throws Exception {
        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();
        totalConsumption.setId(count.incrementAndGet());

        // Create the TotalConsumption
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(totalConsumption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, totalConsumptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTotalConsumption() throws Exception {
        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();
        totalConsumption.setId(count.incrementAndGet());

        // Create the TotalConsumption
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(totalConsumption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTotalConsumption() throws Exception {
        int databaseSizeBeforeUpdate = totalConsumptionRepository.findAll().size();
        totalConsumption.setId(count.incrementAndGet());

        // Create the TotalConsumption
        TotalConsumptionDTO totalConsumptionDTO = totalConsumptionMapper.toDto(totalConsumption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalConsumptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TotalConsumption in the database
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTotalConsumption() throws Exception {
        // Initialize the database
        totalConsumptionRepository.saveAndFlush(totalConsumption);

        int databaseSizeBeforeDelete = totalConsumptionRepository.findAll().size();

        // Delete the totalConsumption
        restTotalConsumptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, totalConsumption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TotalConsumption> totalConsumptionList = totalConsumptionRepository.findAll();
        assertThat(totalConsumptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
