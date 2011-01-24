/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Hoang-PC
 */
public class Author implements Serializable {

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(obj.getClass() == String.class)
        {
            if(author.equalsIgnoreCase(obj.toString()))
                return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if ((this.author == null) ? (other.author != null) : !this.author.equals(other.author)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public String toString() {
        return getAuthor() ;
    }

    public String toHtmlStr() {
        return "<a href=\"showPubsByAuthor.do?authorName=" + getAuthor() + "\">" + getAuthor() + "</a>";
    }

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
