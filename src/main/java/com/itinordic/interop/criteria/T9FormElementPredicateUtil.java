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
        if (t9FormElementSearchDto.hasQ()) {
            String qLike="%" + t9FormElementSearchDto.getQ().trim().toLowerCase() + "%";
            BooleanExpression nameExp = QT9FormElement.t9FormElement.dataElement.dhisName.toLowerCase().like(qLike);
            nameExp = nameExp.or(QT9FormElement.t9FormElement.dataElement.dhisCode.toLowerCase().like(qLike));
            nameExp = nameExp.or(QT9FormElement.t9FormElement.dataElement.dhisId.toLowerCase().like(qLike));
            nameExp = nameExp.or(QT9FormElement.t9FormElement.categoryOptionComboId.toLowerCase().like(qLike));
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }


        return exp;
    }

}
