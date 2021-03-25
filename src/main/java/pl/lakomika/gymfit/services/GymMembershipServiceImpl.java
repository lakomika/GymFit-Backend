package pl.lakomika.gymfit.services;

import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.lakomika.gymfit.DTO.gymMembership.GymMemberShipAllDataResponse;
import pl.lakomika.gymfit.DTO.gymMembership.GymMemberShipsResponse;
import pl.lakomika.gymfit.DTO.gymMembership.GymMembershipAddRequest;
import pl.lakomika.gymfit.entity.GymMembership;
import pl.lakomika.gymfit.repository.GymMembershipRepository;
import pl.lakomika.gymfit.repository.TaxRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class GymMembershipServiceImpl implements GymMembershipService {
    private final GymMembershipRepository gymMembershipRepository;
    private final TaxRepository taxRepository;

    public GymMembershipServiceImpl(GymMembershipRepository gymMembershipRepository, TaxRepository taxRepository) {
        this.gymMembershipRepository = gymMembershipRepository;
        this.taxRepository = taxRepository;
    }

    @Override
    public void save(GymMembershipAddRequest gymMembershipRequest) {
        if (gymMembershipRequest.getNumberOfMonths() < 1 || gymMembershipRequest.getNumberOfMonths() > 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad number of months");
        }
        val priceTotal = gymMembershipRequest
                .getPriceMonthGross()
                .multiply(BigDecimal.valueOf(gymMembershipRequest.getNumberOfMonths()));
        val taxRate = taxRepository.getTaxRateByMaxId();
        val divider = BigDecimal.valueOf((taxRate.getRatePercent() * 0.01)).add(new BigDecimal("1"));
        val priceTotalNet = priceTotal.divide(divider, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.CEILING);
        val priceMonthNet = gymMembershipRequest.getPriceMonthGross().divide(divider, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.CEILING);
        val gymMembership = GymMembership
                .builder()
                .active(true)
                .typeOfMembership(gymMembershipRequest.getTypeOfMembership())
                .name(gymMembershipRequest.getName())
                .numberOfMonths(gymMembershipRequest.getNumberOfMonths())
                .priceMonthGross(gymMembershipRequest.getPriceMonthGross())
                .priceTotalGross(priceTotal)
                .taxRatePercent(taxRate.getRatePercent())
                .priceTotalNet(priceTotalNet)
                .priceMonthNet(priceMonthNet)
                .build();
        gymMembershipRepository.save(gymMembership);
    }

    @Override
    public Page<GymMemberShipsResponse> findAllByStatus(int page, int size, boolean status) {
        val pageRequest = PageRequest.of(page, size);
        val pageResult = gymMembershipRepository.findAllByStatus(status, pageRequest);
        val gymMemberships = pageResult.getContent();
        val response = GymMemberShipsResponse.toResponse(gymMemberships);
        return new PageImpl<>(response, pageRequest, pageResult.getTotalElements());
    }

    @Override
    public void update(GymMembership gymMembership) {
        gymMembership.setActive(false);
        gymMembershipRepository.save(gymMembership);
    }

    @Override
    public void changeStatus(Long id, boolean status) {
        gymMembershipRepository.changeStatus(status, id);
    }

    @Override
    public GymMemberShipAllDataResponse getActiveGymPassById(Long id) {
        val gymMembership = gymMembershipRepository.getActiveGymPassById(id);
        if (gymMembership != null) {
            return GymMemberShipAllDataResponse.toResponse(gymMembership);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
    }
}

