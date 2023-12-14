package de.t1dev.springdemo.domain.repository.jpa;

import de.t1dev.springdemo.domain.core.Offer;
import de.t1dev.springdemo.domain.repository.infra.OfferDbEntity;
import de.t1dev.springdemo.domain.repository.infra.OfferJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class OfferRepository {

    private final OfferJpaRepository offerJpaRepository;

    @Autowired
    public OfferRepository(OfferJpaRepository offerJpaRepository) {
        this.offerJpaRepository = offerJpaRepository;
    }

    private static Offer fromDbEntity(OfferDbEntity dbEntity) {
        switch (Offer.OfferType.valueOf(dbEntity.getType())) {
            case USED_CAR -> {
                return Offer.usedCar(
                        dbEntity.getId(),
                        dbEntity.getModel(),
                        dbEntity.getColor(),
                        dbEntity.getMileage(),
                        dbEntity.getGrossPrice(),
                        dbEntity.getAge());
            }
            case DEMO_CAR -> {
                return Offer.demoCar(
                        dbEntity.getId(),
                        dbEntity.getModel(),
                        dbEntity.getColor(),
                        dbEntity.getMileage(),
                        dbEntity.getGrossPrice(),
                        dbEntity.getAge());
            }
            case CAR_CONFIGURATION -> {
                return Offer.config(dbEntity.getId(), dbEntity.getModel(), dbEntity.getColor(), dbEntity.getGrossPrice());
            }
            default -> throw new IllegalArgumentException("Invalid offer type in db: " + dbEntity.getType());
        }
    }

    @Transactional
    public void save(final Offer offer) {
        if (offer.getId() == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        final var dbEntity = new OfferDbEntity();

        dbEntity.setId(offer.getId());
        dbEntity.setColor(offer.getColor());
        dbEntity.setModel(offer.getModel());
        dbEntity.setGrossPrice(offer.getGrossPrice());
        dbEntity.setState(offer.getState().toString());
        dbEntity.setType(offer.getType().toString());
        dbEntity.setReservedBy(offer.getReservedBy().orElse(null));
        dbEntity.setMileage(offer.getMileage().orElse(null));
        dbEntity.setAge(offer.getAge().orElse(null));

        this.offerJpaRepository.save(dbEntity);
    }

    @Transactional
    public boolean exists(final String id) {
        return this.offerJpaRepository.findById(id).isPresent();
    }

    @Transactional
    public Stream<Offer> findAll(Pageable pageable) {
        final var offers = this.offerJpaRepository.findAll(pageable);

        return offers.stream().map(OfferRepository::fromDbEntity);
    }

    @Transactional
    public Optional<Offer> findById(String id) {
        return this.offerJpaRepository.findById(id).map(OfferRepository::fromDbEntity);
    }
}
