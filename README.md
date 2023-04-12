<h1>This is project with automation tests for "qase" with using Selenide</h1>

<h2>Qase is the test management system where users can create projects and add some entities inside of it, like create test-cases, test-plans, raising the defects, etc.</h2>

<h2>General check-list for projects:</h2>
<ol type="1">
   <li>Check login with acceptable username and password</li>
   <li>Check created project is displayed on ProjectPage with project name, project code</li>
   <li>Check created project is displayed on SettingsPage with project name, project code, description</li>
   <li>Check updated project is displayed on ProjectPage with updated project name, project code</li>
   <li>Check updated project is displayed on SettingsPage with updated project name, project code, description</li>
   <li>Check that project can be deleted in SettingsPage</li>
   <li>Check that project can be deleted in ProjectsPage</li>
</ol>

<h2>Check-list for test-plans:</h2>
<ol type="1">
   <li>Check that test-plan can be created with required fields (Title and test case)</li>
   <li>Check that test-plan can be created with all available fields (Title, description, test case)</li>
   <li>Check that test-plan can be updated with different Title, Description and Test case</li>
   <li>Check that test-plan can be deleted</li>
   <li>Check that test-plan can be created with the same name, that already exists</li>
   <li>Try to create plan w/o Title and validate the error</li>
   <li>Try to create plan w/o test case and validate the error</li>
   <li>Try to update plan with empty Title and validate the error</li>
   <li>Try to update plan w/o test case and validate the error</li>
</ol>