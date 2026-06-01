package io.assemblers.project100endgame.common.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("OK");
    }
}
