/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qass.model;

import java.io.Serializable;

/**
 *
 * @author Hoang-PC
 */
public class Author implements Serializable{

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorNum() {
        return authorNum;
    }

    public void setAuthorNum(int authorNum) {
        this.authorNum = authorNum;
    }

    public boolean isEditor() {
        return editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Publication getPublications() {
        return publications;
    }

    public void setPublications(Publication publications) {
        this.publications = publications;
    }
    
    private int id;
    private String author;
    private boolean editor;
    private int authorNum;
    private Publication publications;
}
