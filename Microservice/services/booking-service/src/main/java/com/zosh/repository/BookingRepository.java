package com.zosh.repository;

import com.zosh.enums.BookingStatus;
import com.zosh.model.Booking;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    Long countByFlightInstanceId(Long flightInstanceId);

    @Query("""
        select distinct b from Booking b
        left join fetch b.passengers p
        where b.airlineId=:airlineId
        and (:search is null or 
        LOWER(b.bookingReference) like LOWER(concat('%',:search,'%')) OR 
        lower(p.firstName) like LOWER(concat('%',:search,'%')) OR 
        lower(p.lastName) like LOWER(concat('%',:search,'%')) OR 
        lower(p.email) like LOWER(concat('%',:search,'%')) OR 
        lower(p.phone) like LOWER(concat('%',:search,'%')) OR 
        lower(b.contactInfo.email) like LOWER(concat('%',:search,'%')) OR 
        lower(b.contactInfo.phone) like LOWER(concat('%',:search,'%')))
        
        and (:status is null or b.status =:status) 
        and (:flightInstanceId is null or b.flightInstanceId =:flightInstanceId)         
""")
    List<Booking> findByAirlineWIthFilter(
            @Param("airlineId") Long airlineId,
            @Param("search")  String search,
            @Param("status") BookingStatus status,
            @Param("flightInstanceId") Long flightInstanceId,
            Sort sort
    );

    Boolean existsByBookingReference(String bookingReference);
}
