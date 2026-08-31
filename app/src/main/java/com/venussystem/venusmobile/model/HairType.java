package com.venussystem.venusmobile.model;

/**
 * Espelha o enum HairType da API.
 *
 * A tela oferece 7 tipos (1, 2A, 2B, 2C, 3A, 3B, 3C) porque e assim que o
 * usuario identifica o proprio cabelo. A API trabalha com 5 categorias
 * amplas. A traducao acontece aqui, num lugar so.
 */
public enum HairType {

    STRAIGHT,
    WAVY,
    CURLY,
    COILY,
    OTHER;

    /** Converte a opcao escolhida na tela para o valor que a API espera. */
    public static HairType daOpcao(String opcao) {
        if (opcao == null) {
            return OTHER;
        }
        switch (opcao.toUpperCase()) {
            case "1":
                return STRAIGHT;
            case "2A":
            case "2B":
            case "2C":
                return WAVY;
            case "3A":
            case "3B":
            case "3C":
                return CURLY;
            case "4A":
            case "4B":
            case "4C":
                return COILY;
            default:
                return OTHER;
        }
    }
}
