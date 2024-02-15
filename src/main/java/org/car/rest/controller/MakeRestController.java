package org.car.rest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.car.rest.domain.dto.ResponseMakeDto;
import org.car.rest.domain.dto.RequestMakeDto;
import org.car.rest.service.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/make")
@SecurityRequirement(name = "bearerAuth")
public class MakeRestController {

    private final MakeService makeService;

    @Autowired
    public MakeRestController(MakeService makeService) {
        this.makeService = makeService;
    }

    @GetMapping("/")
    public ResponseMakeDto searchMaker(@RequestBody RequestMakeDto requestMakeDto){
        return makeService.getMakerByExample(requestMakeDto);
    }

    @GetMapping("/{id}")
    public ResponseMakeDto getMakerById(@PathVariable long id){
        return makeService.getMakerById(id);
    }

    @PatchMapping("/{id}")
    public ResponseMakeDto updateMakerName(@PathVariable long id, @RequestBody RequestMakeDto requestMakeDto){
        return makeService.updateMaker(id ,requestMakeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMaker(@PathVariable long id){
        makeService.deleteMakerById(id);
    }

    @PostMapping("/")
    public ResponseMakeDto createMaker(@RequestBody RequestMakeDto requestMakeDto){
       return makeService.createMaker(requestMakeDto);
    }
}
