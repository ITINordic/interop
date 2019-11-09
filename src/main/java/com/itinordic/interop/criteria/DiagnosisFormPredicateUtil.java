package com.itinordic.interop.criteria;

import com.itinordic.interop.entity.QDiagnosisForm;
import com.querydsl.core.types.dsl.BooleanExpression;
//import zw.co.endlini.entity.QCity;

/**
 *
 * @author Charles Chigoriwa
 */
public class DiagnosisFormPredicateUtil {

    public static BooleanExpression getPredicate(DiagnosisFormSearchDto diagnosisFormSearchDto) {
        BooleanExpression exp = null;
        
        if (diagnosisFormSearchDto.getNoT9FormElements() != null && diagnosisFormSearchDto.getNoT9FormElements()) {
            BooleanExpression noFormElementsExp = QDiagnosisForm.diagnosisForm.formElements.isEmpty();
            exp = exp != null ? exp.and(noFormElementsExp) : noFormElementsExp;
        }
        return exp;
    }

}
