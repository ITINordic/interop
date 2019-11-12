package com.itinordic.interop.sync;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.service.DiagnosisFormService;
import com.itinordic.interop.transform.EventToDiagnosisFormTransformer;
import com.itinordic.interop.util.GeneralUtility;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Transactional
@Service
public class DiagnosisFormSyncHelperImpl implements DiagnosisFormSyncHelper{
    
    
    @Autowired
    private DiagnosisFormService diagnosisFormService;
    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    @Autowired
    private EventToDiagnosisFormTransformer eventToDiagnosisFormTransformer;
    
    
    @Transactional
    @Override
    public int syncPage(DiagnosisFormSearchDto diagnosisFormSearchDto) {
        Page<DiagnosisForm> diagnosisFormPage = diagnosisFormService.findDiagnosisForms(diagnosisFormSearchDto, "random", true, 50);
        for (DiagnosisForm diagnosisForm : diagnosisFormPage.getContent()) {
            List<T9FormElement> t9FormElements = eventToDiagnosisFormTransformer.computeMappedT9FormElements(diagnosisForm);
            if (!GeneralUtility.isEmpty(t9FormElements)) {
                diagnosisForm.setFormElements(t9FormElements);
                diagnosisFormRepository.saveAndFlush(diagnosisForm);
            }
        }
        return diagnosisFormPage.getTotalPages();
    }
    
}
