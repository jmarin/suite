package org.geoserver.gsr.map;


/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */
public class Field {

    private String name;

    public Field(String name, String type, int length, boolean editable, FieldDomain domain) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.editable = editable;
        this.domain = domain;
    }

    private String type;

    private int length;

    private boolean editable;

    private FieldDomain domain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public FieldDomain getDomain() {
        return domain;
    }

    public void setDomain(FieldDomain domain) {
        this.domain = domain;
    }

}
