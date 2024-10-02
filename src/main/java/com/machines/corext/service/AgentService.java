package com.machines.corext.service;

import com.machines.corext.dto.agentDtos.*;
import com.machines.corext.entity.Agent;

import java.util.List;
import java.util.Optional;

public interface AgentService {
    Optional<Agent> createAgent(CreateAgentDto createAgentDto);
    List<GetAgentsDto> getAllAgents();
    Optional<GetAgentByIdDto> getAgentById(Long id);
    Optional<Agent> updateAgent(Long id, UpdateAgentDto updateAgentDto);
    boolean deleteAgent(Long id);
    Optional<PinResetResponseDto> resetPin(PinResetRequestDto pinResetRequestDto);
    Optional<ForgetPinResponseDto> forgetPin(ForgetPinRequestDto forgetPinRequestDto);
}

