package com.machines.corext.dto.accountDtos;

public record TopUpRequestDto(
        String accountNumber,
        String pin,
        Double amount
) {}
