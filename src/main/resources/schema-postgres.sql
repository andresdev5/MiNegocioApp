DO
'
BEGIN
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname = ''users_identification_type'') THEN
        DROP TYPE users_identification_type CASCADE;
    END IF;
END;
' LANGUAGE PLPGSQL;

CREATE TYPE users_identification_type AS ENUM ('CI', 'RUC', 'PASSPORT');

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    identification_type users_identification_type NOT NULL,
    identification_value varchar(32) NOT NULL,
    names VARCHAR(64),
    email VARCHAR(64) UNIQUE,
    phone VARCHAR(16),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(identification_type, identification_value)
);

DROP TABLE IF EXISTS provinces CASCADE;
CREATE TABLE provinces(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    UNIQUE(name)
);

DROP TABLE IF EXISTS cities CASCADE;
CREATE TABLE cities(
    id SERIAL PRIMARY KEY,
    province_id INTEGER NOT NULL,
    name VARCHAR(64) NOT NULL,
    UNIQUE(name, province_id),
    FOREIGN KEY (province_id) REFERENCES provinces(id)
);

DROP TABLE IF EXISTS addresses CASCADE;
CREATE TABLE addresses(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    province_id INTEGER NOT NULL,
    city_id INTEGER NOT NULL,
    description VARCHAR(128) NOT NULL,
    is_main BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (province_id) REFERENCES provinces(id),
    FOREIGN KEY (city_id) REFERENCES cities(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE(user_id, province_id, city_id, description)
);
