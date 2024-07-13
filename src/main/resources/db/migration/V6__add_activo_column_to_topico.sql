-- V6__add_activo_column_to_topico.sql
ALTER TABLE topico ADD COLUMN activo TINYINT DEFAULT 1;
UPDATE topico SET activo = 1;
