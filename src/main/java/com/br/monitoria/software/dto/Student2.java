package com.br.monitoria.software.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student2 {
    private int matricula2;
    private String nome2;
    private int perguntasc4;
    private int valPerguntasc4;
    private int desenvolverC4;
    private int valDesenvolverC4;
    private int checklist;
    private int valChecklist;
    private int entrevista;
    private int melhorEntrevista;
    private int criatividade;
    private int rigorArquit;
    private int completude;
    private int corretudeTec;
    private int Forms2;
    private String MeioPonto1;
    private String MeioPonto2;
    private String MeioPonto3;

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