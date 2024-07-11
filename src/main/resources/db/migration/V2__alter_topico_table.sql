-- V2__alter_topico_table.sql
ALTER TABLE Topico
    CHANGE autor autor_id INT NOT NULL,
    CHANGE curso curso_id INT NOT NULL;
