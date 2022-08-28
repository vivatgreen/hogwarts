CREATE TABLE driverbase
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    age     SMALLINT NOT NULL CHECK (age > 0),
    license BOOLEAN  NOT NULL DEFAULT false,
    car_id  INTEGER REFERENCES carbase (id)
);

CREATE TABLE carbase
(
    id    SERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    cost  BIGINT     NOT NULL CHECK (cost > 0)
);