package com.machines.corext.dto.agentDtos;

public record PinResetRequestDto(
        String phone,
        String oldPin,
        String newPin
) {}

