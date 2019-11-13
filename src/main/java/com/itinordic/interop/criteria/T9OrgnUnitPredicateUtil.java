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
        if (t9OrgnUnitSearchDto.hasQ()) {
            String qLike="%" + t9OrgnUnitSearchDto.getQ().trim().toLowerCase() + "%";            
            BooleanExpression nameExp = QT9OrganizationUnit.t9OrganizationUnit.dhisName.toLowerCase().like(qLike);
            nameExp = nameExp.or(QT9OrganizationUnit.t9OrganizationUnit.dhisCode.toLowerCase().like(qLike));
            nameExp = nameExp.or(QT9OrganizationUnit.t9OrganizationUnit.dhisId.toLowerCase().like(qLike));
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }

        return exp;
    }

}
