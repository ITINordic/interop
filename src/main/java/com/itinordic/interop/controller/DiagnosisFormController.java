package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.service.DiagnosisFormService;
import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.dhis.service.EventService;
import com.itinordic.interop.util.EventList;
import com.itinordic.interop.util.PageUtil;
import com.itinordic.interop.util.Pager;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class DiagnosisFormController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    @Autowired
    private DiagnosisFormService diagnosisFormService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/admin/diagnosis/forms", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisFormSearchDto searchDto) {
        Page<DiagnosisForm> diagnosisFormPage = diagnosisFormService.findDiagnosisForms(searchDto, "id", true, 10);
        model.addAttribute("forms", diagnosisFormPage);
        PageUtil.injectPageAspects(model, diagnosisFormPage);
        return "diagnosisForm/diagnosisForms";
    }

    @RequestMapping(value = "/admin/diagnosis/forms/sync", method = RequestMethod.POST)
    public synchronized String sync(Principal principal, Model model) throws IOException {

        int page = 1;

        Pager pager;

        do {

            EventList eventList = eventService.getEventList(page);
            pager = eventList.getPager();

            List<Event> events = eventList.getEvents();
            for (Event event : events) {
                DiagnosisForm diagnosisForm = diagnosisFormService.transform(event).orElse(null);
                if (diagnosisForm != null) {
                    diagnosisFormRepository.save(diagnosisForm);
                }

                page++;

            }
        } while (page <= pager.getPageCount());

        return "redirect:/admin/diagnosis/forms";

    }

}
