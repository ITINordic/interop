package com.itinordic.interop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class IndexController {
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
