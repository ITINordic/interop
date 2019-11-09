package com.itinordic.interop.sync;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.service.DiagnosisFormService;
import com.itinordic.interop.util.GeneralUtility;
import java.util.List;
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
    
    

    @Autowired
    private DiagnosisFormService diagnosisFormService;
    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    

    @Scheduled(fixedDelay = 60000 * 2, initialDelay = 60000 * 2)
    public void syncDiagnosisFormsWithT9FormElements() {
        int page = 1;
        Page<DiagnosisForm> diagnosisFormPage;
        DiagnosisFormSearchDto diagnosisFormSearchDto=new DiagnosisFormSearchDto(true);
        do {
            diagnosisFormSearchDto.setPage(String.valueOf(page++));
            diagnosisFormPage = diagnosisFormService.findDiagnosisForms(diagnosisFormSearchDto, "random", true, 50);
            for (DiagnosisForm diagnosisForm:diagnosisFormPage.getContent()) {
                List<T9FormElement> t9FormElements = diagnosisFormService.computeMappedT9FormElements(diagnosisForm);
                if (!GeneralUtility.isEmpty(t9FormElements)) {
                    diagnosisForm.setFormElements(t9FormElements);
                    diagnosisFormRepository.save(diagnosisForm);
                }
            }
        } while (page <=  diagnosisFormPage.getTotalPages());

    }

}
