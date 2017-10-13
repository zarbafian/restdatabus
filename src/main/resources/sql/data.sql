-- Users
INSERT INTO person(name) VALUES('Administrator');
INSERT INTO person(name) VALUES('Pouriya');


-- Security account entities

-- password is 'admin'
INSERT INTO account (username, password, enabled, expired, credentials_expired, locked, created_by, created_at, updated_by, updated_at, person_id)
SELECT 'admin', 'ee216758b3b5daebd90cf8ec2565620aa017968f0b2f25776d81d44423b52d99293deee1845b793d58f29bff6856811c716da981c282304d2dc0ce8f0f1f1d5a6a3fa7f40821576f', true, false, false, false, 1, NOW(), NULL, NULL, p.id FROM person p WHERE p.name = 'Administrator';

-- password is 'poz'
INSERT INTO account (username, password, enabled, expired, credentials_expired, locked, created_by, created_at, updated_by, updated_at, person_id)
SELECT 'poz', '4effd7566090af5787c99a76b0a02286d3f9e094c7c96ee90302ffef4eb270b7fae128f200dd99020c673222ef0f5a377a1e8890fbf6037a69b005ea840a6c4fd827705bdd9f9de3', true, false, false, false, 1, NOW(), NULL, NULL, p.id FROM person p WHERE p.name = 'Pouriya';

-- Create role entities
INSERT INTO role (id, code, label, ordinal, effective_at, expires_at, created_at) VALUES (1, 'ROLE_USER', 'User', 0, '2015-01-01 00:00:00', NULL, NOW());
INSERT INTO role (id, code, label, ordinal, effective_at, expires_at, created_at) VALUES (2, 'ROLE_ADMIN', 'Admin', 1, '2015-01-01 00:00:00', NULL, NOW());
INSERT INTO role (id, code, label, ordinal, effective_at, expires_at, created_at) VALUES (3, 'ROLE_SYSADMIN', 'System Admin', 2, '2015-01-01 00:00:00', NULL, NOW());

-- role Admin for admin
INSERT INTO account_role (account_id, role_id) SELECT a.id, r.id FROM account a, role r WHERE a.username = 'admin' and r.id = 2;

-- role User for poz
INSERT INTO account_role (account_id, role_id) SELECT a.id, r.id FROM account a, role r WHERE a.username = 'poz' and r.id = 1;
