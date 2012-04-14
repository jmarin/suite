package org.geoserver.gsr.map;

public enum HtmlPopupType {

    NONE("ServerHTMLPopupTypeNone"), URL("ServerHTMLPopupTypeAsURL"), HTML_TEXT(
            "ServerHTMLPopupTypeAsHTMLText");
    private String htmlPopupType;

    public String getHtmlPopupType() {
        return htmlPopupType;
    }

    private HtmlPopupType(String type) {
        this.htmlPopupType = type;
    }

}
