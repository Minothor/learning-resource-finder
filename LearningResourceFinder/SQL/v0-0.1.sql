
-- Thomas D 2013-08-09  14h00  

create table url_resource (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        name varchar(50),
        url varchar(255),
        createdby_id int8,
        updatedby_id int8,
        resource_id int8,
        primary key (id)
    );

    alter table url_resource 
        add constraint fkd214dcde9ae62ac7 
        foreign key (createdby_id) 
        references users;

    alter table url_resource 
        add constraint fkd214dcdebf94315b 
        foreign key (resource_id) 
        references resource;

    alter table url_resource 
        add constraint fkd214dcde750f4d4 
        foreign key (updatedby_id) 
        references users;
        
        
-- Thomas D 2013-08-09 15h30

        create table urlresource (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        name varchar(50),
        url varchar(255),
        createdby_id int8,
        updatedby_id int8,
        resource_id int8,
        primary key (id)
    );

    alter table urlresource 
        add constraint fk4dc4aa7d9ae62ac7 
        foreign key (createdby_id) 
        references users;

    alter table urlresource 
        add constraint fk4dc4aa7dbf94315b 
        foreign key (resource_id) 
        references resource;

    alter table urlresource 
        add constraint fk4dc4aa7d750f4d4 
        foreign key (updatedby_id) 
        references users;
        
        
        
-- Ibrahim 2013-08-20

    alter table playlist 
        add column slug varchar(50);

    alter table resource 
        add column slug varchar(50);
        
        