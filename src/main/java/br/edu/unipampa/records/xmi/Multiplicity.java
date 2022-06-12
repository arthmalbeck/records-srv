package br.edu.unipampa.records.xmi;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


public class Multiplicity {

    @XStreamAlias("value")
    @XStreamAsAttribute()
    private String valor;

    @XStreamAlias("xmi:type")
    @XStreamAsAttribute()
    private String type;

    @XStreamAlias("xmi:id")
    @XStreamAsAttribute()
    private String id;

    public Multiplicity(String valor, String classeId){
        this.setType("uml:LiteralInteger");
        this.setId(classeId);
        this.setValor(valor);
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
