Feature: User can open a video in default browser

    Scenario: user can open a video in the browser
        Given command "open video" is selected
        When  url "http://www.google.com" is entered
        Then  url "http://www.google.com" is opened in browser