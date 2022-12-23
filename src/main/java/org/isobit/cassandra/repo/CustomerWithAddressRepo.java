package org.isobit.cassandra.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.isobit.cassandra.models.CustomerWithAddress;

import java.util.UUID;

@Repository
public interface CustomerWithAddressRepo extends CrudRepository<CustomerWithAddress, UUID> {
}
