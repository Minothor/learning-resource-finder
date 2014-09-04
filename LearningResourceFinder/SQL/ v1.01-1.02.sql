  -- 2014-09-04 Faissal

    create table urlgeneric (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        url varchar(255) not null,
        createdby_id int8,
        updatedby_id int8,
        primary key (id)
    );

    alter table urlgeneric 
        add constraint fk_5yvlke9bugwep02jdhvxh8gyq 
        foreign key (createdby_id) 
        references users;

    alter table urlgeneric 
        add constraint fk_js17rf45aesvw4vm040dwwjks 
        foreign key (updatedby_id) 
        references users;