package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Model;
import org.car.rest.domain.dto.RequestModelDto;
import org.car.rest.domain.dto.ResponseModelDto;
import org.car.rest.domain.mapper.ModelMapper;
import org.car.rest.repository.ModelRepository;
import org.car.rest.service.exception.ModelServiceException;
import org.car.rest.service.response.error.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    public List<ResponseModelDto> getAllModels() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(modelMapper::modelToResponseModelDto).toList();
    }

    public ResponseModelDto getModelById(Long id) {
        return modelMapper.modelToResponseModelDto(repository.findById(id)
                .orElseThrow(() -> new ModelServiceException(Code.REQUEST_VALIDATION_ERROR,
                        "Can't find model", "Can't find model with id: " + id, HttpStatus.BAD_REQUEST)));
    }

    public List<ResponseModelDto> getModelsByExample(RequestModelDto requestModelDto){
        Example<Model> example = Example.of(modelMapper.requestModelDtoToModel(requestModelDto));

        return repository.findAll(example).stream().map(modelMapper::modelToResponseModelDto).toList();
    }

    public ResponseModelDto createModel(RequestModelDto  requestModelDto) {
        Model newModel = modelMapper.requestModelDtoToModel(requestModelDto);

        modelExist(newModel);

        return modelMapper.modelToResponseModelDto(repository.save(newModel));
    }

    public void createSeveralModels(List<RequestModelDto> modelsDto) {
        repository.saveAll(modelsDto.stream().map(modelMapper::requestModelDtoToModel).toList());
    }

    public ResponseModelDto updateModel(long id, RequestModelDto requestModelDto) {
        Model newModel = modelMapper.requestModelDtoToModel(requestModelDto);
        newModel.setId(id);

        modelExist(newModel);

        return modelMapper.modelToResponseModelDto(repository.save(newModel));
    }

    public void deleteModelById(long id) {
        repository.deleteById(id);
    }

    private void modelExist(Model model){
        if (repository.existsByNameAndMake(model.getName(), model.getMake())){
            throw new ModelServiceException(
                    Code.REQUEST_VALIDATION_ERROR,
                    "This model already exist: " + model.getName(), "Model already exist.", HttpStatus.CONFLICT);
        }
    }
}
