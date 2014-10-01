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
