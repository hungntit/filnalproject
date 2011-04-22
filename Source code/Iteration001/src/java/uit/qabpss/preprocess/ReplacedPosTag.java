/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.preprocess;

/**
 *
 * @author ThuanHung
 */
public class ReplacedPosTag {

    protected String afterPostag;
    protected String value;
    protected String posTag;

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the value of afterPostag
     *
     * @return the value of afterPostag
     */
    public String getAfterPostag() {
        return afterPostag;
    }

    /**
     * Set the value of afterPostag
     *
     * @param afterPostag new value of afterPostag
     */
    public void setAfterPostag(String afterPostag) {
        this.afterPostag = afterPostag;
    }

    /**
     * Get the value of posTag
     *
     * @return the value of posTag
     */
    public String getPosTag() {
        return posTag;
    }

    /**
     * Set the value of posTag
     *
     * @param posTag new value of posTag
     */
    public void setPosTag(String posTag) {
        this.posTag = posTag;
    }

    public ReplacedPosTag(String posTag, String afterPostag) {
        this.afterPostag = afterPostag;
        this.posTag = posTag;
        this.value = "";
    }

    public ReplacedPosTag(String posTag) {
        this.posTag = posTag;
        this.afterPostag = "";
        this.value = "";
    }
}
