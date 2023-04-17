drop table if exists chat;
drop table if exists link;
drop sequence if exists chat_sequence;
drop sequence if exists links_sequence;

create sequence links_sequence start with 1 increment by 1;

CREATE TABLE link
(
    id              BIGINT          NOT NULL DEFAULT nextval('links_sequence'),
    url             VARCHAR(255)    NOT NULL,
    last_updated    TIMESTAMP,
    last_checked    TIMESTAMP,
    last_checked_when_was_updated TIMESTAMP,
    last_commit_date  TIMESTAMP,
    last_answer_date  TIMESTAMP,

    CONSTRAINT link_pk PRIMARY KEY (id)
);

ALTER SEQUENCE links_sequence
OWNED BY link.id;