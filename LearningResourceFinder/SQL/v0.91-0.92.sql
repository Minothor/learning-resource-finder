-- 2014-04-17

    alter table resource 
        add column maxcycle_id int8;

    alter table resource 
        add column mincycle_id int8;

    alter table resource 
        add constraint fk_ksqun4hna530skwqnvy8sglje 
        foreign key (maxcycle_id) 
        references cycle;

    alter table resource 
        add constraint fk_8l3ue6wkeqbojv9m6g0p1014d 
        foreign key (mincycle_id) 
        references cycle;
        