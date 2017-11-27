Feature: User can add new book to the database with adequate parameters

    Scenario: user can add new book with correct parameters
       Given command "add book" is selected
       When  title "Uusi64646" and author "kirjailija864831" and ISBN "123-456-78912-3-4" are entered
       Then  new book is added with title "Uusi64646" and author "kirjailija864831" and ISBN "123-456-78912-3-4"

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
       Then  only one book is added with title "Uusi12345" and author "kirjailija" and ISBN "111--111--111"
