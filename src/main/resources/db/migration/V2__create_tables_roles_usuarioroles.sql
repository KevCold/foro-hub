-- Crear la tabla roles si no existe
CREATE TABLE IF NOT EXISTS roles (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL UNIQUE
    );

-- Insertar el rol "USUARIO" si no existe
INSERT INTO roles (name) VALUES ('USUARIO')
    ON DUPLICATE KEY UPDATE id=id;

-- Crear la tabla usuario_roles
CREATE TABLE IF NOT EXISTS usuario_roles (
                                             usuario_id BIGINT NOT NULL,
                                             role_id BIGINT NOT NULL,
                                             PRIMARY KEY (usuario_id, role_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
    );

-- Asignar el rol "USUARIO" a todos los usuarios existentes
INSERT INTO usuario_roles (usuario_id, role_id)
SELECT u.id, r.id
FROM usuario u, roles r
WHERE r.name = 'USUARIO';
