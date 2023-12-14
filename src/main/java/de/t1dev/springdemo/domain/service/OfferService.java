package de.t1dev.springdemo.domain.service;

import de.t1dev.springdemo.domain.core.Offer;
import de.t1dev.springdemo.domain.repository.jpa.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Stream;


@Service
public class OfferService {
    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Stream<Offer> listAll(Pageable pageable) {
        return this.offerRepository.findAll(pageable);
    }

    @Transactional
    public void reserve(ReserveOfferCommand cmd) {
        if (cmd.id == null || cmd.id.isBlank()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        final var maybeOffer = this.offerRepository.findById(cmd.id);

        if (maybeOffer.isEmpty()) {
            throw new IllegalStateException("Offer with this id does not exist");
        }

        final var offer = maybeOffer.get();

        offer.reserveBy(cmd.name);

        this.offerRepository.save(offer);
    }

    public void registerUsedCarOffer(RegisterCarOfferCommand cmd) {
        if (this.offerRepository.exists(cmd.id)) {
            throw new IllegalStateException("Offer with this id already exists");
        }

        final var usedCarOffer = Offer.usedCar(
                cmd.id,
                cmd.model,
                cmd.color,
                cmd.mileage,
                cmd.grossPrice,
                cmd.age
        );

        this.offerRepository.save(usedCarOffer);
    }

    public void registerDemoCarOffer(RegisterCarOfferCommand cmd) {
        if (this.offerRepository.exists(cmd.id)) {
            throw new IllegalStateException("Offer with this id already exists");
        }

        final var demoCarOffer = Offer.demoCar(
                cmd.id,
                cmd.model,
                cmd.color,
                cmd.mileage,
                cmd.grossPrice,
                cmd.age
        );

        this.offerRepository.save(demoCarOffer);
    }

    public void registerConfigOffer(RegisterConfigOfferCommand cmd) {
        if (this.offerRepository.exists(cmd.id)) {
            throw new IllegalStateException("Offer with this id already exists");
        }

        final var configOffer = Offer.config(
                cmd.id,
                cmd.model,
                cmd.color,
                cmd.grossPrice
        );

        this.offerRepository.save(configOffer);
    }

    public record RegisterCarOfferCommand(
            String id,
            String model,
            String color,
            long mileage,
            BigDecimal grossPrice,
            long age) {}

    public record RegisterConfigOfferCommand(
            String id,
            String model,
            String color,
            BigDecimal grossPrice) {}

    public record ReserveOfferCommand(
            String id,
            String name) {}
}
