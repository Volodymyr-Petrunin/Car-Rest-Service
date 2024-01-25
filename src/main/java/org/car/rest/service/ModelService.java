package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.ModelDto;
import org.car.rest.domain.mapper.ModelMapper;
import org.car.rest.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ModelService {
    private final ModelRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ModelService(ModelRepository repository,
                        ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<ModelDto> getAllModels() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(modelMapper::modelToModelDto).toList();
    }

    public ModelDto getModelById(Long id) {
        return modelMapper.modelToModelDto(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find model with id: " + id)));
    }

    public List<ModelDto> getModelsByExample(ModelDto modelDto){
        Example<Model> example = Example.of(modelMapper.modelDtoToModel(modelDto));

        return repository.findAll(example).stream().map(modelMapper::modelToModelDto).toList();
    }

    public ModelDto createModel(ModelDto modelDto) {
        Model newModel = modelMapper.modelDtoToModel(modelDto);

        if (repository.existsByNameAndMake(newModel.getName(), newModel.getMake())){
            throw new IllegalArgumentException("This model already exist: " + newModel);
        }

        return modelMapper.modelToModelDto(repository.save(newModel));
    }

    public void createSeveralModels(List<ModelDto> modelsDto) {
        repository.saveAll(modelsDto.stream().map(modelMapper::modelDtoToModel).toList());
    }

    public ModelDto updateModel(ModelDto modelDto) {
        if (repository.findById(modelDto.getId()).isPresent()) {
            return modelMapper.modelToModelDto(repository.save(modelMapper.modelDtoToModel(modelDto)));
        } else {
            throw new IllegalArgumentException("No model for update with id: " + modelDto.getId());
        }
    }

    public void deleteModelById(ModelDto modelDto) {
        repository.deleteById(modelDto.getId());
    }
}
