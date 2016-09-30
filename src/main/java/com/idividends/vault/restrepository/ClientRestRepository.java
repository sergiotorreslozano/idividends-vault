package com.idividends.vault.restrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idividends.vault.domain.Client;

@RepositoryRestResource(collectionResourceRel = "client", path = "clients")
public interface ClientRestRepository extends JpaRepository<Client, Long> {

}
