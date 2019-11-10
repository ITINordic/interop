package com.itinordic.interop.entity;

import com.itinordic.interop.dao.UuidIdentifiable;
import com.itinordic.interop.util.GeneralUtility;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Charles Chigoriwa
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable, UuidIdentifiable{
    
    
    @GeneratedValue( generator = "custom-uuid2" )
    @GenericGenerator( name = "custom-uuid2", strategy = "com.itinordic.interop.dao.CustomUuidGenerator" )
    @Id
    protected UUID id;    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    protected Date creationDateTime = new Date();
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date modificationDateTime = new Date();  
    @Column(nullable = false, updatable = false)
    protected String uuid = UUID.randomUUID().toString();
    protected boolean deleted;
    @Column(updatable = false)
    protected Integer random=new Random().nextInt();
    protected String creatorUserName;
    protected String updatorUserName;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
    
    

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

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public String getUpdatorUserName() {
        return updatorUserName;
    }

    public void setUpdatorUserName(String updatorUserName) {
        this.updatorUserName = updatorUserName;
    }
    
    public boolean hasUpdatorUserName(){
        return !GeneralUtility.isEmpty(updatorUserName);
    }
    
    public boolean hasCreatorUserName(){
        return !GeneralUtility.isEmpty(creatorUserName);
    }
    
    

    
}
