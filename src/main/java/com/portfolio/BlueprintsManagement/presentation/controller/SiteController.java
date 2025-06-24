package com.portfolio.BlueprintsManagement.presentation.controller;

import com.portfolio.BlueprintsManagement.application.service.BlueprintService;
import com.portfolio.BlueprintsManagement.application.service.SiteService;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import com.portfolio.BlueprintsManagement.presentation.dto.message.SuccessMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
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
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sites")
public class SiteController {

    private final SiteService siteService;
    private final BlueprintService blueprintService;

    @Operation(
            summary = "現場の存在確認",
            description = "現場がデータベースに存在するか確認します。",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "applicaiton/json")),
                    @ApiResponse(responseCode = "200", description = "現場が存在する場合200コードを返却する"),
                    @ApiResponse(responseCode = "404", description = "現場が存在しない場合404コードを返却する")
            }
    )
    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkExistSites() {
        if (siteService.checkExistSites()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "現場一覧の取得",
            description = "現場の一覧を取得します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Site.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"message\": \"現場が追加されていません\"}"),
                                    schema = @Schema(implementation = NotFoundException.class))
                    )
            }
    )
    @GetMapping
    public List<Site> searchSites() throws NotFoundException {
        return siteService.getSites();
    }

    @Operation(
            summary = "現場一件の取得",
            description = "現場IDに紐づく現場を一件取得します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Site.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"message\": \"idに該当する現場が存在しません\"}"),
                                    schema = @Schema(implementation = NotFoundException.class))
                    )

            }
    )
    @GetMapping("/{id}")
    public Site searchSite(
            @Parameter(description = "現場ID", required = true, example = "00000000-0000-1000-8000-000000000001") @PathVariable @ValidId String id)
            throws NotFoundException {
        return siteService.getSite(id);
    }

    @Operation(
            summary = "図面一覧の取得",
            description = "現場IDに紐づく図面を取得します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Blueprint.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"message\": \"現場idに該当する図面が存在しません\"}"),
                                    schema = @Schema(implementation = NotFoundException.class))
                    )
            }
    )
    @GetMapping("/{id}/blueprints")
    public List<Blueprint> searchBlueprintsBySiteId(
            @Parameter(description = "現場ID", required = true, example = "00000000-0000-1000-8000-000000000001") @PathVariable @ValidId String id)
            throws NotFoundException {
        return blueprintService.getBlueprintsBySiteId(id);
    }

    @Operation(
            summary = "現場登録",
            description = "新規現場を登録します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "{\"id\": \"00000000-0000-1000-8000-000000000001\"}")}
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Map<String, String>> registerSite(
            @RequestBody @Valid SiteRequest request) {
        String id = siteService.addSite(request);
        Map<String, String> response = Map.of("id", id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "現場修正",
            description = "現場を修正します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "{\"message\": \"現場情報が更新されました\"}")}
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"message\": \"idに該当する現場が存在しません\"}"),
                                    schema = @Schema(implementation = NotFoundException.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateSite(@RequestBody @Valid SiteRequest request,
            @Parameter(description = "現場ID", required = true, example = "00000000-0000-1000-8000-000000000001")
            @PathVariable @ValidId String id) throws NotFoundException {
        siteService.updateSite(request, id);
        Map<String, String> message = Map.of("message",
                SuccessMessage.COMPLETE_UPDATE_SITE.getMessage());
        return ResponseEntity.ok().body(message);
    }

    @Operation(
            summary = "現場削除",
            description = "現場を削除します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(value = "{\"message\": \"現場が削除されました\"}")}
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"message\": \"idに該当する現場が存在しません\"}"),
                                    schema = @Schema(implementation = NotFoundException.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSite(
            @Parameter(description = "現場ID", required = true, example = "00000000-0000-1000-8000-000000000001") @PathVariable @ValidId String id)
            throws NotFoundException {
        siteService.deleteSite(id);
        Map<String, String> message = Map.of("message",
                SuccessMessage.COMPLETE_DELETE_SITE.getMessage());
        return ResponseEntity.ok().body(message);
    }
}
