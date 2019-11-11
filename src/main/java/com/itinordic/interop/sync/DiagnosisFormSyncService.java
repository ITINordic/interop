package com.itinordic.interop.sync;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;

/**
 *
 * @author Charles Chigoriwa
 */
public interface DiagnosisFormSyncService {
    
    public void syncDiagnosisForms();
    
  public int savePage(DiagnosisFormSearchDto diagnosisFormSearchDto);
    
}
