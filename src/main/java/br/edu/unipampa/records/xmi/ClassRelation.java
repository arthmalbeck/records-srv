package br.edu.unipampa.records.xmi;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("ownedEnd")
public class ClassRelation {

    @XStreamAlias("type")
    @XStreamAsAttribute()
    private String nameClass;

    @XStreamAlias("xmi:type")
    @XStreamAsAttribute()
    private String type;

    @XStreamAlias("lowerValue")
    @XStreamAsAttribute()
    private Multiplicity multiplicityLower;

    @XStreamAlias("upperValue")
    @XStreamAsAttribute()
    private Multiplicity multiplicityUpper;

    public ClassRelation(){
        this.setType("uml:Property");
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Multiplicity getMultiplicityLower() {
        return multiplicityLower;
    }

    public void setMultiplicityLower(Multiplicity multiplicityLower) {
        this.multiplicityLower = multiplicityLower;
    }

    public Multiplicity getMultiplicityUpper() {
        return multiplicityUpper;
    }

    public void setMultiplicityUpper(Multiplicity multiplicityUpper) {
        this.multiplicityUpper = multiplicityUpper;
    }
}
