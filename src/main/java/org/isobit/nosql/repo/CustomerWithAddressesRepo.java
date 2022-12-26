package org.isobit.nosql.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.isobit.nosql.model.CustomerWithAddresses;

import java.util.UUID;

@Repository
public interface CustomerWithAddressesRepo extends CrudRepository<CustomerWithAddresses, UUID> {
}
