package Portfolio.BlueprintsManagement.presentation.controller;

import Portfolio.BlueprintsManagement.application.service.SiteService;
import Portfolio.BlueprintsManagement.domain.model.Site;
import Portfolio.BlueprintsManagement.presentation.dto.message.SuccessMessage;
import Portfolio.BlueprintsManagement.presentation.dto.request.site.SiteRequest;
import Portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import Portfolio.BlueprintsManagement.presentation.exception.validation.idValidation.ValidId;
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

    @GetMapping
    public List<Site> searchSites() throws NotFoundException {
        return siteService.getSites();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registerSite(@RequestBody @Valid SiteRequest request) {
        siteService.addSite(request);
        Map<String, String> message = Map.of("message", SuccessMessage.COMPLETE_REGISTER_SITE.getMessage());
        return ResponseEntity.ok().body(message);
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
