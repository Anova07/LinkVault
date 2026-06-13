package com.anova.url_shortner.service;

import com.anova.url_shortner.dto.UrlStatsResponse;
import com.anova.url_shortner.entity.UrlMapping;
import com.anova.url_shortner.exception.ShortCodeNotFoundException;
import com.anova.url_shortner.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String createShortUrl(String originalUrl) {

        Optional<UrlMapping> existingUrl =
                urlRepository.findByOriginalUrl(originalUrl);

        if(existingUrl.isPresent()) {
            return existingUrl.get().getShortCode();
        }

        String shortCode = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        UrlMapping mapping = new UrlMapping();

        mapping.setShortCode(shortCode);
        mapping.setOriginalUrl(originalUrl);

        urlRepository.save(mapping);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {

        UrlMapping mapping = urlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortCodeNotFoundException("Short code not found"));


        System.out.println("Before: " + mapping.getClickCount());

        mapping.setClickCount(mapping.getClickCount() + 1);

        System.out.println("After: " + mapping.getClickCount());

        urlRepository.save(mapping);

        return mapping.getOriginalUrl();
    }

    public UrlStatsResponse getUrlStats(String shortCode) {

        UrlMapping mapping = urlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortCodeNotFoundException("Short code not found"));

        return new UrlStatsResponse(
                mapping.getShortCode(),
                mapping.getOriginalUrl(),
                mapping.getClickCount(),
                mapping.getCreatedAt()
        );
    }

    public UrlMapping getUrlMappingForRedirect(String shortCode) {

        UrlMapping mapping = urlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortCodeNotFoundException("Short code not found"));

        Long currentCount = mapping.getClickCount();

        if(currentCount == null) {
            currentCount = 0L;
        }

        mapping.setClickCount(currentCount + 1);

        urlRepository.save(mapping);

        return mapping;
    }


}