-- 2014-08-07 Ahmed Zarioh

    alter table resource 
        add column validationdate timestamp;

    alter table resource 
        add column validationstatus varchar(255);

    alter table resource 
        add column validator_id int8;

    alter table resource 
        add constraint fk_qfst4snpxl8n1lq9fet5bcglw 
        foreign key (validator_id) 
        references users;
        
     update resource SET validationstatus = 'WAITING';
     
--2014-08-07 Alain Georges
     
      alter table users 
        add column usertype varchar(255);
        
	update users SET usertype = 'ADULT';
	
-- 2014-08-08 Ahmed Zarioh
        
     update resource SET validationstatus = NULL WHERE validationstatus = 'WAITING';
     
-- 2014-08-11 Pierre


    create table contribution (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        elementtype int4 not null,
        points int4 not null,
        createdby_id int8,
        updatedby_id int8,
        ressource_id int8,
        user_id int8,
        primary key (id)
    );

    alter table contribution 
        add constraint fk_kp6mum79s0h3tu2qwtg7l30q7 
        foreign key (createdby_id) 
        references users;

    alter table contribution 
        add constraint fk_f9enoakmxxk45ije9wj67bhv2 
        foreign key (updatedby_id) 
        references users;

    alter table contribution 
        add constraint fk_s5uik2st9kii1mdy1mevm5bom 
        foreign key (ressource_id) 
        references resource;

    alter table contribution 
        add constraint fk_9lptqf2a3s1s7eksjp9wd4pof 
        foreign key (user_id) 
        references users;

    ALTER TABLE contribution
        ADD UNIQUE (ressource_id, user_id, elementtype);

    alter table contribution 
        add column action int4 not null;
        
-- 2014-08-11 Ramzi G.

	alter table playlist_resource 
		add column list_index int4;   
	
	update playlist_resource pr set list_index = i-1 
		from (select playlist_id, resources_id, row_number() over (partition by playlist_id) as i from playlist_resource) s 
		where s.playlist_id=pr.playlist_id and s.resources_id=pr.resources_id;	
	
	delete from rating
		where id in (select id
		              from (select row_number() 
					over (partition by resource_id, user_id order by id) as rownum, id
		                     FROM rating) t
		              where t.rownum > 1);
		
	alter table rating 
		alter column resource_id set not null;
		
	alter table rating 
		alter column user_id set not null;
		
	alter table rating 
		add unique (resource_id, user_id);	
		
-- 2014-08-12 Pierre G.	
		alter table contribution drop column elementtype;
		
		
        
-- 2014-08-11  Redouane

    alter table resource 
        add column platformscollonstring varchar(255); 

        
EXECUTED ON SERVER 2014-08-29 ******************
EXECUTED ON SERVER 2014-08-29 ******************
EXECUTED ON SERVER 2014-08-29 ******************
EXECUTED ON SERVER 2014-08-29 ******************
EXECUTED ON SERVER 2014-08-29 ******************
