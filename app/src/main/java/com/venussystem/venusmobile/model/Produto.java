package com.venussystem.venusmobile.model;

/**
 * Produto como a tela precisa dele: junta o que a API separa em
 * ProductResponse (nome), BrandResponse (marca) e ProductScoreResponse
 * (overallScore).
 *
 * Os nomes dos campos seguem os da API de proposito — quando os endpoints
 * existirem, o Retrofit encaixa sem renomear nada.
 */
public class Produto {

    private final Long id;
    private final String name;
    private final String brandName;
    private final Integer overallScore;
    private final String imageUrl;

    public Produto(Long id, String name, String brandName, Integer overallScore, String imageUrl) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.overallScore = overallScore;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrandName() {
        return brandName;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
