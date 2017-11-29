Feature: User can delete a book from database

    Scenario: user can delete an added book
       Given book with title "Kirja123456", author "Kirjailija123456" and ISBN "00-000-00" has been added
       And   command "delete book" is selected
       When  title "Kirja123456" and author "Kirjailija123456" and ISBN "00-000-00" are entered
       Then  book with title "Kirja123456" and author "Kirjailija123456" and ISBN "00-000-00" is deleted

#    Scenario: user cannot delete a nonexistent book
#       Given book with title "Kirja123456", author "Kirjailija123456" and ISBN "00-000-00" has been added
#       And   command "delete book" is selected
#       When  title "Kirja12345" and author "Kirjailija123456" and ISBN "00-000-00" are entered
#       Then  ...