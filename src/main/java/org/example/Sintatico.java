package org.example;

import org.example.utils.enumFile.TokenEnum;

public class Sintatico {
    private Lexico lexico;
    private Token token;
    private int linha;
    private int coluna;

    public void lerToken() {
        token = lexico.getToken(linha, coluna);
        coluna = token.getColuna() + token.getTamanhoDoToken();
        linha = token.getLinha();
        System.out.println(token);
    }

    public Sintatico(String nomeDoArquivo) {
        linha = 1;
        coluna = 1;
        this.lexico = new Lexico(nomeDoArquivo);
    }

    public void analisar() {
        lerToken();
        programa();
    }

    public void programa() {
        if (validaPrograma("program")) {
            lerToken();
            if (token.getClasse() == TokenEnum.cId) {
                lerToken();
                corpo();
                if (token.getClasse() == TokenEnum.cPonto) {
                    lerToken();
                } else {
                    mostraError("Faltou encerrar com ponto - ");
                }
            } else {
                mostraError(" Faltou identificar o nome do programa - ");
            }
        } else {
            mostraError(" Faltou começar com programa - ");
        }
    }

    public void corpo() {
        declara();
        if (validaPrograma("begin")) {
            lerToken();
            sentencas();
            if (validaPrograma("end")) {
                lerToken();
            } else {
                mostraError(" Faltou finalizar com end - ");
            }
        } else {
            mostraError(" Faltou começar com begin - ");
        }
    }

    public void declara() {
        if (validaPrograma("var")) {
            lerToken();
            dvar();
            maisDc();
        }
    }

    public void maisDc() {
        if (token.getClasse() == TokenEnum.cPontoVirgula) {
            lerToken();
            contDc();
        } else {
            mostraError(" Faltou colocar o ponto e virgula - ");
        }
    }

    public void contDc() {
        if (token.getClasse() == TokenEnum.cId) {
            dvar();
            maisDc();
        }
    }

    public void dvar() {
        variaveis();
        if (token.getClasse() == TokenEnum.cDoisPontos) {
            lerToken();
            tipo_var();
        } else {
            mostraError(" Faltou colocar o dois pontos - ");
        }
    }

    public void tipo_var() {
        if (validaPrograma("integer")) {
            lerToken();
        } else if (validaPrograma("real")) {
            lerToken();
        } else {
            mostraError(" Faltou a declaração do integer - ");
        }
    }

    public void variaveis() {
        if (token.getClasse() == TokenEnum.cId) {
            lerToken();
            mais_var();
        } else {
            mostraError(" Faltou identificador - ");
        }
    }

    public void mais_var() {
        if (token.getClasse() == TokenEnum.cVirgula) {
            lerToken();
            variaveis();
        }
    }

    public void sentencas() {
        comando();
        mais_sentencas();
    }

    public void mais_sentencas() {
        if (token.getClasse() == TokenEnum.cPontoVirgula) {
            lerToken();
            cont_sentencas();
        } else {
            mostraError(" Faltou o ponto e virgula - ");
        }
    }

    public void cont_sentencas() {
        if (validaPrograma("read") ||
                validaPrograma("write") ||
                validaPrograma("for") ||
                validaPrograma("repeat") ||
                validaPrograma("while") ||
                validaPrograma("if") ||
                ((token.getClasse() == TokenEnum.cId))) {
            sentencas();
        }
    }

    public void var_read() {
        if (token.getClasse() == TokenEnum.cId) {
            lerToken();
            mais_var_read();
        } else {
            mostraError(" Faltou o identificador - ");
        }
    }

    public void mais_var_read() {
        if (token.getClasse() == TokenEnum.cVirgula) {
            lerToken();
            var_read();
        }
    }

    public void var_write() {
        if (token.getClasse() == TokenEnum.cId) {
            lerToken();
            mais_var_write();
        } else {
            mostraError(" Faltou o identificador - ");
        }
    }

    public void mais_var_write() {
        if (token.getClasse() == TokenEnum.cVirgula) {
            lerToken();
            var_write();
        }
    }

