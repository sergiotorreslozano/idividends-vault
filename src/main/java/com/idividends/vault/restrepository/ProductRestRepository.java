package com.idividends.vault.restrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idividends.vault.domain.Product;

@RepositoryRestResource(collectionResourceRel = "product", path = "products")
public interface ProductRestRepository extends JpaRepository<Product, Long> {

}
