package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ProductConsumption;
import com.mycompany.myapp.repository.ProductConsumptionRepository;
import com.mycompany.myapp.service.criteria.ProductConsumptionCriteria;
import com.mycompany.myapp.service.dto.ProductConsumptionDTO;
import com.mycompany.myapp.service.mapper.ProductConsumptionMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ProductConsumption} entities in the database.
 * The main input is a {@link ProductConsumptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductConsumptionDTO} or a {@link Page} of {@link ProductConsumptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductConsumptionQueryService extends QueryService<ProductConsumption> {

    private final Logger log = LoggerFactory.getLogger(ProductConsumptionQueryService.class);

    private final ProductConsumptionRepository productConsumptionRepository;

    private final ProductConsumptionMapper productConsumptionMapper;

    public ProductConsumptionQueryService(
        ProductConsumptionRepository productConsumptionRepository,
        ProductConsumptionMapper productConsumptionMapper
    ) {
        this.productConsumptionRepository = productConsumptionRepository;
        this.productConsumptionMapper = productConsumptionMapper;
    }

    /**
     * Return a {@link List} of {@link ProductConsumptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductConsumptionDTO> findByCriteria(ProductConsumptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductConsumption> specification = createSpecification(criteria);
        return productConsumptionMapper.toDto(productConsumptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductConsumptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductConsumptionDTO> findByCriteria(ProductConsumptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductConsumption> specification = createSpecification(criteria);
        return productConsumptionRepository.findAll(specification, page).map(productConsumptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductConsumptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductConsumption> specification = createSpecification(criteria);
        return productConsumptionRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductConsumptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductConsumption> createSpecification(ProductConsumptionCriteria criteria) {
        Specification<ProductConsumption> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductConsumption_.id));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectId(), ProductConsumption_.projectId));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductId(), ProductConsumption_.productId));
            }
            if (criteria.getProductConsumeId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProductConsumeId(), ProductConsumption_.productConsumeId));
            }
            if (criteria.getQuantityConsumed() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQuantityConsumed(), ProductConsumption_.quantityConsumed));
            }
            if (criteria.getTotalProductsCost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalProductsCost(), ProductConsumption_.totalProductsCost));
            }
        }
        return specification;
    }
}
