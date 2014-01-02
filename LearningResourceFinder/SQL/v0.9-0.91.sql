-- JOHN 2014-01-01

alter table competence 
        add column vraisforumpage varchar(255);
        
ALTER TABLE competence
   ALTER COLUMN code TYPE character varying(10);
      
   
   
ALTER TABLE competence_resource
  RENAME TO resource_competence;
ALTER TABLE resource_competence RENAME resources_id  TO resource_id;
  
  
-- DEPLOYED
-- DEPLOYED
-- DEPLOYED
-- DEPLOYED
-- DEPLOYED
