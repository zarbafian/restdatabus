DROP SCHEMA public CASCADE;

CREATE SCHEMA public
  AUTHORIZATION restdatabus;

GRANT ALL ON SCHEMA public TO restdatabus;
COMMENT ON SCHEMA public
  IS 'standard public schema';

------------------------------------------------------------
---------------------- Security tables----------------------
------------------------------------------------------------

-- account
CREATE TABLE account
(
  id bigserial NOT NULL,
  username character varying NOT NULL,
  password character varying NOT NULL,
  enabled boolean NOT NULL DEFAULT true,
  expired boolean NOT NULL DEFAULT false,
  credentials_expired boolean NOT NULL DEFAULT false,
  locked boolean NOT NULL DEFAULT false,
  created_by bigint NOT NULL,
  created_at timestamp without time zone NOT NULL,
  updated_by bigint,
  updated_at timestamp without time zone,
  CONSTRAINT pk_account PRIMARY KEY (id),
  CONSTRAINT uq_account_username UNIQUE (username),
  CONSTRAINT fk_account_created_by FOREIGN KEY (created_by)
        REFERENCES account (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_account_updated_by FOREIGN KEY (updated_by)
        REFERENCES account (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account
  OWNER TO restdatabus;

-- role
CREATE TABLE role
(
  id bigserial NOT NULL,
  code character varying NOT NULL,
  label character varying NOT NULL,
  ordinal integer NOT NULL,
  effective_at timestamp without time zone NOT NULL,
  expires_at timestamp without time zone,
  created_at timestamp without time zone NOT NULL,
  CONSTRAINT pk_role PRIMARY KEY (id),
  CONSTRAINT uq_role_code UNIQUE (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE role
  OWNER TO restdatabus;

-- account-role join table
CREATE TABLE account_role
(
  account_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT pk_account_role PRIMARY KEY (account_id, role_id),
  CONSTRAINT fk_account_role_account_id FOREIGN KEY (account_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_account_role_role_id FOREIGN KEY (role_id)
      REFERENCES role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account_role
  OWNER TO restdatabus;

-- person
CREATE TABLE person
(
  id bigserial NOT NULL,
  name character varying NOT NULL,
  account_id bigserial NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT fk_person_account_id FOREIGN KEY (account_id)
        REFERENCES account (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE person
  OWNER TO restdatabus;

------------------------------------------------------------
---------------------- Business tables----------------------
------------------------------------------------------------

-- field_type
CREATE TABLE field_type
(
  id bigserial NOT NULL,
  key character varying NOT NULL,
  CONSTRAINT pk_field_type PRIMARY KEY (id),
  CONSTRAINT uq_field_type_code UNIQUE (key)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE field_type
  OWNER TO restdatabus;

-- entity_definition
CREATE TABLE entity_definition
(
  id bigserial NOT NULL,
  name character varying NOT NULL,
  CONSTRAINT pk_entity_definition PRIMARY KEY (id),
  CONSTRAINT uq_entity_definition_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE entity_definition
  OWNER TO restdatabus;

-- field_definition
CREATE TABLE field_definition
(
  id bigserial NOT NULL,
  name character varying NOT NULL,
  field_type_id bigint NOT NULL,
  entity_definition_id bigint NOT NULL,
  CONSTRAINT pk_field_definition_id PRIMARY KEY (id),
  CONSTRAINT fk_entity_definition_id FOREIGN KEY (entity_definition_id)
      REFERENCES entity_definition (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_field_type_id FOREIGN KEY (field_type_id)
      REFERENCES field_type (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE field_definition
  OWNER TO restdatabus;