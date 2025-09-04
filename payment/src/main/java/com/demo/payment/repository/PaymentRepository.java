package com.demo.payment.repository;

import com.demo.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository extends JpaRepository<Payment, String>, JpaSpecificationExecutor<Payment> { }
