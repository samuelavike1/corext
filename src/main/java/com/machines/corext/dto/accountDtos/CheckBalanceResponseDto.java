package com.machines.corext.dto.accountDtos;

public record CheckBalanceResponseDto(
        String phone,
        String accountNumber,
        Double balance
) {}

