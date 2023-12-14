package de.t1dev.springdemo.domain.repository.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferJpaRepository
        extends JpaRepository<OfferDbEntity, String> {
}
