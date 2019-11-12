package com.itinordic.interop.transform;

import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.entity.T9FormElement;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Charles Chigoriwa
 */
public interface EventToDiagnosisFormTransformer extends Transformer{
        
    public static final String OUTCOME_DATA_ELEMENT_ID = "emFE351TuNs";
    public static final String AGE_DATA_ELEMENT_ID = "bl1Dflv1nag";
    public static final String DIAGNOSIS_DATA_ELEMENT_ID = "PvciLByskeE";
    
    public Optional<DiagnosisForm> transform(Event event);
    
    public List<T9FormElement> computeMappedT9FormElements(DiagnosisForm diagnosisForm);
    
}
