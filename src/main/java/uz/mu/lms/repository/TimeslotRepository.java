package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.TimeSlot;
import uz.mu.lms.service.timeslot.TimeslotService;
import uz.mu.lms.service.timeslot.TimeslotServiceImpl;

@Repository
public interface TimeslotRepository extends JpaRepository<TimeSlot, Integer> {

    @Query("SELECT COUNT(t) > 0 FROM TimeSlot t WHERE t.orderNumber = :orderNumber")
    boolean existsByOrderNumber(@Param("orderNumber") Integer orderNumber);
}
