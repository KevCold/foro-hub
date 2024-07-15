CREATE TABLE curso (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(100) NOT NULL,
                       categoria VARCHAR(100) NOT NULL
);

CREATE TABLE perfil (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL
);

CREATE TABLE usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         correo_electronico VARCHAR(100) NOT NULL,
                         contrasena VARCHAR(100) NOT NULL
);

CREATE TABLE usuario_perfil (
                                usuario_id BIGINT,
                                perfil_id BIGINT,
                                PRIMARY KEY (usuario_id, perfil_id),
                                FOREIGN KEY (usuario_id) REFERENCES usuario(id),
                                FOREIGN KEY (perfil_id) REFERENCES perfil(id)
);

CREATE TABLE topico (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        titulo VARCHAR(100) NOT NULL UNIQUE,
                        mensaje TEXT NOT NULL,
                        fecha_creacion DATETIME NOT NULL,
                        status_topicos VARCHAR(50) NOT NULL,
                        autor_id BIGINT,
                        curso_id BIGINT,
                        activo TINYINT DEFAULT 1,
                        FOREIGN KEY (autor_id) REFERENCES usuario(id),
                        FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE respuesta (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           mensaje TEXT NOT NULL,
                           fecha_creacion DATETIME NOT NULL,
                           activo TINYINT DEFAULT 1,
                           solucion TINYINT DEFAULT 0,
                           autor_id BIGINT,
                           topico_id BIGINT,
                           FOREIGN KEY (autor_id) REFERENCES usuario(id),
                           FOREIGN KEY (topico_id) REFERENCES topico(id)
);
