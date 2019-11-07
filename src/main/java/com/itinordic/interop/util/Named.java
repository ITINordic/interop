package com.itinordic.interop.util;

/**
 *
 * @author Charles Chigoriwa
 */
public class Named {

    private String id;
    private String name;
    private String type;
    private Integer number;
    private String imageName;
    private String uuid;
    private String niceId;

    public Named() {
    }

    public Named(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Named(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Named(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNiceId() {
        return niceId;
    }

    public void setNiceId(String niceId) {
        this.niceId = niceId;
    }

  

}
