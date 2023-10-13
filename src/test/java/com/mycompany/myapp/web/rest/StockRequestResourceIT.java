package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.StockRequest;
import com.mycompany.myapp.domain.enumeration.Status;
import com.mycompany.myapp.repository.StockRequestRepository;
import com.mycompany.myapp.service.criteria.StockRequestCriteria;
import com.mycompany.myapp.service.dto.StockRequestDTO;
import com.mycompany.myapp.service.mapper.StockRequestMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link StockRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StockRequestResourceIT {

    private static final Double DEFAULT_QTY_REQUIRED = 1D;
    private static final Double UPDATED_QTY_REQUIRED = 2D;
    private static final Double SMALLER_QTY_REQUIRED = 1D - 1D;

    private static final Instant DEFAULT_REQ_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQ_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_PROD = false;
    private static final Boolean UPDATED_IS_PROD = true;

    private static final Status DEFAULT_STATUS = Status.REQUESTED;
    private static final Status UPDATED_STATUS = Status.APPROVED;

    private static final Long DEFAULT_RAW_MATERIAL_ID = 1L;
    private static final Long UPDATED_RAW_MATERIAL_ID = 2L;
    private static final Long SMALLER_RAW_MATERIAL_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;
    private static final Long SMALLER_PRODUCT_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCTION_LINE_ID = 1L;
    private static final Long UPDATED_PRODUCTION_LINE_ID = 2L;
    private static final Long SMALLER_PRODUCTION_LINE_ID = 1L - 1L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;
    private static final Long SMALLER_PROJECT_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/stock-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StockRequestRepository stockRequestRepository;

    @Autowired
    private StockRequestMapper stockRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockRequestMockMvc;

    private StockRequest stockRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockRequest createEntity(EntityManager em) {
        StockRequest stockRequest = new StockRequest()
            .qtyRequired(DEFAULT_QTY_REQUIRED)
            .reqDate(DEFAULT_REQ_DATE)
            .isProd(DEFAULT_IS_PROD)
            .status(DEFAULT_STATUS)
            .rawMaterialId(DEFAULT_RAW_MATERIAL_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .productionLineId(DEFAULT_PRODUCTION_LINE_ID)
            .projectId(DEFAULT_PROJECT_ID);
        return stockRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockRequest createUpdatedEntity(EntityManager em) {
        StockRequest stockRequest = new StockRequest()
            .qtyRequired(UPDATED_QTY_REQUIRED)
            .reqDate(UPDATED_REQ_DATE)
            .isProd(UPDATED_IS_PROD)
            .status(UPDATED_STATUS)
            .rawMaterialId(UPDATED_RAW_MATERIAL_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productionLineId(UPDATED_PRODUCTION_LINE_ID)
            .projectId(UPDATED_PROJECT_ID);
        return stockRequest;
    }

    @BeforeEach
    public void initTest() {
        stockRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createStockRequest() throws Exception {
        int databaseSizeBeforeCreate = stockRequestRepository.findAll().size();
        // Create the StockRequest
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(stockRequest);
        restStockRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeCreate + 1);
        StockRequest testStockRequest = stockRequestList.get(stockRequestList.size() - 1);
        assertThat(testStockRequest.getQtyRequired()).isEqualTo(DEFAULT_QTY_REQUIRED);
        assertThat(testStockRequest.getReqDate()).isEqualTo(DEFAULT_REQ_DATE);
        assertThat(testStockRequest.getIsProd()).isEqualTo(DEFAULT_IS_PROD);
        assertThat(testStockRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStockRequest.getRawMaterialId()).isEqualTo(DEFAULT_RAW_MATERIAL_ID);
        assertThat(testStockRequest.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testStockRequest.getProductionLineId()).isEqualTo(DEFAULT_PRODUCTION_LINE_ID);
        assertThat(testStockRequest.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
    }

    @Test
    @Transactional
    void createStockRequestWithExistingId() throws Exception {
        // Create the StockRequest with an existing ID
        stockRequest.setId(1L);
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(stockRequest);

        int databaseSizeBeforeCreate = stockRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStockRequests() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList
        restStockRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].qtyRequired").value(hasItem(DEFAULT_QTY_REQUIRED.doubleValue())))
            .andExpect(jsonPath("$.[*].reqDate").value(hasItem(DEFAULT_REQ_DATE.toString())))
            .andExpect(jsonPath("$.[*].isProd").value(hasItem(DEFAULT_IS_PROD.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].rawMaterialId").value(hasItem(DEFAULT_RAW_MATERIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productionLineId").value(hasItem(DEFAULT_PRODUCTION_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())));
    }

    @Test
    @Transactional
    void getStockRequest() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get the stockRequest
        restStockRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, stockRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockRequest.getId().intValue()))
            .andExpect(jsonPath("$.qtyRequired").value(DEFAULT_QTY_REQUIRED.doubleValue()))
            .andExpect(jsonPath("$.reqDate").value(DEFAULT_REQ_DATE.toString()))
            .andExpect(jsonPath("$.isProd").value(DEFAULT_IS_PROD.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.rawMaterialId").value(DEFAULT_RAW_MATERIAL_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.productionLineId").value(DEFAULT_PRODUCTION_LINE_ID.intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()));
    }

    @Test
    @Transactional
    void getStockRequestsByIdFiltering() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        Long id = stockRequest.getId();

        defaultStockRequestShouldBeFound("id.equals=" + id);
        defaultStockRequestShouldNotBeFound("id.notEquals=" + id);

        defaultStockRequestShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockRequestShouldNotBeFound("id.greaterThan=" + id);

        defaultStockRequestShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockRequestShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStockRequestsByQtyRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where qtyRequired equals to DEFAULT_QTY_REQUIRED
        defaultStockRequestShouldBeFound("qtyRequired.equals=" + DEFAULT_QTY_REQUIRED);

        // Get all the stockRequestList where qtyRequired equals to UPDATED_QTY_REQUIRED
        defaultStockRequestShouldNotBeFound("qtyRequired.equals=" + UPDATED_QTY_REQUIRED);
    }

    @Test
    @Transactional
    void getAllStockRequestsByQtyRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where qtyRequired in DEFAULT_QTY_REQUIRED or UPDATED_QTY_REQUIRED
        defaultStockRequestShouldBeFound("qtyRequired.in=" + DEFAULT_QTY_REQUIRED + "," + UPDATED_QTY_REQUIRED);

        // Get all the stockRequestList where qtyRequired equals to UPDATED_QTY_REQUIRED
        defaultStockRequestShouldNotBeFound("qtyRequired.in=" + UPDATED_QTY_REQUIRED);
    }

    @Test
    @Transactional
    void getAllStockRequestsByQtyRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where qtyRequired is not null
        defaultStockRequestShouldBeFound("qtyRequired.specified=true");

        // Get all the stockRequestList where qtyRequired is null
        defaultStockRequestShouldNotBeFound("qtyRequired.specified=false");
    }

    @Test
    @Transactional
    void getAllStockRequestsByQtyRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where qtyRequired is greater than or equal to DEFAULT_QTY_REQUIRED
        defaultStockRequestShouldBeFound("qtyRequired.greaterThanOrEqual=" + DEFAULT_QTY_REQUIRED);

        // Get all the stockRequestList where qtyRequired is greater than or equal to UPDATED_QTY_REQUIRED
        defaultStockRequestShouldNotBeFound("qtyRequired.greaterThanOrEqual=" + UPDATED_QTY_REQUIRED);
    }

    @Test
    @Transactional
    void getAllStockRequestsByQtyRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where qtyRequired is less than or equal to DEFAULT_QTY_REQUIRED
        defaultStockRequestShouldBeFound("qtyRequired.lessThanOrEqual=" + DEFAULT_QTY_REQUIRED);

        // Get all the stockRequestList where qtyRequired is less than or equal to SMALLER_QTY_REQUIRED
        defaultStockRequestShouldNotBeFound("qtyRequired.lessThanOrEqual=" + SMALLER_QTY_REQUIRED);
    }

    @Test
    @Transactional
    void getAllStockRequestsByQtyRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where qtyRequired is less than DEFAULT_QTY_REQUIRED
        defaultStockRequestShouldNotBeFound("qtyRequired.lessThan=" + DEFAULT_QTY_REQUIRED);

        // Get all the stockRequestList where qtyRequired is less than UPDATED_QTY_REQUIRED
        defaultStockRequestShouldBeFound("qtyRequired.lessThan=" + UPDATED_QTY_REQUIRED);
    }

    @Test
    @Transactional
    void getAllStockRequestsByQtyRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where qtyRequired is greater than DEFAULT_QTY_REQUIRED
        defaultStockRequestShouldNotBeFound("qtyRequired.greaterThan=" + DEFAULT_QTY_REQUIRED);

        // Get all the stockRequestList where qtyRequired is greater than SMALLER_QTY_REQUIRED
        defaultStockRequestShouldBeFound("qtyRequired.greaterThan=" + SMALLER_QTY_REQUIRED);
    }

    @Test
    @Transactional
    void getAllStockRequestsByReqDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where reqDate equals to DEFAULT_REQ_DATE
        defaultStockRequestShouldBeFound("reqDate.equals=" + DEFAULT_REQ_DATE);

        // Get all the stockRequestList where reqDate equals to UPDATED_REQ_DATE
        defaultStockRequestShouldNotBeFound("reqDate.equals=" + UPDATED_REQ_DATE);
    }

    @Test
    @Transactional
    void getAllStockRequestsByReqDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where reqDate in DEFAULT_REQ_DATE or UPDATED_REQ_DATE
        defaultStockRequestShouldBeFound("reqDate.in=" + DEFAULT_REQ_DATE + "," + UPDATED_REQ_DATE);

        // Get all the stockRequestList where reqDate equals to UPDATED_REQ_DATE
        defaultStockRequestShouldNotBeFound("reqDate.in=" + UPDATED_REQ_DATE);
    }

    @Test
    @Transactional
    void getAllStockRequestsByReqDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where reqDate is not null
        defaultStockRequestShouldBeFound("reqDate.specified=true");

        // Get all the stockRequestList where reqDate is null
        defaultStockRequestShouldNotBeFound("reqDate.specified=false");
    }

    @Test
    @Transactional
    void getAllStockRequestsByIsProdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where isProd equals to DEFAULT_IS_PROD
        defaultStockRequestShouldBeFound("isProd.equals=" + DEFAULT_IS_PROD);

        // Get all the stockRequestList where isProd equals to UPDATED_IS_PROD
        defaultStockRequestShouldNotBeFound("isProd.equals=" + UPDATED_IS_PROD);
    }

    @Test
    @Transactional
    void getAllStockRequestsByIsProdIsInShouldWork() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where isProd in DEFAULT_IS_PROD or UPDATED_IS_PROD
        defaultStockRequestShouldBeFound("isProd.in=" + DEFAULT_IS_PROD + "," + UPDATED_IS_PROD);

        // Get all the stockRequestList where isProd equals to UPDATED_IS_PROD
        defaultStockRequestShouldNotBeFound("isProd.in=" + UPDATED_IS_PROD);
    }

    @Test
    @Transactional
    void getAllStockRequestsByIsProdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where isProd is not null
        defaultStockRequestShouldBeFound("isProd.specified=true");

        // Get all the stockRequestList where isProd is null
        defaultStockRequestShouldNotBeFound("isProd.specified=false");
    }

    @Test
    @Transactional
    void getAllStockRequestsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where status equals to DEFAULT_STATUS
        defaultStockRequestShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the stockRequestList where status equals to UPDATED_STATUS
        defaultStockRequestShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStockRequestsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultStockRequestShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the stockRequestList where status equals to UPDATED_STATUS
        defaultStockRequestShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStockRequestsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where status is not null
        defaultStockRequestShouldBeFound("status.specified=true");

        // Get all the stockRequestList where status is null
        defaultStockRequestShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllStockRequestsByRawMaterialIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where rawMaterialId equals to DEFAULT_RAW_MATERIAL_ID
        defaultStockRequestShouldBeFound("rawMaterialId.equals=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the stockRequestList where rawMaterialId equals to UPDATED_RAW_MATERIAL_ID
        defaultStockRequestShouldNotBeFound("rawMaterialId.equals=" + UPDATED_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByRawMaterialIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where rawMaterialId in DEFAULT_RAW_MATERIAL_ID or UPDATED_RAW_MATERIAL_ID
        defaultStockRequestShouldBeFound("rawMaterialId.in=" + DEFAULT_RAW_MATERIAL_ID + "," + UPDATED_RAW_MATERIAL_ID);

        // Get all the stockRequestList where rawMaterialId equals to UPDATED_RAW_MATERIAL_ID
        defaultStockRequestShouldNotBeFound("rawMaterialId.in=" + UPDATED_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByRawMaterialIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where rawMaterialId is not null
        defaultStockRequestShouldBeFound("rawMaterialId.specified=true");

        // Get all the stockRequestList where rawMaterialId is null
        defaultStockRequestShouldNotBeFound("rawMaterialId.specified=false");
    }

    @Test
    @Transactional
    void getAllStockRequestsByRawMaterialIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where rawMaterialId is greater than or equal to DEFAULT_RAW_MATERIAL_ID
        defaultStockRequestShouldBeFound("rawMaterialId.greaterThanOrEqual=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the stockRequestList where rawMaterialId is greater than or equal to UPDATED_RAW_MATERIAL_ID
        defaultStockRequestShouldNotBeFound("rawMaterialId.greaterThanOrEqual=" + UPDATED_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByRawMaterialIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where rawMaterialId is less than or equal to DEFAULT_RAW_MATERIAL_ID
        defaultStockRequestShouldBeFound("rawMaterialId.lessThanOrEqual=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the stockRequestList where rawMaterialId is less than or equal to SMALLER_RAW_MATERIAL_ID
        defaultStockRequestShouldNotBeFound("rawMaterialId.lessThanOrEqual=" + SMALLER_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByRawMaterialIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where rawMaterialId is less than DEFAULT_RAW_MATERIAL_ID
        defaultStockRequestShouldNotBeFound("rawMaterialId.lessThan=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the stockRequestList where rawMaterialId is less than UPDATED_RAW_MATERIAL_ID
        defaultStockRequestShouldBeFound("rawMaterialId.lessThan=" + UPDATED_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByRawMaterialIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where rawMaterialId is greater than DEFAULT_RAW_MATERIAL_ID
        defaultStockRequestShouldNotBeFound("rawMaterialId.greaterThan=" + DEFAULT_RAW_MATERIAL_ID);

        // Get all the stockRequestList where rawMaterialId is greater than SMALLER_RAW_MATERIAL_ID
        defaultStockRequestShouldBeFound("rawMaterialId.greaterThan=" + SMALLER_RAW_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productId equals to DEFAULT_PRODUCT_ID
        defaultStockRequestShouldBeFound("productId.equals=" + DEFAULT_PRODUCT_ID);

        // Get all the stockRequestList where productId equals to UPDATED_PRODUCT_ID
        defaultStockRequestShouldNotBeFound("productId.equals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productId in DEFAULT_PRODUCT_ID or UPDATED_PRODUCT_ID
        defaultStockRequestShouldBeFound("productId.in=" + DEFAULT_PRODUCT_ID + "," + UPDATED_PRODUCT_ID);

        // Get all the stockRequestList where productId equals to UPDATED_PRODUCT_ID
        defaultStockRequestShouldNotBeFound("productId.in=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productId is not null
        defaultStockRequestShouldBeFound("productId.specified=true");

        // Get all the stockRequestList where productId is null
        defaultStockRequestShouldNotBeFound("productId.specified=false");
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productId is greater than or equal to DEFAULT_PRODUCT_ID
        defaultStockRequestShouldBeFound("productId.greaterThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the stockRequestList where productId is greater than or equal to UPDATED_PRODUCT_ID
        defaultStockRequestShouldNotBeFound("productId.greaterThanOrEqual=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productId is less than or equal to DEFAULT_PRODUCT_ID
        defaultStockRequestShouldBeFound("productId.lessThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the stockRequestList where productId is less than or equal to SMALLER_PRODUCT_ID
        defaultStockRequestShouldNotBeFound("productId.lessThanOrEqual=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productId is less than DEFAULT_PRODUCT_ID
        defaultStockRequestShouldNotBeFound("productId.lessThan=" + DEFAULT_PRODUCT_ID);

        // Get all the stockRequestList where productId is less than UPDATED_PRODUCT_ID
        defaultStockRequestShouldBeFound("productId.lessThan=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productId is greater than DEFAULT_PRODUCT_ID
        defaultStockRequestShouldNotBeFound("productId.greaterThan=" + DEFAULT_PRODUCT_ID);

        // Get all the stockRequestList where productId is greater than SMALLER_PRODUCT_ID
        defaultStockRequestShouldBeFound("productId.greaterThan=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductionLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productionLineId equals to DEFAULT_PRODUCTION_LINE_ID
        defaultStockRequestShouldBeFound("productionLineId.equals=" + DEFAULT_PRODUCTION_LINE_ID);

        // Get all the stockRequestList where productionLineId equals to UPDATED_PRODUCTION_LINE_ID
        defaultStockRequestShouldNotBeFound("productionLineId.equals=" + UPDATED_PRODUCTION_LINE_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductionLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productionLineId in DEFAULT_PRODUCTION_LINE_ID or UPDATED_PRODUCTION_LINE_ID
        defaultStockRequestShouldBeFound("productionLineId.in=" + DEFAULT_PRODUCTION_LINE_ID + "," + UPDATED_PRODUCTION_LINE_ID);

        // Get all the stockRequestList where productionLineId equals to UPDATED_PRODUCTION_LINE_ID
        defaultStockRequestShouldNotBeFound("productionLineId.in=" + UPDATED_PRODUCTION_LINE_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductionLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productionLineId is not null
        defaultStockRequestShouldBeFound("productionLineId.specified=true");

        // Get all the stockRequestList where productionLineId is null
        defaultStockRequestShouldNotBeFound("productionLineId.specified=false");
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductionLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productionLineId is greater than or equal to DEFAULT_PRODUCTION_LINE_ID
        defaultStockRequestShouldBeFound("productionLineId.greaterThanOrEqual=" + DEFAULT_PRODUCTION_LINE_ID);

        // Get all the stockRequestList where productionLineId is greater than or equal to UPDATED_PRODUCTION_LINE_ID
        defaultStockRequestShouldNotBeFound("productionLineId.greaterThanOrEqual=" + UPDATED_PRODUCTION_LINE_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductionLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productionLineId is less than or equal to DEFAULT_PRODUCTION_LINE_ID
        defaultStockRequestShouldBeFound("productionLineId.lessThanOrEqual=" + DEFAULT_PRODUCTION_LINE_ID);

        // Get all the stockRequestList where productionLineId is less than or equal to SMALLER_PRODUCTION_LINE_ID
        defaultStockRequestShouldNotBeFound("productionLineId.lessThanOrEqual=" + SMALLER_PRODUCTION_LINE_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductionLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productionLineId is less than DEFAULT_PRODUCTION_LINE_ID
        defaultStockRequestShouldNotBeFound("productionLineId.lessThan=" + DEFAULT_PRODUCTION_LINE_ID);

        // Get all the stockRequestList where productionLineId is less than UPDATED_PRODUCTION_LINE_ID
        defaultStockRequestShouldBeFound("productionLineId.lessThan=" + UPDATED_PRODUCTION_LINE_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProductionLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where productionLineId is greater than DEFAULT_PRODUCTION_LINE_ID
        defaultStockRequestShouldNotBeFound("productionLineId.greaterThan=" + DEFAULT_PRODUCTION_LINE_ID);

        // Get all the stockRequestList where productionLineId is greater than SMALLER_PRODUCTION_LINE_ID
        defaultStockRequestShouldBeFound("productionLineId.greaterThan=" + SMALLER_PRODUCTION_LINE_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProjectIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where projectId equals to DEFAULT_PROJECT_ID
        defaultStockRequestShouldBeFound("projectId.equals=" + DEFAULT_PROJECT_ID);

        // Get all the stockRequestList where projectId equals to UPDATED_PROJECT_ID
        defaultStockRequestShouldNotBeFound("projectId.equals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProjectIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where projectId in DEFAULT_PROJECT_ID or UPDATED_PROJECT_ID
        defaultStockRequestShouldBeFound("projectId.in=" + DEFAULT_PROJECT_ID + "," + UPDATED_PROJECT_ID);

        // Get all the stockRequestList where projectId equals to UPDATED_PROJECT_ID
        defaultStockRequestShouldNotBeFound("projectId.in=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProjectIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where projectId is not null
        defaultStockRequestShouldBeFound("projectId.specified=true");

        // Get all the stockRequestList where projectId is null
        defaultStockRequestShouldNotBeFound("projectId.specified=false");
    }

    @Test
    @Transactional
    void getAllStockRequestsByProjectIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where projectId is greater than or equal to DEFAULT_PROJECT_ID
        defaultStockRequestShouldBeFound("projectId.greaterThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the stockRequestList where projectId is greater than or equal to UPDATED_PROJECT_ID
        defaultStockRequestShouldNotBeFound("projectId.greaterThanOrEqual=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProjectIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where projectId is less than or equal to DEFAULT_PROJECT_ID
        defaultStockRequestShouldBeFound("projectId.lessThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the stockRequestList where projectId is less than or equal to SMALLER_PROJECT_ID
        defaultStockRequestShouldNotBeFound("projectId.lessThanOrEqual=" + SMALLER_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProjectIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where projectId is less than DEFAULT_PROJECT_ID
        defaultStockRequestShouldNotBeFound("projectId.lessThan=" + DEFAULT_PROJECT_ID);

        // Get all the stockRequestList where projectId is less than UPDATED_PROJECT_ID
        defaultStockRequestShouldBeFound("projectId.lessThan=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void getAllStockRequestsByProjectIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        // Get all the stockRequestList where projectId is greater than DEFAULT_PROJECT_ID
        defaultStockRequestShouldNotBeFound("projectId.greaterThan=" + DEFAULT_PROJECT_ID);

        // Get all the stockRequestList where projectId is greater than SMALLER_PROJECT_ID
        defaultStockRequestShouldBeFound("projectId.greaterThan=" + SMALLER_PROJECT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockRequestShouldBeFound(String filter) throws Exception {
        restStockRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].qtyRequired").value(hasItem(DEFAULT_QTY_REQUIRED.doubleValue())))
            .andExpect(jsonPath("$.[*].reqDate").value(hasItem(DEFAULT_REQ_DATE.toString())))
            .andExpect(jsonPath("$.[*].isProd").value(hasItem(DEFAULT_IS_PROD.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].rawMaterialId").value(hasItem(DEFAULT_RAW_MATERIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productionLineId").value(hasItem(DEFAULT_PRODUCTION_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())));

        // Check, that the count call also returns 1
        restStockRequestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockRequestShouldNotBeFound(String filter) throws Exception {
        restStockRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockRequestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStockRequest() throws Exception {
        // Get the stockRequest
        restStockRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStockRequest() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();

        // Update the stockRequest
        StockRequest updatedStockRequest = stockRequestRepository.findById(stockRequest.getId()).get();
        // Disconnect from session so that the updates on updatedStockRequest are not directly saved in db
        em.detach(updatedStockRequest);
        updatedStockRequest
            .qtyRequired(UPDATED_QTY_REQUIRED)
            .reqDate(UPDATED_REQ_DATE)
            .isProd(UPDATED_IS_PROD)
            .status(UPDATED_STATUS)
            .rawMaterialId(UPDATED_RAW_MATERIAL_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productionLineId(UPDATED_PRODUCTION_LINE_ID)
            .projectId(UPDATED_PROJECT_ID);
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(updatedStockRequest);

        restStockRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
        StockRequest testStockRequest = stockRequestList.get(stockRequestList.size() - 1);
        assertThat(testStockRequest.getQtyRequired()).isEqualTo(UPDATED_QTY_REQUIRED);
        assertThat(testStockRequest.getReqDate()).isEqualTo(UPDATED_REQ_DATE);
        assertThat(testStockRequest.getIsProd()).isEqualTo(UPDATED_IS_PROD);
        assertThat(testStockRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStockRequest.getRawMaterialId()).isEqualTo(UPDATED_RAW_MATERIAL_ID);
        assertThat(testStockRequest.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testStockRequest.getProductionLineId()).isEqualTo(UPDATED_PRODUCTION_LINE_ID);
        assertThat(testStockRequest.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void putNonExistingStockRequest() throws Exception {
        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();
        stockRequest.setId(count.incrementAndGet());

        // Create the StockRequest
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(stockRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStockRequest() throws Exception {
        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();
        stockRequest.setId(count.incrementAndGet());

        // Create the StockRequest
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(stockRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStockRequest() throws Exception {
        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();
        stockRequest.setId(count.incrementAndGet());

        // Create the StockRequest
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(stockRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockRequestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStockRequestWithPatch() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();

        // Update the stockRequest using partial update
        StockRequest partialUpdatedStockRequest = new StockRequest();
        partialUpdatedStockRequest.setId(stockRequest.getId());

        partialUpdatedStockRequest.isProd(UPDATED_IS_PROD).rawMaterialId(UPDATED_RAW_MATERIAL_ID).projectId(UPDATED_PROJECT_ID);

        restStockRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockRequest))
            )
            .andExpect(status().isOk());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
        StockRequest testStockRequest = stockRequestList.get(stockRequestList.size() - 1);
        assertThat(testStockRequest.getQtyRequired()).isEqualTo(DEFAULT_QTY_REQUIRED);
        assertThat(testStockRequest.getReqDate()).isEqualTo(DEFAULT_REQ_DATE);
        assertThat(testStockRequest.getIsProd()).isEqualTo(UPDATED_IS_PROD);
        assertThat(testStockRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStockRequest.getRawMaterialId()).isEqualTo(UPDATED_RAW_MATERIAL_ID);
        assertThat(testStockRequest.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testStockRequest.getProductionLineId()).isEqualTo(DEFAULT_PRODUCTION_LINE_ID);
        assertThat(testStockRequest.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void fullUpdateStockRequestWithPatch() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();

        // Update the stockRequest using partial update
        StockRequest partialUpdatedStockRequest = new StockRequest();
        partialUpdatedStockRequest.setId(stockRequest.getId());

        partialUpdatedStockRequest
            .qtyRequired(UPDATED_QTY_REQUIRED)
            .reqDate(UPDATED_REQ_DATE)
            .isProd(UPDATED_IS_PROD)
            .status(UPDATED_STATUS)
            .rawMaterialId(UPDATED_RAW_MATERIAL_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productionLineId(UPDATED_PRODUCTION_LINE_ID)
            .projectId(UPDATED_PROJECT_ID);

        restStockRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockRequest))
            )
            .andExpect(status().isOk());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
        StockRequest testStockRequest = stockRequestList.get(stockRequestList.size() - 1);
        assertThat(testStockRequest.getQtyRequired()).isEqualTo(UPDATED_QTY_REQUIRED);
        assertThat(testStockRequest.getReqDate()).isEqualTo(UPDATED_REQ_DATE);
        assertThat(testStockRequest.getIsProd()).isEqualTo(UPDATED_IS_PROD);
        assertThat(testStockRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStockRequest.getRawMaterialId()).isEqualTo(UPDATED_RAW_MATERIAL_ID);
        assertThat(testStockRequest.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testStockRequest.getProductionLineId()).isEqualTo(UPDATED_PRODUCTION_LINE_ID);
        assertThat(testStockRequest.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingStockRequest() throws Exception {
        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();
        stockRequest.setId(count.incrementAndGet());

        // Create the StockRequest
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(stockRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stockRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStockRequest() throws Exception {
        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();
        stockRequest.setId(count.incrementAndGet());

        // Create the StockRequest
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(stockRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStockRequest() throws Exception {
        int databaseSizeBeforeUpdate = stockRequestRepository.findAll().size();
        stockRequest.setId(count.incrementAndGet());

        // Create the StockRequest
        StockRequestDTO stockRequestDTO = stockRequestMapper.toDto(stockRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockRequest in the database
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStockRequest() throws Exception {
        // Initialize the database
        stockRequestRepository.saveAndFlush(stockRequest);

        int databaseSizeBeforeDelete = stockRequestRepository.findAll().size();

        // Delete the stockRequest
        restStockRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, stockRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockRequest> stockRequestList = stockRequestRepository.findAll();
        assertThat(stockRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
