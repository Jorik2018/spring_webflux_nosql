package org.isobit.nosql.cassandra.userbooks;

import java.time.LocalDate;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(value = "book_by_user_and_bookid")
public class UserBooks {
    
    @PrimaryKey
    private UserBooksPrimaryKey key;
    
    @Column("started_date")
    @CassandraType(type = Name.DATE)
    private LocalDate startedDate;

    @Column("completed_date")
    @CassandraType(type = Name.DATE)
    private LocalDate completedDate;

    @Column("reading_status")
    @CassandraType(type = Name.TEXT)
    private String readingStatus;

    @Column("rating")
    @CassandraType(type = Name.INT)
    private int rating;

}