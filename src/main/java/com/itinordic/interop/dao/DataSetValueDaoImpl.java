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
public class DataSetValueDaoImpl implements DataSetValueDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DataSetValueElement> findDataSetValueElements() {
        return entityManager.createQuery("select new com.itinordic.interop.util.DataSetValueElement(count(df),df.eventPeriod,fe,df.t9OrgUnit) from DiagnosisForm df join df.formElements fe group by df.eventPeriod,df.t9OrgUnit,fe")
                .getResultList();
    }

    @Override
    public List<DataSetValueElement> findDataSetValueElements(String eventPeriod) {
        return entityManager.createQuery("select new com.itinordic.interop.util.DataSetValueElement(count(df),df.eventPeriod,fe,df.t9OrgUnit) from DiagnosisForm df join df.formElements fe where df.eventPeriod=:eventPeriod  group by df.eventPeriod,df.t9OrgUnit,fe")
                .setParameter("eventPeriod", eventPeriod)
                .getResultList();
    }

    @Override
    public List<DataSetValueElement> findDataSetValueElements(int pageNumber, int perPage) {
        int start = (pageNumber - 1) * perPage;
        start = start < 0 ? 0 : start;
        return entityManager.createQuery("select new com.itinordic.interop.util.DataSetValueElement(count(df),df.eventPeriod,fe,df.t9OrgUnit) from DiagnosisForm df join df.formElements fe group by df.eventPeriod,df.t9OrgUnit,fe")
                .setFirstResult(start)
                .setMaxResults(perPage)
                .getResultList();
    }

    @Override
    public List<DataSetValueElement> findDataSetValueElements(String eventPeriod,int pageNumber, int perPage) {
        int start = (pageNumber - 1) * perPage;
        start = start < 0 ? 0 : start;        
        return entityManager.createQuery("select new com.itinordic.interop.util.DataSetValueElement(count(df),df.eventPeriod,fe,df.t9OrgUnit) from DiagnosisForm df join df.formElements fe where df.eventPeriod=:eventPeriod  group by df.eventPeriod,df.t9OrgUnit,fe")
                .setParameter("eventPeriod", eventPeriod)
                .setFirstResult(start)
                .setMaxResults(perPage)
                .getResultList();
    }

    @Override
    public Long findDataSetValueElementCount() {
        return (Long) entityManager.createQuery("select count(fe) from DiagnosisForm df join df.formElements fe")
                .getSingleResult();
    }

    @Override
    public Long findDataSetValueElementCount(String eventPeriod) {
        return (Long) entityManager.createQuery("select count(fe) from DiagnosisForm df join df.formElements fe where df.eventPeriod=:eventPeriod")
                .setParameter("eventPeriod", eventPeriod)
                .getSingleResult();
    }

}
