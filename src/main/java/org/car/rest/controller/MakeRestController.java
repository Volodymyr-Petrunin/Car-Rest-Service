package org.car.rest.controller;

import org.car.rest.domain.dto.MakeDto;
import org.car.rest.service.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maker")
public class MakeRestController {

    private final MakeService makeService;

    @Autowired
    public MakeRestController(MakeService makeService) {
        this.makeService = makeService;
    }

    @GetMapping("/all")
    public List<MakeDto> allMake(){
        return makeService.getAllMaker();
    }

    @GetMapping("/show/id/{id}")
    public MakeDto showMakerById(@PathVariable long id){
        return makeService.getMakerById(id);
    }

    @GetMapping("/show/name/{name}")
    public MakeDto showMakerByName(@PathVariable String name){
        return makeService.getMakerByName(name);
    }

    @PatchMapping("/update/{id}/{name}")
    public MakeDto updateMakerName(@ModelAttribute MakeDto makeDto){
        return makeService.updateMaker(makeDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMaker(@PathVariable long id){
        makeService.deleteMakerById(id);
    }
    @PutMapping("/create/maker/{name}")
    public MakeDto createMaker(@ModelAttribute MakeDto makeDto){
       return makeService.createMaker(makeDto);
    }
}
