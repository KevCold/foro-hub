-- V5__clean_and_add_unique_constraint_to_topico_titulo.sql
ALTER TABLE topico ADD CONSTRAINT UK_topico_titulo UNIQUE (titulo);
