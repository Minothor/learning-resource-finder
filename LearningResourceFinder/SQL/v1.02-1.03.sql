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