CREATE TABLE comingsoonmail
(
  id bigint NOT NULL,
  createdon timestamp without time zone,
  updatedon timestamp without time zone,
  email character varying(255) NOT NULL,
  createdby_id bigint,
  updatedby_id bigint,
  CONSTRAINT comingsoonmail_pkey PRIMARY KEY (id),
  CONSTRAINT fk_6xvxsnwe5ypjacj9m7cgea1mv FOREIGN KEY (createdby_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_lwf3u054hbum5n2ry1w1jwih3 FOREIGN KEY (updatedby_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);