Feature: User can delete a book from database

    Scenario: user can delete an added book
       Given book with title "Kirja123456", author "Kirjailija123456" and ISBN "00-000-00" has been added
       And   command "delete book" is selected
       When  title "Kirja123456" is entered
       And   author "Kirjailija123456" is entered
       And   ISBN "00-000-00" is entered
       Then  book with title "Kirja123456" and author "Kirjailija123456" and ISBN "00-000-00" is deleted

    Scenario: user cannot delete a nonexistent book
       Given book with title "Kirja123456", author "Kirjailija123456" and ISBN "00-000-00" has been added
       And   command "delete book" is selected
       When  title "Kirja12345" is entered
       And   author "Kirjailija123456" is entered
       And   ISBN "00-000-00" is entered
       Then  no books are deleted
       
    Scenario: user can delete an added podcast
       Given podcast with name "Podcastname" and author "Author" and title "Podcast" and URL "https://www.podcastsaresogreat.com" has been added
       And   command "delete podcast" is selected
       When  name "Podcastname" is entered
       And   author "Author" is entered
       And   title "Podcast" is entered
       And   URL "https://www.podcastsaresogreat.com" is entered
       Then  podcast with name "Podcastname" and author "Author" and title "Podcast" and URL "https://www.podcastsaresogreat.com" is deleted

    Scenario: user cannot delete a nonexistent podcast
       Given podcast with name "Podcastname" and author "Author" and title "Podcast" and URL "https://www.podcastsaresogreat.com" has been added
       And   command "delete podcast" is selected
       When  name "PodcastnameNOTEXISTS" is entered
       And   author "Author" is entered
       And   title "Podcast" is entered
       And   URL "https://www.podcastsaresogreat.com" is entered
       Then  no podcasts are deleted

    Scenario: user can delete an added video
       Given video with URL "www.testi1234.fi" and name "testi1234" has been added
       And   command "delete video" is selected
       When  row number "1" is entered
       Then  video with URL "www.testi1234.fi" and name "testi1234" is deleted

    Scenario: user cannot delete a nonexistent video
       Given video with URL "www.testi5678.com" and name "testi5678" has been added
       And   command "delete video" is selected
       When  row number "3" is entered
       And   row number "cancel" is entered
       Then  user is notified the entered row number is invalid
