-- Step 1: Add the roletype column to the employee_project_relation table
ALTER TABLE employee_project_relation
ADD COLUMN roletype INT;

-- Step 2: Define the roletype column as a foreign key referencing the id column of the role table
ALTER TABLE employee_project_relation
ADD CONSTRAINT fk_roletype
FOREIGN KEY (roletype) REFERENCES role(id);

-- Step 3: Update existing records in the employee_project_relation table with roletype values
UPDATE employee_project_relation epr
JOIN role r ON epr.role = r.type
SET epr.roletype = r.id;
