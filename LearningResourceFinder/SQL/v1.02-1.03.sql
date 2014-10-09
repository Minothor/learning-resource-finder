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
        
 
        
-- 2014-10-01 Alain Faissal create main competences (root, Français...)
DO $$

DECLARE v_ROOT integer;
DECLARE v_FR integer;
DECLARE v_CM integer;
DECLARE v_ES integer;
DECLARE v_EG integer;
DECLARE v_EA integer;
DECLARE v_EH integer;
DECLARE curtime timestamp := now();

BEGIN
select nextval('hibernate_sequence') INTO v_ROOT ; 
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_ROOT, curtime, NULL, 'ROOT', NULL, 'Root', NULL, NULL, NULL);
select nextval('hibernate_sequence') INTO v_FR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR, curtime, NULL, 'FR', NULL, 'Français', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_CM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM, curtime, NULL, 'CM', NULL, 'Calcul et Mathématiques', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_ES ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_ES, curtime, NULL, 'ES', NULL, 'Eveil scientifique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG, curtime, NULL, 'EG', NULL, 'Eveil géographique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA, curtime, NULL , 'EA', NULL, 'Eveil artistique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EH ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH, curtime, NULL, 'EH', NULL, 'Eveil historique', NULL, NULL, v_ROOT);
END $$;
        
-- 2014-10-01 Ramzi
DROP TABLE task;
drop table users_resource ;      

