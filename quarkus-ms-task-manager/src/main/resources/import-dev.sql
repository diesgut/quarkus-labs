-- password its admin
INSERT INTO "users" ("id", "name", "password", "created_at", "version")
VALUES (1, 'admin', '$2a$10$e2pPIYNUWEVCoHsJVg/9jeJ4tKghgg2n10qg2ax8Zfi87FhzOIYmm', NOW(), 0)
    ON CONFLICT DO NOTHING;

INSERT INTO "user_roles" ("id", "role") VALUES (1, 'admin')
    ON CONFLICT DO NOTHING;
INSERT INTO "user_roles" ("id", "role") VALUES (1, 'user')
    ON CONFLICT DO NOTHING;

-- password its user
INSERT INTO "users" ("id", "name", "password", "created_at", "version")
VALUES (2, 'user', '$2a$10$fK0zvUsMnYW28uHKNyjWQOpzRTnyypxOrbtfI0m/c2vv.QGY6LeJi', NOW(), 0)
    ON CONFLICT DO NOTHING;
INSERT INTO "user_roles" ("id", "role") VALUES (2, 'user')
    ON CONFLICT DO NOTHING;

INSERT INTO "projects" ("id", "name", "user_id", "created_at", "version")
VALUES (1, 'Work', 1, NOW(), 0)
    ON CONFLICT DO NOTHING;

-- ALTER SEQUENCE IF EXISTS hibernate_sequence RESTART WITH 10;
ALTER SEQUENCE IF EXISTS persons_SEQ RESTART WITH 10;
ALTER SEQUENCE IF EXISTS projects_SEQ RESTART WITH 10;
ALTER SEQUENCE IF EXISTS tasks_SEQ RESTART WITH 10;
ALTER SEQUENCE IF EXISTS users_SEQ RESTART WITH 10;
