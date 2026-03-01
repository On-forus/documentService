DROP TABLE databasechangelog;
DROP TABLE databasechangeloglock;
DROP TABLE documents CASCADE;
DROP TABLE history;
DROP TABLE registry;
-- TRUNCATE TABLE documents;

drop sequence documents_id_seq;
drop sequence documents_seq;
drop sequence history_seq;
drop sequence registry_id_seq;

SELECT * FROM documents where id = 1;

SELECT * FROM documents where author = 'qwerqw';

ALTER SEQUENCE documents_id_seq222 INCREMENT BY 30;

INSERT INTO documents (id, uuid, create_date,version, update_date, status, author, title)
SELECT i,
    gen_random_uuid(),
       date(now()),
       0,
       date(now()),
       'DRAFT',
       md5(random()::text),
       md5(random()::text)
FROM generate_series(1001, 1000000) as i;

INSERT INTO documents (status, author)
SELECT 'status' || i,
       'author' || i
           FROM generate_series(1112, 1113) as s(i);


EXPLAIN SELECT * FROM documents;

SELECT * From documents
WHERE documents.status = 'DRAFT'