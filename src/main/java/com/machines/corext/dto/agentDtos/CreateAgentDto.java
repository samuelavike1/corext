package com.machines.corext.dto.agentDtos;

public record CreateAgentDto(
        String phone,
        String email,
        String name,
        String digitalAddress,
        String businessRegistrationName,
        String businessRegistrationNumber
) {}

