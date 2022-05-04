package br.edu.unipampa.records.xmi;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;


@XStreamAlias("packagedElement")
public class Element {

    @XStreamAlias("name")
    @XStreamAsAttribute()
    protected String name;

    @XStreamAlias("xmi:type")
    @XStreamAsAttribute()
    protected String type;

    @XStreamImplicit(itemFieldName = "packagedElement")
    protected ArrayList<Element> elements;

    public Element() {
        elements = new ArrayList();
    }

    public Element(String type, String name) {
        setName(name);
        setType(type);
        elements = new ArrayList();
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
        name = name.replaceAll("\r", "");
        name = name.replaceAll("\t", "");
        name = name.replaceAll("\n", "");
        name = name.replaceAll(" ", "");
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

    /**
     * @return the elements
     */
    public ArrayList<Element> getElements() {
        return elements;
    }

    /**
     * @param elements the elements to set
     */
    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }
}
