package com.anova.url_shortner.controller;


import com.anova.url_shortner.dto.CreateUrlRequest;
import com.anova.url_shortner.dto.CreateUrlResponse;
import com.anova.url_shortner.dto.UrlResponse;
import com.anova.url_shortner.dto.UrlStatsResponse;
import com.anova.url_shortner.entity.UrlMapping;
import com.anova.url_shortner.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public CreateUrlResponse createShortUrl(
            @Valid @RequestBody CreateUrlRequest request) {

        String shortCode =
                urlService.createShortUrl(request.getOriginalUrl());

        return new CreateUrlResponse(shortCode);
    }

    @GetMapping("/{shortCode}")
    public UrlResponse getOriginalUrl(
            @PathVariable String shortCode) {

        String originalUrl =
                urlService.getOriginalUrl(shortCode);

        return new UrlResponse(originalUrl);
    }

    @GetMapping("/{shortCode}/stats")
    public UrlStatsResponse getUrlStats(
            @PathVariable String shortCode) {

        return urlService.getUrlStats(shortCode);
    }

    @GetMapping("/{shortCode}/redirect")
    public ResponseEntity<Void> redirectToOriginalUrl(
            @PathVariable String shortCode) {

        UrlMapping mapping =
                urlService.getUrlMappingForRedirect(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(mapping.getOriginalUrl()))
                .build();
    }

}