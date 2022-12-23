package org.isobit.cassandra.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.isobit.cassandra.models.SimpleCustomer;

import java.util.UUID;

@Repository
public interface SimpleCustomerRepo extends CrudRepository<SimpleCustomer, UUID> {
}
