package com.br.monitoria.software.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private String matricula;
    private String nome;
    private int atv1;
    private int bad1;
    private int atv2;
    private int bad2;
    private int atv3;
    private int bad3;
    private int atv4;
    private int bad4;
    private int atv5;
    private int bad5;
    private int perfil;
    private String perfis;
    private int quizz;
    private int learn;
    private int forms;
    private int otimosExemplos;
    private int bonsRecursos;
    private int cumprirOTempo;
    private int baseTeorica;
    private int trabalhoEmEquipe;


    public String getFormattedNome() {
        if (nome == null || nome.isEmpty()) {
            return "";
        }
        String[] nomes = nome.split(" ");
        if (nomes.length < 2) {
            return capitalize(nomes[0]);
        }
        return capitalize(nomes[0]) + " " + capitalize(nomes[1]);
    }

    public String getFormattedPerfis() {
        if (perfis == null || perfis.isEmpty()) {
            return "";
        }
        String[] perfisArray = perfis.split(", ");
        StringBuilder formattedPerfis = new StringBuilder();
        for (int i = 0; i < Math.min(perfisArray.length, 1); i++) {
            if (i > 0) {
                formattedPerfis.append(", ");
            }
            formattedPerfis.append(capitalize(perfisArray[i]));
        }
        return formattedPerfis.toString();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
