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
            
-- Julien (John) 2013-09-16  
    alter table competence 
        add column cycle_id int8;

    alter table competence 
        add constraint fk_jp9dw79j8hfwljeiixqwc6gqf 
        foreign key (cycle_id) 
        references cycle;
        
-- Thomas 2013-09-16
ALTER TABLE resource ADD COLUMN nature character varying(255); 

-- Emile 2013-09-27
alter table resource 
        add column score float8; 
        
-- Julien 2013-09-27
        
        create table rating (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        score float8,
        createdby_id int8,
        updatedby_id int8,
        resource_id int8,
        user_id int8,
        primary key (id)
    );

    alter table rating 
        add constraint fk_echhg31lrj7xqwilu1quu8vo3 
        foreign key (createdby_id) 
        references users;

    alter table rating 
        add constraint fk_kk591y4orkmfaaf90sw579f0h 
        foreign key (updatedby_id) 
        references users;

    alter table rating 
        add constraint fk_4qubpqyyepfhty50lwce7wa82 
        foreign key (resource_id) 
        references resource;

    alter table rating 
        add constraint fk_e04g3baa3n3ufi0m3nrmm17r6 
        foreign key (user_id) 
        references users;
        
-- déployement effectué, merci d'indiquer les nouveaux scripts dans le fichier v0.3-0.4.sql
        