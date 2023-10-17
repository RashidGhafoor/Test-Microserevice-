package com.example.goldprices.repository;

import com.example.goldprices.model.GoldPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoldPricesRepository extends JpaRepository<GoldPrice, UUID> {
    Optional<GoldPrice> findByData(Date date);
    void deleteByDataBetween(Date startDate, Date endDate);
}