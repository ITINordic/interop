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
            return A_UNDER_1;
        } else if (age < 1 && outcome.equals("C")) {
            return C_UNDER_1;
        } else if (age < 1 && outcome.equals("D")) {
            return D_UNDER_1;
        } else if (age < 1 && outcome.equals("T")) {
            return T_UNDER_1;
        } else if (age >= 1 && age <= 4 && outcome.equals("A")) {
            return A_1_TO_4;
        } else if (age >= 1 && age <= 4 && outcome.equals("C")) {
            return C_1_TO_4;
        } else if (age >= 1 && age <= 4 && outcome.equals("D")) {
            return D_1_TO_4;
        } else if (age >= 1 && age <= 4 && outcome.equals("T")) {
            return T_1_TO_4;
        } else if (age >= 5 && outcome.equals("A")) {
            return A_5_PLUS;
        } else if (age >= 5 && outcome.equals("C")) {
            return C_5_PLUS;
        } else if (age >= 5 && outcome.equals("D")) {
            return D_5_PLUS;
        } else if (age >= 5 && outcome.equals("T")) {
            return T_5_PLUS;
        }

        return null;
    }

    //Total per age mapping (C or Cases or Total)
    @Override
    public String getTotalCategoryOptionComboId(Integer age) {
        if (age < 1) {
            return C_UNDER_1;
        } else if (age >= 1 && age <= 4) {
            return C_1_TO_4;
        } else if (age >= 5) {
            return C_5_PLUS;
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
            case A_UNDER_1:
                suffix = "under 1 year with A";
                break;
            case C_UNDER_1:
                suffix = "under 1 year with C";
                break;
            case D_UNDER_1:
                suffix = "under 1 year with D";
                break;
            case T_UNDER_1:
                suffix = "under 1 year with T";
                break;
            case A_1_TO_4:
                suffix = "1 to 4 years with A";
                break;
            case C_1_TO_4:
                suffix = "1 to 4 years with C";
                break;
            case D_1_TO_4:
                suffix = "1 to 4 years with D";
                break;
            case T_1_TO_4:
                suffix = "1 to 4 years with T";
                break;
            case A_5_PLUS:
                suffix = "5 plus years with A";
                break;
            case C_5_PLUS:
                suffix = "5 plus years with C";
                break;
            case D_5_PLUS:
                suffix = "5 plus years with D";
                break;
            case T_5_PLUS:
                suffix = "5 plus years with T";
                break;
            default:
                break;
        }
        return suffix;
    }

}
