Feature: User can add new book to the database with adequate parameters

    Scenario: user can add new podcast with valid name, title, author and URL
       Given command "add podcast" is selected
       When  name "NewPodCastName" and author "NewPodcastAuthor" and title "NewPodcastTitle" and URL "https://www.podcast.com" are entered
       Then  new podcast is added with name "NewPodCastName" and author "NewPodcastAuthor" and title "NewPodcastTitle" and URL "https://www.podcast.com"

    Scenario: user can add new podcast without URL
       Given command "add podcast" is selected
       When  name "NewPodcastName" and author "NewPodcastAuthor" and title "NewPodcastTitle" and URL "" are entered
       Then  new podcast is added with name "NewPodcastName" and author "NewPodcastAuthor" and title "NewPodcastTitle" and URL ""
       
   Scenario: user cannot add new podcast without name
       Given command "add podcast" is selected
       When  name "" and author "NewPodcastAuthor" and title "NewPodcastTitle" and URL "https://www.podcast.com" are entered
       Then  podcast with name "" and author "NewPodcastAuthor" and title "NewPodcastTitle" and URL "https://www.podcast.com" is not added