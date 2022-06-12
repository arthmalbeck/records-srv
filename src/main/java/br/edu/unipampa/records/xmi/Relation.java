package br.edu.unipampa.records.xmi;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("ownedMember")
public class Relation {
    @XStreamAlias("xmi:type")
    @XStreamAsAttribute()
    private String type;

    @XStreamAlias("ownedEnd")
    @XStreamAsAttribute()
    private ClassRelation classeOrigem;

    @XStreamAlias("ownedEnd")
    @XStreamAsAttribute()
    private ClassRelation classeDestino;

    public Relation(){
        this.type = "uml:Association";
    }

    public ClassRelation getClasseOrigem() {
        return classeOrigem;
    }

    public void setClasseOrigem(ClassRelation classeOrigem) {
        this.classeOrigem = classeOrigem;
    }

    public ClassRelation getClasseDestino() {
        return classeDestino;
    }

    public void setClasseDestino(ClassRelation classeDestino) {
        this.classeDestino = classeDestino;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
