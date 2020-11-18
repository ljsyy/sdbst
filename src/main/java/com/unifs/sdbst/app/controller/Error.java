package com.unifs.sdbst.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "error")
public class Error {

    @GetMapping("updating")
    public String updating(){
        return "error/updating";
    }
}
