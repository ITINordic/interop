package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9DataElementPredicateUtil;
import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.dhis.DataElement;
import com.itinordic.interop.dhis.Option;
import com.itinordic.interop.dhis.service.MappingService;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.repo.T9DataElementRepository;
import com.itinordic.interop.util.GeneralUtility;
import com.itinordic.interop.util.MappingResult;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class T9DataElementServiceImpl implements T9DataElementService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private T9DataElementRepository t9DataElementRepository;
    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;
    @Autowired
    private MappingService mappingService;

    @Override
    public synchronized String autoBindOptions() {
        MappingResult mappingResult = mappingService.getMappingResult();
        Map<DataElement, Set<Option>> mapped = mappingResult.getMapped();
        for (DataElement dataElement : mapped.keySet()) {
            T9DataElement t9DataElement = t9DataElementRepository.findByDhisId(dataElement.getId());
            if (t9DataElement != null && !t9DataElement.hasOptions()) {

                Set<Option> options = mapped.get(dataElement);
                if (!GeneralUtility.isEmpty(options)) {
                    List<DiagnosisOption> newDiagnosisOptionList = new ArrayList<>();

                    for (Option option : options) {
                        DiagnosisOption diagnosisOption = diagnosisOptionRepository.findByDhisId(option.getId());
                        if (diagnosisOption != null) {
                            newDiagnosisOptionList.add(diagnosisOption);
                        } else {
                            logger.info("DiagnosisOption with id %s not found", option.getId());
                        }
                    }

                    if (!newDiagnosisOptionList.isEmpty()) {
                        t9DataElement.setOptions(newDiagnosisOptionList);
                        t9DataElement.setModificationDateTime(new Date());
                        t9DataElementRepository.save(t9DataElement);
                    }
                }

            }
        }
        return "redirect:/admin/t9/dataElements";
    }

    @Override
    public Page<T9DataElement> findT9DataElements(T9DataElementSearchDto dataElementSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber = dataElementSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);

        Predicate predicate = T9DataElementPredicateUtil.getPredicate(dataElementSearchDto);
        if (predicate != null) {
            return t9DataElementRepository.findAll(predicate, pageRequest);
        } else {
            return t9DataElementRepository.findAll(pageRequest);
        }
    }

    @Override
    public long getT9DataElementCount(T9DataElementSearchDto dataElementSearchDto) {
        if(dataElementSearchDto==null){
           return t9DataElementRepository.count(); 
        }
        Predicate predicate = T9DataElementPredicateUtil.getPredicate(dataElementSearchDto);
        return t9DataElementRepository.count(predicate);
    }

}
