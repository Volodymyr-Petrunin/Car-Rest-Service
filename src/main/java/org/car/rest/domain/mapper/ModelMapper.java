package org.car.rest.domain.mapper;

import org.car.rest.domain.Model;
import org.car.rest.domain.dto.ModelDto;
import org.car.rest.repository.MakeRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = MakeMapper.class)
public abstract class ModelMapper {

    @Autowired
    private MakeRepository makeRepository;

    @Mapping(source = "make.name", target = "makeName")
    public abstract ModelDto modelToModelDto(Model model);

    @Mapping(source = "makeName", target = "make.name")
    public abstract Model modelDtoToModel(ModelDto modelDto);

    @AfterMapping
    protected void setMake(@MappingTarget Model model, ModelDto modelDto) {
        if (modelDto.getMakeName() != null) {
            model.setMake(makeRepository.findByName(modelDto.getMakeName()));
        }
    }
}
