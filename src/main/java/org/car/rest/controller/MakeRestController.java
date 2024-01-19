package org.car.rest.controller;

import org.car.rest.domain.dto.MakeDto;
import org.car.rest.service.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/make")
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

    @GetMapping("/{id}")
    public MakeDto getMakerById(@PathVariable long id){
        return makeService.getMakerById(id);
    }

    @GetMapping("/search")
    public MakeDto searchMaker(@RequestBody MakeDto makeDto){
        return makeService.getMakerByExample(makeDto);
    }

    @PatchMapping("/")
    public MakeDto updateMakerName(@RequestBody MakeDto makeDto){
        return makeService.updateMaker(makeDto);
    }

    @DeleteMapping("/")
    public void deleteMaker(@RequestBody MakeDto makeDto){
        makeService.deleteMakerById(makeDto.getId());
    }

    @PostMapping("/")
    public MakeDto createMaker(@RequestBody MakeDto makeDto){
       return makeService.createMaker(makeDto);
    }
}
