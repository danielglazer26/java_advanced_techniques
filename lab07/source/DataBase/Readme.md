Napisz program, który pozwoli zasymulować działanie narzędzia do obsługi finansów komitetu rodzicielskiego jakiejś szkolnej klasy. Główną funkcją tego narzędzia ma być obsługa przypadku zbierania funduszy na organizowane wydarzenia. Narzędzie może działać jako aplikacja desktopowa w trybie konsolowym, może też być zaimplementowane jako aplikacja z graficznym interfejsem (desktopowa lub internetowa, ale to wymagałoby więcej pracy).

Zadanie należy zrealizować wykorzystując relacyjną bazę danych. Można skorzystać z sqlite czy też h2 (zalecane, bo nie ma potrzeby instalowania żadnego dodatkowego serwisu) lub innej bazy danych (pod warunkiem, że podczas oddawania zadania będzie można połączyć się z tą bazą danych).

Podczas realizacji zadania można skorzystać ze wzorca projektowego DAO (oraz możliwości, jakie daje JDBC) lub mapowania ORM (oraz możliwości, jakie daje JPA razem z frameworkiem Hibernate).

W przypadku użycia DAO (z JDBC) proszę pamiętać, by parametryzować zapytania SQL (nie wolno budować zapytań poprzez "sklejanie" kolejnych ciągów znaków). Proszę pamiętać o przewijalności zbioru wynikowego.

W przypadku zastosowania mapowania ORM proszę zadbać o automatyczne wygenerowanie schematu bazy danych oraz zastosowanie warstwy serwisów.

Zakładamy, że w bazie danych będą przechowywane następujące informacje:
* Wydarzenie - identyfikator, nazwa, miejsce, termin
* Osoba - identyfikator, imię, nazwisko
* Raty - identyfikator, identyfikator wydarzenia, numer raty, termin płatności, kwota
* Wpłaty - identyfikator, termin wpłaty, kwota wpłaty, identyfikator osoby, identyfikator wydarzenia, numer raty


Program powinien:
- umożliwiać ręczne wprowadzanie danych (osób, wydarzeń, rat, wpłat) oraz ładowanie danych z plików csv.
- umożliwiać przeglądanie danych (w szczególności przeglądanie należnych i dokonanych wpłat)
- automatycznie sprawdzać terminowość i wysokość wpłat oraz wysyłać monity o kolejnych płatnościach (wystarczy, że będzie pisał do pliku z logami monitów, upływ czasu należy zasymulować).
- automatycznie eskalować monity w przypadku braku terminowej wpłaty (wystarczy, że będzie pisał do pliku z logami ekalowanych monitów, upływ czasu należy zasymulować)
