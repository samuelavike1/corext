package com.machines.corext.dto.agentDtos;

public record ForgetPinResponseDto(
        String phone,
        String accountNumber,
        String newPin
) {}

