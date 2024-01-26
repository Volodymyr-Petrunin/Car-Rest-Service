package org.car.rest.domain.mapper;

import org.car.rest.domain.Model;
import org.car.rest.domain.dto.ResponseModelDto;
import org.car.rest.domain.dto.RequestModelDto;
import org.car.rest.repository.MakeRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = MakeMapper.class)
public abstract class ModelMapper {

    @Autowired
    private MakeRepository makeRepository;

    @Mapping(source = "make.name", target = "makeName")
    public abstract ResponseModelDto modelToResponseModelDto(Model model);

    @Mapping(source = "makeName", target = "make.name")
    public abstract Model requestModelDtoToModel(RequestModelDto modelDto);

    @AfterMapping
    protected void setMake(@MappingTarget Model model, RequestModelDto requestModelDto) {
        if (requestModelDto.getMakeName() != null) {
            model.setMake(makeRepository.findByName(requestModelDto.getMakeName()));
        }
    }
}
