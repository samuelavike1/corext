package com.machines.corext.dto.agentDtos;

public record GetAgentsDto(
        Long id,
        String phone,
        String accountNumber,
        Double walletBalance,
        Double accumulatedCommission,
        String name,
        String email,
        String digitalAddress,
        String businessRegistrationName,
        String businessRegistrationNumber
) {}

