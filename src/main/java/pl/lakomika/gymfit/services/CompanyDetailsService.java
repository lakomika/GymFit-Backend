package pl.lakomika.gymfit.services;

import pl.lakomika.gymfit.entity.CompanyDetails;

public interface CompanyDetailsService {
    CompanyDetails get();

    void updateName(String updateCompanyName);

    void updateAccountNumber(String accountNumber);

    void updateAddress(String updateStreet, String updatePostcode, String updateCity);

    void updateTaxId(String updateTaxId);
}
