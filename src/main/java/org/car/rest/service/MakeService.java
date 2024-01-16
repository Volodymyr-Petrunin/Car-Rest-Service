package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Make;
import org.car.rest.repository.MakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MakeService {
    private final MakeRepository repository;

    @Autowired
    public MakeService(MakeRepository repository) {
        this.repository = repository;
    }

    public List<Make> getAllMaker() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
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
