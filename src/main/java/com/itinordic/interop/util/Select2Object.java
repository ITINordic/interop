package com.itinordic.interop.util;

/**
 *
 * @author Charles Chigoriwa
 */
public class Select2Object {

    private String id;
    private String text;

    public Select2Object() {
    }

    public Select2Object(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
