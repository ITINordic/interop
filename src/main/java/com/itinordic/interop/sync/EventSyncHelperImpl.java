package com.itinordic.interop.sync;

import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.service.DiagnosisFormService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
@Transactional
public class EventSyncHelperImpl implements EventSyncHelper{
    
    
    @Autowired
    private DiagnosisFormService diagnosisFormService;
    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    
    
    @Transactional
    @Override
    public void saveBatchOfEvents(List<Event> events) {
        for (Event event : events) {
            DiagnosisForm diagnosisForm = diagnosisFormService.transform(event).orElse(null);
            if (diagnosisForm != null) {
                diagnosisFormRepository.saveAndFlush(diagnosisForm);
            }

        }
    }
    
}
