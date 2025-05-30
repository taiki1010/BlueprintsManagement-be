package Portfolio.BlueprintsManagement.presentation.controller;

import Portfolio.BlueprintsManagement.application.service.BlueprintService;
import Portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import Portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.BlueprintRequest;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blueprints")
public class BlueprintController {

    private final BlueprintService blueprintService;

    @GetMapping("/{id}")
    public BlueprintInfo searchBlueprintInfo(@PathVariable String id) throws NotFoundException {
        return blueprintService.getBlueprintInfo(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registerBlueprint(@ModelAttribute BlueprintRequest blueprintRequest) throws IOException {
        String id = blueprintService.addBlueprint(blueprintRequest);
        Map<String, String> response = Map.of("id", id);
        return ResponseEntity.ok().body(response);
    }

}
