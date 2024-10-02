package com.machines.corext.service;

import com.machines.corext.dto.accountDtos.CheckBalanceRequestDto;
import com.machines.corext.dto.accountDtos.CheckBalanceResponseDto;
import com.machines.corext.dto.accountDtos.TopUpRequestDto;
import com.machines.corext.dto.accountDtos.TopUpResponseDto;

import java.util.Optional;

public interface AccountService {
    Optional<TopUpResponseDto> topUp(TopUpRequestDto topUpRequestDto);
    Optional<CheckBalanceResponseDto> checkBalance(CheckBalanceRequestDto checkBalanceRequestDto);
}

