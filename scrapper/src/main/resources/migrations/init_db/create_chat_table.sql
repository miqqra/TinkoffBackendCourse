create sequence if not exists chat_sequence start with 1 increment by 1;

CREATE TABLE chat
(
    id              BIGINT      UNIQUE      DEFAULT nextval('chat_sequence'),
    tgChatId        BIGINT      NOT NULL,
    trackedLink     BIGINT,
    FOREIGN KEY (trackedLink) REFERENCES link(id),

    CONSTRAINT chat_pk PRIMARY KEY (id)
);