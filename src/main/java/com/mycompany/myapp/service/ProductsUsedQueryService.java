package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ProductsUsed;
import com.mycompany.myapp.repository.ProductsUsedRepository;
import com.mycompany.myapp.service.criteria.ProductsUsedCriteria;
import com.mycompany.myapp.service.dto.ProductsUsedDTO;
import com.mycompany.myapp.service.mapper.ProductsUsedMapper;
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
 * Service for executing complex queries for {@link ProductsUsed} entities in the database.
 * The main input is a {@link ProductsUsedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductsUsedDTO} or a {@link Page} of {@link ProductsUsedDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductsUsedQueryService extends QueryService<ProductsUsed> {

    private final Logger log = LoggerFactory.getLogger(ProductsUsedQueryService.class);

    private final ProductsUsedRepository productsUsedRepository;

    private final ProductsUsedMapper productsUsedMapper;

    public ProductsUsedQueryService(ProductsUsedRepository productsUsedRepository, ProductsUsedMapper productsUsedMapper) {
        this.productsUsedRepository = productsUsedRepository;
        this.productsUsedMapper = productsUsedMapper;
    }

    /**
     * Return a {@link List} of {@link ProductsUsedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductsUsedDTO> findByCriteria(ProductsUsedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductsUsed> specification = createSpecification(criteria);
        return productsUsedMapper.toDto(productsUsedRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductsUsedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductsUsedDTO> findByCriteria(ProductsUsedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductsUsed> specification = createSpecification(criteria);
        return productsUsedRepository.findAll(specification, page).map(productsUsedMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductsUsedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductsUsed> specification = createSpecification(criteria);
        return productsUsedRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductsUsedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductsUsed> createSpecification(ProductsUsedCriteria criteria) {
        Specification<ProductsUsed> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductsUsed_.id));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductId(), ProductsUsed_.productId));
            }
            if (criteria.getProductConsumed() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductConsumed(), ProductsUsed_.productConsumed));
            }
        }
        return specification;
    }
}
