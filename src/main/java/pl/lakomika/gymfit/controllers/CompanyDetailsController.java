package pl.lakomika.gymfit.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lakomika.gymfit.services.CompanyDetailsService;


@CrossOrigin
@RestController
@RequestMapping("/api/management-company/")
@PreAuthorize("hasRole('ADMIN')")
public class CompanyDetailsController {
    private final CompanyDetailsService companyDetailsService;

    @Autowired
    public CompanyDetailsController(CompanyDetailsService companyDetailsService) {
        this.companyDetailsService = companyDetailsService;
    }

    @PreAuthorize("hasAnyRole('CLIENT','ADMIN','RECEPTIONIST')")
    @GetMapping("/company-details")
    public ResponseEntity<?> getCompanyDetails() {
        return ResponseEntity.ok(companyDetailsService.get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-name")
    public void changeCompanyDetails(@RequestParam String updateName) {
        companyDetailsService.updateName(updateName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-account-number")
    public void changeAccountNumber(@RequestParam String updateAccountNumber) {
        companyDetailsService.updateAccountNumber(updateAccountNumber);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-tax-id")
    public void changeTaxId(@RequestParam String updateTaxId) {
        companyDetailsService.updateTaxId(updateTaxId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-address")
    public void changeAddress(@RequestParam String updateStreet, @RequestParam String updatePostcode, @RequestParam String updateCity) {
        companyDetailsService.updateAddress(updateStreet, updatePostcode, updateCity);
    }


}