-- 2014-10-03 Ramzi
alter table urlresource 
drop constraint fk_46tgpros9mvmhgsq16r5kl1pb,
add CONSTRAINT fk_46tgpros9mvmhgsq16r5kl1pb FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table contribution 
drop constraint fk_s5uik2st9kii1mdy1mevm5bom,
add CONSTRAINT fk_s5uik2st9kii1mdy1mevm5bom FOREIGN KEY (ressource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table rating
drop constraint fk_4qubpqyyepfhty50lwce7wa82,
add CONSTRAINT fk_4qubpqyyepfhty50lwce7wa82 FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table favorite
drop constraint fk_f5di74fb4qlxwgmvhgwimuaim,
add CONSTRAINT fk_f5di74fb4qlxwgmvhgwimuaim FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table playlist_resource
drop constraint fk_i42f3fdaj4urawyot4yfbjdam,
add CONSTRAINT fk_i42f3fdaj4urawyot4yfbjdam FOREIGN KEY (resources_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table resource_competence
drop constraint fk_79qwnp8edqv0wb8jspfbt5cwn,
add CONSTRAINT fk_79qwnp8edqv0wb8jspfbt5cwn FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table problem
drop constraint fk_kroba1edmwvbl1yuqoaqgly6w,
add CONSTRAINT fk_kroba1edmwvbl1yuqoaqgly6w FOREIGN KEY (resource_id)
      REFERENCES resource (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;

alter table discussion
drop constraint fk_6ynxjag4dmgajl7tygossaiw,
add CONSTRAINT fk_6ynxjag4dmgajl7tygossaiw FOREIGN KEY (problem_id)
      REFERENCES problem (id) MATCH SIMPLE
      ON UPDATE NO ACTION on delete cascade;
      
      
-- 2014-10-07 alain full DB insert categories
 

      
delete from resource_competence;
delete from competence;
      
DO $$

DECLARE v_ROOT integer;

DECLARE v_FR integer;

DECLARE v_FR01 integer;
DECLARE v_FR01AL integer;
DECLARE v_FR01AL1 integer;
DECLARE v_FR01AL2 integer;
DECLARE v_FR01PO integer;
DECLARE v_FR01SYL integer;
DECLARE v_FR01HOM integer;
DECLARE v_FR01HOM1 integer;
DECLARE v_FR01HOM2 integer;
DECLARE v_FR01HOM3 integer;
DECLARE v_FR01CH integer;
DECLARE v_FR01CH1 integer;
DECLARE v_FR01CH2 integer;

DECLARE v_FR02 integer;
DECLARE v_FR02ART integer;
DECLARE v_FR02ART1 integer;
DECLARE v_FR02ART2 integer;
DECLARE v_FR02NOM integer;
DECLARE v_FR02ADJ integer;
DECLARE v_FR02ADJ1 integer;
DECLARE v_FR02ADJ2 integer;
DECLARE v_FR02ADJ3 integer;
DECLARE v_FR02FEM integer;
DECLARE v_FR02PRL integer;
DECLARE v_FR02VER integer;
DECLARE v_FR02PRN integer;
DECLARE v_FR02PRN1 integer;
DECLARE v_FR02PRN2 integer;
DECLARE v_FR02PRN3 integer;
DECLARE v_FR02PRP integer;
DECLARE v_FR02CON integer;
DECLARE v_FR02CON1 integer;
DECLARE v_FR02CON2 integer;
DECLARE v_FR02ADV integer;
DECLARE v_FR02INT integer;
DECLARE v_FR02PFX integer;
DECLARE v_FR02SFX integer;
DECLARE v_FR02SYN integer;

DECLARE v_FR03 integer;
DECLARE v_FR03SUJ integer;
DECLARE v_FR03VRB integer;
DECLARE v_FR03CMP integer;
DECLARE v_FR03CMP1 integer;
DECLARE v_FR03CMP2 integer;
DECLARE v_FR03PRO integer;
DECLARE v_FR03PRO1 integer;
DECLARE v_FR03PRO2 integer;
DECLARE v_FR03CPL integer;
DECLARE v_FR03CPL1 integer;
DECLARE v_FR03CPL2 integer;
DECLARE v_FR03CPL3 integer;

DECLARE v_FR04 integer;
DECLARE v_FR04IND integer;
DECLARE v_FR04IND1 integer;
DECLARE v_FR04IND2 integer;
DECLARE v_FR04IND3 integer;
DECLARE v_FR04IND4 integer;
DECLARE v_FR04IND5 integer;
DECLARE v_FR04IND6 integer;
DECLARE v_FR04IND7 integer;
DECLARE v_FR04IND8 integer;
DECLARE v_FR04IMP integer;
DECLARE v_FR04IMP1 integer;
DECLARE v_FR04IMP2 integer;
DECLARE v_FR04CON integer;
DECLARE v_FR04CON1 integer;
DECLARE v_FR04CON2 integer;
DECLARE v_FR04SUB integer;
DECLARE v_FR04SUB1 integer;
DECLARE v_FR04SUB2 integer;
DECLARE v_FR04SUB3 integer;
DECLARE v_FR04SUB4 integer;
DECLARE v_FR04INF integer;
DECLARE v_FR04INF1 integer;
DECLARE v_FR04INF2 integer;
DECLARE v_FR04PAR integer;
DECLARE v_FR04PAR1 integer;
DECLARE v_FR04PAR2 integer;
DECLARE v_FR04PAR3 integer;
DECLARE v_FR04CDT integer;
DECLARE v_FR04ACT integer;
DECLARE v_FR04ETR integer;
DECLARE v_FR04AVO integer;
DECLARE v_FR04DIR integer;
DECLARE v_FR04VRE integer;
DECLARE v_FR04VIR integer;

DECLARE v_FR05 integer;
DECLARE v_FR05LIR integer;
DECLARE v_FR05PLA integer;

DECLARE v_FR06 integer;
DECLARE  v_FR06FAB integer;
DECLARE  v_FR06POE integer;
DECLARE  v_FR06ROM integer;
DECLARE  v_FR06ESS integer;
DECLARE  v_FR06LET integer;
DECLARE  v_FR06BIO integer;
DECLARE  v_FR06JLF integer;

DECLARE v_CM integer;
DECLARE v_CM01 integer;
DECLARE v_CM01CHI integer;
DECLARE v_CM01COM integer;
DECLARE v_CM01ENT integer;
DECLARE v_CM01DEC integer;
DECLARE v_CM01ARR integer;
DECLARE v_CM01PRE integer;
DECLARE v_CM01PAR integer;
DECLARE v_CM01RED integer;
DECLARE v_CM01POU integer;
DECLARE v_CM01ADD integer;
DECLARE v_CM01SOU integer;
DECLARE v_CM01MUL integer;
DECLARE v_CM01DIV integer;
DECLARE v_CM01TMU integer;
DECLARE v_CM01REG integer;
DECLARE v_CM02 integer;
DECLARE v_CM02MES integer;
DECLARE v_CM02MES1 integer;
DECLARE v_CM02MES2 integer;
DECLARE v_CM02MES3 integer;
DECLARE v_CM02MES4 integer;
DECLARE v_CM02COM integer;
DECLARE v_CM02COM1 integer;
DECLARE v_CM02COM2 integer;
DECLARE v_CM02COM3 integer;
DECLARE v_CM03 integer;
DECLARE v_CM03POI integer;
DECLARE v_CM03LIG integer;
DECLARE v_CM03QUA integer;
DECLARE v_CM03TRI integer;
DECLARE v_CM03CER integer;
DECLARE v_CM03POL integer;
DECLARE v_CM03PLA integer;
DECLARE v_CM03ANG integer;
DECLARE v_CM03SYM integer;
DECLARE v_CM04 integer;
DECLARE v_CM04CME integer;
DECLARE v_CM04ANA integer;
DECLARE v_CM04OPE integer;
DECLARE v_CM04COM integer;
DECLARE v_CM04EGA integer;
DECLARE v_CM04PAV integer;
DECLARE v_CM04PEN integer;
DECLARE v_CM04SPA integer;
DECLARE v_CM04ECH integer;
DECLARE v_CM04TAB integer;

DECLARE v_ES integer;
DECLARE v_ES01 integer;
DECLARE v_ES01ETA integer;
DECLARE v_ES01MET integer;
DECLARE v_ES01CRI integer;
DECLARE v_ES01PRO integer;
DECLARE v_ES01PRO1 integer;
DECLARE v_ES01PRO2 integer;
DECLARE v_ES02 integer;
DECLARE v_ES02CHA integer;
DECLARE v_ES02COU integer;
DECLARE v_ES02OMB integer;
DECLARE v_ES02ELE integer;
DECLARE v_ES02ELE01 integer;
DECLARE v_ES02ELE02 integer;
DECLARE v_ES02ELE03 integer;
DECLARE v_ES02TRA integer;
DECLARE v_ES02FOR integer;
DECLARE v_ES02OND integer;
DECLARE v_ES03 integer;
DECLARE v_ES03OEI integer;
DECLARE v_ES03DIG integer;
DECLARE v_ES03RES integer;
DECLARE v_ES03CIR integer;
DECLARE v_ES03REP integer;
DECLARE v_ES03CER integer;
DECLARE v_ES03DEN integer;
DECLARE v_ES03MUS integer;
DECLARE v_ES03ONG integer;
DECLARE v_ES03VIS integer;
DECLARE v_ES03SQU integer;
DECLARE v_ES03TOU integer;
DECLARE v_ES03OUI integer;
DECLARE v_ES03ODO integer;
DECLARE v_ES03GOU integer;
DECLARE v_ES03VUE integer;
DECLARE v_ES03HYG integer;
DECLARE v_ES03MIC integer;
DECLARE v_ES04 integer;
DECLARE v_ES04ATM integer;
DECLARE v_ES04PLA integer;
DECLARE v_ES04ETO integer;
DECLARE v_ES04GAL integer;
DECLARE v_ES04VOI integer;
DECLARE v_ES04COM integer;
DECLARE v_ES04AST integer;
DECLARE v_ES04BIG integer;
DECLARE v_ES04TRO integer;
DECLARE v_ES05 integer;
DECLARE v_ES05INS integer;
DECLARE v_ES05JOU integer;
DECLARE v_ES05PHA integer;
DECLARE v_ES05ECL integer;
DECLARE v_ES05SAI integer;
DECLARE v_ES05PLU integer;
DECLARE v_ES05NEI integer;
DECLARE v_ES05NUA integer;
DECLARE v_ES05ORA integer;
DECLARE v_ES05ARC integer;
DECLARE v_ES05FEU integer;
DECLARE v_ES05VEN integer;
DECLARE v_ES05AVA integer;
DECLARE v_ES05INO integer;
DECLARE v_ES05MAR integer;
DECLARE v_ES06 integer;
DECLARE v_ES06MON integer;
DECLARE v_ES06MER integer;
DECLARE v_ES06COU integer;
DECLARE v_ES06FOR integer;
DECLARE v_ES06VOL integer;
DECLARE v_ES06DES integer;
DECLARE v_ES06CYC integer;
DECLARE v_ES07 integer;
DECLARE v_ES07EFF integer;
DECLARE v_ES07TRI integer;
DECLARE v_ES07POL integer;
DECLARE v_ES07DEV integer;
DECLARE v_ES08 integer;
DECLARE v_ES08IDE integer;
DECLARE v_ES08NUT integer;
DECLARE v_ES08CRI integer;
DECLARE v_ES08REP integer;
DECLARE v_ES08CHA integer;
DECLARE v_ES08CHA1 integer;
DECLARE v_ES08CHA2 integer;
DECLARE v_ES08CHA3 integer;
DECLARE v_ES08CHA4 integer;
DECLARE v_ES08INV integer;
DECLARE v_ES09 integer;
DECLARE v_ES09IDE integer;
DECLARE v_ES09ARB integer;
DECLARE v_ES09FRU integer;
DECLARE v_ES09CHA integer;
DECLARE v_ES09REP integer;
DECLARE v_ES09NUT integer;
DECLARE v_ES10 integer;
DECLARE v_ES10CHA integer;
DECLARE v_ES10VAS integer;
DECLARE v_ES10ENG integer;
DECLARE v_ES10ECL integer;
DECLARE v_ES10VINV integer;
DECLARE v_ES11 integer;
DECLARE v_ES11HEU integer;
DECLARE v_ES11HAM integer;
DECLARE v_ES11HAA integer;
DECLARE v_ES11JMA integer;
DECLARE v_ES11UMB integer;
DECLARE v_ES11RAB integer;
DECLARE v_ES11RDE integer;


DECLARE v_EG integer;
DECLARE v_EG01 integer;
DECLARE v_EG01DIV integer;
DECLARE v_EG01VIL integer;
DECLARE v_EG01REL integer;
DECLARE v_EG01COU integer;
DECLARE v_EG01TOU integer;
DECLARE v_EG02 integer;
DECLARE v_EG02CAR integer;
DECLARE v_EG02DIV integer;
DECLARE v_EG02UNI integer;
DECLARE v_EG02VIL integer;
DECLARE v_EG02TEC integer;
DECLARE v_EG02POI integer;
DECLARE v_EG02POL integer;
DECLARE v_EG03 integer;
DECLARE v_EG03CHA integer;
DECLARE v_EG03PEC integer;
DECLARE v_EG03AGR integer;
DECLARE v_EG03ENE integer;

DECLARE v_EA integer;
DECLARE v_EA01 integer;
DECLARE v_EA01PEI integer;
DECLARE v_EA01COU integer;
DECLARE v_EA02 integer;
DECLARE v_EA02INS integer;
DECLARE v_EA02MUS integer;
DECLARE v_EA02GEN integer;
DECLARE v_EA02CHA integer;
DECLARE v_EA02THE integer;
DECLARE v_EA03 integer;
DECLARE v_EA04 integer;
DECLARE v_EA05 integer;
DECLARE v_EA06 integer;
DECLARE v_EA07 integer;
DECLARE v_EA08 integer;
DECLARE v_EA08COU integer;
DECLARE v_EA08AUT integer;
DECLARE v_EA09 integer;

DECLARE v_EH integer;
DECLARE v_EH01 integer;
DECLARE v_EH02 integer;
DECLARE v_EH02PAL integer;
DECLARE v_EH02NEO integer;
DECLARE v_EH03 integer;
DECLARE v_EH03EGY integer;
DECLARE v_EH03GAU integer;
DECLARE v_EH03GRE integer;
DECLARE v_EH03ROM integer;
DECLARE v_EH03MYT integer;
DECLARE v_EH03AUT integer;
DECLARE v_EH04 integer;
DECLARE v_EH04MER integer;
DECLARE v_EH04CAR integer;
DECLARE v_EH04CAP integer;
DECLARE v_EH04GCA integer;
DECLARE v_EH05 integer;
DECLARE v_EH06 integer;
DECLARE v_EH07 integer;
DECLARE v_EH08 integer;
DECLARE v_EH09 integer;
DECLARE v_EH10 integer;
DECLARE v_EH11 integer;
DECLARE v_EH12 integer;
DECLARE v_EH13 integer;
DECLARE v_EH14 integer;
DECLARE v_EH15 integer;
DECLARE v_EH16 integer;
DECLARE v_EH17 integer;
DECLARE v_EH18 integer;
DECLARE v_EH19 integer;
DECLARE v_EH20 integer;




DECLARE curtime timestamp := now();


BEGIN
select nextval('hibernate_sequence') INTO v_ROOT ; 
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_ROOT, curtime, NULL, 'ROOT', NULL, 'Root', NULL, NULL, NULL);

select nextval('hibernate_sequence') INTO v_FR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR, curtime, NULL, 'FR', NULL, 'Français', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_FR01 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01, curtime, NULL, 'FR01', NULL, 'Les lettres, les accents, les sons', NULL, NULL, v_FR);
select nextval('hibernate_sequence') INTO v_FR01AL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01AL, curtime, NULL, 'FR01AL', NULL, 'L''alphabet', NULL, NULL, v_FR01);
select nextval('hibernate_sequence') INTO v_FR01AL1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01AL1, curtime, NULL, 'FR01AL1', NULL, 'Lettres, chiffres, symboles', NULL, NULL, v_FR01AL);
select nextval('hibernate_sequence') INTO v_FR01AL2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01AL2, curtime, NULL, 'FR01AL2', NULL, 'Minuscules/Majuscules', NULL, NULL, v_FR01AL);
select nextval('hibernate_sequence') INTO v_FR01PO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01PO, curtime, NULL, 'FR01PO', NULL, 'Les signes de ponctuation', NULL, NULL, v_FR01);
select nextval('hibernate_sequence') INTO v_FR01SYL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01SYL, curtime, NULL, 'FR01SYL', NULL, 'Les syllabes', NULL, NULL, v_FR01);
select nextval('hibernate_sequence') INTO v_FR01HOM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01HOM, curtime, NULL, 'FR01HOM', NULL, 'Les homophones', NULL, NULL, v_FR01);
select nextval('hibernate_sequence') INTO v_FR01HOM1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01HOM1, curtime, NULL, 'FR01HOM1', NULL, 'o/eau/au', NULL, NULL, v_FR01HOM);
select nextval('hibernate_sequence') INTO v_FR01HOM2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01HOM2, curtime, NULL, 'FR01HOM2', NULL, 'c''est/s''est/ses', NULL, NULL, v_FR01HOM);
select nextval('hibernate_sequence') INTO v_FR01HOM3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01HOM3, curtime, NULL, 'FR01HOM3', NULL, 'Autres', NULL, NULL, v_FR01HOM);
select nextval('hibernate_sequence') INTO v_FR01CH ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01CH, curtime, NULL, 'FR01CH', NULL, 'Les chiffres', NULL, NULL, v_FR01);
select nextval('hibernate_sequence') INTO v_FR01CH1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01CH1, curtime, NULL, 'FR01CH1', NULL, 'Les cardinaux', NULL, NULL, v_FR01CH);
select nextval('hibernate_sequence') INTO v_FR01CH2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR01CH2, curtime, NULL, 'FR01CH2', NULL, 'Les ordinaux', NULL, NULL, v_FR01CH);



