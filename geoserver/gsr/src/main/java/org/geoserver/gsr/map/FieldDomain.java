package org.geoserver.gsr.map;

import java.util.List;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public class FieldDomain {

    public FieldDomain(String type, String name, List<CodedValue> codedValues) {
        this.type = type;
        this.name = name;
        this.codedValues = codedValues;
    }

    private String type;

    private String name;

    private List<CodedValue> codedValues;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CodedValue> getCodedValues() {
        return codedValues;
    }

    public void setCodedValues(List<CodedValue> codedValues) {
        this.codedValues = codedValues;
    }

}
