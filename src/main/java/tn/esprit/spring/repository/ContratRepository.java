package tn.esprit.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entities.Contrat;

public interface ContratRepository extends CrudRepository<Contrat, Integer>{

	Optional<Contrat> findByEmployeId(int idEmploye);

} 
