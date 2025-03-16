package com.imt.GachaGameAPI.summon.controller;

import com.imt.GachaGameAPI.summon.dto.SummonDto;
import com.imt.GachaGameAPI.summon.service.SummonService;

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

    @PostMapping("/{userId}")
    public ResponseEntity<SummonDto> summonMonster(@PathVariable String userId) {
        SummonDto summon = summonService.summonMonster(userId);
        return ResponseEntity.ok(summon);
    }
    
    @PostMapping("/{userId}/regenerate")
    public ResponseEntity<List<SummonDto>> summonRegenerate(@PathVariable String userId, @RequestBody List<SummonDto> pastSummons) {
        List<SummonDto> regeneratedSummons = summonService.regenerateSummons(userId, pastSummons);
        return ResponseEntity.ok(regeneratedSummons);
    }
}
