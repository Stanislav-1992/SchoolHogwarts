--liquibase formatted sql

-- changeSet styulkov:1
CREATE INDEX student_name ON student (name);
-- changeSet styulkov:2
CREATE INDEX faculty_name_color ON faculty (name, color);