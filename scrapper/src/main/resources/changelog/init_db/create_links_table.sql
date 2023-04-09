create sequence links_sequence start with 1 increment by 1;

CREATE TABLE link
(
    id      BIGINT          NOT NULL,
    url     VARCHAR(255)    NOT NULL,

    CONSTRAINT link_pk PRIMARY KEY (id)
);