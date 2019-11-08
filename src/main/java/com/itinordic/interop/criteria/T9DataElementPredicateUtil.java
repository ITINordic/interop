package com.itinordic.interop.criteria;

import com.itinordic.interop.entity.QT9DataElement;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 *
 * @author Charles Chigoriwa
 */
public class T9DataElementPredicateUtil {

    public static BooleanExpression getPredicate(T9DataElementSearchDto dataElementSearchDto) {
        BooleanExpression exp = null;

        if (dataElementSearchDto.getQ() != null && !dataElementSearchDto.getQ().trim().isEmpty()) {
            BooleanExpression nameExp = QT9DataElement.t9DataElement.dhisName.like("%" + dataElementSearchDto.getQ().trim() + "%");
            exp = exp != null ? exp.and(nameExp) : nameExp;
        }

        if (dataElementSearchDto.getNoOptions() != null && dataElementSearchDto.getNoOptions()) {
            BooleanExpression noOptionsExp = QT9DataElement.t9DataElement.options.isEmpty();
            exp = exp != null ? exp.and(noOptionsExp) : noOptionsExp;
        }

        return exp;
    }

}
