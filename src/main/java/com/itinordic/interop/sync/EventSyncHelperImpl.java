package com.itinordic.interop.sync;

import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.transform.EventToDiagnosisFormTransformer;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */

@Transactional
@Service
public class EventSyncHelperImpl implements EventSyncHelper{
    
    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    @Autowired
    private EventToDiagnosisFormTransformer eventToDiagnosisFormTransformer;
    
    
    @Transactional
    @Override
    public void saveBatchOfEvents(List<Event> events) {
        for (Event event : events) {
            DiagnosisForm diagnosisForm = eventToDiagnosisFormTransformer.transform(event).orElse(null);
            if (diagnosisForm != null) {
                diagnosisFormRepository.saveAndFlush(diagnosisForm);
            }
        }
    }
    
}
