Feature: User can edit a podcast bookmark

    Scenario: user sees a list of podcasts before editing one
        Given podcast with name "podcast456" and author "author86478" and title "title4868" and URL "url485" has been added
        And   command "edit podcast" is selected
        Then  podcast with name "podcast456" and author "author86478" and title "title4868" and URL "url485" is displayed

    Scenario: user can cancel editing before choosing a podcast to edit
        Given podcast with name "podcast656" and author "author864568" and title "title45668" and URL "url35" has been added
        And   command "edit podcast" is selected
        When  user enters "cancel"
        Then  edit command is cancelled and main menu displayed

    Scenario: user can change the URL of an existing podcast
        Given podcast with name "podcast1323" and author "author2343" and title "title923" and URL "url23r44" has been added
        And   command "edit podcast" is selected
        When  row number "1" is entered
        And   name "" is entered
        And   author "" is entered
        And   title "" is entered
        And   URL "url0841" is entered
        Then  podcast with name "podcast1323" and author "author2343" and title "title923" and URL "url0841" is in the library
        And   podcast with name "podcast1323" and author "author2343" and title "title923" and URL "url23r44" is not in the library

    Scenario: user can change the name of an existing podcast
        Given podcast with name "podcast133" and author "author233" and title "title2323" and URL "url2344" has been added
        And   command "edit podcast" is selected
        When  row number "1" is entered
        And   name "newpodcast34234" is entered
        And   author "" is entered
        And   title "" is entered
        And   URL "" is entered
        Then  podcast with name "newpodcast34234" and author "author233" and title "title2323" and URL "url2344" is in the library
        And   podcast with name "podcast133" and author "author233" and title "title2323" and URL "url2344" is not in the library

    Scenario: user can change the author of an existing podcast
        Given podcast with name "podcast13325" and author "author23522" and title "title129" and URL "url12847" has been added
        And   command "edit podcast" is selected
        When  row number "1" is entered
        And   name "" is entered
        And   author "newauthor243" is entered
        And   title "" is entered
        And   URL "" is entered
        Then  podcast with name "podcast13325" and author "newauthor243" and title "title129" and URL "url12847" is in the library
#        And   podcast with name "podcast13325" and author "author23522" and title "title129" and URL "url12847" is not in the library
    
    Scenario: user can change the title of an existing podcast
        Given podcast with name "podcast4866" and author "author742" and title "title33489" and URL "url4864" has been added
        And   command "edit podcast" is selected
        When  row number "1" is entered
        And   name "" is entered
        And   author "" is entered
        And   title "newtitle290" is entered
        And   URL "" is entered
        Then  podcast with name "podcast4866" and author "author742" and title "newtitle290" and URL "url4864" is in the library
#        And   podcast with name "podcast4866" and author "author742" and title "title33489" and URL "url4864" is not in the library

