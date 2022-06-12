package br.edu.unipampa.records.xmi;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


public class Generalization {
     
    @XStreamAlias("xmi:type")
    @XStreamAsAttribute()
    private String type;

    @XStreamAlias("specific")
    @XStreamAsAttribute()
    private String classeOrigem;

    @XStreamAlias("general")
    @XStreamAsAttribute()
    private String classeDestino;

    public Generalization(){
        this.type = "uml:Generalization";
    }

    public String getClasseOrigem() {
        return classeOrigem;
    }

    public void setClasseOrigem(String classeOrigem) {
        this.classeOrigem = classeOrigem;
    }

    public String getClasseDestino() {
        return classeDestino;
    }

    public void setClasseDestino(String classeDestino) {
        this.classeDestino = classeDestino;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}