package pl.lakomika.gymfit.services;


public interface TaxService {
    Short getTaxRate();

    void updateTaxRate(Short newTaxRateValue);
}
