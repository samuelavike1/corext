package com.machines.corext.service.impl;

import com.machines.corext.dto.accountDtos.CheckBalanceRequestDto;
import com.machines.corext.dto.accountDtos.CheckBalanceResponseDto;
import com.machines.corext.dto.accountDtos.TopUpRequestDto;
import com.machines.corext.dto.accountDtos.TopUpResponseDto;
import com.machines.corext.entity.Agent;
import com.machines.corext.entity.Transaction;
import com.machines.corext.repository.AgentRepository;
import com.machines.corext.repository.TransactionRepository;
import com.machines.corext.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    private final AgentRepository agentRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AgentRepository agentRepository, TransactionRepository transactionRepository) {
        this.agentRepository = agentRepository;
        this.transactionRepository = transactionRepository;
    }

    private String generateTransactionId() {
        int length = 12;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder transactionId = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            transactionId.append(characters.charAt(random.nextInt(characters.length())));
        }

        return transactionId.toString();
    }


    @Override
    public Optional<TopUpResponseDto> topUp(TopUpRequestDto topUpRequestDto) {
        Optional<Agent> agentOptional = agentRepository.findByAccountNumber(topUpRequestDto.accountNumber());

        if (agentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found for account number: " + topUpRequestDto.accountNumber());
        }

        Agent agent = agentOptional.get();

        if (!agent.getPin().equals(topUpRequestDto.pin())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect PIN provided for account number: " + topUpRequestDto.accountNumber());
        }

        // Record balance before top-up
        double balanceBefore = (agent.getWalletBalance() != null ? agent.getWalletBalance() : 0.0);
        double balanceAfter = balanceBefore + topUpRequestDto.amount();

        // Perform the top-up
        agent.setWalletBalance(balanceAfter);
        agentRepository.save(agent);

        // Record the transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setAmount(topUpRequestDto.amount());
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(balanceAfter);
        transaction.setType("top-up");
        transaction.setStatus("successful");
        transaction.setCommission(0.0);

        // Source and destination are the same for top-ups
        transaction.setSourceName(agent.getName());
        transaction.setSourceAccountNumber(agent.getAccountNumber());
        transaction.setSourcePhone(agent.getPhone());
        transaction.setDestinationName(agent.getName());
        transaction.setDestinationAccountNumber(agent.getAccountNumber());
        transaction.setDestinationPhone(agent.getPhone());

        // Save the transaction
        transactionRepository.save(transaction);

        // Return the response
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
