package com.itinordic.interop.criteria;

import com.itinordic.interop.entity.QDiagnosisOrganizationUnit;
import com.querydsl.core.types.dsl.BooleanExpression;
//import zw.co.endlini.entity.QCity;

/**
 *
 * @author Charles Chigoriwa
 */
public class DiagnosisOrgnUnitPredicateUtil {

    public static BooleanExpression getPredicate(DiagnosisOrgnUnitSearchDto diagnosisOrgnUnitSearchDto) {
        BooleanExpression exp = null;    
        
        if (diagnosisOrgnUnitSearchDto.hasQ()) {
            String qLike="%" + diagnosisOrgnUnitSearchDto.getQ().trim().toLowerCase() + "%";
            BooleanExpression nameExp = QDiagnosisOrganizationUnit.diagnosisOrganizationUnit.dhisName.toLowerCase().like(qLike);
            nameExp = nameExp.or(QDiagnosisOrganizationUnit.diagnosisOrganizationUnit.dhisCode.toLowerCase().like(qLike));
            nameExp = nameExp.or(QDiagnosisOrganizationUnit.diagnosisOrganizationUnit.dhisId.toLowerCase().like(qLike));
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }

        return exp;
    }

}
