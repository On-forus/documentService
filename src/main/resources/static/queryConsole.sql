DROP TABLE databasechangelog;
DROP TABLE databasechangeloglock;
DROP TABLE documents CASCADE;
-- DROP TABLE history;
DROP TABLE registry;
-- TRUNCATE TABLE documents;

INSERT INTO documents (author, title, uuid, create_date, status)
SELECT 'Name ' || i, 'title ' || i, gen_random_uuid(), current_date, status
FROM generate_series(1, 100) AS i, unnest(ARRAY['DRAFT', 'SUBMITTED', 'APPROVED']) AS status
ORDER BY random()
LIMIT 100;

INSERT INTO history (author, action, comments, update_date, history_id)
SELECT 'Scrug1', 'SUBMIT','random comment',current_date, 3

SELECT * FROM documents WHERE id = 1 ORDER BY id LIMIT 100;


