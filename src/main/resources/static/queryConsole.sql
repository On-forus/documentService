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
