Feature: User can add new book to the database with adequate parameters

    Scenario: user can add new book with valid title, author and ISBN
       Given command "add book" is selected
       When  title "Uusi64646" and author "kirjailija864831" and ISBN "123-456-78912-3-4" are entered
       Then  new book is added with title "Uusi64646" and author "kirjailija864831" and ISBN "123-456-78912-3-4"

    Scenario: user cannot add new book without title
       Given command "add book" is selected
       When  title "" and author "kirjailija8646435" and ISBN "64948-646213" are entered
       Then  book with title "" and author "kirjailija8646435" and ISBN "64948-646213" is not added

    Scenario: user cannot add new book without author
       Given command "add book" is selected
       When  title "kirja156483" and author "" and ISBN "64948-646213" are entered
       Then  book with title "kirja156483" and author "" and ISBN "64948-646213" is not added

    Scenario: user can add new book without ISBN
       Given command "add book" is selected
       When  title "Uusi646746" and author "kirjailija8264831" and ISBN "" are entered
       Then  new book is added with title "Uusi646746" and author "kirjailija8264831" and ISBN ""

    Scenario: user cannot add the same book twice with same author, title and ISBN
       Given command "add book" is selected
       When  title "Uusi38195" and author "kirjailija02835" and ISBN "111--111--111" are entered
       And   command "add book" is selected
       And   title "Uusi38195" and author "kirjailija02835" and ISBN "111--111--111" are entered
       Then  only one book is added with title "Uusi38195" and author "kirjailija02835" and ISBN "111--111--111"

    Scenario: user cannot add the same book twice with same author and title, and different ISBN
       Given command "add book" is selected
       When  title "Uusi12345" and author "kirjailija" and ISBN "111--111--111" are entered
       And   command "add book" is selected
       And   title "Uusi12345" and author "kirjailija" and ISBN "" are entered
       Then  only one book is added with title "Uusi12345" and author "kirjailija"
