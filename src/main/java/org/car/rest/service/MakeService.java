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

    public List<Make> getAllMaker() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<MakeDto> getAllMakesAsDto() {
        return this.getAllMaker().stream().map(makeMapper::makeToMakeDto).toList();
    }

    public Make getMakerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find maker by id: " + id));
    }

    public void createMaker(Make make) {
        repository.save(make);
    }

    public void createSeveralMaker(List<Make> makes) {
        repository.saveAll(makes);
    }

    public void deleteMakerById(Long id) {
        repository.deleteById(id);
    }
}
