-- 2014-09-26 Vincent -- Change cycles ids (so they follow a sequence)
  
-- Remove foreign keys
alter table resource   drop constraint fk_ksqun4hna530skwqnvy8sglje;
alter table resource   drop constraint fk_8l3ue6wkeqbojv9m6g0p1014d;
alter table competence drop constraint fk_jp9dw79j8hfwljeiixqwc6gqf;

-- Clear table cycle
delete from cycle;

-- BEFORE : 300 P1-2, 303 P3-4 302 P5-6, 304 S1-2, 305 S3-6
-- AFTER  : 300 P1-2, 301 P3-4 302 P5-6, 303 S1-2, 304 S3-6
insert into cycle values(300, clock_timestamp(), NULL, 'P1-2', 150, NULL);
insert into cycle values(301, clock_timestamp(), NULL, 'P3-4', 150, NULL);
insert into cycle values(302, clock_timestamp(), NULL, 'P5-6', 150, NULL);
insert into cycle values(303, clock_timestamp(), NULL, 'S1-2', 150, NULL);
insert into cycle values(304, clock_timestamp(), NULL, 'S3-6', 150, NULL);

-- 303 ==> 301
update resource set maxcycle_id = 301 where maxcycle_id = 303;
update resource set mincycle_id = 301 where mincycle_id = 303;
update competence set cycle_id = 301 where cycle_id = 303;

-- 304 ==> 303
update resource set maxcycle_id = 303 where maxcycle_id = 304;
update resource set mincycle_id = 303 where mincycle_id = 304;
update competence set cycle_id = 303 where cycle_id = 304;

-- 305 ==> 304
update resource set maxcycle_id = 304 where maxcycle_id = 305;
update resource set mincycle_id = 304 where mincycle_id = 305;
update competence set cycle_id = 304 where cycle_id = 305;

-- Add foreign keys
alter table resource 
    add constraint fk_ksqun4hna530skwqnvy8sglje 
    foreign key (maxcycle_id) 
    references cycle;

alter table resource 
    add constraint fk_8l3ue6wkeqbojv9m6g0p1014d 
    foreign key (mincycle_id) 
    references cycle;

alter table competence 
        add constraint fk_jp9dw79j8hfwljeiixqwc6gqf 
        foreign key (cycle_id) 
        references cycle;

        
-- 2014-09-29 Lionel -- create new field (viewcount) and insert default value (0)
        
ALTER TABLE resource
ADD viewcount bigint NOT NULL
CONSTRAINT viewcountconstraint DEFAULT 0;


-- 2014-09-30 create new field (popularity) and insert default value (0)
ALTER TABLE resource
ADD popularity double precision NOT NULL
CONSTRAINT popularityconstraint DEFAULT 0;

-- 2014-09-30 Vincent -- Remove link between tables competence (table containing all the new categories) and cycle

-- Remove all foreign keys for competence
alter table competence drop constraint fk_1wk32xsu4uv1t7ifn3kmkgl7q;
alter table competence drop constraint fk_2e6smrkx5e38he28jmo39okmi;
alter table competence drop constraint fk_6qfnv43sclpe4kiwpio6483yk;
alter table competence drop constraint fk_jp9dw79j8hfwljeiixqwc6gqf;
alter table competence drop constraint uk_qv4oxfirnlcs493nj2scebk2e;

alter table resource_competence drop constraint fk_fsaflsfswkcnlm81cjhhfpc12;

alter table competence drop constraint competence_pkey;

-- Rename competence to keep a backup
alter table competence rename to competence_backup;

