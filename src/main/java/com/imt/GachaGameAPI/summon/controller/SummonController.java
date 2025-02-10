package com.imt.GachaGameAPI.summon.controller;

import com.imt.GachaGameAPI.summon.dto.SummonDto;
import com.imt.GachaGameAPI.summon.service.SummonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summon")
public class SummonController {

    @Autowired
    private SummonService summonService;

    @PostMapping("/{userId}")
    public ResponseEntity<SummonDto> summonMonster(@PathVariable String userId) {
        SummonDto summon = summonService.summonMonster(userId);
        return ResponseEntity.ok(summon);
    }
}
