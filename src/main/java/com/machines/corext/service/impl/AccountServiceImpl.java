package com.machines.corext.service.impl;

import com.machines.corext.dto.accountDtos.CheckBalanceRequestDto;
import com.machines.corext.dto.accountDtos.CheckBalanceResponseDto;
import com.machines.corext.dto.accountDtos.TopUpRequestDto;
import com.machines.corext.dto.accountDtos.TopUpResponseDto;
import com.machines.corext.entity.Agent;
import com.machines.corext.repository.AgentRepository;
import com.machines.corext.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AgentRepository agentRepository;

    public AccountServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Optional<TopUpResponseDto> topUp(TopUpRequestDto topUpRequestDto) {
        Optional<Agent> agentOptional = agentRepository.findByAccountNumber(topUpRequestDto.accountNumber());

        if (agentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

        Agent agent = agentOptional.get();

        if (!agent.getPin().equals(topUpRequestDto.pin())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid pin");
        }

        agent.setWalletBalance(agent.getWalletBalance() + topUpRequestDto.amount());
        agentRepository.save(agent);

        return Optional.of(new TopUpResponseDto(
                agent.getPhone(),
                agent.getAccountNumber(),
                agent.getWalletBalance()
        ));
    }

    @Override
    public Optional<CheckBalanceResponseDto> checkBalance(CheckBalanceRequestDto checkBalanceRequestDto) {
        Optional<Agent> agentOptional = agentRepository.findByAccountNumber(checkBalanceRequestDto.accountNumber());

        if (agentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

        Agent agent = agentOptional.get();

        if (!agent.getPin().equals(checkBalanceRequestDto.pin())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid pin");
        }

        return Optional.of(new CheckBalanceResponseDto(
                agent.getPhone(),
                agent.getAccountNumber(),
                agent.getWalletBalance()
        ));
    }
}
