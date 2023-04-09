INSERT INTO chat(id, tgchatid, trackedlinks)
    values (100, 200, Array[
        (1, 'github.com')::link, (2, 'vk.com')::link]);

INSERT INTO chat(id, tgchatid, trackedlinks)
    values (101, 200, Array[
        (1, 'github.com')::link, (3, 'stackoverflow.com')::link]);

INSERT INTO chat(id, tgchatid, trackedlinks)
    values (102, 200, Array[
        (2, 'vk.com')::link, (4, 'mit.com')::link]);