package com.itinordic.interop.entity;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 *
 * @author developer
 */
@MappedSuperclass
public abstract class BaseEntity {
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    protected Date creationDateTime = new Date();
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date modificationDateTime = new Date();  
    @Column(nullable = false, updatable = false)
    protected String uuid = UUID.randomUUID().toString();
    protected boolean deleted;
    @Column(updatable = false)
    private Integer random=new Random().nextInt();

    public Date getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Date getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(Date modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getRandom() {
        return random;
    }

    public void setRandom(Integer random) {
        this.random = random;
    }
    
    

    
}
