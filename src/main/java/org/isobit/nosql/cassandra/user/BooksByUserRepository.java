package org.isobit.nosql.cassandra.user;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BooksByUserRepository extends ReactiveCassandraRepository<BooksByUser, String> {

    //Slice<BooksByUser> findAllById(String id, Pageable pageable);
    
}