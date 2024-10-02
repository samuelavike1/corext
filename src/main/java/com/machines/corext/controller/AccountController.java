package com.machines.corext.controller;

import com.machines.corext.dto.accountDtos.CheckBalanceRequestDto;
import com.machines.corext.dto.accountDtos.CheckBalanceResponseDto;
import com.machines.corext.dto.accountDtos.TopUpRequestDto;
import com.machines.corext.dto.accountDtos.TopUpResponseDto;
import com.machines.corext.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/top-up")
    public ResponseEntity<TopUpResponseDto> topUp(@RequestBody TopUpRequestDto topUpRequestDto) {
        Optional<TopUpResponseDto> response = accountService.topUp(topUpRequestDto);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/check-balance")
    public ResponseEntity<CheckBalanceResponseDto> checkBalance(@RequestBody CheckBalanceRequestDto checkBalanceRequestDto) {
        Optional<CheckBalanceResponseDto> response = accountService.checkBalance(checkBalanceRequestDto);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}

