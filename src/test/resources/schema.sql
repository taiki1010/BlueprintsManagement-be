CREATE TABLE IF NOT EXISTS sites (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(161) NOT NULL,
    remark VARCHAR(200) DEFAULT NULL,
    is_deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS blueprints (
    id VARCHAR(36) PRIMARY KEY,
    site_id VARCHAR(36) NOT NULL,
    name VARCHAR(20) NOT NULL,
    is_deleted TINYINT DEFAULT 0,
    CONSTRAINT fk_site FOREIGN KEY (site_id) REFERENCES sites(id)
);

CREATE TABLE IF NOT EXISTS architectural_drawings(
    id VARCHAR(36) PRIMARY KEY,
    blueprint_id VARCHAR(36) NOT NULL,
    created_at Date,
    file_path TEXT,
    is_deleted TINYINT DEFAULT 0,
    CONSTRAINT fk_blueprint FOREIGN KEY (blueprint_id) REFERENCES blueprints(id)
);
