package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductConsumption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductConsumption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductConsumptionRepository
    extends JpaRepository<ProductConsumption, Long>, JpaSpecificationExecutor<ProductConsumption> {}
