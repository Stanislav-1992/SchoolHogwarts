CREATE TABLE person
(
    id SERIAL PRIMARY KEY,
    name TEXT,
    age SMALLINT,
    driving_license BOOLEAN,
    car_id INTEGER REFERENCES car (id)
)

CREATE TABLE car
(
id SERIAL PRIMARY KEY,
brand TEXT,
model TEXT,
price NUMERIC
)