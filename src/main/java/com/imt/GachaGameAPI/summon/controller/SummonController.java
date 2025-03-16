package com.imt.GachaGameAPI.summon.controller;

import com.imt.GachaGameAPI.summon.dto.SummonDto;
import com.imt.GachaGameAPI.summon.model.Summon;
import com.imt.GachaGameAPI.summon.service.SummonService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/summon")
@CrossOrigin(
    origins = "*", 
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
    maxAge = 3600
)
public class SummonController {

    @Autowired
    private SummonService summonService;

    @PostMapping("/{userId}/{token}")
    public ResponseEntity<SummonDto> summonMonster(@PathVariable String userId, @PathVariable String token) {
        SummonDto summon = summonService.summonMonster(userId, token);
        return ResponseEntity.ok(summon);
    }
    
    @PostMapping("/regenerate/{token}")
    public ResponseEntity<List<SummonDto>> summonRegenerate(@PathVariable String token, @Valid @RequestBody List<Summon> pastSummons) {
        List<SummonDto> regeneratedSummons = summonService.regenerateSummons(pastSummons, token);
        return ResponseEntity.ok(regeneratedSummons);
    }
}
