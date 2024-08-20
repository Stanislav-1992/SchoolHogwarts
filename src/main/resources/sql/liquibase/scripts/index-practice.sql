--liquibase formatted sql

-- changeset styulkov:1
CREATE INDEX student_name ON student (name);
-- changeset styulkov:2
CREATE INDEX faculty_name_color ON faculty (name, color);