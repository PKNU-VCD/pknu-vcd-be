CREATE TABLE IF NOT EXISTS admin
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS guest_book_entry
(
    id         SERIAL PRIMARY KEY,
    content    TEXT         NOT NULL,
    client_ip  VARCHAR(50)  NOT NULL,
    banned     BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS project
(
    id                SERIAL PRIMARY KEY,
    designer_email    VARCHAR(255) NOT NULL,
    designer_name_kr  VARCHAR(255) NOT NULL,
    designer_name_en  VARCHAR(255) NOT NULL,
    project_name_kr   VARCHAR(255) NOT NULL,
    project_name_en   VARCHAR(255),
    description_kr    TEXT         NOT NULL,
    description_en    TEXT,
    thumbnail_url     VARCHAR(255) NOT NULL,
    categories_string VARCHAR(500) NOT NULL,
    created_at        TIMESTAMP(6) NOT NULL,
    updated_at        TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS project_file
(
    id         SERIAL PRIMARY KEY,
    project_id BIGINT       NOT NULL,
    file_url   VARCHAR(255) NOT NULL,
    display_order  INTEGER      NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
);