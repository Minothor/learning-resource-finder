-- v0.3 to v0.4

        
---- Ahmed 2013-09-27

ALTER TABLE competence
   ALTER COLUMN name TYPE character varying(255);
   
---- John 2013-09-29

alter table resource 
        add column topic varchar(20);