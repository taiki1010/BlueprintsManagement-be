package Portfolio.BlueprintsManagement.presentation.controller;

import Portfolio.BlueprintsManagement.application.service.BlueprintService;
import Portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.DeleteBlueprintRequest;
import Portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import Portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/blueprints")
public class BlueprintController {

    private final BlueprintService blueprintService;

    @GetMapping("/{id}")
    public BlueprintInfo searchBlueprintInfo(@PathVariable @ValidId String id) throws NotFoundException {
        return blueprintService.getBlueprintInfo(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registerBlueprint(@ModelAttribute @Valid AddBlueprintRequest addBlueprintRequest) throws IOException {
        String id = blueprintService.addBlueprint(addBlueprintRequest);
        Map<String, String> response = Map.of("id", id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> updateBlueprint(@RequestBody @Valid UpdateBlueprintRequest updateBlueprintRequest) {
        blueprintService.updateBlueprint(updateBlueprintRequest);
        Map<String, String> response = Map.of("message", "図面名の更新が完了しました");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public Map<String, Boolean> deleteBlueprint(@RequestBody @Valid DeleteBlueprintRequest deleteBlueprintRequest) {
        boolean isDeletedBlueprint = blueprintService.deleteBlueprint(deleteBlueprintRequest);
        return Map.of("isDeletedBlueprint", isDeletedBlueprint);
    }

}
