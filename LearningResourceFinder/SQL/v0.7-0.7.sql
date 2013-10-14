-- Thomas Delizée Le 2013-10-14


    create table favorite (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        createdby_id int8,
        updatedby_id int8,
        resource_id int8,
        user_id int8,
        primary key (id)
    );

    create table users_resource (
        users_id int8 not null,
        favorites_id int8 not null,
        primary key (users_id, favorites_id)
    );

    alter table users_resource 
        add constraint uk_5canmovpj5u1xhlnolsaja4xk unique (favorites_id);

    alter table favorite 
        add constraint fk_6lihail9ogbvgs5cnc0d82b4s 
        foreign key (createdby_id) 
        references users;

    alter table favorite 
        add constraint fk_59kye7ev7t2gbnibev4gnv40o 
        foreign key (updatedby_id) 
        references users;

    alter table favorite 
        add constraint fk_f5di74fb4qlxwgmvhgwimuaim 
        foreign key (resource_id) 
        references resource;

    alter table favorite 
        add constraint fk_9uj2ws7rmgr0d16c40sm8uo10 
        foreign key (user_id) 
        references users;

    alter table users_resource 
        add constraint fk_5canmovpj5u1xhlnolsaja4xk 
        foreign key (favorites_id) 
        references resource;

    alter table users_resource 
        add constraint fk_qs8gh92wph8nvxwm8yh5jb1d 
        foreign key (users_id) 
        references users;