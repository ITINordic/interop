package com.itinordic.interop.util;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

/**
 *
 * @author Charles Chigoriwa
 */
public class PageUtil {

    public static void injectPageAspects(Model model, Page<?> page) {

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        boolean isFirstPage = current == 1;
        boolean isLastPage = current == page.getTotalPages();

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("isFirstPage", isFirstPage);
        model.addAttribute("isLastPage", isLastPage);
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
    }
    
  
}
