package com.machines.corext.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;


@Entity
@Setter
@Getter
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String phone;
    private String pin;

    @Column(unique=true,nullable=false)
    private String accountNumber;
    private Double walletBalance;
    private Double accumulatedCommission;
    private String name;

    @Column(unique=true, nullable=false)
    private String email;
    private String digitalAddress;
    private String businessRegistrationName;
    private String businessRegistrationNumber;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

}
