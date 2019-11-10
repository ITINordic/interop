package com.itinordic.interop.sync;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.service.DiagnosisFormService;
import com.itinordic.interop.util.GeneralUtility;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class DiagnosisFormSyncServiceImpl implements DiagnosisFormSyncService {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisFormService diagnosisFormService;
    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    //To enable transaction management
    private final DiagnosisFormSyncService diagnosisFormSyncService=this;
    

    @Scheduled(fixedDelay = 60000 * 2, initialDelay = 60000 * 2)
    public void syncDiagnosisFormsWithT9FormElements() {
        logger.info("Diagnosis Forms -> T9 Form Elements synchronization started");
        diagnosisFormSyncService.syncDiagnosisForms();
        logger.info("Diagnosis Forms -> T9 Form Elements synchronization ended");
    }
    
    @Transactional
    @Override
    public synchronized void syncDiagnosisForms(){
         int page = 1;
        Page<DiagnosisForm> diagnosisFormPage;
        DiagnosisFormSearchDto diagnosisFormSearchDto=new DiagnosisFormSearchDto(true);
        do {
            diagnosisFormSearchDto.setPage(String.valueOf(page));
            diagnosisFormPage = diagnosisFormService.findDiagnosisForms(diagnosisFormSearchDto, "random", true, 50);
            for (DiagnosisForm diagnosisForm:diagnosisFormPage.getContent()) {
                List<T9FormElement> t9FormElements = diagnosisFormService.computeMappedT9FormElements(diagnosisForm);
                if (!GeneralUtility.isEmpty(t9FormElements)) {
                    diagnosisForm.setFormElements(t9FormElements);
                    diagnosisFormRepository.save(diagnosisForm);
                }
            }
        } while (++page <=  diagnosisFormPage.getTotalPages());
    }

}
