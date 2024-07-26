-- Step 1: Drop any foreign key constraints that reference these columns
ALTER TABLE employee_project_relation DROP FOREIGN KEY fk_roletype;
-- Assuming there's a foreign key constraint named 'fk_roletype' referencing the 'roletype' column.

-- Step 2: Drop the 'roletype' column
ALTER TABLE employee_project_relation DROP COLUMN roletype;

-- Step 3: Drop the 'id' column
ALTER TABLE employee_project_relation DROP COLUMN id;
