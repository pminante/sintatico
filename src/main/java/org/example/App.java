package org.example;

import org.example.utils.formater.SubstituirTabulacao;

public class App {
    public static void main(String[] args) {
        String nmDoArq = "CalcularSalario.pas";
        SubstituirTabulacao.execute(nmDoArq);
        Sintatico sintatico = new Sintatico(nmDoArq);
        sintatico.analisar();

    }



}