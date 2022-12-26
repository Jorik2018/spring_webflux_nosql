package org.isobit.nosql.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.isobit.nosql.model.SimpleCustomer;

import java.util.UUID;

@Repository
public interface SimpleCustomerRepo extends CrudRepository<SimpleCustomer, UUID> {
	
}
