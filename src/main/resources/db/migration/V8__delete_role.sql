-- Step 1: Create a backup of your data if needed
-- Optional: Backup the data in case you need it later
SELECT * INTO employee_project_relation_backup FROM employee_project_relation;

-- Step 2: Remove the role column from the table
ALTER TABLE employee_project_relation
DROP COLUMN role;