select nextval('hibernate_sequence') INTO v_FR02 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02, curtime, NULL, 'FR02', NULL, 'Les mots, les classes de mots, le féminin, le pluriel', NULL, NULL, v_FR);
select nextval('hibernate_sequence') INTO v_FR02ART ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02ART, curtime, NULL, 'FR02ART', NULL, 'L''article', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02ART1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02ART1, curtime, NULL, 'FR02ART1', NULL, 'Défini', NULL, NULL, v_FR02ART);
select nextval('hibernate_sequence') INTO v_FR02ART2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02ART2, curtime, NULL, 'FR02ART2', NULL, 'Indéfini', NULL, NULL, v_FR02ART);
select nextval('hibernate_sequence') INTO v_FR02NOM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02NOM, curtime, NULL, 'FR02NOM', NULL, 'Le nom propre, le nom commun', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02ADJ ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02ADJ, curtime, NULL, 'FR02ADJ', NULL, 'L''adjectif', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02ADJ1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02ADJ1, curtime, NULL, 'FR02ADJ1', NULL, 'Qualificatif', NULL, NULL, v_FR02ADJ);
select nextval('hibernate_sequence') INTO v_FR02ADJ2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02ADJ2, curtime, NULL, 'FR02ADJ2', NULL, 'Possessif', NULL, NULL, v_FR02ADJ);
select nextval('hibernate_sequence') INTO v_FR02ADJ3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02ADJ3, curtime, NULL, 'FR02ADJ3', NULL, 'Démonstratif', NULL, NULL, v_FR02ADJ);
select nextval('hibernate_sequence') INTO v_FR02FEM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02FEM, curtime, NULL, 'FR02FEM', NULL, 'Masculin/Féminin', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02PRL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02PRL, curtime, NULL, 'FR02PRL', NULL, 'Singulier/Pluriel', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02VER ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02VER, curtime, NULL, 'FR02VER', NULL, 'Le verbe', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02PRN ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02PRN, curtime, NULL, 'FR02PRN', NULL, 'Le pronom', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02PRN1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02PRN1, curtime, NULL, 'FR02PRN1', NULL, 'Personnel', NULL, NULL, v_FR02PRN);
select nextval('hibernate_sequence') INTO v_FR02PRN2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02PRN2, curtime, NULL, 'FR02PRN2', NULL, 'Possessif', NULL, NULL, v_FR02PRN);
select nextval('hibernate_sequence') INTO v_FR02PRN3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02PRN3, curtime, NULL, 'FR02PRN3', NULL, 'Démonstratif', NULL, NULL, v_FR02PRN);
select nextval('hibernate_sequence') INTO v_FR02PRP ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02PRP, curtime, NULL, 'FR02PRP', NULL, 'La préposition', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02CON ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02CON, curtime, NULL, 'FR02CON', NULL, 'La conjonction', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02CON1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02CON1, curtime, NULL, 'FR02CON1', NULL, 'De coordination', NULL, NULL, v_FR02CON);
select nextval('hibernate_sequence') INTO v_FR02CON2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02CON2, curtime, NULL, 'FR02CON2', NULL, 'De subordination', NULL, NULL, v_FR02CON);
select nextval('hibernate_sequence') INTO v_FR02ADV ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02ADV, curtime, NULL, 'FR02ADV', NULL, 'L''adverbe', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02INT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02INT, curtime, NULL, 'FR02INT', NULL, 'L''interjection', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02PFX ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02PFX, curtime, NULL, 'FR02PFX', NULL, 'Le préfixe', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02SFX ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02SFX, curtime, NULL, 'FR02SFX', NULL, 'Le suffixe', NULL, NULL, v_FR02);
select nextval('hibernate_sequence') INTO v_FR02SYN ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR02SYN, curtime, NULL, 'FR02SYN', NULL, 'Synonymes/Antonymes/Homonymes', NULL, NULL, v_FR02);

select nextval('hibernate_sequence') INTO v_FR03 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03, curtime, NULL, 'FR03', NULL, 'La phrase et ses éléments', NULL, NULL, v_FR);
select nextval('hibernate_sequence') INTO v_FR03SUJ ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03SUJ, curtime, NULL, 'FR03SUJ', NULL, 'Le sujet', NULL, NULL, v_FR03);
select nextval('hibernate_sequence') INTO v_FR03VRB ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03VRB, curtime, NULL, 'FR03VRB', NULL, 'Le verbe', NULL, NULL, v_FR03);
select nextval('hibernate_sequence') INTO v_FR03CMP ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03CMP, curtime, NULL, 'FR03CMP', NULL, 'Complément', NULL, NULL, v_FR03);
select nextval('hibernate_sequence') INTO v_FR03CMP1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03CMP1, curtime, NULL, 'FR03CMP1', NULL, 'Direct du verbe', NULL, NULL, v_FR03CMP);
select nextval('hibernate_sequence') INTO v_FR03CMP2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03CMP2, curtime, NULL, 'FR03CMP2', NULL, 'Indirect du verbe', NULL, NULL, v_FR03CMP);
select nextval('hibernate_sequence') INTO v_FR03PRO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03PRO, curtime, NULL, 'FR03PRO', NULL, 'Propositions', NULL, NULL, v_FR03);
select nextval('hibernate_sequence') INTO v_FR03PRO1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03PRO1, curtime, NULL, 'FR03PRO1', NULL, 'Relatives', NULL, NULL, v_FR03PRO);
select nextval('hibernate_sequence') INTO v_FR03PRO2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03PRO2, curtime, NULL, 'FR03PRO2', NULL, 'Subordonnées', NULL, NULL, v_FR03PRO);
select nextval('hibernate_sequence') INTO v_FR03CPL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03CPL, curtime, NULL, 'FR03CPL', NULL, 'Compléments', NULL, NULL, v_FR03);
select nextval('hibernate_sequence') INTO v_FR03CPL1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03CPL1, curtime, NULL, 'FR03CPL1', NULL, 'De lieu', NULL, NULL, v_FR03CPL);
select nextval('hibernate_sequence') INTO v_FR03CPL2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03CPL2, curtime, NULL, 'FR03CPL2', NULL, 'De temps', NULL, NULL, v_FR03CPL);
select nextval('hibernate_sequence') INTO v_FR03CPL3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR03CPL3, curtime, NULL, 'FR03CPL3', NULL, 'De manière', NULL, NULL, v_FR03CPL);




