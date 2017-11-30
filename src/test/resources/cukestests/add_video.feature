Feature: User can add new video to the database with adequate parameters

#    Scenario: user can add new video with valid URL and title
#       Given command "add video" is selected
#       When  URL "www.address.com" and title "New video" are entered
#       Then  new video is added with URL "www.address.com" and title "New video"
#
#    Scenario: user cannot add new video without URL
#       Given command "add video" is selected
#       When  URL "" and title "Video title" are entered
#       Then  video with URL "" and title "Video title" is not added
#
#    Scenario: user can add new video without title
#       Given command "add video" is selected
#       When  URL "www.videos.com" and title "" are entered
#       Then  new video is added with URL "www.videos.com" and title ""
#
#    Scenario: user cannot add the same video twice with same URL and title
#       Given command "add video" is selected
#       When  URL "www.url.com" and title "title" are entered
#       And   command "add video" is selected
#       And   URL "www.url.com" and title "title" are entered
#       Then  only one video is added with URL "www.url.com" and title "title"
#
#    Scenario: user cannot add the same video twice with same URL and different title
#       Given command "add video" is selected
#       When  URL "www.url.com" and title "title" are entered
#       And   command "add video" is selected
#       And   URL "www.url.com" and title "" are entered
#       Then  only one video is added with URL "www.url.com" and title "title"
