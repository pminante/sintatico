package org.example.utils.formater;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SubstituirTabulacao {
        public static void execute(String input){
        Path caminhoDoArquivo = Paths.get(input);
        int quantidadeDeEspacosPorTabulacao = 4;
        StringBuilder juntaTexto = new StringBuilder();
        String espacos;

        for (int contador = 0; contador < quantidadeDeEspacosPorTabulacao; contador++) {
            juntaTexto.append(" ");
        }

        espacos = juntaTexto.toString();

        String conteudo;

        try {
            conteudo = new String(Files.readAllBytes(caminhoDoArquivo), StandardCharsets.UTF_8);
            conteudo = conteudo.replace("\t", espacos);
            Files.write(caminhoDoArquivo, conteudo.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
