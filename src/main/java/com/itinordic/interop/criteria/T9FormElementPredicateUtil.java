package com.itinordic.interop.criteria;

import com.itinordic.interop.entity.QT9FormElement;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 *
 * @author Charles Chigoriwa
 */
public class T9FormElementPredicateUtil {

    public static BooleanExpression getPredicate(T9FormElementSearchDto t9FormElementSearchDto) {
        BooleanExpression exp = null;        
        if (t9FormElementSearchDto.getQ() != null && !t9FormElementSearchDto.getQ().trim().isEmpty()) {
            BooleanExpression nameExp = QT9FormElement.t9FormElement.dataElement.dhisName.like("%" + t9FormElementSearchDto.getQ().trim() + "%");
            nameExp = nameExp.or(QT9FormElement.t9FormElement.dataElement.dhisCode.like("%" + t9FormElementSearchDto.getQ().trim() + "%"));
            nameExp = nameExp.or(QT9FormElement.t9FormElement.dataElement.dhisId.like("%" + t9FormElementSearchDto.getQ().trim() + "%"));
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }


        return exp;
    }

}
