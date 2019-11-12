package com.itinordic.interop.criteria;

import com.itinordic.interop.entity.QDiagnosisOption;
import com.itinordic.interop.util.GeneralUtility;
import com.querydsl.core.types.dsl.BooleanExpression;
//import zw.co.endlini.entity.QCity;

/**
 *
 * @author Charles Chigoriwa
 */
public class DiagnosisOptionPredicateUtil {

    public static BooleanExpression getPredicate(DiagnosisOptionSearchDto diagnosisOptionSearchDto) {
        BooleanExpression exp = null;        
         if (diagnosisOptionSearchDto.getQ() != null && !diagnosisOptionSearchDto.getQ().trim().isEmpty()) {
             String qLike="%" + diagnosisOptionSearchDto.getQ().trim().toLowerCase() + "%";
            BooleanExpression nameExp = QDiagnosisOption.diagnosisOption.dhisName.toLowerCase().like(qLike);
            nameExp = nameExp.or(QDiagnosisOption.diagnosisOption.dhisCode.toLowerCase().like(qLike));
            nameExp = nameExp.or(QDiagnosisOption.diagnosisOption.dhisId.toLowerCase().like(qLike));
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }
         
        if (GeneralUtility.isTrue(diagnosisOptionSearchDto.getNoDataElements())) {
            BooleanExpression noDataElementsExp = QDiagnosisOption.diagnosisOption.dataElements.isEmpty();
            exp = exp != null ? exp.and(noDataElementsExp) : noDataElementsExp;
        }
        
        if (GeneralUtility.isTrue(diagnosisOptionSearchDto.getChosen())) {
            BooleanExpression noDataElementsExp = QDiagnosisOption.diagnosisOption.diagnosisForms.isNotEmpty();
            exp = exp != null ? exp.and(noDataElementsExp) : noDataElementsExp;
        }


        return exp;
    }

}
