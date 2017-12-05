Feature: User can open a video in default browser

    Scenario: user can open a video in the browser
        Given video with name "google" and url "www.google.com" has been added
        And   command "open video" is selected
        When  row number "1" is entered
        Then  url "https://www.google.com" is opened in browser

    Scenario: user can cancel opening a video
        Given command "open video" is selected
        When  user enters "cancel"
        Then  no url is opened in browser

    Scenario: user is notified of an invalid row number when opening a video
        Given video with name "youtube" and url "http://www.youtube.com" has been added
        When  command "open video" is selected
        And   row number "-1" is entered
        Then  user is notified the entered row number is invalid

    Scenario: user is prompted to enter a number when opening a video and entering a string
        Given  command "open video" is selected
        And   row number "koira" is entered
        Then  user is prompted for a number