package com.machines.corext.dto.agentDtos;

public record UpdateAgentDto(
        String phone,
        String email,
        String name,
        String digitalAddress,
        String businessRegistrationName,
        String businessRegistrationNumber
) {}

