package com.machines.corext.service.impl;

import com.machines.corext.dto.agentDtos.*;
import com.machines.corext.entity.Agent;
import com.machines.corext.repository.AgentRepository;
import com.machines.corext.service.AgentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {


    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        return "CXT" + String.format("%08d", random.nextInt(100000000));
    }
    private String generatePin() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    @Override
    public Optional<Agent> createAgent(CreateAgentDto createAgentDto) {
        Agent agent = new Agent();
        agent.setPhone(createAgentDto.phone());
        agent.setPin(generatePin());
        agent.setEmail(createAgentDto.email());
        agent.setName(createAgentDto.name());
        agent.setDigitalAddress(createAgentDto.digitalAddress());
        agent.setBusinessRegistrationName(createAgentDto.businessRegistrationName());
        agent.setBusinessRegistrationNumber(createAgentDto.businessRegistrationNumber());
        agent.setAccountNumber(generateAccountNumber());
        agent.setAccumulatedCommission(0.01);
        agent.setWalletBalance(0.01);
        return Optional.of(agentRepository.save(agent));
    }

    @Override
    public List<GetAgentsDto> getAllAgents() {
        return agentRepository.findAll().stream()
                .map(agent -> new GetAgentsDto(
                        agent.getId(),
                        agent.getPhone(),
                        agent.getAccountNumber(),
                        agent.getWalletBalance(),
                        agent.getAccumulatedCommission(),
                        agent.getName(),
                        agent.getEmail(),
                        agent.getDigitalAddress(),
                        agent.getBusinessRegistrationName(),
                        agent.getBusinessRegistrationNumber()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GetAgentByIdDto> getAgentById(Long id) {
        return agentRepository.findById(id)
                .map(agent -> new GetAgentByIdDto(
                        agent.getId(),
                        agent.getPhone(),
                        agent.getAccountNumber(),
                        agent.getWalletBalance(),
                        agent.getAccumulatedCommission(),
                        agent.getName(),
                        agent.getEmail(),
                        agent.getDigitalAddress(),
                        agent.getBusinessRegistrationName(),
                        agent.getBusinessRegistrationNumber()
                ));
    }

    @Override
    public Optional<Agent> updateAgent(Long id, UpdateAgentDto updateAgentDto) {
        return agentRepository.findById(id).map(agent -> {
            agent.setPhone(updateAgentDto.phone());
            agent.setEmail(updateAgentDto.email());
            agent.setName(updateAgentDto.name());
            agent.setDigitalAddress(updateAgentDto.digitalAddress());
            agent.setBusinessRegistrationName(updateAgentDto.businessRegistrationName());
            agent.setBusinessRegistrationNumber(updateAgentDto.businessRegistrationNumber());
            return agentRepository.save(agent);
        });
    }

    @Override
    public boolean deleteAgent(Long id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            agentRepository.delete(agent.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<PinResetResponseDto> resetPin(PinResetRequestDto pinResetRequestDto) {
        Optional<Agent> agentOptional = agentRepository.findByPhone(pinResetRequestDto.phone());

        if (agentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found");
        }

        Agent agent = agentOptional.get();

        if (!agent.getPin().equals(pinResetRequestDto.oldPin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old pin is incorrect");
        }

        agent.setPin(pinResetRequestDto.newPin());
        agentRepository.save(agent);

        return Optional.of(new PinResetResponseDto(agent.getPhone(), "Pin successfully updated"));
    }

    @Override
    public Optional<ForgetPinResponseDto> forgetPin(ForgetPinRequestDto forgetPinRequestDto) {
        Optional<Agent> agentOptional = agentRepository.findByPhone(forgetPinRequestDto.phone());

        if (agentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found");
        }

        Agent agent = agentOptional.get();
        String newPin = generatePin();
        agent.setPin(newPin);
        agentRepository.save(agent);

        return Optional.of(new ForgetPinResponseDto(
                agent.getPhone(),
                agent.getAccountNumber(),
                newPin
        ));
    }
}