    public void comando() {
        if (validaPrograma("read")) {
            lerToken();
            if (token.getClasse() == TokenEnum.cParEsq) {
                lerToken();
                var_read();
                if (token.getClasse() == TokenEnum.cParDir) {
                    lerToken();
                } else {
                    mostraError(" Faltou o parentese direito - ");
                }
            } else {
                mostraError(" Faltou o parentese esquerdo - ");
            }
        } else if (validaPrograma("write")) {
            lerToken();
            if (token.getClasse() == TokenEnum.cParEsq) {
                lerToken();
                var_write();
                if (token.getClasse() == TokenEnum.cParDir) {
                    lerToken();
                } else {
                    mostraError(" Faltou o parentese direito - ");
                }
            } else {
                mostraError(" Faltou o parentese esquerdo - ");
            }
        } else

        if (validaPrograma("for")) {
            lerToken();
            if (token.getClasse() == TokenEnum.cId) {
                lerToken();

                if (token.getClasse() == TokenEnum.cAtribuicao) {
                    lerToken();
                    expressao();
                    if (validaPrograma("to")) {
                        lerToken();
                        expressao();
                        if (validaPrograma("do")) {
                            lerToken();
                            if (validaPrograma("begin")) {
                                lerToken();
                                sentencas();
                                if (validaPrograma("end")) {
                                    lerToken();
                                } else {
                                    mostraError(" Faltou o end no for - ");
                                }
                            } else {
                                mostraError(" Faltou o begin no for - ");
                            }
                        } else {
                            mostraError(" Faltou o do no for -");
                        }
                    } else {
                        mostraError(" Faltou o to no for - ");
                    }
                } else {
                    mostraError(" Faltou o dois pontos e igual no for - ");
                }
            } else {
                mostraError(" Faltou o identificador no inicio do for - ");
            }
        } else

        if (validaPrograma("repeat")) {
            lerToken();
            sentencas();
            if (validaPrograma("until")) {
                lerToken();
                if (token.getClasse() == TokenEnum.cParEsq) {
                    lerToken();
                    condicao();
                    if (token.getClasse() == TokenEnum.cParDir) {
                        lerToken();
                    } else {
                        mostraError(" Faltou fechar o parentese no repeat - ");
                    }
                } else {
                    mostraError(" Faltou abrir parentese no repeat - ");
                }
            } else {
                mostraError(" Faltou until no repeat - ");
            }
        }

        else if (validaPrograma("while")) {
            lerToken();
            if (token.getClasse() == TokenEnum.cParEsq) {
                lerToken();
                condicao();
                if (token.getClasse() == TokenEnum.cParDir) {
                    lerToken();
                    if (validaPrograma("do")) {
                        lerToken();
                        if (validaPrograma("begin")) {
                            lerToken();
                            sentencas();
                            if (validaPrograma("end")) {
                                lerToken();
                            } else {
                                mostraError(" Faltou end no while - ");
                            }
                        } else {
                            mostraError(" Faltou begin no while - ");
                        }
                    } else {
                        mostraError(" Faltou do no while - ");
                    }
                } else {
                    mostraError(" Faltou o parentese direito no while - ");
                }
            } else {
                mostraError(" Faltou o parentese esquerdo no while - ");
            }
        } else if (validaPrograma("if")) {
            lerToken();
            if (token.getClasse() == TokenEnum.cParEsq) {
                lerToken();
                condicao();
                if (token.getClasse() == TokenEnum.cParDir) {
                    lerToken();
                    if (validaPrograma("then")) {
                        lerToken();
                        if (validaPrograma("begin")) {
                            lerToken();
                            sentencas();
                            if (validaPrograma("end")) {
                                lerToken();
                                pfalsa();
                            } else {
                                mostraError(" Faltou end no if - ");
                            }
                        } else {
                            mostraError(" Faltou begin no if - ");
                        }
                    } else {
                        mostraError(" Faltou o then no if - ");
                    }
                } else {
                    mostraError(" Faltou o parentese direito no if - ");
                }
            } else {
                mostraError(" Faltou o parentese esquerdo no if - ");
            }
        } else if (token.getClasse() == TokenEnum.cId) {
            lerToken();
            if (token.getClasse() == TokenEnum.cAtribuicao) {
                lerToken();
                expressao();
            } else {
                mostraError(" Faltou atribuição - ");
            }
        }
    }

