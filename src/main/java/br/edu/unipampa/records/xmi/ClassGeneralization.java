package br.edu.unipampa.records.xmi;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("generalization")
public class ClassGeneralization {

    @XStreamAlias("xmi:id")
    @XStreamAsAttribute()
    private String id;
    
    @XStreamAlias("type")
    @XStreamAsAttribute()
    private String nameClass;


    @XStreamAlias("xmi:type")
    @XStreamAsAttribute()
    private String type;

    @XStreamAlias("specific")
    @XStreamAsAttribute()
    private ClassRelation classeOrigem;

    @XStreamAlias("general")
    @XStreamAsAttribute()
    private ClassRelation classeDestino;

    public ClassGeneralization(){
        this.setType("uml:Property");
    }
    
    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
