package com.machines.corext.dto.accountDtos;

public record CheckBalanceRequestDto(
        String accountNumber,
        String pin
) {}

