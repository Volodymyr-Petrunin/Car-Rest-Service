package org.car.rest.controller;

import org.car.rest.service.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/maker")
public class MakeRestController {

    private final MakeService makeService;

    @Autowired
    public MakeRestController(MakeService makeService) {
        this.makeService = makeService;
    }

}
