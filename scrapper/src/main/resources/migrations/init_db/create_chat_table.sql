create sequence chat_sequence start with 1 increment by 1;

CREATE TABLE chat
(
    id              BIGINT      DEFAULT nextval('chat_sequence'),
    tgChatId        BIGINT      NOT NULL,
    trackedLink     BIGINT      NOT NULL,
    FOREIGN KEY (trackedLink) REFERENCES link(id),

    CONSTRAINT chat_pk PRIMARY KEY (id)
);

ALTER SEQUENCE chat_sequence
    OWNED BY chat.id;