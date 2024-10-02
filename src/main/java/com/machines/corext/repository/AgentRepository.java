package com.machines.corext.repository;

import com.machines.corext.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByPhone(String phone);
    Optional<Agent> findByEmail(String email);
    Optional<Agent> findByAccountNumber(String accountNumber);
}

