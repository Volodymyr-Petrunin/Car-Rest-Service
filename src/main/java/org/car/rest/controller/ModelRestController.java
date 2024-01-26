package org.car.rest.controller;

import org.car.rest.domain.dto.RequestModelDto;
import org.car.rest.domain.dto.ResponseModelDto;
import org.car.rest.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/model")
public class ModelRestController {
    private final ModelService modelService;

    @Autowired
    public ModelRestController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/")
    public List<ResponseModelDto> searchModels(@RequestBody RequestModelDto requestModelDto) {
        return modelService.getModelsByExample(requestModelDto);
    }
    @GetMapping("/{id}")
    public ResponseModelDto getModelById(@PathVariable long id){
        return modelService.getModelById(id);
    }

    @PatchMapping("/{id}")
    public ResponseModelDto updateModel(@PathVariable long id, @RequestBody RequestModelDto requestModelDto){
        return modelService.updateModel(id, requestModelDto);
    }

    @DeleteMapping("/{id}")
    public void deleteModel(@PathVariable long id){
        modelService.deleteModelById(id);
    }

    @PostMapping("/")
    public ResponseModelDto createModel(@RequestBody RequestModelDto requestModelDto){
        return modelService.createModel(requestModelDto);
    }
}
