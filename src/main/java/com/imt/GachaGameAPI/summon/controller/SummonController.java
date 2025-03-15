package com.imt.GachaGameAPI.summon.controller;

import com.imt.GachaGameAPI.summon.dto.SummonDto;
import com.imt.GachaGameAPI.summon.service.SummonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/summon")
@CrossOrigin(origins = "http://localhost:8080")  // Enable CORS
// @CrossOrigin(origins = "*", allowCredentials = "true")
public class SummonController {

    @Autowired
    private SummonService summonService;

    @PostMapping("/{userId}")
    public ResponseEntity<SummonDto> summonMonster(@PathVariable int userId) {
        SummonDto summon = summonService.summonMonster(userId);
        return ResponseEntity.ok(summon);
    }
}