select nextval('hibernate_sequence') INTO v_FR04 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04, curtime, NULL, 'FR04', NULL, 'Les modes, les temps et la conjugaison', NULL, NULL, v_FR);
select nextval('hibernate_sequence') INTO v_FR04IND ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND, curtime, NULL, 'FR04IND', NULL, 'L''indicatif', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04IND1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND1, curtime, NULL, 'FR04IND1', NULL, 'Présent', NULL, NULL, v_FR04IND);
select nextval('hibernate_sequence') INTO v_FR04IND2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND2, curtime, NULL, 'FR04IND2', NULL, 'Imparfait', NULL, NULL, v_FR04IND);
select nextval('hibernate_sequence') INTO v_FR04IND3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND3, curtime, NULL, 'FR04IND3', NULL, 'Passé simple', NULL, NULL, v_FR04IND);
select nextval('hibernate_sequence') INTO v_FR04IND4 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND4, curtime, NULL, 'FR04IND4', NULL, 'Passé composé', NULL, NULL, v_FR04IND);
select nextval('hibernate_sequence') INTO v_FR04IND5 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND5, curtime, NULL, 'FR04IND5', NULL, 'Plus-que-parfait', NULL, NULL, v_FR04IND);
select nextval('hibernate_sequence') INTO v_FR04IND6 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND6, curtime, NULL, 'FR04IND6', NULL, 'Passé antérieur', NULL, NULL, v_FR04IND);
select nextval('hibernate_sequence') INTO v_FR04IND7 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND7, curtime, NULL, 'FR04IND7', NULL, 'Futur simple', NULL, NULL, v_FR04IND);
select nextval('hibernate_sequence') INTO v_FR04IND8;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IND8, curtime, NULL, 'FR04IND8', NULL, 'Futur antérieur', NULL, NULL, v_FR04IND);
select nextval('hibernate_sequence') INTO v_FR04IMP ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IMP, curtime, NULL, 'FR04IMP', NULL, 'L''impératif', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04IMP1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IMP1,curtime, NULL, 'FR04IMP1', NULL, 'Présent', NULL, NULL, v_FR04IMP);
select nextval('hibernate_sequence') INTO v_FR04IMP2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04IMP2, curtime, NULL, 'FR04IMP2', NULL, 'Passé', NULL, NULL, v_FR04IMP);
select nextval('hibernate_sequence') INTO v_FR04CON ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04CON, curtime, NULL, 'FR04CON', NULL, 'Le conditionnel', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04CON1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04CON1, curtime, NULL, 'FR040CON1', NULL, 'Présent', NULL, NULL, v_FR04CON);
select nextval('hibernate_sequence') INTO v_FR04CON2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04CON2, curtime, NULL, 'FR04CON2', NULL, 'Passé', NULL, NULL, v_FR04CON);
select nextval('hibernate_sequence') INTO v_FR04SUB ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04SUB, curtime, NULL, 'FR04SUB', NULL, 'Le subjonctif', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04SUB1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04SUB1, curtime, NULL, 'FR04SUB1', NULL, 'Présent', NULL, NULL, v_FR04SUB);
select nextval('hibernate_sequence') INTO v_FR04SUB2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04SUB2, curtime, NULL, 'FR04SUB2', NULL, 'Passé', NULL, NULL, v_FR04SUB);
select nextval('hibernate_sequence') INTO v_FR04SUB3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04SUB3, curtime, NULL, 'FR04SUB3', NULL, 'Imparfait', NULL, NULL, v_FR04SUB);
select nextval('hibernate_sequence') INTO v_FR04SUB4 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04SUB4, curtime, NULL, 'FR04SUB4', NULL, 'Plus-que-parfait', NULL, NULL, v_FR04SUB);
select nextval('hibernate_sequence') INTO v_FR04INF ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04INF, curtime, NULL, 'FR04INF', NULL, 'L''infinitif', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04INF1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04INF1, curtime, NULL, 'FR04INF1', NULL, 'Présent', NULL, NULL, v_FR04INF);
select nextval('hibernate_sequence') INTO v_FR04INF2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04INF2, curtime, NULL, 'FR04INF2', NULL, 'Passé', NULL, NULL, v_FR04INF);
select nextval('hibernate_sequence') INTO v_FR04PAR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04PAR, curtime, NULL, 'FR04PAR', NULL, 'Le participe', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04PAR1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04PAR1, curtime, NULL, 'FR04PAR1', NULL, 'Présent', NULL, NULL, v_FR04PAR);
select nextval('hibernate_sequence') INTO v_FR04PAR2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04PAR2, curtime, NULL, 'FR04PAR2', NULL, 'Passé', NULL, NULL, v_FR04PAR);
select nextval('hibernate_sequence') INTO v_FR04PAR3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04PAR3, curtime, NULL, 'FR04PAR3', NULL, 'Accord du participe passé', NULL, NULL, v_FR04PAR);
select nextval('hibernate_sequence') INTO v_FR04CDT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04CDT, curtime, NULL, 'FR04CDT', NULL, 'La concordance des temps', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04ACT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04ACT, curtime, NULL, 'FR04ACT', NULL, 'Actif/Passif', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04ETR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04ETR, curtime, NULL, 'FR04ETR', NULL, 'Le verbe être', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04AVO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04AVO, curtime, NULL, 'FR04AVO', NULL, 'Le verbe avoir', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04DIR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04DIR, curtime, NULL, 'FR04DIR', NULL, 'Le verbe dire', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04VRE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04VRE, curtime, NULL, 'FR04VRE', NULL, 'Les verbes réguliers', NULL, NULL, v_FR04);
select nextval('hibernate_sequence') INTO v_FR04VIR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR04VIR, curtime, NULL, 'FR04VIR', NULL, 'Les verbes irréguliers', NULL, NULL, v_FR04);



select nextval('hibernate_sequence') INTO v_FR05 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR05, curtime, NULL, 'FR05', NULL, 'Ecrire un texte, préparer un exposé', NULL, NULL, v_FR);
select nextval('hibernate_sequence') INTO v_FR05LIR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR05LIR, curtime, NULL, 'FR05LIR', NULL, 'Lire, parler, réciter avec expression', NULL, NULL, v_FR05);
select nextval('hibernate_sequence') INTO v_FR05PLA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR05PLA, curtime, NULL, 'FR05PLA', NULL, 'Plan', NULL, NULL, v_FR05);


select nextval('hibernate_sequence') INTO v_FR06 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR06, curtime, NULL, 'FR06', NULL, 'Auteurs célèbres et genre littéraires', NULL, NULL, v_FR);
select nextval('hibernate_sequence') INTO v_FR06FAB ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR06FAB, curtime, NULL, 'FR06FAB', NULL, 'La fable', NULL, NULL, v_FR06);
select nextval('hibernate_sequence') INTO v_FR06POE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR06POE, curtime, NULL, 'FR06POE', NULL, 'La poésie', NULL, NULL, v_FR06);
select nextval('hibernate_sequence') INTO v_FR06ROM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR06ROM, curtime, NULL, 'FR06ROM', NULL, 'Le roman', NULL, NULL, v_FR06);
select nextval('hibernate_sequence') INTO v_FR06ESS;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR06ESS, curtime, NULL, 'FR06ESS', NULL, 'L''essai', NULL, NULL, v_FR06);
select nextval('hibernate_sequence') INTO v_FR06LET;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR06LET, curtime, NULL, 'FR06LET', NULL, 'La lettre', NULL, NULL, v_FR06);
select nextval('hibernate_sequence') INTO v_FR06BIO;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR06BIO, curtime, NULL, 'FR06BIO', NULL, 'La biographie', NULL, NULL, v_FR06);
select nextval('hibernate_sequence') INTO v_FR06JLF;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_FR06JLF, curtime, NULL, 'FR06JLF', NULL, 'Jean de la Fontaine', NULL, NULL, v_FR06);







