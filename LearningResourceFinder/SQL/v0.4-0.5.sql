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

---- Julien 2013-10-09

CREATE TABLE discussion
(
  id bigint NOT NULL,
  createdon timestamp without time zone,
  updatedon timestamp without time zone,
  message text NOT NULL,
  createdby_id bigint,
  updatedby_id bigint,
  problem_id bigint,
  CONSTRAINT discussion_pkey PRIMARY KEY (id),
  CONSTRAINT fk_64c8p2264i0wkgfvr3kponjht FOREIGN KEY (updatedby_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_6ynxjag4dmgajl7tygossaiw FOREIGN KEY (problem_id)
      REFERENCES problem (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ohin2xg4qy49afti4qr47l3xq FOREIGN KEY (createdby_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

