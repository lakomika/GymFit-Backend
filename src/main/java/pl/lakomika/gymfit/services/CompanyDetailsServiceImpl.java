package pl.lakomika.gymfit.services;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lakomika.gymfit.entity.CompanyDetails;
import pl.lakomika.gymfit.repository.CompanyDetailsRepository;


@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService {
    private final CompanyDetailsRepository companyDetailsRepository;

    @Autowired
    public CompanyDetailsServiceImpl(CompanyDetailsRepository companyDetailsRepository) {
        this.companyDetailsRepository = companyDetailsRepository;
    }

    @Override
    public CompanyDetails get() {
        try {
            return companyDetailsRepository.findById(1L).get();
        } catch (Exception e) {
            companyDetailsRepository.save(new CompanyDetails(1L));
            return companyDetailsRepository.findById(1L).get();
        }
    }

    @Override
    public void updateName(String updateCompanyName) {
        val companyDetailsUpdatingName = get();
        companyDetailsUpdatingName.setName(updateCompanyName);
        companyDetailsRepository.save(companyDetailsUpdatingName);
    }

    @Override
    public void updateAccountNumber(String accountNumber) {
        val companyDetailsUpdatingAccountNumber = get();
        companyDetailsUpdatingAccountNumber.setAccountNumber(accountNumber);
        companyDetailsRepository.save(companyDetailsUpdatingAccountNumber);
    }

    @Override
    public void updateTaxId(String updateTaxId) {
        val companyDetailsUpdatingAccountNumber = get();
        companyDetailsUpdatingAccountNumber.setTaxId(updateTaxId);
        companyDetailsRepository.save(companyDetailsUpdatingAccountNumber);
    }

    @Override
    public void updateAddress(String updateStreet, String updatePostcode, String updateCity) {
        val companyDetailsUpdatingAddress = get();
        companyDetailsUpdatingAddress.setStreet(updateStreet);
        companyDetailsUpdatingAddress.setPostcode(updatePostcode);
        companyDetailsUpdatingAddress.setCity(updateCity);
        companyDetailsRepository.save(companyDetailsUpdatingAddress);
    }
}