select nextval('hibernate_sequence') INTO v_CM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM, curtime, NULL, 'CM', NULL, 'Calcul et Mathématiques', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_CM01 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01, curtime, NULL, 'CM01', NULL, 'Nombres et opérations', NULL, NULL, v_CM);
select nextval('hibernate_sequence') INTO v_CM01CHI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01CHI, curtime, NULL, 'CM01CHI', NULL, 'Ecrire les chiffres et les nombres', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01COM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01COM, curtime, NULL, 'CM01COM', NULL, 'Compter', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01ENT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01ENT, curtime, NULL, 'CM01ENT', NULL, 'Les nombres entiers', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01DEC ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01DEC, curtime, NULL, 'CM01DEC', NULL, 'Les nombres décimaux', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01ARR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01ARR, curtime, NULL, 'CM01ARR', NULL, 'Arrondir un nombre', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01PRE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01PRE, curtime, NULL, 'CM01PRE', NULL, 'Les nombres premiers', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01PAR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01PAR, curtime, NULL, 'CM01PAR', NULL, 'Les nombres particuliers', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01RED ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01RED, curtime, NULL, 'CM01RED', NULL, 'Réduction de fractions', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01POU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01POU, curtime, NULL, 'CM01POU', NULL, 'Pourcentages', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01ADD ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01ADD, curtime, NULL, 'CM01ADD', NULL, 'L''addition', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01SOU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01SOU, curtime, NULL, 'CM01SOU', NULL, 'La soustraction', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01MUL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01MUL, curtime, NULL, 'CM01MUL', NULL, 'La multiplication', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01DIV ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01DIV, curtime, NULL, 'CM01DIV', NULL, 'La division', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01TMU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01TMU, curtime, NULL, 'CM01TMU', NULL, 'Les tables de multiplication', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM01REG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM01REG, curtime, NULL, 'CM01REG', NULL, 'La règle de 3', NULL, NULL, v_CM01);
select nextval('hibernate_sequence') INTO v_CM02 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02, curtime, NULL, 'CM02', NULL, 'Grandeurs et mesures', NULL, NULL, v_CM);
select nextval('hibernate_sequence') INTO v_CM02MES ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02MES, curtime, NULL, 'CM02MES', NULL, 'Les mesures et leurs unités', NULL, NULL, v_CM02);
select nextval('hibernate_sequence') INTO v_CM02MES1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02MES1, curtime, NULL, 'CM02MES1', NULL, 'Masses', NULL, NULL, v_CM02MES);
select nextval('hibernate_sequence') INTO v_CM02MES2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02MES2, curtime, NULL, 'CM02MES2', NULL, 'Longueurs', NULL, NULL, v_CM02MES);
select nextval('hibernate_sequence') INTO v_CM02MES3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02MES3, curtime, NULL, 'CM02MES3', NULL, 'Durées', NULL, NULL, v_CM02MES);
select nextval('hibernate_sequence') INTO v_CM02MES4 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02MES4, curtime, NULL, 'CM02MES4', NULL, 'Capacités', NULL, NULL, v_CM02MES);
select nextval('hibernate_sequence') INTO v_CM02COM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02COM, curtime, NULL, 'CM02COM', NULL, 'Combinaisons de mesures', NULL, NULL, v_CM02);
select nextval('hibernate_sequence') INTO v_CM02COM1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02COM1, curtime, NULL, 'CM02COM1', NULL, 'Vitesse', NULL, NULL, v_CM02COM);
select nextval('hibernate_sequence') INTO v_CM02COM2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02COM2, curtime, NULL, 'CM02COM2', NULL, 'Pression', NULL, NULL, v_CM02COM);
select nextval('hibernate_sequence') INTO v_CM02COM3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM02COM3, curtime, NULL, 'CM02COM3', NULL, 'Autres combinaisons', NULL, NULL, v_CM02COM);
select nextval('hibernate_sequence') INTO v_CM03 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03, curtime, NULL, 'CM03', NULL, 'Figures et solides', NULL, NULL, v_CM);
select nextval('hibernate_sequence') INTO v_CM03POI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03POI, curtime, NULL, 'CM03POI', NULL, 'Le point', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM03LIG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03LIG, curtime, NULL, 'CM03LIG', NULL, 'Les lignes', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM03QUA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03QUA, curtime, NULL, 'CM03QUA', NULL, 'Les quadrilatères', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM03TRI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03TRI, curtime, NULL, 'CM03TRI', NULL, 'Les triangles', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM03CER ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03CER, curtime, NULL, 'CM03CER', NULL, 'Cercles et disques', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM03POL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03POL, curtime, NULL, 'CM03POL', NULL, 'Polygones', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM03PLA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03PLA, curtime, NULL, 'CM03PLA', NULL, 'Transformation du plan', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM03ANG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03ANG, curtime, NULL, 'CM03ANG', NULL, 'Les angles', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM03SYM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM03SYM, curtime, NULL, 'CM03SYM', NULL, 'Symetrie', NULL, NULL, v_CM03);
select nextval('hibernate_sequence') INTO v_CM04 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04, curtime, NULL, 'CM04', NULL, 'Problèmes', NULL, NULL, v_CM);
select nextval('hibernate_sequence') INTO v_CM04CME ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04CME, curtime, NULL, 'CM04CME', NULL, 'Calcul mental', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04ANA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04ANA, curtime, NULL, 'CM04ANA', NULL, 'Analyse et traitement de données', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04OPE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04OPE, curtime, NULL, 'CM04OPE', NULL, 'Opérations diverses', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04COM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04COM, curtime, NULL, 'CM04COM', NULL, 'Comparaisons (valeurs, mesures...)', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04EGA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04EGA, curtime, NULL, 'CM04EGA', NULL, 'Egalités/Inégalités', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04PAV ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04PAV, curtime, NULL, 'CM04PAV', NULL, 'Prix d''achat, de vente, bénéfice, perte', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04PEN ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04PEN, curtime, NULL, 'CM04PEN', NULL, 'Pentes', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04SPA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04SPA, curtime, NULL, 'CM04SPA', NULL, 'Spatialisation', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04ECH ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04ECH, curtime, NULL, 'CM04ECH', NULL, 'Echelle', NULL, NULL, v_CM04);
select nextval('hibernate_sequence') INTO v_CM04TAB ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_CM04TAB, curtime, NULL, 'CM04TAB', NULL, 'Tableaux et diagrammes', NULL, NULL, v_CM04);






