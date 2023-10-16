package com.example.goldprices.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Data
@Table(name = "goldprices")
public class GoldPrice {

    @Id
    private UUID id;
    private Date data;
    private double cena;

    public GoldPrice() {
        this.id = UUID.randomUUID();
    }
    public GoldPrice(Date data, double cena) {
        this.id = UUID.randomUUID();
        this.data = data;
        this.cena = cena;
    }
}
