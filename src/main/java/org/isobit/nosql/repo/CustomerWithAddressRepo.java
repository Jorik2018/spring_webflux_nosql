package org.isobit.nosql.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.isobit.nosql.model.CustomerWithAddress;

import java.util.UUID;

@Repository
public interface CustomerWithAddressRepo extends CrudRepository<CustomerWithAddress, UUID> {
}
