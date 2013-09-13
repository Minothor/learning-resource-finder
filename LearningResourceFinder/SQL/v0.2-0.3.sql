-- v0.2 to v0.3

-- Julien 2013-09-13 13h : If this competence record with no parent is not present in in your LRF DB, InitializeDB batch do this
INSERT INTO competence(
            id, createdon, updatedon, code, description, name, createdby_id, 
            updatedby_id, parent_id)
    VALUES (nextval('hibernate_sequence'), null, null, 'Les', null, 'Compétences', null , null
            , null);
            
-- Thomas 2013-09-13 15h30

ALTER TABLE resource ADD COLUMN advertising boolean;
ALTER TABLE resource ADD COLUMN duration integer;
ALTER TABLE resource ADD COLUMN format character varying(255);
ALTER TABLE resource ADD COLUMN language character varying(255);
ALTER TABLE resource ADD COLUMN platform character varying(255);
