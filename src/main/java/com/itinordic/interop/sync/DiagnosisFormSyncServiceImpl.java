package com.itinordic.interop.sync;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DiagnosisFormSyncHelper diagnosisFormSyncHelper;

    @Scheduled(fixedDelay = 60000 * 2, initialDelay = 60000 * 2)
    public void syncDiagnosisFormsWithT9FormElements() {
        logger.info("Diagnosis Forms -> T9 Form Elements synchronization started");
        syncDiagnosisForms();
        logger.info("Diagnosis Forms -> T9 Form Elements synchronization ended");
    }

    @Override
    public synchronized void syncDiagnosisForms() {
        int page = 1;
        int pageCount;
        DiagnosisFormSearchDto diagnosisFormSearchDto = new DiagnosisFormSearchDto(true);
        do {
            diagnosisFormSearchDto.setPage(String.valueOf(page));
            pageCount =diagnosisFormSyncHelper.syncPage(diagnosisFormSearchDto);
        } while (++page <= pageCount);
    }

   

}