select nextval('hibernate_sequence') INTO v_ES ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES, curtime, NULL, 'ES', NULL, 'Eveil scientifique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_ES01 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES01, curtime, NULL, 'ES01', NULL, 'Matière et matériaux', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES01ETA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES01ETA, curtime, NULL, 'ES01ETA', NULL, 'Etats de la matière: solide, liquide, gaz, plasma', NULL, NULL, v_ES01);
select nextval('hibernate_sequence') INTO v_ES01MET ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES01MET, curtime, NULL, 'ES01MET', NULL, 'Métaux', NULL, NULL, v_ES01);
select nextval('hibernate_sequence') INTO v_ES01CRI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES01CRI, curtime, NULL, 'ES01CRI', NULL, 'Cristaux', NULL, NULL, v_ES01);
select nextval('hibernate_sequence') INTO v_ES01PRO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES01PRO, curtime, NULL, 'ES01PRO', NULL, 'Propriétés des matériaux', NULL, NULL, v_ES01);
select nextval('hibernate_sequence') INTO v_ES01PRO1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES01PRO1, curtime, NULL, 'ES01PRO1', NULL, 'Atomes', NULL, NULL, v_ES01PRO);
select nextval('hibernate_sequence') INTO v_ES01PRO2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES01PRO2, curtime, NULL, 'ES01PRO2', NULL, 'Ph', NULL, NULL, v_ES01PRO);
select nextval('hibernate_sequence') INTO v_ES02 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02, curtime, NULL, 'ES02', NULL, 'Energie, son, lumière', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES02CHA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02CHA, curtime, NULL, 'ES02CHA', NULL, 'Chaleur et température', NULL, NULL, v_ES02);
select nextval('hibernate_sequence') INTO v_ES02COU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02COU, curtime, NULL, 'ES02COU', NULL, 'Couleur', NULL, NULL, v_ES02);
select nextval('hibernate_sequence') INTO v_ES02OMB ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02OMB, curtime, NULL, 'ES02OMB', NULL, 'Ombre et lumière', NULL, NULL, v_ES02);
select nextval('hibernate_sequence') INTO v_ES02ELE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02ELE, curtime, NULL, 'ES02ELE', NULL, 'Electricité', NULL, NULL, v_ES02);
select nextval('hibernate_sequence') INTO v_ES02ELE01 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02ELE01, curtime, NULL, 'ES02ELE01', NULL, 'Courant électrique', NULL, NULL, v_ES02ELE);
select nextval('hibernate_sequence') INTO v_ES02ELE02 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02ELE02, curtime, NULL, 'ES02ELE02', NULL, 'Electricité statique', NULL, NULL, v_ES02ELE);
select nextval('hibernate_sequence') INTO v_ES02ELE03 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02ELE03, curtime, NULL, 'ES02ELE03', NULL, 'Electroaimants', NULL, NULL, v_ES02ELE);
select nextval('hibernate_sequence') INTO v_ES02TRA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02TRA, curtime, NULL, 'ES02TRA', NULL, 'Transfert d''énergie', NULL, NULL, v_ES02);
select nextval('hibernate_sequence') INTO v_ES02FOR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02FOR, curtime, NULL, 'ES02FOR', NULL, 'Formes d''énergie', NULL, NULL, v_ES02);
select nextval('hibernate_sequence') INTO v_ES02OND ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES02OND, curtime, NULL, 'ES02OND', NULL, 'Ondes', NULL, NULL, v_ES02);
select nextval('hibernate_sequence') INTO v_ES03 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03, curtime, NULL, 'ES03', NULL, 'Le corps humain', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES03OEI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03OEI, curtime, NULL, 'ES03OEI', NULL, 'L''oeil ', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03DIG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03DIG, curtime, NULL, 'ES03DIG', NULL, 'Le système digestif', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03RES ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03RES, curtime, NULL, 'ES03RES', NULL, 'Le système respiratoire', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03CIR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03CIR, curtime, NULL, 'ES03CIR', NULL, 'Le système circulatoire', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03REP ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03REP, curtime, NULL, 'ES03REP', NULL, 'Le système reproducteur', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03CER ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03CER, curtime, NULL, 'ES03CER', NULL, 'Le cerveau', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03DEN ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03DEN, curtime, NULL, 'ES03DEN', NULL, 'Les dents', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03MUS ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03MUS, curtime, NULL, 'ES03MUS', NULL, 'Les muscles', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03ONG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03ONG, curtime, NULL, 'ES03ONG', NULL, 'Les ongles, les poils et cheveux', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03VIS ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03VIS, curtime, NULL, 'ES03VIS', NULL, 'Le visage', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03SQU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03SQU, curtime, NULL, 'ES03SQU', NULL, 'Le squelette', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03TOU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03TOU, curtime, NULL, 'ES03TOU', NULL, 'Le toucher', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03OUI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03OUI, curtime, NULL, 'ES03OUI', NULL, 'L''ouïe', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03ODO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03ODO, curtime, NULL, 'ES03ODO', NULL, 'L''odorat', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03GOU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03GOU, curtime, NULL, 'ES03GOU', NULL, 'Le goût', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03VUE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03VUE, curtime, NULL, 'ES03VUE', NULL, 'La vue', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03HYG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03HYG, curtime, NULL, 'ES03HYG', NULL, 'Hygiène de vie et prévention', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES03MIC ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES03MIC, curtime, NULL, 'ES03MIC', NULL, 'Les microbes, les virus, les bactéries', NULL, NULL, v_ES03);
select nextval('hibernate_sequence') INTO v_ES04 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04, curtime, NULL, 'ES04', NULL, 'Le Système solaire', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES04ATM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04ATM, curtime, NULL, 'ES04ATM', NULL, 'L''atmosphère', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES04PLA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04PLA, curtime, NULL, 'ES04PLA', NULL, 'Les planètes', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES04ETO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04ETO, curtime, NULL, 'ES04ETO', NULL, 'Les étoiles', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES04GAL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04GAL, curtime, NULL, 'ES04GAL', NULL, 'Les galaxies', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES04VOI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04VOI, curtime, NULL, 'ES04VOI', NULL, 'La voie lactée', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES04COM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04COM, curtime, NULL, 'ES04COM', NULL, 'Les comètes', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES04AST ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04AST, curtime, NULL, 'ES04AST', NULL, 'Les astéroïdes', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES04BIG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04BIG, curtime, NULL, 'ES04BIG', NULL, 'Le Big bang', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES04TRO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES04TRO, curtime, NULL, 'ES04TRO', NULL, 'Les trous noirs', NULL, NULL, v_ES04);
select nextval('hibernate_sequence') INTO v_ES05 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05, curtime, NULL, 'ES05', NULL, 'Phénomènes astronomiques, climatiques et météorologiques ', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES05INS ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05INS, curtime, NULL, 'ES05INS', NULL, 'Instruments météorologiques', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05JOU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05JOU, curtime, NULL, 'ES05JOU', NULL, 'Le jour et la nuit', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05PHA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05PHA, curtime, NULL, 'ES05PHA', NULL, 'Les phases lunaires', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05ECL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05ECL, curtime, NULL, 'ES05ECL', NULL, 'Les éclipses', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05SAI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05SAI, curtime, NULL, 'ES05SAI', NULL, 'Les saisons', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05PLU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05PLU, curtime, NULL, 'ES05PLU', NULL, 'La pluie', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05NEI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05NEI, curtime, NULL, 'ES05NEI', NULL, 'La neige', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05NUA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05NUA, curtime, NULL, 'ES05NUA', NULL, 'Les nuages', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05ORA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05ORA, curtime, NULL, 'ES05ORA', NULL, 'Les orages', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05ARC ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05ARC, curtime, NULL, 'ES05ARC', NULL, 'L''arc-en-ciel', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05FEU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05FEU, curtime, NULL, 'ES05FEU', NULL, 'Le feu', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05VEN ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05VEN, curtime, NULL, 'ES05VEN', NULL, 'Le vent', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05AVA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05AVA, curtime, NULL, 'ES05AVA', NULL, 'Les avalanches', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05INO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05INO, curtime, NULL, 'ES05INO', NULL, 'Les inondations', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES05MAR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES05MAR, curtime, NULL, 'ES05MAR', NULL, 'Les marées', NULL, NULL, v_ES05);
select nextval('hibernate_sequence') INTO v_ES06 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES06, curtime, NULL, 'ES06', NULL, 'Ecosystèmes', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES06MON ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES06MON, curtime, NULL, 'ES06MON', NULL, 'Montagnes et glaciers', NULL, NULL, v_ES06);
select nextval('hibernate_sequence') INTO v_ES06MER ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES06MER, curtime, NULL, 'ES06MER', NULL, 'Mers et océans', NULL, NULL, v_ES06);
select nextval('hibernate_sequence') INTO v_ES06COU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES06COU, curtime, NULL, 'ES06COU', NULL, 'Cours d''eau', NULL, NULL, v_ES06);
select nextval('hibernate_sequence') INTO v_ES06FOR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES06FOR, curtime, NULL, 'ES06FOR', NULL, 'Fôrets', NULL, NULL, v_ES06);
select nextval('hibernate_sequence') INTO v_ES06VOL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES06VOL, curtime, NULL, 'ES06VOL', NULL, 'Volcans', NULL, NULL, v_ES06);
select nextval('hibernate_sequence') INTO v_ES06DES ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES06DES, curtime, NULL, 'ES06DES', NULL, 'Déserts', NULL, NULL, v_ES06);
select nextval('hibernate_sequence') INTO v_ES06CYC ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES06CYC, curtime, NULL, 'ES06CYC', NULL, 'Le cycle de l''eau', NULL, NULL, v_ES06);
select nextval('hibernate_sequence') INTO v_ES07 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES07, curtime, NULL, 'ES07', NULL, 'L''environnement', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES07EFF ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES07EFF, curtime, NULL, 'ES07EFF', NULL, 'L''effet de serre, la couche d''ozone et le réchauffement climatique', NULL, NULL, v_ES07);
select nextval('hibernate_sequence') INTO v_ES07TRI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES07TRI, curtime, NULL, 'ES07TRI', NULL, 'Le tri sélectif des déchets', NULL, NULL, v_ES07);
select nextval('hibernate_sequence') INTO v_ES07POL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES07POL, curtime, NULL, 'ES07POL', NULL, 'La pollution ', NULL, NULL, v_ES07);
select nextval('hibernate_sequence') INTO v_ES07DEV ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES07DEV, curtime, NULL, 'ES07DEV', NULL, 'Le développement durable', NULL, NULL, v_ES07);
select nextval('hibernate_sequence') INTO v_ES08 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08, curtime, NULL, 'ES08', NULL, 'Le monde animal', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES08IDE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08IDE, curtime, NULL, 'ES08IDE', NULL, 'Identification des animaux', NULL, NULL, v_ES08);
select nextval('hibernate_sequence') INTO v_ES08NUT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08NUT, curtime, NULL, 'ES08NUT', NULL, 'La nutrition des animaux', NULL, NULL, v_ES08);
select nextval('hibernate_sequence') INTO v_ES08CRI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08CRI, curtime, NULL, 'ES08CRI', NULL, 'Les cris des animaux', NULL, NULL, v_ES08);
select nextval('hibernate_sequence') INTO v_ES08REP ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08REP, curtime, NULL, 'ES08REP', NULL, 'La reproduction', NULL, NULL, v_ES08);
select nextval('hibernate_sequence') INTO v_ES08CHA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08CHA, curtime, NULL, 'ES08CHA', NULL, 'La chaîne alimentaire', NULL, NULL, v_ES08);
select nextval('hibernate_sequence') INTO v_ES08CHA1 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08CHA1, curtime, NULL, 'ES08CHA1', NULL, 'Les mammifères', NULL, NULL, v_ES08CHA);
select nextval('hibernate_sequence') INTO v_ES08CHA2 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08CHA2, curtime, NULL, 'ES08CHA2', NULL, 'les poissons', NULL, NULL, v_ES08CHA);
select nextval('hibernate_sequence') INTO v_ES08CHA3 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08CHA3, curtime, NULL, 'ES08CHA3', NULL, 'Les oiseaux', NULL, NULL, v_ES08CHA);
select nextval('hibernate_sequence') INTO v_ES08CHA4 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08CHA4, curtime, NULL, 'ES08CHA4', NULL, 'Les reptiles', NULL, NULL, v_ES08CHA);
select nextval('hibernate_sequence') INTO v_ES08INV ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES08INV, curtime, NULL, 'ES08INV', NULL, 'Les invertébrés', NULL, NULL, v_ES08);
select nextval('hibernate_sequence') INTO v_ES09 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES09, curtime, NULL, 'ES09', NULL, 'Le monde végétal', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES09IDE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES09IDE, curtime, NULL, 'ES09IDE', NULL, 'Identifier les plantes', NULL, NULL, v_ES09);
select nextval('hibernate_sequence') INTO v_ES09ARB ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES09ARB, curtime, NULL, 'ES09ARB', NULL, 'Arbres, feuilles et fruits', NULL, NULL, v_ES09);
select nextval('hibernate_sequence') INTO v_ES09FRU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES09FRU, curtime, NULL, 'ES09FRU', NULL, 'Fruits et légumes', NULL, NULL, v_ES09);
select nextval('hibernate_sequence') INTO v_ES09CHA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES09CHA, curtime, NULL, 'ES09CHA', NULL, 'Champignons', NULL, NULL, v_ES09);
select nextval('hibernate_sequence') INTO v_ES09REP ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES09REP, curtime, NULL, 'ES09REP', NULL, 'La reproduction des végétaux', NULL, NULL, v_ES09);
select nextval('hibernate_sequence') INTO v_ES09NUT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES09NUT, curtime, NULL, 'ES09NUT', NULL, 'La nutrition des végétaux', NULL, NULL, v_ES09);
select nextval('hibernate_sequence') INTO v_ES10 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES10, curtime, NULL, 'ES10', NULL, 'Technologies', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES10CHA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES10CHA, curtime, NULL, 'ES10CHA', NULL, 'Les châteaux', NULL, NULL, v_ES10);
select nextval('hibernate_sequence') INTO v_ES10VAS ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES10VAS, curtime, NULL, 'ES10VAS', NULL, 'Les vases communicants', NULL, NULL, v_ES10);
select nextval('hibernate_sequence') INTO v_ES10ENG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES10ENG, curtime, NULL, 'ES10ENG', NULL, 'Les engrenages', NULL, NULL, v_ES10);
select nextval('hibernate_sequence') INTO v_ES10ECL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES10ECL, curtime, NULL, 'ES10ECL', NULL, 'Les écluses', NULL, NULL, v_ES10);
select nextval('hibernate_sequence') INTO v_ES10VINV ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES10VINV, curtime, NULL, 'ES10VINV', NULL, 'Inventeurs et inventions', NULL, NULL, v_ES10);
select nextval('hibernate_sequence') INTO v_ES11 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES11, curtime, NULL, 'ES11', NULL, 'Divers', NULL, NULL, v_ES);
select nextval('hibernate_sequence') INTO v_ES11HEU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES11HEU, curtime, NULL, 'ES11HEU', NULL, 'L''heure juste', NULL, NULL, v_ES11);
select nextval('hibernate_sequence') INTO v_ES11HAM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES11HAM, curtime, NULL, 'ES11HAM', NULL, 'L''heure avec les minutes', NULL, NULL, v_ES11);
select nextval('hibernate_sequence') INTO v_ES11HAA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES11HAA, curtime, NULL, 'ES11HAA', NULL, 'L''heure avant/après midi', NULL, NULL, v_ES11);
select nextval('hibernate_sequence') INTO v_ES11JMA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES11JMA, curtime, NULL, 'ES11JMA', NULL, 'Les jours, les mois, les années, les années bissextiles', NULL, NULL, v_ES11);
select nextval('hibernate_sequence') INTO v_ES11UMB ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES11UMB, curtime, NULL, 'ES11UMB', NULL, 'Utiliser de la monnaie et des billets', NULL, NULL, v_ES11);
select nextval('hibernate_sequence') INTO v_ES11RAB ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES11RAB, curtime, NULL, 'ES11RAB', NULL, 'Se repérer avec une boussole', NULL, NULL, v_ES11);
select nextval('hibernate_sequence') INTO v_ES11RDE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id)
	VALUES (v_ES11RDE, curtime, NULL, 'ES11RDE', NULL, 'Se repérer dans l''espace: gauche/droite, haut/bas, avant/arrière', NULL, NULL, v_ES11);


