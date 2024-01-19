package org.car.rest.controller;

import org.car.rest.domain.dto.ModelDto;
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

    @GetMapping("/all")
    public List<ModelDto> allModel(){
        return modelService.getAllModels();
    }

    @GetMapping("/{id}")
    public ModelDto getModelById(@PathVariable long id){
        return modelService.getModelById(id);
    }

    @GetMapping("/search")
    public List<ModelDto> searchModels(@RequestBody ModelDto modelDto) {
        return modelService.getModelsByExample(modelDto);
    }

    @PatchMapping("/")
    public ModelDto updateModel(@RequestBody ModelDto modelDto){
        return modelService.updateModel(modelDto);
    }

    @DeleteMapping("/")
    public void deleteModel(@RequestBody ModelDto modelDto){
        modelService.deleteModelById(modelDto);
    }

    @PostMapping("/")
    public ModelDto createModel(@RequestBody ModelDto modelDto){
        return modelService.createModel(modelDto);
    }
}
