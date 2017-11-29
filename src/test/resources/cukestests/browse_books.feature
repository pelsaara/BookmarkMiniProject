Feature: User can browse books

    Scenario: user can browse books when no books have been added
       Given command "browse" is selected 
       Then  empty list of books is printed

    Scenario: user can browse books after adding one book
       Given book with title "Kirja123456", author "Kirjailija123456" and ISBN "00-000-00" has been added
       And command "browse" is selected 
       Then book with title "Kirja123456" and author "Kirjailija123456" and ISBN "00-000-00" is printed

    Scenario: user can browse books after adding two books
       Given book with title "Kirja123456", author "Kirjailija123456" and ISBN "00-000-00" has been added
       And book with title "Testi567", author "Testaaja890" and ISBN "11-111-11" has been added
       And command "browse" is selected 
       Then book with title "Kirja123456" and book with title "Testi567" is printed