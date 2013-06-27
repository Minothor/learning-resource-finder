/********************************************* USER **************************************************/
INSERT INTO users(
            id, createdon, updatedon, accountconnectedtype, accountstatus, 
            birthdate, consecutivefailedlogins, firstname, gender, ispasswordknownbytheuser, 
            lastaccess, lastfailedlogindate, lastloginip, lastmailsentdate, 
            lastname, lockreason, mail, mailingdelaytype, nlsubscriber, password, 
            picture, role, spammer, title, username, validationcode, createdby_id, 
            updatedby_id, spamreporter_id)
    VALUES (1, null, null, null, 'ACTIVE', 
            null, 0, 'FirstName', null, true, 
            null, null, null, null, 
            'LastName', null, 'mail@mail.com', null, false, null, 
            false, null, false, null, 'UserName', null, null, 
            null, null);

/******************************************* RESOURCE ************************************************/
INSERT INTO resource(
			id, createdon, updatedon, description, title, createdby_id, 
            updatedby_id)
     VALUES (null, null, null, "Super cours de mathématique !", "Math 1", )
            
/******************************************* PROBLEM ************************************************/           
INSERT INTO resource(
			id, createdon, updatedon, description, resolved, title, createdby_id, 
            updatedby_id, resource_id, user_id)
     VALUES (null, null, null, "Problème numéro 1", false, "Titre problème", null,
     		null, 1, 1);
