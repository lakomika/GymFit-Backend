package pl.lakomika.gymfit.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lakomika.gymfit.services.TaxService;

@CrossOrigin
@RestController
@RequestMapping("/api/tax/")
public class TaxController {
    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getTaxRate() {
        return ResponseEntity.ok(taxService.getTaxRate());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update-tax-rate-value")
    public void updateTaxRate(@RequestParam Short newTaxRateValue) {
        taxService.updateTaxRate(newTaxRateValue);
    }


}
