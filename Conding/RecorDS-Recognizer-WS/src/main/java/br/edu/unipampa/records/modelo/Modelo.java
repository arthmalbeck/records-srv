package br.edu.unipampa.records.modelo;

import java.util.ArrayList;


public class Modelo {
    private ArrayList<Classe> classes;
    private ArrayList<Relacao> relacoes;
    private ArrayList<Heranca> herancas;
    private ArrayList<Multiplicidade> multiplicidades;

    public ArrayList<Classe> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Classe> classes) {
        this.classes = classes;
    }

    public ArrayList<Relacao> getRelacoes() {
        return relacoes;
    }

    public void setRelacoes(ArrayList<Relacao> relacoes) {
        this.relacoes = relacoes;
    }

    public ArrayList<Multiplicidade> getMultiplicidades() {
        return multiplicidades;
    }

    public void setMultiplicidades(ArrayList<Multiplicidade> multiplicidades) {
        this.multiplicidades = multiplicidades;
    }
    
    public ArrayList<Heranca> getHerancas() {
        return herancas;
    }

    public void setHerancas(ArrayList<Heranca> herancas) {
        this.herancas = herancas;
    }
    
}
