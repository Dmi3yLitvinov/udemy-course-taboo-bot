ALTER TABLE card ADD COLUMN new_taboos VARCHAR;
UPDATE card SET new_taboos = array_to_string(taboos, ',');
ALTER TABLE card DROP COLUMN taboos;
ALTER TABLE card RENAME COLUMN new_taboos TO taboos;

ALTER TABLE card ADD COLUMN new_all_taboos VARCHAR;
UPDATE card SET new_all_taboos = array_to_string(all_taboos, ',');
ALTER TABLE card DROP COLUMN all_taboos;
ALTER TABLE card RENAME COLUMN new_all_taboos TO all_taboos;