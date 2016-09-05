
Feature: Pardot Test

1.	Log in to Pardot (https://pi.pardot.com, Username: pardot.applicant@pardot.com, Password: Applicant2012)
2.	Create a list with a random name (Marketing > Segmentation > Lists)
3.	Attempt to create another list with that same name and ensure the system correctly gives a validation failure
4.	Rename the original list
5.	Ensure the system allows the creation of another list with the original name now that the original list is renamed
6.	Create a new prospect (Prospect > Prospect List)
7.	Add your new prospect to the newly created list
8.	Ensure the new prospect is successfully added to the list upon save
9.	Send a text only email to the list (Marketing > Emails)  *Please note, email is disabled in this account so you will not actually be able to send the email.  This is okay.
10.	Log out

    Scenario: Pardot Coding Exercise: Create New List
        Given I am logged into Pardot
        When I navigate to the Segmentation Lists page
            And I open the Add List modal for a new list
            And I add the Segmentation List name "Random" with a "New" folder
            And I save the Segmentation List
        Then the Segmentation List is created


