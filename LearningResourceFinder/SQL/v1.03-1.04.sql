-- Vincent 2014-10-17
ALTER TABLE cycle ADD COLUMN description CHARACTER VARYING(50);
ALTER TABLE cycle ADD COLUMN slug CHARACTER VARYING(50);

UPDATE cycle SET description = '1<sup>ère</sup> - 2<sup>ème</sup> primaire', slug = '1ère-2ème-primaire'   WHERE id = 300;
UPDATE cycle SET description = '3<sup>ème</sup> - 4<sup>ème</sup> primaire', slug = '3ème-4ème-primaire'   WHERE id = 301;
UPDATE cycle SET description = '5<sup>ème</sup> - 6<sup>ème</sup> primaire', slug = '5ème-6ème-primaire'   WHERE id = 302;
UPDATE cycle SET description = '1<sup>ère</sup> - 2<sup>ème</sup> secondaire', slug = '1ère-2ème-secondaire' WHERE id = 303;
UPDATE cycle SET description = '3<sup>ème</sup> - 6<sup>ème</sup> secondaire', slug = '3ème-6ème-secondaire' WHERE id = 304;

ALTER TABLE cycle ALTER COLUMN description SET NOT NULL;
ALTER TABLE cycle ALTER COLUMN slug SET NOT NULL;