select nextval('hibernate_sequence') INTO v_EG ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG, curtime, NULL, 'EG', NULL, 'Eveil géographique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EG01 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG01, curtime, NULL, 'EG01', NULL, 'La Belgique', NULL, NULL, v_EG);
select nextval('hibernate_sequence') INTO v_EG01DIV ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG01DIV, curtime, NULL, 'EG01DIV', NULL, 'Divisions administratives, drapeuax, hymnes', NULL, NULL, v_EG01);
select nextval('hibernate_sequence') INTO v_EG01VIL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG01VIL, curtime, NULL, 'EG01VIL', NULL, 'Villes', NULL, NULL, v_EG01);
select nextval('hibernate_sequence') INTO v_EG01REL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG01REL, curtime, NULL, 'EG01REL', NULL, 'Relief', NULL, NULL, v_EG01);
select nextval('hibernate_sequence') INTO v_EG01COU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG01COU, curtime, NULL, 'EG01COU', NULL, 'Cours d''eau', NULL, NULL, v_EG01);
select nextval('hibernate_sequence') INTO v_EG01TOU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG01TOU, curtime, NULL, 'EG0TOU', NULL, 'Tourisme', NULL, NULL, v_EG01);
select nextval('hibernate_sequence') INTO v_EG02 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG02, curtime, NULL, 'EG02', NULL, 'Continents et pays', NULL, NULL, v_EG);
select nextval('hibernate_sequence') INTO v_EG02CAR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG02CAR, curtime, NULL, 'EG02CAR', NULL, 'Cartes et entités géographiques', NULL, NULL, v_EG02);
select nextval('hibernate_sequence') INTO v_EG02DIV ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG02DIV, curtime, NULL, 'EG02DIV', NULL, 'Divisions administratives, drapeaux, monnaies', NULL, NULL, v_EG02);
select nextval('hibernate_sequence') INTO v_EG02UNI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG02UNI, curtime, NULL, 'EG02UNI', NULL, 'L''Union Européenne de sa naissance à nos jours', NULL, NULL, v_EG02);
select nextval('hibernate_sequence') INTO v_EG02VIL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG02VIL, curtime, NULL, 'EG02VIL', NULL, 'Capitales et Villes importantes', NULL, NULL, v_EG02);
select nextval('hibernate_sequence') INTO v_EG02TEC ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG02TEC, curtime, NULL, 'EG02TEC', NULL, 'Tectonique des plaques', NULL, NULL, v_EG02);
select nextval('hibernate_sequence') INTO v_EG02POI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG02POI, curtime, NULL, 'EG02POI', NULL, 'Les points cardinaux', NULL, NULL, v_EG02);
select nextval('hibernate_sequence') INTO v_EG02POL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG02POL, curtime, NULL, 'EG02POL', NULL, 'Les pôles', NULL, NULL, v_EG02);
select nextval('hibernate_sequence') INTO v_EG03 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG03, curtime, NULL, 'EG03', NULL, 'Ressources naturelles', NULL, NULL, v_EG);
select nextval('hibernate_sequence') INTO v_EG03CHA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG03CHA, curtime, NULL, 'EG03CHA', NULL, 'Chasse et élevage', NULL, NULL, v_EG03);
select nextval('hibernate_sequence') INTO v_EG03PEC ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG03PEC, curtime, NULL, 'EG03PEC', NULL, 'Pêche', NULL, NULL, v_EG03);
select nextval('hibernate_sequence') INTO v_EG03AGR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG03AGR, curtime, NULL, 'EG03AGR', NULL, 'Agriculture et cultures', NULL, NULL, v_EG03);
select nextval('hibernate_sequence') INTO v_EG03ENE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EG03ENE, curtime, NULL, 'EG03ENE', NULL, 'Energies fossiles et minerais', NULL, NULL, v_EG03);



