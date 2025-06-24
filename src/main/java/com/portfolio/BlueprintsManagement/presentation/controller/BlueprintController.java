package com.portfolio.BlueprintsManagement.presentation.controller;

import com.portfolio.BlueprintsManagement.application.service.BlueprintService;
import com.portfolio.BlueprintsManagement.domain.model.blueprintInfo.BlueprintInfo;
import com.portfolio.BlueprintsManagement.presentation.dto.message.SuccessMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.AddBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.DeleteBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint.UpdateBlueprintRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/blueprints")
public class BlueprintController {

    private final BlueprintService blueprintService;

    @Operation(
            summary = "図面情報の取得",
            description = "図面IDに該当する図面情報を取得します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BlueprintInfo.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"message\": \"idに該当する図面が存在しません\"}"),
                                    schema = @Schema(implementation = NotFoundException.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public BlueprintInfo searchBlueprintInfo(
            @Parameter(description = "図面ID", required = true, example = "00000000-0000-1000-8000-000000000001") @PathVariable @ValidId String id)
            throws NotFoundException {
        return blueprintService.getBlueprintInfo(id);
    }

    @Operation(
            summary = "図面登録",
            description = "新規図面を登録します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "{\"id\": \"10000000-0000-1000-8000-000000000001\"}")}
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"message\": \"ファイルのアップロードに失敗しました\"}"),
                                    schema = @Schema(implementation = NotFoundException.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Map<String, String>> registerBlueprint(
            @ModelAttribute @Valid AddBlueprintRequest addBlueprintRequest) throws IOException {
        String id = blueprintService.addBlueprint(addBlueprintRequest);
        Map<String, String> response = Map.of("id", id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "図面更新",
            description = "新規図面を更新します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "{\"message\": \"図面名の更新が完了しました\"}")}
                            )
                    )
            }
    )
    @PutMapping
    public ResponseEntity<Map<String, String>> updateBlueprint(
            @RequestBody @Valid UpdateBlueprintRequest updateBlueprintRequest) {
        blueprintService.updateBlueprint(updateBlueprintRequest);
        Map<String, String> response = Map.of("message",
                SuccessMessage.COMPLETE_UPDATE_BLUEPRINT_NAME.getMessage());
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "図面削除",
            description = "図面を削除します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "{\"isDeletedBlueprint\": \"true\"}")}
                            )
                    )
            }
    )
    @DeleteMapping
    public Map<String, Boolean> deleteBlueprint(
            @RequestBody @Valid DeleteBlueprintRequest deleteBlueprintRequest) {
        boolean isDeletedBlueprint = blueprintService.deleteBlueprint(deleteBlueprintRequest);
        return Map.of("isDeletedBlueprint", isDeletedBlueprint);
    }

}
