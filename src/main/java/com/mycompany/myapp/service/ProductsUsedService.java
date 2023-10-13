package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProductsUsed;
import com.mycompany.myapp.repository.ProductsUsedRepository;
import com.mycompany.myapp.service.dto.ProductsUsedDTO;
import com.mycompany.myapp.service.mapper.ProductsUsedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductsUsed}.
 */
@Service
@Transactional
public class ProductsUsedService {

    private final Logger log = LoggerFactory.getLogger(ProductsUsedService.class);

    private final ProductsUsedRepository productsUsedRepository;

    private final ProductsUsedMapper productsUsedMapper;

    public ProductsUsedService(ProductsUsedRepository productsUsedRepository, ProductsUsedMapper productsUsedMapper) {
        this.productsUsedRepository = productsUsedRepository;
        this.productsUsedMapper = productsUsedMapper;
    }

    /**
     * Save a productsUsed.
     *
     * @param productsUsedDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductsUsedDTO save(ProductsUsedDTO productsUsedDTO) {
        log.debug("Request to save ProductsUsed : {}", productsUsedDTO);
        ProductsUsed productsUsed = productsUsedMapper.toEntity(productsUsedDTO);
        productsUsed = productsUsedRepository.save(productsUsed);
        return productsUsedMapper.toDto(productsUsed);
    }

    /**
     * Update a productsUsed.
     *
     * @param productsUsedDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductsUsedDTO update(ProductsUsedDTO productsUsedDTO) {
        log.debug("Request to update ProductsUsed : {}", productsUsedDTO);
        ProductsUsed productsUsed = productsUsedMapper.toEntity(productsUsedDTO);
        productsUsed = productsUsedRepository.save(productsUsed);
        return productsUsedMapper.toDto(productsUsed);
    }

    /**
     * Partially update a productsUsed.
     *
     * @param productsUsedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductsUsedDTO> partialUpdate(ProductsUsedDTO productsUsedDTO) {
        log.debug("Request to partially update ProductsUsed : {}", productsUsedDTO);

        return productsUsedRepository
            .findById(productsUsedDTO.getId())
            .map(existingProductsUsed -> {
                productsUsedMapper.partialUpdate(existingProductsUsed, productsUsedDTO);

                return existingProductsUsed;
            })
            .map(productsUsedRepository::save)
            .map(productsUsedMapper::toDto);
    }

    /**
     * Get all the productsUseds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductsUsedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductsUseds");
        return productsUsedRepository.findAll(pageable).map(productsUsedMapper::toDto);
    }

    /**
     * Get one productsUsed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductsUsedDTO> findOne(Long id) {
        log.debug("Request to get ProductsUsed : {}", id);
        return productsUsedRepository.findById(id).map(productsUsedMapper::toDto);
    }

    /**
     * Delete the productsUsed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductsUsed : {}", id);
        productsUsedRepository.deleteById(id);
    }
}