-- Create new table competence (same than previous without fields cycle_id & vraisforumpage) 
create table competence
(
  id bigint not NULL,
  createdon timestamp without time zone,
  updatedon timestamp without time zone,
  code character varying(10) not NULL,
  description text,
  name character varying(255),
  createdby_id bigint,
  updatedby_id bigint,
  parent_id bigint,
  constraint competence_pkey primary key (id),
  constraint fk_1wk32xsu4uv1t7ifn3kmkgl7q foreign key (createdby_id)
      references users (id) match SIMPLE
      on update no action on delete no action,
  constraint fk_2e6smrkx5e38he28jmo39okmi foreign key (updatedby_id)
      references users (id) match SIMPLE
      on update no action on delete no action,
  constraint fk_6qfnv43sclpe4kiwpio6483yk foreign key (parent_id)
      references competence (id) match SIMPLE
      on update no action on delete no action,
  constraint uk_qv4oxfirnlcs493nj2scebk2e unique (code)
)
with (
  OIDS=FALSE
);

alter table competence
  owner to lrfuser;

-- Clear table resource_competence (as table competence is now empty. It will be filled later with new categories) 
delete from resource_competence;

-- Add again foreign key
alter table resource_competence 
        add constraint fk_fsaflsfswkcnlm81cjhhfpc12 
        foreign key (competences_id) 
        references competence;
        
 
        
-- 2014-10-01 Alain Faissal create main competences (root, Français...)
DO $$

DECLARE v_ROOT integer;
DECLARE v_FR integer;
DECLARE v_CM integer;
DECLARE v_ES integer;
DECLARE v_EG integer;
DECLARE v_EA integer;
DECLARE v_EH integer;
DECLARE curtime timestamp := now();

BEGIN
select nextval('hibernate_sequence') INTO v_ROOT ; 
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_ROOT, curtime, NULL, 'ROOT', NULL, 'Root', NULL, NULL, NULL);
select nextval('hibernate_sequence') INTO v_FR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR, curtime, NULL, 'FR', NULL, 'Français', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_CM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM, curtime, NULL, 'CM', NULL, 'Calcul et Mathématiques', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_ES ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_ES, curtime, NULL, 'ES', NULL, 'Eveil scientifique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG, curtime, NULL, 'EG', NULL, 'Eveil géographique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA, curtime, NULL , 'EA', NULL, 'Eveil artistique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EH ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH, curtime, NULL, 'EH', NULL, 'Eveil historique', NULL, NULL, v_ROOT);
END $$;
        
-- 2014-10-01 Ramzi
DROP TABLE task;
drop table users_resource ;      

-- 2014-10-03 Ramzi
alter table urlresource 
drop constraint fk_46tgpros9mvmhgsq16r5kl1pb,
add CONSTRAINT fk_46tgpros9mvmhgsq16r5kl1pb FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table contribution 
drop constraint fk_s5uik2st9kii1mdy1mevm5bom,
add CONSTRAINT fk_s5uik2st9kii1mdy1mevm5bom FOREIGN KEY (ressource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table rating
drop constraint fk_4qubpqyyepfhty50lwce7wa82,
add CONSTRAINT fk_4qubpqyyepfhty50lwce7wa82 FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table favorite
drop constraint fk_f5di74fb4qlxwgmvhgwimuaim,
add CONSTRAINT fk_f5di74fb4qlxwgmvhgwimuaim FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table playlist_resource
drop constraint fk_i42f3fdaj4urawyot4yfbjdam,
add CONSTRAINT fk_i42f3fdaj4urawyot4yfbjdam FOREIGN KEY (resources_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table resource_competence
drop constraint fk_79qwnp8edqv0wb8jspfbt5cwn,
add CONSTRAINT fk_79qwnp8edqv0wb8jspfbt5cwn FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table problem
drop constraint fk_kroba1edmwvbl1yuqoaqgly6w,
add CONSTRAINT fk_kroba1edmwvbl1yuqoaqgly6w FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table discussion
drop constraint fk_6ynxjag4dmgajl7tygossaiw,
add CONSTRAINT fk_6ynxjag4dmgajl7tygossaiw FOREIGN KEY (problem_id)
      REFERENCES problem (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;