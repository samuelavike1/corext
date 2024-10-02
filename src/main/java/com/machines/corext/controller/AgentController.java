package com.machines.corext.controller;

import com.machines.corext.dto.agentDtos.*;
import com.machines.corext.entity.Agent;
import com.machines.corext.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping
    public ResponseEntity<Agent> createAgent(@RequestBody CreateAgentDto createAgentDto) {
        Optional<Agent> agent = agentService.createAgent(createAgentDto);
        return agent.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<GetAgentsDto>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAgentByIdDto> getAgentById(@PathVariable Long id) {
        Optional<GetAgentByIdDto> agent = agentService.getAgentById(id);
        return agent.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable Long id, @RequestBody UpdateAgentDto updateAgentDto) {
        Optional<Agent> agent = agentService.updateAgent(id, updateAgentDto);
        return agent.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        if (agentService.deleteAgent(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/reset-pin")
    public ResponseEntity<PinResetResponseDto> resetPin(@RequestBody PinResetRequestDto pinResetRequestDto) {
        Optional<PinResetResponseDto> response = agentService.resetPin(pinResetRequestDto);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/forget-pin")
    public ResponseEntity<ForgetPinResponseDto> forgetPin(@RequestBody ForgetPinRequestDto forgetPinRequestDto) {
        Optional<ForgetPinResponseDto> response = agentService.forgetPin(forgetPinRequestDto);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}

