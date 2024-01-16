package org.car.rest.domain.mapper;

import org.car.rest.domain.Make;
import org.car.rest.domain.dto.MakeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MakeMapper {
    MakeDto makeToMakeDto(Make make);
    Make makeDtoToMake(MakeDto makeDto);
}
