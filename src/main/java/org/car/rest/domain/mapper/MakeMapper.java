package org.car.rest.domain.mapper;

import org.car.rest.domain.Make;
import org.car.rest.domain.dto.ResponseMakeDto;
import org.car.rest.domain.dto.RequestMakeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MakeMapper {
    ResponseMakeDto makeToMakeDto(Make make);
    Make requestMakeDtoToMake(RequestMakeDto requestMakeDto);
}
