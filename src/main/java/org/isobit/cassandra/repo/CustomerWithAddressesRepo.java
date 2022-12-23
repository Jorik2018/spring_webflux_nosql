package org.isobit.cassandra.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.isobit.cassandra.models.CustomerWithAddresses;

import java.util.UUID;

@Repository
public interface CustomerWithAddressesRepo extends CrudRepository<CustomerWithAddresses, UUID> {
}
