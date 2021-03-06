package com.idividends.vault.restrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idividends.vault.domain.Operation;

@RepositoryRestResource(collectionResourceRel = "operation", path = "operations")
public interface OperationRestRepository extends JpaRepository<Operation, Long> {

}
