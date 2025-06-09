package com.portfolio.BlueprintsManagement.presentation.controller;

import com.portfolio.BlueprintsManagement.application.service.BlueprintService;
import com.portfolio.BlueprintsManagement.application.service.SiteService;
import com.portfolio.BlueprintsManagement.domain.model.blueprint.Blueprint;
import com.portfolio.BlueprintsManagement.domain.model.site.Site;
import com.portfolio.BlueprintsManagement.presentation.dto.message.SuccessMessage;
import com.portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sites")
public class SiteController {

    private final SiteService siteService;
    private final BlueprintService blueprintService;

    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkExistSites() {
        if(siteService.checkExistSites()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Site> searchSites() throws NotFoundException {
        return siteService.getSites();
    }

    @GetMapping("/{id}")
    public Site searchSite(@PathVariable @ValidId String id) throws NotFoundException {
        return siteService.getSite(id);
    }

    @GetMapping("/{id}/blueprints")
    public List<Blueprint> searchBlueprintsBySiteId(@PathVariable @ValidId String id) throws NotFoundException {
        return blueprintService.getBlueprintsBySiteId(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registerSite(@RequestBody @Valid SiteRequest request) {
        String id = siteService.addSite(request);
        Map<String, String> response = Map.of("id", id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateSite(@RequestBody @Valid SiteRequest request, @PathVariable @ValidId String id) throws NotFoundException {
        siteService.updateSite(request, id);
        Map<String, String> message = Map.of("message", SuccessMessage.COMPLETE_UPDATE_SITE.getMessage());
        return ResponseEntity.ok().body(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSite(@PathVariable @ValidId String id) throws NotFoundException {
        siteService.deleteSite(id);
        Map<String, String> message = Map.of("message", SuccessMessage.COMPLETE_DELETE_SITE.getMessage());
        return ResponseEntity.ok().body(message);
    }
}
