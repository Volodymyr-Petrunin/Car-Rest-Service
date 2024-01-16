package org.car.rest.service;

import jakarta.transaction.Transactional;
import org.car.rest.domain.Model;
import org.car.rest.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ModelService {
    private final ModelRepository repository;

    @Autowired
    public ModelService(ModelRepository repository) {
        this.repository = repository;
    }

    public List<Model> getAllModels() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Model getModelById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find model with id: " + id));
    }

    public void createModel(Model model) {
        repository.save(model);
    }

    public void createSeveralModels(List<Model> models) {
        repository.saveAll(models);
    }

    public void updateModel(Model model) {
        repository.save(model);
    }

    public void deleteModelById(Long id) {
        repository.deleteById(id);
    }
}
