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


>>>>>>>>>>>>>>>>>>>>>>  DEPLOYED = STOP USING THIS FILE
>>>>>>>>>>>>>>>>>>>>>>  DEPLOYED = STOP USING THIS FILE
>>>>>>>>>>>>>>>>>>>>>>  DEPLOYED = STOP USING THIS FILE
>>>>>>>>>>>>>>>>>>>>>>  DEPLOYED = STOP USING THIS FILE
>>>>>>>>>>>>>>>>>>>>>>  DEPLOYED = STOP USING THIS FILE
>>>>>>>>>>>>>>>>>>>>>>  DEPLOYED = STOP USING THIS FILE
>>>>>>>>>>>>>>>>>>>>>>  DEPLOYED = STOP USING THIS FILE