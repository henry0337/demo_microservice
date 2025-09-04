package com.demo.payment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    private String orderId;
}
