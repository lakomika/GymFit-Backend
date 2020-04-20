package pl.lakomika.gymfit.services;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.lakomika.gymfit.entity.Tax;
import pl.lakomika.gymfit.repository.TaxRepository;

import java.sql.Timestamp;

@Service
public class TaxServiceImpl implements TaxService {
    private final TaxRepository taxRepository;

    public TaxServiceImpl(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Override
    public Short getTaxRate() {
        val taxRate = taxRepository.getTaxRateByMaxId();
        return taxRate.getRatePercent();
    }

    @Override
    public void updateTaxRate(Short newTaxRateValue) {
        val oldTaxRate = taxRepository.getTaxRateByMaxId();
        if (oldTaxRate.getRatePercent() == newTaxRateValue) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tax rate is the same like current rate");
        } else if (newTaxRateValue > 99 || newTaxRateValue < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tax rate have no correct value");
        } else {
            val currentTimestamp = new Timestamp(System.currentTimeMillis());
            oldTaxRate.setEnd_DTTM(currentTimestamp);
            taxRepository.save(oldTaxRate);
            val newTaxRate = Tax
                    .builder()
                    .Start_DTTM(currentTimestamp)
                    .ratePercent(newTaxRateValue)
                    .build();
            taxRepository.save(newTaxRate);
        }
    }
}
