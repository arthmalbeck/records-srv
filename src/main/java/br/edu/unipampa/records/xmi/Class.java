package br.edu.unipampa.records.xmi;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;


@XStreamAlias("packagedElement")
public class Class extends Element{
    @XStreamAlias("xmi:id")
    @XStreamAsAttribute()
    private String id;

    @XStreamImplicit(itemFieldName = "ownedMember")
    private ArrayList<Relation> relacoes;
    
    @XStreamImplicit(itemFieldName = "generalization")
    private ArrayList<Generalization> herancas;

    public Class(String name){
        super();
        this.name = name;
        this.id = name;
        this.type = "uml:Class";
        this.relacoes = new ArrayList<Relation>();
        this.herancas = new ArrayList<Generalization>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Relation> getRelacoes() {
        return relacoes;
    }

    public void setRelacoes(ArrayList<Relation> relacoes) {
        this.relacoes = relacoes;
    }
    
    public ArrayList<Generalization> getHerancas() {
        return herancas;
    }

    public void setHerancas(ArrayList<Generalization> herancas) {
        this.herancas = herancas;
    }
}
