CREATE DOMAIN text AS VARCHAR;

CREATE SEQUENCE public.users_id_seq START WITH 100 INCREMENT BY 1;

CREATE TABLE public.users (
            id bigint DEFAULT public.users_id_seq.nextval NOT NULL,
            active boolean NOT NULL,
            createdat timestamp NOT NULL,
            updatedat timestamp NOT NULL,
            email varchar(255) NOT NULL,
            name varchar(255) NOT NULL,
            password varchar(255) NOT NULL,
            phone varchar(11) NOT NULL,
            role varchar(32) NOT NULL,
            text text,
            token varchar(255) NOT NULL,
            username varchar(50) NOT NULL,
            author_id bigint,
            CONSTRAINT users_pkey PRIMARY KEY (id),
            CONSTRAINT users_author_id_fk FOREIGN KEY (author_id) REFERENCES public.users (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
            CONSTRAINT users_email_uq UNIQUE (email),
            CONSTRAINT users_phone_uq UNIQUE (phone),
            CONSTRAINT users_token_uq UNIQUE (token),
            CONSTRAINT users_username_uq UNIQUE (username)
            );

CREATE SEQUENCE public.userfiles_id_seq START WITH 100 INCREMENT BY 1;

CREATE TABLE public.userfiles (
            id bigint DEFAULT public.userfiles_id_seq.nextval NOT NULL,
            path varchar(255) NOT NULL,
            sort int NOT NULL,
            title varchar(255) NOT NULL,
            user_id bigint NOT NULL,
            CONSTRAINT userfiles_pkey PRIMARY KEY (id),
            CONSTRAINT userfiles_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
            );
