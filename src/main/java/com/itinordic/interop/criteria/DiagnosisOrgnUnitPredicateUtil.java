package com.itinordic.interop.criteria;

import com.itinordic.interop.entity.QDiagnosisOption;
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
        
        if (diagnosisOrgnUnitSearchDto.getQ() != null && !diagnosisOrgnUnitSearchDto.getQ().trim().isEmpty()) {
            BooleanExpression nameExp = QDiagnosisOrganizationUnit.diagnosisOrganizationUnit.dhisName.like("%" + diagnosisOrgnUnitSearchDto.getQ().trim() + "%");
            nameExp = nameExp.or(QDiagnosisOrganizationUnit.diagnosisOrganizationUnit.dhisCode.like("%" + diagnosisOrgnUnitSearchDto.getQ().trim() + "%"));
            nameExp = nameExp.or(QDiagnosisOrganizationUnit.diagnosisOrganizationUnit.dhisId.like("%" + diagnosisOrgnUnitSearchDto.getQ().trim() + "%"));
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }

        return exp;
    }

}
