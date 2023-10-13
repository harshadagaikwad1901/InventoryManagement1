package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.RawMaterialConsumption;
import com.mycompany.myapp.repository.RawMaterialConsumptionRepository;
import com.mycompany.myapp.service.criteria.RawMaterialConsumptionCriteria;
import com.mycompany.myapp.service.dto.RawMaterialConsumptionDTO;
import com.mycompany.myapp.service.mapper.RawMaterialConsumptionMapper;
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
 * Service for executing complex queries for {@link RawMaterialConsumption} entities in the database.
 * The main input is a {@link RawMaterialConsumptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RawMaterialConsumptionDTO} or a {@link Page} of {@link RawMaterialConsumptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RawMaterialConsumptionQueryService extends QueryService<RawMaterialConsumption> {

    private final Logger log = LoggerFactory.getLogger(RawMaterialConsumptionQueryService.class);

    private final RawMaterialConsumptionRepository rawMaterialConsumptionRepository;

    private final RawMaterialConsumptionMapper rawMaterialConsumptionMapper;

    public RawMaterialConsumptionQueryService(
        RawMaterialConsumptionRepository rawMaterialConsumptionRepository,
        RawMaterialConsumptionMapper rawMaterialConsumptionMapper
    ) {
        this.rawMaterialConsumptionRepository = rawMaterialConsumptionRepository;
        this.rawMaterialConsumptionMapper = rawMaterialConsumptionMapper;
    }

    /**
     * Return a {@link List} of {@link RawMaterialConsumptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RawMaterialConsumptionDTO> findByCriteria(RawMaterialConsumptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RawMaterialConsumption> specification = createSpecification(criteria);
        return rawMaterialConsumptionMapper.toDto(rawMaterialConsumptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RawMaterialConsumptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RawMaterialConsumptionDTO> findByCriteria(RawMaterialConsumptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RawMaterialConsumption> specification = createSpecification(criteria);
        return rawMaterialConsumptionRepository.findAll(specification, page).map(rawMaterialConsumptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RawMaterialConsumptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RawMaterialConsumption> specification = createSpecification(criteria);
        return rawMaterialConsumptionRepository.count(specification);
    }

    /**
     * Function to convert {@link RawMaterialConsumptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RawMaterialConsumption> createSpecification(RawMaterialConsumptionCriteria criteria) {
        Specification<RawMaterialConsumption> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RawMaterialConsumption_.id));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectId(), RawMaterialConsumption_.projectId));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductId(), RawMaterialConsumption_.productId));
            }
            if (criteria.getProductionLine() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProductionLine(), RawMaterialConsumption_.productionLine));
            }
            if (criteria.getRawMaterialId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRawMaterialId(), RawMaterialConsumption_.rawMaterialId));
            }
            if (criteria.getQuantityConsumed() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQuantityConsumed(), RawMaterialConsumption_.quantityConsumed));
            }
            if (criteria.getScrapGenerated() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getScrapGenerated(), RawMaterialConsumption_.scrapGenerated));
            }
            if (criteria.getTotalMaterialCost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalMaterialCost(), RawMaterialConsumption_.totalMaterialCost));
            }
        }
        return specification;
    }
}
