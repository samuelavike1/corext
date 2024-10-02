package com.machines.corext.dto.accountDtos;

public record TopUpResponseDto(
        String phone,
        String accountNumber,
        Double newBalance
) {}

