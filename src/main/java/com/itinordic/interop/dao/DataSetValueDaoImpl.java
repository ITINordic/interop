package com.itinordic.interop.dao;

import com.itinordic.interop.util.DataSetValueElement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Charles Chigoriwa
 */
@Repository
public class DataSetValueDaoImpl implements DataSetValueDao{
    
    
     @PersistenceContext
    private EntityManager entityManager;
    
    
     @Override
    public List<DataSetValueElement> findDataSetValueElements() {      
        return entityManager.createQuery("select new com.itinordic.interop.util.DataSetValueElement(count(df),df.eventPeriod,fe,df.t9OrgUnit) from DiagnosisForm df join df.formElements fe group by df.eventPeriod,df.t9OrgUnit,fe")
                    .getResultList();
    }
    
}
