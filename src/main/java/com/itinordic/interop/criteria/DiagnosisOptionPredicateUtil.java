package com.itinordic.interop.criteria;

import com.itinordic.interop.entity.QDiagnosisOption;
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
            BooleanExpression nameExp = QDiagnosisOption.diagnosisOption.dhisName.like("%" + diagnosisOptionSearchDto.getQ().trim() + "%");
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }

        return exp;
    }

}
