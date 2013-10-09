
/*                        
                             ////
-- 2013-10-09 -- Ahmed -- (£|°_°|£)
*/
    alter table users 
        add column accountlevel int4 not null default 1;

    alter table users 
        add column userprogresspoints int4 not null default 0;
        
        
--*******************************************End of SQL********************************************