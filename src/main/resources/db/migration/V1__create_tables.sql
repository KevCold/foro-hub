-- Crear la tabla Usuario
CREATE TABLE Usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(255) NOT NULL,
                         correo_electronico VARCHAR(255) NOT NULL UNIQUE,
                         contrasena VARCHAR(255) NOT NULL
);

-- Crear la tabla Perfil
CREATE TABLE Perfil (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(255) NOT NULL
);

-- Crear la tabla Curso
CREATE TABLE Curso (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(255) NOT NULL,
                       categoria VARCHAR(255) NOT NULL
);

-- Crear la tabla Tópico
CREATE TABLE Topico (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        titulo VARCHAR(255) NOT NULL,
                        mensaje TEXT NOT NULL,
                        fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(50) NOT NULL,
                        autor INT NOT NULL,
                        curso INT NOT NULL,
                        FOREIGN KEY (autor) REFERENCES Usuario(id),
                        FOREIGN KEY (curso) REFERENCES Curso(id)
);

-- Crear la tabla Respuesta
CREATE TABLE Respuesta (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           mensaje TEXT NOT NULL,
                           topico INT NOT NULL,
                           fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           autor INT NOT NULL,
                           solucion BOOLEAN NOT NULL DEFAULT FALSE,
                           FOREIGN KEY (topico) REFERENCES Topico(id),
                           FOREIGN KEY (autor) REFERENCES Usuario(id)
);

-- Crear la tabla para la relación entre Usuario y Perfil
CREATE TABLE Usuario_Perfil (
                                usuario_id INT NOT NULL,
                                perfil_id INT NOT NULL,
                                PRIMARY KEY (usuario_id, perfil_id),
                                FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
                                FOREIGN KEY (perfil_id) REFERENCES Perfil(id)
);