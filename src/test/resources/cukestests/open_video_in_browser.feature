Feature: User can open a video in default browser

    Scenario: user can open a video in the browser
        Given video with URL "www.google.com" and name "google" has been added
        And   command "open video" is selected
        When  row number "1" is entered
        Then  URL "https://www.google.com" is opened in browser

    Scenario: user can cancel opening a video
        Given command "open video" is selected
        When  user enters "cancel"
        Then  no URL is opened in browser

    Scenario: user is notified of an invalid row number when opening a video
        Given video with URL "http://www.youtube.com" and name "youtube" has been added
        And   command "open video" is selected
        When  row number "-1" is entered
        Then  user is notified the entered row number is invalid

    Scenario: user is prompted to enter a number when opening a video and entering a string
        Given  command "open video" is selected
        And   row number "koira" is entered
        Then  user is prompted for a number