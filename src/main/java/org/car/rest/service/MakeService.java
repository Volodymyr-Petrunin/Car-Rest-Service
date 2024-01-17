package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Make;
import org.car.rest.domain.dto.MakeDto;
import org.car.rest.domain.mapper.MakeMapper;
import org.car.rest.repository.MakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<MakeDto> getAllMaker() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(makeMapper::makeToMakeDto).toList();
    }

    public MakeDto getMakerById(Long id) {
        return makeMapper.makeToMakeDto(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find maker by id: " + id)));
    }

    public MakeDto getMakerByName(String name){
        return makeMapper.makeToMakeDto(repository.findByName(name));
    }

    public MakeDto createMaker(MakeDto makeDto) {
       return makeMapper.makeToMakeDto(repository.save(makeMapper.makeDtoToMake(makeDto)));
    }

    public void createSeveralMaker(List<MakeDto> makes) {
        repository.saveAll(makes.stream().map(makeMapper::makeDtoToMake).toList());
    }

    public MakeDto updateMaker(MakeDto makeDto){
        return makeMapper.makeToMakeDto(repository.save(makeMapper.makeDtoToMake(makeDto)));
    }

    public void deleteMakerById(Long id) {
        repository.deleteById(id);
    }
}
