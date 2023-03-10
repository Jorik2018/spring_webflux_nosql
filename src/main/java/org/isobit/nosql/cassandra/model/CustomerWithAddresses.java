package org.isobit.nosql.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("customer_with_addresses")
public class CustomerWithAddresses {
	
    @PrimaryKey
    UUID id;
    
	String name;
    
	String telephone;
    
	String email;

    Map<String, Address> addresses;

}