select nextval('hibernate_sequence') INTO v_EA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA, curtime, NULL , 'EA', NULL, 'Eveil artistique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EA01 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA01, curtime, NULL , 'EA01', NULL, 'Peinture et Dessin', NULL, NULL, v_EA);
select nextval('hibernate_sequence') INTO v_EA01PEI ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA01PEI, curtime, NULL , 'EA01PEI', NULL, 'Peintres célèbres', NULL, NULL, v_EA01);
select nextval('hibernate_sequence') INTO v_EA01COU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA01COU, curtime, NULL , 'EA01COU', NULL, 'Courants artistiques', NULL, NULL, v_EA01);
select nextval('hibernate_sequence') INTO v_EA02 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA02, curtime, NULL , 'EA02', NULL, 'Musique', NULL, NULL, v_EA);
select nextval('hibernate_sequence') INTO v_EA02INS ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA02INS, curtime, NULL , 'EA02INS', NULL, 'Instruments', NULL, NULL, v_EA02);
select nextval('hibernate_sequence') INTO v_EA02MUS ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA02MUS, curtime, NULL , 'EA02MUS', NULL, 'Musiciens célèbres', NULL, NULL, v_EA02);
select nextval('hibernate_sequence') INTO v_EA02GEN ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA02GEN, curtime, NULL , 'EA02GEN', NULL, 'Genres musicaux', NULL, NULL, v_EA02);
select nextval('hibernate_sequence') INTO v_EA02CHA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA02CHA, curtime, NULL , 'EA02CHA', NULL, 'Chanteurs et Chansons', NULL, NULL, v_EA02);
select nextval('hibernate_sequence') INTO v_EA02THE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA02THE, curtime, NULL , 'EA02THE', NULL, 'Théorie musicale', NULL, NULL, v_EA02);
select nextval('hibernate_sequence') INTO v_EA03 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA03, curtime, NULL , 'EA03', NULL, 'Architecture', NULL, NULL, v_EA);
select nextval('hibernate_sequence') INTO v_EA04 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA04, curtime, NULL , 'EA04', NULL, 'Photographie', NULL, NULL, v_EA);
select nextval('hibernate_sequence') INTO v_EA05 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA05, curtime, NULL , 'EA05', NULL, 'Cinema', NULL, NULL, v_EA);
select nextval('hibernate_sequence') INTO v_EA06 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA06, curtime, NULL , 'EA06', NULL, 'Théâtre et Danse', NULL, NULL, v_EA);
select nextval('hibernate_sequence') INTO v_EA07 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA07, curtime, NULL , 'EA07', NULL, 'Sculpture', NULL, NULL, v_EA);
select nextval('hibernate_sequence') INTO v_EA08 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA08, curtime, NULL , 'EA08', NULL, 'Littérature et Poésie', NULL, NULL, v_EA);
select nextval('hibernate_sequence') INTO v_EA08COU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA08COU, curtime, NULL , 'EA08COU', NULL, 'Grands courants', NULL, NULL, v_EA08);
select nextval('hibernate_sequence') INTO v_EA08AUT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA08AUT, curtime, NULL , 'EA08AUT', NULL, 'Auteurs célèbres', NULL, NULL, v_EA08);
select nextval('hibernate_sequence') INTO v_EA09 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EA09, curtime, NULL , 'EA09', NULL, 'Bande Dessinée', NULL, NULL, v_EA);




select nextval('hibernate_sequence') INTO v_EH ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH, curtime, NULL, 'EH', NULL, 'Eveil historique', NULL, NULL, v_ROOT);
select nextval('hibernate_sequence') INTO v_EH01 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH01, curtime, NULL, 'EH01', NULL, 'Ligne du temps', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH02 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH02 , curtime, NULL, 'EH02', NULL, 'Préhistoire', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH02PAL ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH02PAL , curtime, NULL, 'EH02PAL', NULL, 'Paléolithique', NULL, NULL, v_EH02);
select nextval('hibernate_sequence') INTO v_EH02NEO ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH02NEO , curtime, NULL, 'EH02NEO', NULL, 'Néolithique', NULL, NULL, v_EH02);
select nextval('hibernate_sequence') INTO v_EH03 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH03, curtime, NULL, 'EH03', NULL, 'Antiquité', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH03EGY ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH03EGY, curtime, NULL, 'EH03EGY', NULL, 'L''Egypte', NULL, NULL, v_EH03);
select nextval('hibernate_sequence') INTO v_EH03GAU ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH03GAU, curtime, NULL, 'EH03GAU', NULL, 'Les Gaulois', NULL, NULL, v_EH03);
select nextval('hibernate_sequence') INTO v_EH03GRE ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH03GRE, curtime, NULL, 'EH03GRE', NULL, 'La Grèce', NULL, NULL, v_EH03);
select nextval('hibernate_sequence') INTO v_EH03ROM ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH03ROM, curtime, NULL, 'EH03ROM', NULL, 'Rome', NULL, NULL, v_EH03);
select nextval('hibernate_sequence') INTO v_EH03MYT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH03MYT, curtime, NULL, 'EH03MYT', NULL, 'La mythologie antique', NULL, NULL, v_EH03);
select nextval('hibernate_sequence') INTO v_EH03AUT ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH03AUT, curtime, NULL, 'EH03AUT', NULL, 'Autres civilisations', NULL, NULL, v_EH03);
select nextval('hibernate_sequence') INTO v_EH04 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH04, curtime, NULL, 'EH04', NULL, 'Moyen Age', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH04MER ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH04MER, curtime, NULL, 'EH04MER', NULL, 'Les Mérovingiens', NULL, NULL, v_EH04);
select nextval('hibernate_sequence') INTO v_EH04CAR ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH04CAR, curtime, NULL, 'EH04CAR', NULL, 'Les Carolingiens', NULL, NULL, v_EH04);
select nextval('hibernate_sequence') INTO v_EH04CAP ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH04CAP, curtime, NULL, 'EH04CAP', NULL, 'Les Capétiens', NULL, NULL, v_EH04);
select nextval('hibernate_sequence') INTO v_EH04GCA ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH04GCA, curtime, NULL, 'EH04GCA', NULL, 'La Guerre de Cent Ans', NULL, NULL, v_EH04);
select nextval('hibernate_sequence') INTO v_EH05 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH05, curtime, NULL, 'EH05', NULL, 'Les Grandes Découvertes', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH06 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH06, curtime, NULL, 'EH06', NULL, 'Christophe Colomb et la colonisation des Amériques', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH07 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH07, curtime, NULL, 'EH07', NULL, 'Les colonies européennes', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH08 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH08, curtime, NULL, 'EH08', NULL, 'La Renaissance', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH09 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH09, curtime, NULL, 'EH09', NULL, 'Le Siècle des Lumières', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH10 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH10, curtime, NULL, 'EH10', NULL, 'La Révolution industrielle', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH11 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH11, curtime, NULL, 'EH11', NULL, 'La naissance de  la Belgique', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH12 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH12, curtime, NULL, 'EH12', NULL, 'Le Congo Belge', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH13 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH13, curtime, NULL, 'EH13', NULL, 'La première Guerre Mondiale', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH14 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH14, curtime, NULL, 'EH14', NULL, 'La Grande Dépression', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH15 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH15, curtime, NULL, 'EH15', NULL, 'La seconde Guerre Mondiale', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH16 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH16, curtime, NULL, 'EH16', NULL, 'La décolonisation', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH17 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH17, curtime, NULL, 'EH17', NULL, 'La conquête de l''espace', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH18 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH18, curtime, NULL, 'EH18', NULL, 'La Guerre Froide', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH19 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH19, curtime, NULL, 'EH19', NULL, 'Les guerres d''après 1990', NULL, NULL, v_EH);
select nextval('hibernate_sequence') INTO v_EH20 ;
INSERT INTO competence (id, createdon, updatedon, code, description, name, createdby_id, updatedby_id, parent_id) VALUES (v_EH20, curtime, NULL, 'EH20', NULL, 'Personnages historiques', NULL, NULL, v_EH);
END $$;


!!!!!!!!!!!!!!!!!!DEPLOYED ON 2014-10-07 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!!!!!!!!!!!!!!!!DEPLOYED ON 2014-10-07 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!!!!!!!!!!!!!!!!DEPLOYED ON 2014-10-07 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!!!!!!!!!!!!!!!!DEPLOYED ON 2014-10-07 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!!!!!!!!!!!!!!!!DEPLOYED ON 2014-10-07 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!!!!!!!!!!!!!!!!DEPLOYED ON 2014-10-07 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!