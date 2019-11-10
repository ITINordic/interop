package com.itinordic.interop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service(value = "categoryOptionComboService")
public class CategoryOptionComboServiceImpl implements CategoryOptionComboService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getCategoryOptionComboId(String outcome, Integer age) {
        if (age < 1 && outcome.equals("A")) {
            return UNDER_1_A;
        } else if (age < 1 && outcome.equals("C")) {
            return UNDER_1_C;
        } else if (age < 1 && outcome.equals("D")) {
            return UNDER_1_D;
        } else if (age < 1 && outcome.equals("T")) {
            return UNDER_1_T;
        } else if (age >= 1 && age <= 4 && outcome.equals("A")) {
            return _1_TO_4_A;
        } else if (age >= 1 && age <= 4 && outcome.equals("C")) {
            return _1_TO_4_C;
        } else if (age >= 1 && age <= 4 && outcome.equals("D")) {
            return _1_TO_4_D;
        } else if (age >= 1 && age <= 4 && outcome.equals("T")) {
            return _1_TO_4_T;
        } else if (age >= 5 && outcome.equals("A")) {
            return _5_PLUS_A;
        } else if (age >= 5 && outcome.equals("C")) {
            return _5_PLUS_C;
        } else if (age >= 5 && outcome.equals("D")) {
            return _5_PLUS_D;
        } else if (age >= 5 && outcome.equals("T")) {
            return _5_PLUS_T;
        }

        return null;
    }

    //Total per age mapping (C or Cases or Total)
    @Override
    public String getTotalCategoryOptionComboId(Integer age) {
        if (age < 1) {
            return UNDER_1_C;
        } else if (age >= 1 && age <= 4) {
            return _1_TO_4_C;
        } else if (age >= 5) {
            return _5_PLUS_C;
        }
        return null;
    }

    @Override
    public String getCategoryOptionComboName(String categoryOptionComboId) {
        String suffix = "";
        switch (categoryOptionComboId) {
            case DEFAULT:
                suffix = "default";
                break;
            case UNDER_1_A:
                suffix = "under 1 year with A";
                break;
            case UNDER_1_C:
                suffix = "under 1 year with C";
                break;
            case UNDER_1_D:
                suffix = "under 1 year with D";
                break;
            case UNDER_1_T:
                suffix = "under 1 year with T";
                break;
            case _1_TO_4_A:
                suffix = "1 to 4 years with A";
                break;
            case _1_TO_4_C:
                suffix = "1 to 4 years with C";
                break;
            case _1_TO_4_D:
                suffix = "1 to 4 years with D";
                break;
            case _1_TO_4_T:
                suffix = "1 to 4 years with T";
                break;
            case _5_PLUS_A:
                suffix = "5 plus years with A";
                break;
            case _5_PLUS_C:
                suffix = "5 plus years with C";
                break;
            case _5_PLUS_D:
                suffix = "5 plus years with D";
                break;
            case _5_PLUS_T:
                suffix = "5 plus years with T";
                break;
            default:
                break;
        }
        return suffix;
    }

}
