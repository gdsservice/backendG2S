-- V2__fix_role_names.sql
UPDATE user_role SET name = 'ADMIN' WHERE name = 'ROLE_ADMIN';
UPDATE user_role SET name = 'USER' WHERE name = 'ROLE_USER';
UPDATE user_role SET name = 'SUPER_ADMIN' WHERE name = 'ROLE_SUPER_ADMIN';
