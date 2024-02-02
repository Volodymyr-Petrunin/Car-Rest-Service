package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Make;
import org.car.rest.domain.dto.ResponseMakeDto;
import org.car.rest.domain.dto.RequestMakeDto;
import org.car.rest.domain.mapper.MakeMapper;
import org.car.rest.repository.MakeRepository;
import org.car.rest.service.exception.MakeServiceException;
import org.car.rest.service.response.error.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MakeService {
    private final MakeRepository repository;
    private final MakeMapper makeMapper;

    @Autowired
    public MakeService(MakeRepository repository,
                       MakeMapper makeMapper) {
        this.repository = repository;
        this.makeMapper = makeMapper;
    }

    public List<ResponseMakeDto> getAllMaker() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(makeMapper::makeToResponseMakeDto).toList();
    }

    public ResponseMakeDto getMakerById(Long id) {
        return makeMapper.makeToResponseMakeDto(repository.findById(id)
                .orElseThrow(() -> new MakeServiceException(Code.REQUEST_VALIDATION_ERROR,
                        "No make for with id: " + id, "Can't find make by id.", HttpStatus.BAD_REQUEST)));
    }

    public ResponseMakeDto getMakerByExample(RequestMakeDto requestMakeDto){
        Example<Make> example = Example.of(makeMapper.requestMakeDtoToMake(requestMakeDto));

        return makeMapper.makeToResponseMakeDto(repository.findOne(example)
                .orElseThrow(() -> new MakeServiceException(Code.REQUEST_VALIDATION_ERROR,
                        "Can't find make.", "Can't find make by example.", HttpStatus.BAD_REQUEST)));
    }

    public ResponseMakeDto createMaker(RequestMakeDto requestMakeDto) {
        Make make = makeMapper.requestMakeDtoToMake(requestMakeDto);

        return makeMapper.makeToResponseMakeDto(repository.save(make));
    }

    public void createSeveralMaker(List<RequestMakeDto> makes) {
        repository.saveAll(makes.stream().map(makeMapper::requestMakeDtoToMake).toList());
    }

    public ResponseMakeDto updateMaker(long id, RequestMakeDto requestMakeDto) {
        if (repository.findById(id).isPresent()) {
            Make make = makeMapper.requestMakeDtoToMake(requestMakeDto);
            make.setId(id);

            return makeMapper.makeToResponseMakeDto(repository.save(make));
        } else {
            throw new MakeServiceException(Code.REQUEST_VALIDATION_ERROR,
                    "No make for update with id: " + id, "Can't find make.", HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteMakerById(Long id) {
        repository.deleteById(id);
    }
}
