package com.br.monitoria.software.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student2 {
    private int matricula2;
    private String nome2;
    private int entrevista;
    private int valEntrevista;
    private int col4;
    private int val4;
    private int c4;
    private int valC4;
    private int avaliacaoDeArq;
    private int valAvaliacaoDeArq;
    private int dojo4;
    private int dojoC4;
    private int melhorEntrevista;
    private int criatividade;
    private int rigorArquit;
    private int completude;
    private int corretudeTec;

    public String getFormattedNome() {
        if (nome2 == null || nome2.isEmpty()) {
            return "";
        }
        String[] nomes = nome2.split(" ");
        if (nomes.length < 2) {
            return capitalize(nomes[0]);
        }
        return capitalize(nomes[0]) + " " + capitalize(nomes[1]);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}