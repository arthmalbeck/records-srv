package br.edu.unipampa.records.modelo;

import java.util.ArrayList;


public class Classe extends Elemento{
    private String nome;
    private ArrayList<Relacao> relacao;

    public Classe(){
        relacao = new ArrayList<Relacao>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Relacao> getRelacao() {
        return relacao;
    }

    public void setRelacao(ArrayList<Relacao> relacao) {
        this.relacao = relacao;
    }
}