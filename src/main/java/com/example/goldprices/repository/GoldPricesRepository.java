package com.example.goldprices.repository;

import com.example.goldprices.model.GoldPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface GoldPricesRepository extends JpaRepository<GoldPrice, UUID> {

    @Query("SELECT e FROM GoldPrice e WHERE e.data = :searchDate")
    GoldPrice findByDate(@Param("searchDate") Date date);

    @Modifying
    @Query("DELETE FROM GoldPrice e WHERE e.data BETWEEN :startDate AND :endDate")
    void deleteRecordsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}