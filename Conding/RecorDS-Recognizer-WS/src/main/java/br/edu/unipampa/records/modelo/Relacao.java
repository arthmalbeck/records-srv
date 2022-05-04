package br.edu.unipampa.records.modelo;


public class Relacao extends Elemento{
    private Classe classeOrigem;
    private Classe classeDestino;
    private Multiplicidade multiplicidadeOrigem;
    private Multiplicidade multiplicidadeDestino;
    private RelationTypes type;

    public Classe getClasseOrigem() {
        return classeOrigem;
    }

    public void setClasseOrigem(Classe classeOrigem) {
        this.classeOrigem = classeOrigem;
    }

    public Classe getClasseDestino() {
        return classeDestino;
    }

    public void setClasseDestino(Classe classeDestino) {
        this.classeDestino = classeDestino;
    }

    public Multiplicidade getMultiplicidadeOrigem() {
        return multiplicidadeOrigem;
    }

    public void setMultiplicidadeOrigem(Multiplicidade multiplicidadeOrigem) {
        this.multiplicidadeOrigem = multiplicidadeOrigem;
    }

    public Multiplicidade getMultiplicidadeDestino() {
        return multiplicidadeDestino;
    }

    public void setMultiplicidadeDestino(Multiplicidade multiplicidadeDestino) {
        this.multiplicidadeDestino = multiplicidadeDestino;
    }
    
    public RelationTypes getType() {
        return type;
    }

    public void setType(RelationTypes type) {
        this.type = type;
    }
}
