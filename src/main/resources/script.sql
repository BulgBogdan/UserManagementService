DROP SCHEMA IF EXISTS "user-management" CASCADE;

CREATE SCHEMA "user-management"
  AUTHORIZATION postgres;

/*create table role*/
CREATE TABLE "user-management".role
(
  role_id   integer                                        NOT NULL,
  role_name character varying COLLATE pg_catalog."default" NOT NULL,
  CONSTRAINT role_pkey PRIMARY KEY (role_id)
)
  TABLESPACE pg_default;

ALTER TABLE "user-management".role
  OWNER to postgres;

insert into "user-management".role (role_id, role_name)
values (1, 'USER');
insert into "user-management".role (role_id, role_name)
values (2, 'ADMIN');

/*create table UserAccount*/
CREATE TABLE "user-management".user_account
(
  user_id      integer                                            NOT NULL,
  username     character varying(16) COLLATE pg_catalog."default" NOT NULL,
  password     character varying(16) COLLATE pg_catalog."default" NOT NULL,
  first_name   character varying(16) COLLATE pg_catalog."default" NOT NULL,
  last_name    character varying(16) COLLATE pg_catalog."default" NOT NULL,
  user_status  boolean                                            NOT NULL,
  created_date timestamp with time zone                           NOT NULL default now(),
  role_fk      integer                                            NOT NULL,
  CONSTRAINT "userAccount_pkey" PRIMARY KEY (user_id),
  CONSTRAINT user_role FOREIGN KEY (role_fk)
    REFERENCES "user-management".role (role_id) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  TABLESPACE pg_default;

ALTER TABLE "user-management".user_account
  OWNER to postgres;

insert into "user-management".user_account (user_id,
                                            username,
                                            password,
                                            first_name,
                                            last_name,
                                            user_status,
                                            created_date,
                                            role_fk)
values (1, 'admin', 'admin1', 'admin', 'admin', true, now(), 2);
