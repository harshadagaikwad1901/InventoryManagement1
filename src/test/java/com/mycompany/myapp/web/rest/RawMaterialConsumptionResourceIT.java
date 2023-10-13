package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RawMaterialConsumption;
import com.mycompany.myapp.repository.RawMaterialConsumptionRepository;
import com.mycompany.myapp.service.criteria.RawMaterialConsumptionCriteria;
import com.mycompany.myapp.service.dto.RawMaterialConsumptionDTO;
import com.mycompany.myapp.service.mapper.RawMaterialConsumptionMapper;
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
 * Integration tests for the {@link RawMaterialConsumptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RawMaterialConsumptionResourceIT {

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;
    private static final Long SMALLER_PROJECT_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;
    private static final Long SMALLER_PRODUCT_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCTION_LINE = 1L;
    private static final Long UPDATED_PRODUCTION_LINE = 2L;
    private static final Long SMALLER_PRODUCTION_LINE = 1L - 1L;

    private static final Long DEFAULT_RAW_MATERIAL_ID = 1L;
    private static final Long UPDATED_RAW_MATERIAL_ID = 2L;
    private static final Long SMALLER_RAW_MATERIAL_ID = 1L - 1L;

    private static final Double DEFAULT_QUANTITY_CONSUMED = 1D;
    private static final Double UPDATED_QUANTITY_CONSUMED = 2D;
    private static final Double SMALLER_QUANTITY_CONSUMED = 1D - 1D;

    private static final Double DEFAULT_SCRAP_GENERATED = 1D;
    private static final Double UPDATED_SCRAP_GENERATED = 2D;
    private static final Double SMALLER_SCRAP_GENERATED = 1D - 1D;

    private static final Double DEFAULT_TOTAL_MATERIAL_COST = 1D;
    private static final Double UPDATED_TOTAL_MATERIAL_COST = 2D;
    private static final Double SMALLER_TOTAL_MATERIAL_COST = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/raw-material-consumptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RawMaterialConsumptionRepository rawMaterialConsumptionRepository;

    @Autowired
    private RawMaterialConsumptionMapper rawMaterialConsumptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRawMaterialConsumptionMockMvc;

    private RawMaterialConsumption rawMaterialConsumption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterialConsumption createEntity(EntityManager em) {
        RawMaterialConsumption rawMaterialConsumption = new RawMaterialConsumption()
            .projectId(DEFAULT_PROJECT_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .productionLine(DEFAULT_PRODUCTION_LINE)
            .rawMaterialId(DEFAULT_RAW_MATERIAL_ID)
            .quantityConsumed(DEFAULT_QUANTITY_CONSUMED)
            .scrapGenerated(DEFAULT_SCRAP_GENERATED)
            .totalMaterialCost(DEFAULT_TOTAL_MATERIAL_COST);
        return rawMaterialConsumption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterialConsumption createUpdatedEntity(EntityManager em) {
        RawMaterialConsumption rawMaterialConsumption = new RawMaterialConsumption()
            .projectId(UPDATED_PROJECT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productionLine(UPDATED_PRODUCTION_LINE)
            .rawMaterialId(UPDATED_RAW_MATERIAL_ID)
            .quantityConsumed(UPDATED_QUANTITY_CONSUMED)
            .scrapGenerated(UPDATED_SCRAP_GENERATED)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST);
        return rawMaterialConsumption;
    }

    @BeforeEach
    public void initTest() {
        rawMaterialConsumption = createEntity(em);
    }

    @Test
    @Transactional
    void createRawMaterialConsumption() throws Exception {
        int databaseSizeBeforeCreate = rawMaterialConsumptionRepository.findAll().size();
        // Create the RawMaterialConsumption
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(rawMaterialConsumption);
        restRawMaterialConsumptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeCreate + 1);
        RawMaterialConsumption testRawMaterialConsumption = rawMaterialConsumptionList.get(rawMaterialConsumptionList.size() - 1);
        assertThat(testRawMaterialConsumption.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testRawMaterialConsumption.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRawMaterialConsumption.getProductionLine()).isEqualTo(DEFAULT_PRODUCTION_LINE);
        assertThat(testRawMaterialConsumption.getRawMaterialId()).isEqualTo(DEFAULT_RAW_MATERIAL_ID);
        assertThat(testRawMaterialConsumption.getQuantityConsumed()).isEqualTo(DEFAULT_QUANTITY_CONSUMED);
        assertThat(testRawMaterialConsumption.getScrapGenerated()).isEqualTo(DEFAULT_SCRAP_GENERATED);
        assertThat(testRawMaterialConsumption.getTotalMaterialCost()).isEqualTo(DEFAULT_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void createRawMaterialConsumptionWithExistingId() throws Exception {
        // Create the RawMaterialConsumption with an existing ID
        rawMaterialConsumption.setId(1L);
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(rawMaterialConsumption);

        int databaseSizeBeforeCreate = rawMaterialConsumptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRawMaterialConsumptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptions() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList
        restRawMaterialConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawMaterialConsumption.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productionLine").value(hasItem(DEFAULT_PRODUCTION_LINE.intValue())))
            .andExpect(jsonPath("$.[*].rawMaterialId").value(hasItem(DEFAULT_RAW_MATERIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].quantityConsumed").value(hasItem(DEFAULT_QUANTITY_CONSUMED.doubleValue())))
            .andExpect(jsonPath("$.[*].scrapGenerated").value(hasItem(DEFAULT_SCRAP_GENERATED.doubleValue())))
            .andExpect(jsonPath("$.[*].totalMaterialCost").value(hasItem(DEFAULT_TOTAL_MATERIAL_COST.doubleValue())));
    }

    @Test
    @Transactional
    void getRawMaterialConsumption() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get the rawMaterialConsumption
        restRawMaterialConsumptionMockMvc
            .perform(get(ENTITY_API_URL_ID, rawMaterialConsumption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rawMaterialConsumption.getId().intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.productionLine").value(DEFAULT_PRODUCTION_LINE.intValue()))
            .andExpect(jsonPath("$.rawMaterialId").value(DEFAULT_RAW_MATERIAL_ID.intValue()))
            .andExpect(jsonPath("$.quantityConsumed").value(DEFAULT_QUANTITY_CONSUMED.doubleValue()))
            .andExpect(jsonPath("$.scrapGenerated").value(DEFAULT_SCRAP_GENERATED.doubleValue()))
            .andExpect(jsonPath("$.totalMaterialCost").value(DEFAULT_TOTAL_MATERIAL_COST.doubleValue()));
    }

    @Test
    @Transactional
    void getRawMaterialConsumptionsByIdFiltering() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        Long id = rawMaterialConsumption.getId();

        defaultRawMaterialConsumptionShouldBeFound("id.equals=" + id);
        defaultRawMaterialConsumptionShouldNotBeFound("id.notEquals=" + id);

        defaultRawMaterialConsumptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRawMaterialConsumptionShouldNotBeFound("id.greaterThan=" + id);

        defaultRawMaterialConsumptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRawMaterialConsumptionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProjectIdIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where projectId equals to DEFAULT_PROJECT_ID
        defaultRawMaterialConsumptionShouldBeFound("projectId.equals=" + DEFAULT_PROJECT_ID);

        // Get all the rawMaterialConsumptionList where projectId equals to UPDATED_PROJECT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("projectId.equals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProjectIdIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where projectId in DEFAULT_PROJECT_ID or UPDATED_PROJECT_ID
        defaultRawMaterialConsumptionShouldBeFound("projectId.in=" + DEFAULT_PROJECT_ID + "," + UPDATED_PROJECT_ID);

        // Get all the rawMaterialConsumptionList where projectId equals to UPDATED_PROJECT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("projectId.in=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProjectIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where projectId is not null
        defaultRawMaterialConsumptionShouldBeFound("projectId.specified=true");

        // Get all the rawMaterialConsumptionList where projectId is null
        defaultRawMaterialConsumptionShouldNotBeFound("projectId.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProjectIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where projectId is greater than or equal to DEFAULT_PROJECT_ID
        defaultRawMaterialConsumptionShouldBeFound("projectId.greaterThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the rawMaterialConsumptionList where projectId is greater than or equal to UPDATED_PROJECT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("projectId.greaterThanOrEqual=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProjectIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where projectId is less than or equal to DEFAULT_PROJECT_ID
        defaultRawMaterialConsumptionShouldBeFound("projectId.lessThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the rawMaterialConsumptionList where projectId is less than or equal to SMALLER_PROJECT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("projectId.lessThanOrEqual=" + SMALLER_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProjectIdIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where projectId is less than DEFAULT_PROJECT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("projectId.lessThan=" + DEFAULT_PROJECT_ID);

        // Get all the rawMaterialConsumptionList where projectId is less than UPDATED_PROJECT_ID
        defaultRawMaterialConsumptionShouldBeFound("projectId.lessThan=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProjectIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where projectId is greater than DEFAULT_PROJECT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("projectId.greaterThan=" + DEFAULT_PROJECT_ID);

        // Get all the rawMaterialConsumptionList where projectId is greater than SMALLER_PROJECT_ID
        defaultRawMaterialConsumptionShouldBeFound("projectId.greaterThan=" + SMALLER_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productId equals to DEFAULT_PRODUCT_ID
        defaultRawMaterialConsumptionShouldBeFound("productId.equals=" + DEFAULT_PRODUCT_ID);

        // Get all the rawMaterialConsumptionList where productId equals to UPDATED_PRODUCT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("productId.equals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productId in DEFAULT_PRODUCT_ID or UPDATED_PRODUCT_ID
        defaultRawMaterialConsumptionShouldBeFound("productId.in=" + DEFAULT_PRODUCT_ID + "," + UPDATED_PRODUCT_ID);

        // Get all the rawMaterialConsumptionList where productId equals to UPDATED_PRODUCT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("productId.in=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productId is not null
        defaultRawMaterialConsumptionShouldBeFound("productId.specified=true");

        // Get all the rawMaterialConsumptionList where productId is null
        defaultRawMaterialConsumptionShouldNotBeFound("productId.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productId is greater than or equal to DEFAULT_PRODUCT_ID
        defaultRawMaterialConsumptionShouldBeFound("productId.greaterThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the rawMaterialConsumptionList where productId is greater than or equal to UPDATED_PRODUCT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("productId.greaterThanOrEqual=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productId is less than or equal to DEFAULT_PRODUCT_ID
        defaultRawMaterialConsumptionShouldBeFound("productId.lessThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the rawMaterialConsumptionList where productId is less than or equal to SMALLER_PRODUCT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("productId.lessThanOrEqual=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductIdIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productId is less than DEFAULT_PRODUCT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("productId.lessThan=" + DEFAULT_PRODUCT_ID);

        // Get all the rawMaterialConsumptionList where productId is less than UPDATED_PRODUCT_ID
        defaultRawMaterialConsumptionShouldBeFound("productId.lessThan=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productId is greater than DEFAULT_PRODUCT_ID
        defaultRawMaterialConsumptionShouldNotBeFound("productId.greaterThan=" + DEFAULT_PRODUCT_ID);

        // Get all the rawMaterialConsumptionList where productId is greater than SMALLER_PRODUCT_ID
        defaultRawMaterialConsumptionShouldBeFound("productId.greaterThan=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductionLineIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productionLine equals to DEFAULT_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldBeFound("productionLine.equals=" + DEFAULT_PRODUCTION_LINE);

        // Get all the rawMaterialConsumptionList where productionLine equals to UPDATED_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldNotBeFound("productionLine.equals=" + UPDATED_PRODUCTION_LINE);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductionLineIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productionLine in DEFAULT_PRODUCTION_LINE or UPDATED_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldBeFound("productionLine.in=" + DEFAULT_PRODUCTION_LINE + "," + UPDATED_PRODUCTION_LINE);

        // Get all the rawMaterialConsumptionList where productionLine equals to UPDATED_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldNotBeFound("productionLine.in=" + UPDATED_PRODUCTION_LINE);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductionLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productionLine is not null
        defaultRawMaterialConsumptionShouldBeFound("productionLine.specified=true");

        // Get all the rawMaterialConsumptionList where productionLine is null
        defaultRawMaterialConsumptionShouldNotBeFound("productionLine.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductionLineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productionLine is greater than or equal to DEFAULT_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldBeFound("productionLine.greaterThanOrEqual=" + DEFAULT_PRODUCTION_LINE);

        // Get all the rawMaterialConsumptionList where productionLine is greater than or equal to UPDATED_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldNotBeFound("productionLine.greaterThanOrEqual=" + UPDATED_PRODUCTION_LINE);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductionLineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productionLine is less than or equal to DEFAULT_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldBeFound("productionLine.lessThanOrEqual=" + DEFAULT_PRODUCTION_LINE);

        // Get all the rawMaterialConsumptionList where productionLine is less than or equal to SMALLER_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldNotBeFound("productionLine.lessThanOrEqual=" + SMALLER_PRODUCTION_LINE);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductionLineIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productionLine is less than DEFAULT_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldNotBeFound("productionLine.lessThan=" + DEFAULT_PRODUCTION_LINE);

        // Get all the rawMaterialConsumptionList where productionLine is less than UPDATED_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldBeFound("productionLine.lessThan=" + UPDATED_PRODUCTION_LINE);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByProductionLineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where productionLine is greater than DEFAULT_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldNotBeFound("productionLine.greaterThan=" + DEFAULT_PRODUCTION_LINE);

        // Get all the rawMaterialConsumptionList where productionLine is greater than SMALLER_PRODUCTION_LINE
        defaultRawMaterialConsumptionShouldBeFound("productionLine.greaterThan=" + SMALLER_PRODUCTION_LINE);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByRawMaterialIdIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where rawMaterialId equals to DEFAULT_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldBeFound("rawMaterialId.equals=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the rawMaterialConsumptionList where rawMaterialId equals to UPDATED_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldNotBeFound("rawMaterialId.equals=" + UPDATED_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByRawMaterialIdIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where rawMaterialId in DEFAULT_RAW_MATERIAL_ID or UPDATED_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldBeFound("rawMaterialId.in=" + DEFAULT_RAW_MATERIAL_ID + "," + UPDATED_RAW_MATERIAL_ID);

        // Get all the rawMaterialConsumptionList where rawMaterialId equals to UPDATED_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldNotBeFound("rawMaterialId.in=" + UPDATED_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByRawMaterialIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where rawMaterialId is not null
        defaultRawMaterialConsumptionShouldBeFound("rawMaterialId.specified=true");

        // Get all the rawMaterialConsumptionList where rawMaterialId is null
        defaultRawMaterialConsumptionShouldNotBeFound("rawMaterialId.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByRawMaterialIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where rawMaterialId is greater than or equal to DEFAULT_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldBeFound("rawMaterialId.greaterThanOrEqual=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the rawMaterialConsumptionList where rawMaterialId is greater than or equal to UPDATED_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldNotBeFound("rawMaterialId.greaterThanOrEqual=" + UPDATED_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByRawMaterialIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where rawMaterialId is less than or equal to DEFAULT_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldBeFound("rawMaterialId.lessThanOrEqual=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the rawMaterialConsumptionList where rawMaterialId is less than or equal to SMALLER_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldNotBeFound("rawMaterialId.lessThanOrEqual=" + SMALLER_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByRawMaterialIdIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where rawMaterialId is less than DEFAULT_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldNotBeFound("rawMaterialId.lessThan=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the rawMaterialConsumptionList where rawMaterialId is less than UPDATED_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldBeFound("rawMaterialId.lessThan=" + UPDATED_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByRawMaterialIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where rawMaterialId is greater than DEFAULT_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldNotBeFound("rawMaterialId.greaterThan=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the rawMaterialConsumptionList where rawMaterialId is greater than SMALLER_RAW_MATERIAL_ID
        defaultRawMaterialConsumptionShouldBeFound("rawMaterialId.greaterThan=" + SMALLER_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByQuantityConsumedIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where quantityConsumed equals to DEFAULT_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldBeFound("quantityConsumed.equals=" + DEFAULT_QUANTITY_CONSUMED);

        // Get all the rawMaterialConsumptionList where quantityConsumed equals to UPDATED_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldNotBeFound("quantityConsumed.equals=" + UPDATED_QUANTITY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByQuantityConsumedIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where quantityConsumed in DEFAULT_QUANTITY_CONSUMED or UPDATED_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldBeFound("quantityConsumed.in=" + DEFAULT_QUANTITY_CONSUMED + "," + UPDATED_QUANTITY_CONSUMED);

        // Get all the rawMaterialConsumptionList where quantityConsumed equals to UPDATED_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldNotBeFound("quantityConsumed.in=" + UPDATED_QUANTITY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByQuantityConsumedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where quantityConsumed is not null
        defaultRawMaterialConsumptionShouldBeFound("quantityConsumed.specified=true");

        // Get all the rawMaterialConsumptionList where quantityConsumed is null
        defaultRawMaterialConsumptionShouldNotBeFound("quantityConsumed.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByQuantityConsumedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where quantityConsumed is greater than or equal to DEFAULT_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldBeFound("quantityConsumed.greaterThanOrEqual=" + DEFAULT_QUANTITY_CONSUMED);

        // Get all the rawMaterialConsumptionList where quantityConsumed is greater than or equal to UPDATED_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldNotBeFound("quantityConsumed.greaterThanOrEqual=" + UPDATED_QUANTITY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByQuantityConsumedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where quantityConsumed is less than or equal to DEFAULT_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldBeFound("quantityConsumed.lessThanOrEqual=" + DEFAULT_QUANTITY_CONSUMED);

        // Get all the rawMaterialConsumptionList where quantityConsumed is less than or equal to SMALLER_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldNotBeFound("quantityConsumed.lessThanOrEqual=" + SMALLER_QUANTITY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByQuantityConsumedIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where quantityConsumed is less than DEFAULT_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldNotBeFound("quantityConsumed.lessThan=" + DEFAULT_QUANTITY_CONSUMED);

        // Get all the rawMaterialConsumptionList where quantityConsumed is less than UPDATED_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldBeFound("quantityConsumed.lessThan=" + UPDATED_QUANTITY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByQuantityConsumedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where quantityConsumed is greater than DEFAULT_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldNotBeFound("quantityConsumed.greaterThan=" + DEFAULT_QUANTITY_CONSUMED);

        // Get all the rawMaterialConsumptionList where quantityConsumed is greater than SMALLER_QUANTITY_CONSUMED
        defaultRawMaterialConsumptionShouldBeFound("quantityConsumed.greaterThan=" + SMALLER_QUANTITY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByScrapGeneratedIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where scrapGenerated equals to DEFAULT_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldBeFound("scrapGenerated.equals=" + DEFAULT_SCRAP_GENERATED);

        // Get all the rawMaterialConsumptionList where scrapGenerated equals to UPDATED_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldNotBeFound("scrapGenerated.equals=" + UPDATED_SCRAP_GENERATED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByScrapGeneratedIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where scrapGenerated in DEFAULT_SCRAP_GENERATED or UPDATED_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldBeFound("scrapGenerated.in=" + DEFAULT_SCRAP_GENERATED + "," + UPDATED_SCRAP_GENERATED);

        // Get all the rawMaterialConsumptionList where scrapGenerated equals to UPDATED_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldNotBeFound("scrapGenerated.in=" + UPDATED_SCRAP_GENERATED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByScrapGeneratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where scrapGenerated is not null
        defaultRawMaterialConsumptionShouldBeFound("scrapGenerated.specified=true");

        // Get all the rawMaterialConsumptionList where scrapGenerated is null
        defaultRawMaterialConsumptionShouldNotBeFound("scrapGenerated.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByScrapGeneratedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where scrapGenerated is greater than or equal to DEFAULT_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldBeFound("scrapGenerated.greaterThanOrEqual=" + DEFAULT_SCRAP_GENERATED);

        // Get all the rawMaterialConsumptionList where scrapGenerated is greater than or equal to UPDATED_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldNotBeFound("scrapGenerated.greaterThanOrEqual=" + UPDATED_SCRAP_GENERATED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByScrapGeneratedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where scrapGenerated is less than or equal to DEFAULT_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldBeFound("scrapGenerated.lessThanOrEqual=" + DEFAULT_SCRAP_GENERATED);

        // Get all the rawMaterialConsumptionList where scrapGenerated is less than or equal to SMALLER_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldNotBeFound("scrapGenerated.lessThanOrEqual=" + SMALLER_SCRAP_GENERATED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByScrapGeneratedIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where scrapGenerated is less than DEFAULT_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldNotBeFound("scrapGenerated.lessThan=" + DEFAULT_SCRAP_GENERATED);

        // Get all the rawMaterialConsumptionList where scrapGenerated is less than UPDATED_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldBeFound("scrapGenerated.lessThan=" + UPDATED_SCRAP_GENERATED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByScrapGeneratedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where scrapGenerated is greater than DEFAULT_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldNotBeFound("scrapGenerated.greaterThan=" + DEFAULT_SCRAP_GENERATED);

        // Get all the rawMaterialConsumptionList where scrapGenerated is greater than SMALLER_SCRAP_GENERATED
        defaultRawMaterialConsumptionShouldBeFound("scrapGenerated.greaterThan=" + SMALLER_SCRAP_GENERATED);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByTotalMaterialCostIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where totalMaterialCost equals to DEFAULT_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldBeFound("totalMaterialCost.equals=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the rawMaterialConsumptionList where totalMaterialCost equals to UPDATED_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldNotBeFound("totalMaterialCost.equals=" + UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByTotalMaterialCostIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where totalMaterialCost in DEFAULT_TOTAL_MATERIAL_COST or UPDATED_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldBeFound(
            "totalMaterialCost.in=" + DEFAULT_TOTAL_MATERIAL_COST + "," + UPDATED_TOTAL_MATERIAL_COST
        );

        // Get all the rawMaterialConsumptionList where totalMaterialCost equals to UPDATED_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldNotBeFound("totalMaterialCost.in=" + UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByTotalMaterialCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is not null
        defaultRawMaterialConsumptionShouldBeFound("totalMaterialCost.specified=true");

        // Get all the rawMaterialConsumptionList where totalMaterialCost is null
        defaultRawMaterialConsumptionShouldNotBeFound("totalMaterialCost.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByTotalMaterialCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is greater than or equal to DEFAULT_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldBeFound("totalMaterialCost.greaterThanOrEqual=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is greater than or equal to UPDATED_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldNotBeFound("totalMaterialCost.greaterThanOrEqual=" + UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByTotalMaterialCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is less than or equal to DEFAULT_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldBeFound("totalMaterialCost.lessThanOrEqual=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is less than or equal to SMALLER_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldNotBeFound("totalMaterialCost.lessThanOrEqual=" + SMALLER_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByTotalMaterialCostIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is less than DEFAULT_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldNotBeFound("totalMaterialCost.lessThan=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is less than UPDATED_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldBeFound("totalMaterialCost.lessThan=" + UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void getAllRawMaterialConsumptionsByTotalMaterialCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is greater than DEFAULT_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldNotBeFound("totalMaterialCost.greaterThan=" + DEFAULT_TOTAL_MATERIAL_COST);

        // Get all the rawMaterialConsumptionList where totalMaterialCost is greater than SMALLER_TOTAL_MATERIAL_COST
        defaultRawMaterialConsumptionShouldBeFound("totalMaterialCost.greaterThan=" + SMALLER_TOTAL_MATERIAL_COST);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRawMaterialConsumptionShouldBeFound(String filter) throws Exception {
        restRawMaterialConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawMaterialConsumption.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productionLine").value(hasItem(DEFAULT_PRODUCTION_LINE.intValue())))
            .andExpect(jsonPath("$.[*].rawMaterialId").value(hasItem(DEFAULT_RAW_MATERIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].quantityConsumed").value(hasItem(DEFAULT_QUANTITY_CONSUMED.doubleValue())))
            .andExpect(jsonPath("$.[*].scrapGenerated").value(hasItem(DEFAULT_SCRAP_GENERATED.doubleValue())))
            .andExpect(jsonPath("$.[*].totalMaterialCost").value(hasItem(DEFAULT_TOTAL_MATERIAL_COST.doubleValue())));

        // Check, that the count call also returns 1
        restRawMaterialConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRawMaterialConsumptionShouldNotBeFound(String filter) throws Exception {
        restRawMaterialConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRawMaterialConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRawMaterialConsumption() throws Exception {
        // Get the rawMaterialConsumption
        restRawMaterialConsumptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRawMaterialConsumption() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();

        // Update the rawMaterialConsumption
        RawMaterialConsumption updatedRawMaterialConsumption = rawMaterialConsumptionRepository
            .findById(rawMaterialConsumption.getId())
            .get();
        // Disconnect from session so that the updates on updatedRawMaterialConsumption are not directly saved in db
        em.detach(updatedRawMaterialConsumption);
        updatedRawMaterialConsumption
            .projectId(UPDATED_PROJECT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productionLine(UPDATED_PRODUCTION_LINE)
            .rawMaterialId(UPDATED_RAW_MATERIAL_ID)
            .quantityConsumed(UPDATED_QUANTITY_CONSUMED)
            .scrapGenerated(UPDATED_SCRAP_GENERATED)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST);
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(updatedRawMaterialConsumption);

        restRawMaterialConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawMaterialConsumptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialConsumption testRawMaterialConsumption = rawMaterialConsumptionList.get(rawMaterialConsumptionList.size() - 1);
        assertThat(testRawMaterialConsumption.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testRawMaterialConsumption.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRawMaterialConsumption.getProductionLine()).isEqualTo(UPDATED_PRODUCTION_LINE);
        assertThat(testRawMaterialConsumption.getRawMaterialId()).isEqualTo(UPDATED_RAW_MATERIAL_ID);
        assertThat(testRawMaterialConsumption.getQuantityConsumed()).isEqualTo(UPDATED_QUANTITY_CONSUMED);
        assertThat(testRawMaterialConsumption.getScrapGenerated()).isEqualTo(UPDATED_SCRAP_GENERATED);
        assertThat(testRawMaterialConsumption.getTotalMaterialCost()).isEqualTo(UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void putNonExistingRawMaterialConsumption() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();
        rawMaterialConsumption.setId(count.incrementAndGet());

        // Create the RawMaterialConsumption
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(rawMaterialConsumption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawMaterialConsumptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRawMaterialConsumption() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();
        rawMaterialConsumption.setId(count.incrementAndGet());

        // Create the RawMaterialConsumption
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(rawMaterialConsumption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRawMaterialConsumption() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();
        rawMaterialConsumption.setId(count.incrementAndGet());

        // Create the RawMaterialConsumption
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(rawMaterialConsumption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRawMaterialConsumptionWithPatch() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();

        // Update the rawMaterialConsumption using partial update
        RawMaterialConsumption partialUpdatedRawMaterialConsumption = new RawMaterialConsumption();
        partialUpdatedRawMaterialConsumption.setId(rawMaterialConsumption.getId());

        partialUpdatedRawMaterialConsumption
            .projectId(UPDATED_PROJECT_ID)
            .productionLine(UPDATED_PRODUCTION_LINE)
            .rawMaterialId(UPDATED_RAW_MATERIAL_ID)
            .quantityConsumed(UPDATED_QUANTITY_CONSUMED)
            .scrapGenerated(UPDATED_SCRAP_GENERATED);

        restRawMaterialConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterialConsumption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterialConsumption))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialConsumption testRawMaterialConsumption = rawMaterialConsumptionList.get(rawMaterialConsumptionList.size() - 1);
        assertThat(testRawMaterialConsumption.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testRawMaterialConsumption.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRawMaterialConsumption.getProductionLine()).isEqualTo(UPDATED_PRODUCTION_LINE);
        assertThat(testRawMaterialConsumption.getRawMaterialId()).isEqualTo(UPDATED_RAW_MATERIAL_ID);
        assertThat(testRawMaterialConsumption.getQuantityConsumed()).isEqualTo(UPDATED_QUANTITY_CONSUMED);
        assertThat(testRawMaterialConsumption.getScrapGenerated()).isEqualTo(UPDATED_SCRAP_GENERATED);
        assertThat(testRawMaterialConsumption.getTotalMaterialCost()).isEqualTo(DEFAULT_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void fullUpdateRawMaterialConsumptionWithPatch() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();

        // Update the rawMaterialConsumption using partial update
        RawMaterialConsumption partialUpdatedRawMaterialConsumption = new RawMaterialConsumption();
        partialUpdatedRawMaterialConsumption.setId(rawMaterialConsumption.getId());

        partialUpdatedRawMaterialConsumption
            .projectId(UPDATED_PROJECT_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productionLine(UPDATED_PRODUCTION_LINE)
            .rawMaterialId(UPDATED_RAW_MATERIAL_ID)
            .quantityConsumed(UPDATED_QUANTITY_CONSUMED)
            .scrapGenerated(UPDATED_SCRAP_GENERATED)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST);

        restRawMaterialConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterialConsumption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterialConsumption))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialConsumption testRawMaterialConsumption = rawMaterialConsumptionList.get(rawMaterialConsumptionList.size() - 1);
        assertThat(testRawMaterialConsumption.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testRawMaterialConsumption.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRawMaterialConsumption.getProductionLine()).isEqualTo(UPDATED_PRODUCTION_LINE);
        assertThat(testRawMaterialConsumption.getRawMaterialId()).isEqualTo(UPDATED_RAW_MATERIAL_ID);
        assertThat(testRawMaterialConsumption.getQuantityConsumed()).isEqualTo(UPDATED_QUANTITY_CONSUMED);
        assertThat(testRawMaterialConsumption.getScrapGenerated()).isEqualTo(UPDATED_SCRAP_GENERATED);
        assertThat(testRawMaterialConsumption.getTotalMaterialCost()).isEqualTo(UPDATED_TOTAL_MATERIAL_COST);
    }

    @Test
    @Transactional
    void patchNonExistingRawMaterialConsumption() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();
        rawMaterialConsumption.setId(count.incrementAndGet());

        // Create the RawMaterialConsumption
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(rawMaterialConsumption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rawMaterialConsumptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRawMaterialConsumption() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();
        rawMaterialConsumption.setId(count.incrementAndGet());

        // Create the RawMaterialConsumption
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(rawMaterialConsumption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRawMaterialConsumption() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialConsumptionRepository.findAll().size();
        rawMaterialConsumption.setId(count.incrementAndGet());

        // Create the RawMaterialConsumption
        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = rawMaterialConsumptionMapper.toDto(rawMaterialConsumption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialConsumptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterialConsumption in the database
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRawMaterialConsumption() throws Exception {
        // Initialize the database
        rawMaterialConsumptionRepository.saveAndFlush(rawMaterialConsumption);

        int databaseSizeBeforeDelete = rawMaterialConsumptionRepository.findAll().size();

        // Delete the rawMaterialConsumption
        restRawMaterialConsumptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, rawMaterialConsumption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RawMaterialConsumption> rawMaterialConsumptionList = rawMaterialConsumptionRepository.findAll();
        assertThat(rawMaterialConsumptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
