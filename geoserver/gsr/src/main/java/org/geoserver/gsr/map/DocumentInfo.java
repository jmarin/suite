package org.geoserver.gsr.map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

@XStreamAlias(value = "")
public class DocumentInfo {

    private String title;

    private String author;

    private String comments;

    private String Subject;

    private String Category;

    private String keywords;

    private String credits;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

}
