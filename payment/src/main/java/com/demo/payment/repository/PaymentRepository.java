package com.demo.payment.repository;

import com.demo.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author <a href="https://github.com/henry0337">Moineau</a>
 */
public interface PaymentRepository extends JpaRepository<Payment, String>, JpaSpecificationExecutor<Payment> { }
