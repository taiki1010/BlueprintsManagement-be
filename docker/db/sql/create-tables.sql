CREATE TABLE IF NOT EXISTS sites (
    id varchar(36) NOT NULL,
    name varchar(50) NOT NULL,
    address varchar(161) NOT NULL,
    remark varchar(200) DEFAULT NULL,
    is_deleted tinyint(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS blueprints (
    id varchar(36) NOT NULL,
    sites_id varchar(36) NOT NULL,
    name varchar(20) NOT NULL,
    is_deleted tinyint(1) NOT NULL DEFAULT 0,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS architectural_drawings(
    id varchar(36) NOT NULL,
    blueprints_id varchar(36) NOT NULL,
    created_at DATE,
    file_path TEXT,
    is_deleted tinyint(1) NOT NULL DEFAULT 0,
    PRIMARY KEY(id)
);
