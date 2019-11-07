package com.itinordic.interop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class IndexController {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
