package com.itinordic.interop.criteria;

import com.itinordic.interop.entity.QT9OrganizationUnit;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 *
 * @author Charles Chigoriwa
 */
public class T9OrgnUnitPredicateUtil {

    public static BooleanExpression getPredicate(T9OrgnUnitSearchDto t9OrgnUnitSearchDto) {
        BooleanExpression exp = null;        
        if (t9OrgnUnitSearchDto.getQ() != null && !t9OrgnUnitSearchDto.getQ().trim().isEmpty()) {
            BooleanExpression nameExp = QT9OrganizationUnit.t9OrganizationUnit.dhisName.like("%" + t9OrgnUnitSearchDto.getQ().trim() + "%");
            nameExp = nameExp.or(QT9OrganizationUnit.t9OrganizationUnit.dhisCode.like("%" + t9OrgnUnitSearchDto.getQ().trim() + "%"));
            nameExp = nameExp.or(QT9OrganizationUnit.t9OrganizationUnit.dhisId.like("%" + t9OrgnUnitSearchDto.getQ().trim() + "%"));
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }

        return exp;
    }

}
