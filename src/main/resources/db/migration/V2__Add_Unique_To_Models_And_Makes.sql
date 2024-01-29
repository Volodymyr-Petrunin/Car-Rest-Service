ALTER TABLE models
    ADD CONSTRAINT unique_model_name_make_make_id UNIQUE (model_name, make_make_id);

ALTER TABLE makes
    ADD CONSTRAINT unique_make_name UNIQUE (make_name);
