# --- !Ups

ALTER TABLE public."user" ADD CONSTRAINT email_uniq UNIQUE (email);