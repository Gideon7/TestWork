CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  hash_password VARCHAR(255),
  username VARCHAR(255),
  role VARCHAR(255)
);

CREATE TABLE tokens (
  id BIGSERIAL PRIMARY KEY,
  value VARCHAR(255),
  user_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE jogs_info (
  id BIGSERIAL PRIMARY KEY,
  date DATE,
  distance DOUBLE PRECISION,
  time TIME,
  user_id BIGINT NOT NULL REFERENCES users(id)
);