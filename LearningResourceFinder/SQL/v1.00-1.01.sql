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