CREATE TABLE users (
    "id"         BIGSERIAL   NOT NULL PRIMARY KEY,
    "first_name" VARCHAR     NOT NULL,
    "last_name"  VARCHAR     NOT NULL,
    "email"      VARCHAR     NOT NULL UNIQUE,
    "password"   VARCHAR     NOT NULL,
    "username"   VARCHAR     NOT NULL UNIQUE,
    "role"       VARCHAR     NOT NULL,
    "active"     BOOLEAN     DEFAULT  true,
    "created"    timestamptz DEFAULT  CURRENT_TIMESTAMP,
    "modified"   timestamptz
);

CREATE TABLE roles (
    "id"       BIGSERIAL   PRIMARY KEY,
    "name"     VARCHAR     NOT NULL,
    "created"  timestamptz DEFAULT CURRENT_TIMESTAMP,
    "modified" timestamptz
);

CREATE TABLE permissions (
    "id"       BIGSERIAL   PRIMARY KEY,
    "name"     VARCHAR     NOT NULL,
    "created"  timestamptz DEFAULT CURRENT_TIMESTAMP,
    "modified" timestamptz
);

-- relations
CREATE TABLE user_role_relations (
    "user_id" BIGSERIAL   NOT NULL,
    "role_id" BIGSERIAL   NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles ON DELETE CASCADE
);
