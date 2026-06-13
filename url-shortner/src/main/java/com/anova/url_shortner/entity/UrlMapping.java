package com.anova.url_shortner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String shortCode;

    @Column(nullable = false, unique = true, length = 2000)
    private String originalUrl;

    @Column(nullable = false)
    private Long clickCount= 0L;
    private LocalDateTime createdAt = LocalDateTime.now();
}
