-- v0.3 to v0.4

        
---- Ahmed 2013-09-27

ALTER TABLE competence
   ALTER COLUMN name TYPE character varying(255);
   
---- John 2013-09-29

alter table resource 
        add column topic varchar(20);

---- Emile 2013-09-30
alter table resource 
        add column counter int8;  
        
ALTER TABLE resource RENAME score  TO avgratingscore;
ALTER TABLE resource RENAME counter  TO countrating;


---- Julien 2013-10-09 bis

CREATE TABLE discussion
(
  id bigint NOT NULL,
  createdon timestamp without time zone,
  updatedon timestamp without time zone,
  message text NOT NULL,
  createdby_id bigint,
  updatedby_id bigint,
  problem_id bigint,
  CONSTRAINT discussion_pkey PRIMARY KEY (id)
);

    alter table discussion 
        add constraint fk_64c8p2264i0wkgfvr3kponjht 
        foreign key (updatedby_id) 
        references users;
        
    alter table discussion 
        add constraint fk_6ynxjag4dmgajl7tygossaiw 
        foreign key (problem_id) 
        references problem;

    alter table discussion 
        add constraint fk_ohin2xg4qy49afti4qr47l3xq 
        foreign key (createdby_id) 
        references users;
        
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE
-- DEPLOYED, DO NOT USE THIS FILE ANYMORE

        
