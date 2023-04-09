create sequence chat_sequence start with 1 increment by 1;

CREATE TABLE chat
(
    id              BIGINT      NOT NULL,
    tgChatId        BIGINT      NOT NULL,
    trackedLinks    link[],

    CONSTRAINT chat_pk PRIMARY KEY (id)
);