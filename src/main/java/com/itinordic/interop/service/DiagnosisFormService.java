package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.entity.T9FormElement;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.springframework.data.domain.Page;

/**
 *
 * @author Charles Chigoriwa
 */
public interface DiagnosisFormService {    
    
    public static final String OUTCOME_DATA_ELEMENT_ID = "emFE351TuNs";
    public static final String AGE_DATA_ELEMENT_ID = "bl1Dflv1nag";
    public static final String DIAGNOSIS_DATA_ELEMENT_ID = "PvciLByskeE";

    public List<T9FormElement> computeMappedT9FormElements(DiagnosisForm diagnosisForm);

    @Nonnull
    public Optional<DiagnosisForm> transform(Event event);

    public Page<DiagnosisForm> findDiagnosisForms(DiagnosisFormSearchDto diagnosisFormSearchDto, String orderField, boolean desc, Integer pageSize);
}
