package org.isobit.cassandra.repo;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.isobit.cassandra.models.DeliveryTimeslot;

import java.time.LocalDate;
import java.util.List;

public interface DeliveryTimeslotRepo extends CrudRepository<DeliveryTimeslot, DeliveryTimeslot.Key> {
    @Query("SELECT * FROM delivery_timeslots WHERE delivery_date = ?0 ORDER BY start_time ASC")
    List<DeliveryTimeslot> findByDeliveryDate(LocalDate deliveryDate);
}
