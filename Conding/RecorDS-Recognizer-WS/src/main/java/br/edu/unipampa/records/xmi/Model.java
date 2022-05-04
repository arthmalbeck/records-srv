package br.edu.unipampa.records.xmi;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("uml:Model")
public class Model {

    @XStreamAlias("name")
    @XStreamAsAttribute()
    private String name = "diagrama";

    @XStreamAlias("xmi:type")
    @XStreamAsAttribute()
    private String type = "uml:Model";

    @XStreamAlias("packagedElement")
    @XStreamAsAttribute()
    private Element element;

    public Model() {
        this.element = new Element();
        this.element.setType("uml:Model");
        this.element.setName("diagrama");
    }

    /**
     * @return the element
     */
    public Element getElement() {
        return element;
    }

    /**
     * @param element the element to set
     */
    public void setElement(Element element) {
        this.element = element;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
