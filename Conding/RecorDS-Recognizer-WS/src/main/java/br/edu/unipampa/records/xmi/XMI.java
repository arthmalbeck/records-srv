package br.edu.unipampa.records.xmi;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("xmi:XMI")
public class XMI {

    @XStreamAlias("xmi:version")
    @XStreamAsAttribute()
    private String version;

    @XStreamAlias("xmlns:uml")
    @XStreamAsAttribute()
    private final String schemaXML = "http://schema.omg.org/spec/UML/2.0";

    @XStreamAlias("xmlns:xmi")
    @XStreamAsAttribute()
    private final String schemaXMI = "http://schema.omg.org/spec/XMI/2.1";

    @XStreamAlias("uml:Model")
    @XStreamAsAttribute()
    private Model model;

    public XMI() {
        this.model = new Model();
    }

    /**
     * @return the content
     */
    public Model getModel() {
        return model;
    }

    /**
     * @param content the content to set
     */
    public void setModel(Model content) {
        this.model = content;
    }


    public String gerarXMI() {
        XStream xtream = new XStream();
        xtream.autodetectAnnotations(true);
        return xtream.toXML(this);
    }
}