    public void condicao() {
        expressao();
        relacao();
        expressao();
    }

    public void pfalsa() {
        if (validaPrograma("else")) {
            lerToken();
            if (validaPrograma("begin")) {
                lerToken();
                sentencas();
                if (validaPrograma("end")) {
                    lerToken();
                } else {
                    mostraError(" Faltou finalizar com end - ");
                }
            } else {
                mostraError(" Faltou inicia com begin - ");
            }
        }
    }

    public void relacao() {
        if (validaTipos(1, TokenEnum.cIgual, TokenEnum.cIgual)) {
            lerToken();
        } else if (validaTipos(1, TokenEnum.cMaior, TokenEnum.cMaior)) {
            lerToken();
        } else if (validaTipos(1, TokenEnum.cMenor, TokenEnum.cMenor)) {
            lerToken();
        } else if (validaTipos(1, TokenEnum.cMaiorIgual, TokenEnum.cMaiorIgual)) {
            lerToken();
        } else if (validaTipos(1, TokenEnum.cMenorIgual, TokenEnum.cMenorIgual)) {
            lerToken();
        } else if (validaTipos(1, TokenEnum.cDiferente, TokenEnum.cDiferente)) {
            lerToken();
        } else {
            mostraError("Faltou o operador de relação - ");
        }
    }

    public void expressao() {
        termo();
        outros_termos();
    }

    public void outros_termos() {
        if (validaTipos(2, TokenEnum.cMais, TokenEnum.cMenos)) {
            op_ad();
            termo();
            outros_termos();
        }
    }

    public void op_ad() {
        if (validaTipos(2, TokenEnum.cMais, TokenEnum.cMenos)) {
            lerToken();
        } else {
            mostraError("Faltou colocar o operador de adição ou subtração - ");
        }
    }

    public void termo() {
        fator();
        mais_fatores();
    }

    public void mais_fatores() {
        if (validaTipos(2, TokenEnum.cMultiplicacao, TokenEnum.cDivisao)) {
            op_mul();
            fator();
            mais_fatores();
        }
    }

    public void op_mul() {
        if (validaTipos(2, TokenEnum.cMultiplicacao, TokenEnum.cDivisao)) {
            lerToken();
        } else {
            mostraError("Faltou colocar o operador de multiplicação ou divisão - ");
        }
    }

    public void fator() {
        if (validaTipos(1, TokenEnum.cId, TokenEnum.cId)) {
            lerToken();
        } else if (validaTipos(2, TokenEnum.cInt, TokenEnum.cReal)) {
            lerToken();
        } else if (validaTipos(1, TokenEnum.cParEsq, TokenEnum.cParEsq)) {
            lerToken();
            expressao();
            if (validaTipos(1, TokenEnum.cParDir, TokenEnum.cParDir)) {
                lerToken();
            } else {
                mostraError("Faltou o parentese direito - ");
            }
        } else {
            mostraError("Faltou fator in num exp - ");
        }
    }

    public Boolean validaTipos(int numeroDeValidacoes, TokenEnum token1, TokenEnum token2) {
        if (numeroDeValidacoes == 1) {
            return token.getClasse() == token1;
        }

        return token.getClasse() == token1 || token.getClasse() == token2;
    }

    public Boolean validaPrograma(String valor) {
        return (token.getClasse() == TokenEnum.cPalRes)
                && (token.getValor().getValorIdentificador().equalsIgnoreCase(valor));
    }

    public void mostraError(String mensagem) {
        System.out.println("Error:" + mensagem + " na linha: " + token.getLinha() + " e na coluna: " + token.getColuna() );
    }
}
