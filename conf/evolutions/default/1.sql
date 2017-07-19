# --- !Ups

CREATE TABLE public.todo
(
  id             BIGINT,
  title          TEXT                        NOT NULL,
  description    TEXT                        NOT NULL,
  created        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated        TIMESTAMP WITHOUT TIME ZONE,
  completed      BOOLEAN,
  completed_date TIMESTAMP WITHOUT TIME ZONE,
  created_by_id  BIGINT                      NOT NULL,
  assigned_to_id BIGINT,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE public.todo
  OWNER TO processst;

CREATE TABLE public.comment
(
  id            BIGINT                      NOT NULL,
  description   TEXT                        NOT NULL,
  created_by_id BIGINT                      NOT NULL,
  todo_id       BIGINT                      NOT NULL,
  created       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE public.comment
  OWNER TO processst;

CREATE TABLE public."user"
(
  id    BIGINT                 NOT NULL,
  email CHARACTER VARYING(512) NOT NULL,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE public."user"
  OWNER TO processst;

ALTER TABLE public.comment
  ADD CONSTRAINT comment_todo FOREIGN KEY (todo_id)
REFERENCES public.todo (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION;

ALTER TABLE public.comment
  ADD CONSTRAINT comment_user FOREIGN KEY (created_by_id)
REFERENCES public."user" (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION;

ALTER TABLE public.todo
  ADD CONSTRAINT todo_assigned_user FOREIGN KEY (assigned_to_id)
REFERENCES public."user" (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION;

ALTER TABLE public.todo
  ADD CONSTRAINT todo_created_user FOREIGN KEY (created_by_id)
REFERENCES public."user" (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION;

CREATE SEQUENCE user_id_seq;
ALTER TABLE "user" ALTER id SET DEFAULT NEXTVAL('user_id_seq');

CREATE SEQUENCE todo_id_seq;
ALTER TABLE todo ALTER id SET DEFAULT NEXTVAL('todo_id_seq');

CREATE SEQUENCE comment_id_seq;
ALTER TABLE comment ALTER id SET DEFAULT NEXTVAL('comment_id_seq');


# --- !Downs

DROP TABLE public.comment;
DROP TABLE public.todo;
DROP TABLE public.user;

DROP SEQUENCE user_id_seq;
DROP SEQUENCE todo_id_seq;
DROP SEQUENCE comment_id_seq;
