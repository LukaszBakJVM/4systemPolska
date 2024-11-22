#https://systempolska.freeddns.org      -->aplikacja  uruchomiona  w Docker Container, proxy nginx
   
   
   
   @PostMapping("/upload")
   Wczytuje liste uzytkowników w formacie xml i zapisuje do bazy danych.
   ----------------------------------------------------------------------
   Przykładowy xml;

````
<users>
    <user>
        <name>name1</name>
        <surname>surname1</surname>
        <login>login1</login>
    </user>
</users>
````           

---------------------------------------------------------------

@GetMapping

/user

RequestParam required = false String searchKeyword 

RequestParam required = false  String searchBy  --> defualt id

RequestParam  required = false  String sortBy --> defualt id

RequestParam required = false  int page  --> defualt 0

--------------------------------------------------------------
 1: W przypadku gdy tabele Search Keyword:   jest pusta tabela Select Column:   nie jest brana pod uwagę   i wyszukuje wszystkich użytkowników posortowanych na podstawie Sort By
 (które domyslnie sortuje na podstawie id) a po wybraniu sort by sortuje na podstawie wybranego parametru.


2: W przypadku gdy tabele Search Keyword:   jest uzupełniona , a columna a Select Column:   jest pusta wyszukuje   znaki w columnie domyślnej id ,
w  przypadku gdy Select Column jest uzupełnione  przeszukuje zaznaczona  columne w bazie danych   w poszukiwaniu dokładnie takiego wyrażenia. 

 3:W przypadku pozostawienia (dotyczy  wszystkich  wyżej wymienionych przypadków) Sort By:  puste wyniki po wyszukiwaniu są sortowane po id ,
 gdy Sort By: jest uzupełnione wyniki są sortowane na podstawie wybranego parametru.

Nazwy kolumn do sortowania i wyszukiwnia  są pobierane  dynamicznie z klasy Encji na podstawie adnotacji  @Column.  

Haszowanie odbywa sie w  klasie maper przy pobieraniu danych (dane w bazie są  niezmienione)
W przypadku złego formatu pliku otrzymujemy błąd  -> Wrong File Format.

Po poprawnym wczytaniu pliku otrzymujemy wiadomość  ->     {ilosc} entries added


Uruchomienie 

java -jar /sciezka/do/pliku/systemPolska-0.0.1-SNAPSHOT.jar

lub 
przykładowy dockerfile


FROM   openjdk:21-slim-bookworm

EXPOSE 8080

COPY /jar/systemPolska-*.jar /systemPolska.jar

ENTRYPOINT ["java","-jar","/systemPolska.jar"]
  

