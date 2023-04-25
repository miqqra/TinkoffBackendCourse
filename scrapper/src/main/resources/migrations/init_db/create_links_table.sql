drop table if exists chat;
drop table if exists link;
drop sequence if exists chat_sequence;
drop sequence if exists links_sequence;

create sequence if not exists links_sequence start with 1 increment by 1;

CREATE TABLE link
(
    id              BIGINT          NOT NULL DEFAULT nextval('links_sequence'),
    url             VARCHAR(255)    NOT NULL,

    CONSTRAINT link_pk PRIMARY KEY (id)
);