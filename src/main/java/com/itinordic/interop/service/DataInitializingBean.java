package com.itinordic.interop.service;

import com.itinordic.interop.dhis.CategoryCombo;
import com.itinordic.interop.dhis.CategoryOptionCombo;
import com.itinordic.interop.dhis.DataElement;
import com.itinordic.interop.dhis.DataSet;
import com.itinordic.interop.dhis.DataSetElement;
import com.itinordic.interop.dhis.Option;
import com.itinordic.interop.dhis.OptionSet;
import com.itinordic.interop.dhis.OrganizationUnit;
import com.itinordic.interop.dhis.service.DataSetService;
import com.itinordic.interop.dhis.service.OptionSetService;
import com.itinordic.interop.dhis.service.OrganizationUnitService;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.entity.DiagnosisOrganizationUnit;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.entity.T9OrganizationUnit;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.repo.DiagnosisOrganizationUnitRepository;
import com.itinordic.interop.repo.T9DataElementRepository;
import com.itinordic.interop.repo.T9FormElementRepository;
import com.itinordic.interop.repo.T9OrganizationUnitRepository;
import com.itinordic.interop.util.DhisSystem;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class DataInitializingBean implements InitializingBean {

    @Autowired
    private DiagnosisOrganizationUnitRepository diagnosisOrganizationUnitRepository;
    @Autowired
    private OrganizationUnitService organizationUnitService;
    @Autowired
    private T9OrganizationUnitRepository t9OrganizationUnitRepository;
    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;
    @Autowired
    private OptionSetService optionSetService;
    @Autowired
    private T9DataElementRepository t9DataElementRepository;
    @Autowired
    private DataSetService dataSetService;
    @Autowired
    private T9DataElementService t9DataElementService;
    @Autowired
    private T9FormElementRepository t9FormElementRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        downloadImmisOrganizationUnits();
        downloadNhisOrganizationUnits();
        downloadOptions();
        downloadDataElements();
        downloadFormElements();
    }

    private void downloadImmisOrganizationUnits() {
        if (diagnosisOrganizationUnitRepository.findAll().isEmpty()) {
            List<OrganizationUnit> organizationUnits = organizationUnitService.getOrganizationUnits(DhisSystem.IMMIS);
            for (OrganizationUnit organizationUnit : organizationUnits) {
                DiagnosisOrganizationUnit diagnosisOrganizationUnit = diagnosisOrganizationUnitRepository.findByDhisId(organizationUnit.getId());
                if (diagnosisOrganizationUnit == null) {
                    diagnosisOrganizationUnit = new DiagnosisOrganizationUnit();
                    diagnosisOrganizationUnit.setDhisCode(organizationUnit.getCode());
                    diagnosisOrganizationUnit.setDhisName(organizationUnit.getName());
                    diagnosisOrganizationUnit.setDhisId(organizationUnit.getId());
                    diagnosisOrganizationUnit.setDhisShortName(organizationUnit.getShortName());
                    diagnosisOrganizationUnitRepository.save(diagnosisOrganizationUnit);
                }
            }
        }
    }

    private void downloadNhisOrganizationUnits() {
        if (t9OrganizationUnitRepository.findAll().isEmpty()) {
            List<OrganizationUnit> organizationUnits = organizationUnitService.getOrganizationUnits(DhisSystem.NHIS);
            for (OrganizationUnit organizationUnit : organizationUnits) {
                T9OrganizationUnit t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisId(organizationUnit.getId());
                if (t9OrganizationUnit == null) {
                    t9OrganizationUnit = new T9OrganizationUnit();
                    t9OrganizationUnit.setDhisCode(organizationUnit.getCode());
                    t9OrganizationUnit.setDhisName(organizationUnit.getName());
                    t9OrganizationUnit.setDhisId(organizationUnit.getId());
                    t9OrganizationUnit.setDhisShortName(organizationUnit.getShortName());
                    t9OrganizationUnitRepository.save(t9OrganizationUnit);
                }
            }

        }
    }

    private void downloadOptions() {
        if (diagnosisOptionRepository.findAll().isEmpty()) {
            OptionSet optionSet = optionSetService.getOptionSet();
            List<Option> options = optionSet.getOptions();
            for (Option option : options) {
                DiagnosisOption diagnosisOption = diagnosisOptionRepository.findByDhisId(option.getId());
                if (diagnosisOption == null) {
                    diagnosisOption = new DiagnosisOption();
                    diagnosisOption.setDhisCode(option.getCode());
                    diagnosisOption.setDhisName(option.getName());
                    diagnosisOption.setDhisId(option.getId());
                    diagnosisOptionRepository.save(diagnosisOption);
                }
            }

            if (!t9DataElementRepository.findAll().isEmpty()) {
                t9DataElementService.autoBindOptions();
            }
        }

    }

    private void downloadDataElements() {
        if (t9DataElementRepository.findAll().isEmpty()) {
            DataSet dataSet = dataSetService.getDataSet();
            List<DataSetElement> dataSetElements = dataSet.getDataSetElements();
            for (DataSetElement dataSetElement : dataSetElements) {
                DataElement dataElement = dataSetElement.getDataElement();
                T9DataElement t9DataElement = t9DataElementRepository.findByDhisId(dataElement.getId());
                if (t9DataElement == null) {
                    t9DataElement = new T9DataElement();
                    t9DataElement.setDhisCode(dataElement.getCode());
                    t9DataElement.setDhisName(dataElement.getName());
                    t9DataElement.setDhisId(dataElement.getId());
                    t9DataElementRepository.save(t9DataElement);
                }
            }

            if (!diagnosisOptionRepository.findAll().isEmpty()) {
                t9DataElementService.autoBindOptions();
            }

        }

    }

    private void downloadFormElements() {
        if (t9FormElementRepository.findAll().isEmpty()) {
            DataSet dataSet = dataSetService.getDataSet();
            List<DataSetElement> dataSetElements = dataSet.getDataSetElements();
            for (DataSetElement dataSetElement : dataSetElements) {
                DataElement dataElement = dataSetElement.getDataElement();
                CategoryCombo categoryCombo = dataElement.getCategoryCombo();
                T9DataElement t9DataElement = t9DataElementRepository.findByDhisId(dataElement.getId());
                if (categoryCombo != null) {
                    List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
                    if (categoryOptionCombos != null && !categoryOptionCombos.isEmpty()) {
                        for (CategoryOptionCombo categoryOptionCombo : categoryOptionCombos) {
                            String categoryOptionComboId = categoryOptionCombo.getId();
                            T9FormElement t9FormElement = t9FormElementRepository.findByDataElementAndCategoryOptionComboId(t9DataElement, categoryOptionComboId);
                            if (t9FormElement == null) {
                                t9FormElement = new T9FormElement();
                                t9FormElement.setCategoryOptionComboId(categoryOptionComboId);
                                t9FormElement.setDataElement(t9DataElement);
                                t9FormElementRepository.save(t9FormElement);
                            }
                        }
                    }

                }
            }
        }

    }

}
