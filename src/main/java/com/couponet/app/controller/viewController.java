package com.couponet.app.controller;

import com.couponet.app.service.AssigmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class viewController {
    private final AssigmentService assigmentService;

    @Autowired
    public viewController(AssigmentService assigmentService) {
        this.assigmentService = assigmentService;
    }

}
