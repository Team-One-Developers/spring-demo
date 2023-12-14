package de.t1dev.springdemo.domain.core;

import java.math.BigDecimal;
import java.util.Optional;

public class Offer {

    private Offer() {}

    public enum OfferType {
        USED_CAR, DEMO_CAR, CAR_CONFIGURATION
    }

    public enum OfferState {
        OPEN, RESERVED
    }

    public String getId() {
        return id;
    }

    private String id;

    public OfferType getType() {
        return type;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public Optional<Long> getAge() {
        return age;
    }

    public Optional<Long> getMileage() {
        return mileage;
    }

    public OfferState getState() {
        return state;
    }

    public Optional<String> getReservedBy() {
        return reservedBy;
    }

    private OfferType type;

    private BigDecimal grossPrice;

    private String model;

    private String color;

    private Optional<Long> age;

    private Optional<Long> mileage;

    private OfferState state;

    private Optional<String> reservedBy;

    private static Offer car(
            final String id,
            final String model,
            final String color,
            final long mileage,
            final BigDecimal grossPrice,
            final long age) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (model == null || model.isEmpty()) {
            throw new IllegalArgumentException("model cannot be empty");
        }
        if (color == null || color.isEmpty()) {
            throw new IllegalArgumentException("color cannot be empty");
        }
        if (mileage <= 0) {
            throw new IllegalArgumentException("mileage must be positive");
        }
        if (grossPrice == null || grossPrice.doubleValue() <= 0) {
            throw new IllegalArgumentException("grossPrice must be positive");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("age must be positive");
        }

        final var offer = new Offer();

        offer.id = id;
        offer.model = model;
        offer.color = color;
        offer.mileage = Optional.of(mileage);
        offer.grossPrice = grossPrice;
        offer.age = Optional.of(age);
        offer.reservedBy = Optional.empty();
        offer.state = OfferState.OPEN;

        return offer;
    }

    public static Offer usedCar(final String id,
                                final String model,
                                final String color,
                                final long mileage,
                                final BigDecimal grossPrice,
                                final long age) {
        final var offer = Offer.car(id, model, color, mileage, grossPrice, age);

        offer.type = OfferType.USED_CAR;

        return offer;
    }

    public static Offer demoCar(final String id,
                                final String model,
                                final String color,
                                final long mileage,
                                final BigDecimal grossPrice,
                                final long age) {
        final var offer = Offer.car(id, model, color, mileage, grossPrice, age);

        offer.type = OfferType.DEMO_CAR;

        return offer;
    }

    public static Offer config(
            final String id,
            final String model,
            final String color,
            final BigDecimal grossPrice) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (model == null || model.isEmpty()) {
            throw new IllegalArgumentException("model cannot be empty");
        }
        if (color == null || color.isEmpty()) {
            throw new IllegalArgumentException("color cannot be empty");
        }
        if (grossPrice == null || grossPrice.doubleValue() <= 0) {
            throw new IllegalArgumentException("grossPrice must be positive");
        }

        final var offer = new Offer();

        offer.type = OfferType.CAR_CONFIGURATION;
        offer.id = id;
        offer.model = model;
        offer.color = color;
        offer.mileage = Optional.empty();
        offer.grossPrice = grossPrice;
        offer.age = Optional.empty();
        offer.reservedBy = Optional.empty();
        offer.state = OfferState.OPEN;

        return offer;
    }

    public void reserveBy(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("name cannot be empty");
        }

        if (this.getState().equals(OfferState.RESERVED) && this.getReservedBy().map(name::equals).orElse(false)) {
            throw new IllegalStateException("Offer has already been reserved");
        }

        this.state = OfferState.RESERVED;
        this.reservedBy = Optional.of(name);
    }
}
