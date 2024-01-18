CREATE SEQUENCE IF NOT EXISTS make_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS model_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE cars
(
    object_id      VARCHAR(255) NOT NULL,
    car_year       SMALLINT,
    model_model_id BIGINT,
    CONSTRAINT pk_car PRIMARY KEY (object_id)
);

CREATE TABLE cars_categories
(
    car_id   VARCHAR(255) NOT NULL,
    category VARCHAR(255)
);

CREATE TABLE makes
(
    make_id    BIGINT NOT NULL,
    make_name VARCHAR(255),
    CONSTRAINT pk_make PRIMARY KEY (make_id)
);

CREATE TABLE models
(
    model_id     BIGINT NOT NULL,
    model_name   VARCHAR(255),
    make_make_id BIGINT,
    CONSTRAINT pk_model PRIMARY KEY (model_id)
);

ALTER TABLE cars
    ADD CONSTRAINT FK_CAR_ON_MODEL_MODEL FOREIGN KEY (model_model_id) REFERENCES models (model_id) ON DELETE CASCADE;

ALTER TABLE models
    ADD CONSTRAINT FK_MODEL_ON_MAKE_MAKE FOREIGN KEY (make_make_id) REFERENCES makes (make_id) ON DELETE CASCADE;

ALTER TABLE cars_categories
    ADD CONSTRAINT fk_car_categories_on_car FOREIGN KEY (car_id) REFERENCES cars (object_id) ON DELETE CASCADE;