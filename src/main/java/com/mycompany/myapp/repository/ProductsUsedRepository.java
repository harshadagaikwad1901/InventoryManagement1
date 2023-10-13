package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductsUsed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductsUsed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductsUsedRepository extends JpaRepository<ProductsUsed, Long>, JpaSpecificationExecutor<ProductsUsed> {}
