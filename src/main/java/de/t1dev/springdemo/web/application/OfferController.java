package de.t1dev.springdemo.web.application;

import de.t1dev.springdemo.domain.service.OfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    private final OfferService offerService;

    @Autowired
    OfferController(OfferService offerService) {
        this.offerService = offerService;
    }
}
