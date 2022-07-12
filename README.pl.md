# Java Techniki Zaawansowane

## Spis treści
* [Laboratorium 1](#laboratorium-1)
* [Laboratorium 2](#laboratorium-2)
* [Laboratorium 3](#laboratorium-3)
* [Laboratorium 4](#laboratorium-4)
* [Laboratorium 5](#laboratorium-5)
* [Laboratorium 6](#laboratorium-6)
* [Laboratorium 7](#laboratorium-7)
* [Laboratorium 8](#laboratorium-8)
* [Laboratorium 9](#laboratorium-9)
* [Laboratorium 10](#laboratorium-10)
* [Laboratorium 11](#laboratorium-11) 
* [Laboratorium 12](#laboratorium-12) 
* [Laboratorium 13](#laboratorium-13) 
* [Laboratorium 14](#laboratorium-14)  
* [Laboratorium 15](#laboratorium-15) 
## Laboratorium 1

Polecenie
-------------
Napisz aplikację, która pozwoli na sprawdzania wskazanych katalogów pod kątem wystąpienia zmian w zawartych w nich plikach. 

Aplikacja powinna wyliczać skrót MD5 dla każdego badanego pliku w dwóch krokach: 1. przygotowując "snapshot" bieżącej sytuacji oraz 2. weryfikując na podstawie zapamiętanego "snapshotu", czy w plikach wprowadzono jakieś zmiany.

Aplikacja powinna być też napisana z wykorzystaniem modułów (wprowadzonych w Javie od jdk 9). Powstać ma moduł biblioteki oraz moduł samej aplikacji (korzystający z modułu biblioteki). 

Powstałe moduły należy wyeksportować do plików jar.

Używając jlink należy przygotować minimalne środowisko uruchomieniowe, do którego podpięte zostaną wymienione wyżej moduły.

Aplikację powinno dać się uruchomić z linii komend, korzystając tylko z wygenerowanego środowiska uruchomieniowego. Sama aplikacja powinna oferować interfejs użytkownika (najlepiej graficzny, minimum - tekstowy).

Do operacji na plikach i katalogów należy wykorzystać pakiet klas java.nio. Ponadto zalecane jest napisanie programu w stylu funkcjonalnym.

## Laboratorium 2

Polecenie
-------------


Napisz aplikację, która umożliwi przeglądanie danych osobowych zapisanych na dysku. Zakładamy, że dane osobowe zapisywane będą w folderach o nazwach odpowiadających identyfikatorom osób, których dotyczą. Dokładniej, w folderach pojawiać się mają dwa pliki: record.txt (o dowolnej strukturze, w pliku tym zapisane mają być: imię, nazwisko, wiek, ....) oraz image.png (ze zdjęciem danej osoby, przy czym do celów testowych można zamiast zdjęcia użyć dowolnego obrazka).

Interfejs graficzny tej aplikacji można zrealizować za pomocą dwóch paneli - jednego, przeznaczonego na listę przeglądanych folderów oraz drugiego, służącego do wyświetlania danych osobowych i zdjęcia odpowiedniego do folderu wybranego z listy.

Aplikację należy zaprojektować z wykorzystaniem słabych referencji (ang. weak references). Zakładamy, że podczas przeglądania folderów zawartość plików tekstowych i  plików zawierających obrazki będzie ładowana do odpowiedniej struktury. Słabe referencje powinny pozwolić na ominięcie konieczności wielokrotnego ładowania tej samej zawartości - co może nastąpić podczas poruszanie się wprzód i wstecz po liście folderów.

Aplikacja powinna wskazywać, czy zawartość pliku została załadowana ponownie, czy też została pobrana z pamięci. Wskazanie to może być zrealizowane za pomocą jakiegoś znacznika prezentowanego na interfejsie.

W celu oceny poprawności działania aplikację należy uruchamiać przekazując wirtualnej maszynie parametry ograniczające przydzielaną jej pamięć. Na przykład -Xms512m (co oznacza minimalnie 512 MB pamięci), -Xmx1024m (co oznacza maksymalnie 1GB).
Należy też przetestować możliwość regulowania zachowania się algorytmu odśmiecania, do czego przydają się opcje -XX:+ShrinkHeapInSteps, -XX:-ShrinkHeapInSteps. Proszę przestudiować, jakie inne atrybuty można przekazać do wirtualnej maszyny, w tym selekcji algorytmu -XX:+UseSerialGC, -XX:+UseParNewGC (deprecated), -XX:+UseParallelGC, -XX:+UseG1GC.

Architektura aplikacji powinna umożliwiać dołączanie różnych podglądaczy zawartości (czyli klas odpowiedzialnych za renderowanie zawartości plików z danymi), przy czym podglądacze powinny być konfigurowalne (np. poprzez określenie sposobu renderowania czcionek czy obrazków).

Proszę dodać do źródeł plik readme.md z wnioskami co do stosowalności opcji wirtualnej maszyny.

## Laboratorium 3

Polecenie
---------

Napisz aplikację, która pozwoli skonsumować dane pozyskiwane z serwisu oferującego publiczne restowe API. Ciekawą listę serwisów można znaleźć pod adresem:
https://rapidapi.com/collection/list-of-free-apis (wymagają klucza API), czy też https://mixedanalytics.com/blog/list-actually-free-open-no-auth-needed-apis/ (te klucza API nie wymagają). W szczególności w tej drugiej grupie istnieje: https://developers.teleport.org/api/reference/. Właśnie to api ma być użyte w aplikacji.

Bazując na nim należy zbudować intefejs użytkownika, który pozwoli na przeprowadzanie testów z wiedzy z geograficznej. Renderowanie zapytań i odpowiedzi powinno być tak zaimplementowane, by dało się zmianić ustawienia językowe (lokalizacji) w oparciu o tzw. bundle (definiowane w plikach i klasach - obie te opcje należy przetestować). Wspierane mają być języki: polski i angielski. 

Proszę zapoznać się z api i zaproponować kilka schematów zapytań i pól odpowiedzi. Niech użytkownik ma możliwość parametryzowania tych zapytania (w miejsce kropek niech wpisywane będą wartości z list wyboru - jeśli da się je pozyskać z serwisu, lub niech będą to wartości wprowadzone wolnym tekstem) oraz ma możliwość zadeklarowanie własnej odpowiedzi. Odpowiedź podana przez użytkownika powinna być zweryfikowana przez aplikację (aplikacja, po wysłaniu zapytania przez api powinna sprawdzić, czy wynik tego zapytania jest zgodny z odpowiedzią udzieloną przez użytkownika).

Na przykład dla szablonu zapytania zapytania: 
    "Ile jednostek administracyjnych poziomu ... istnieje w państwie ...." (w miejsce kropek powinno dać się coś wpisać)
powinno istnieć jedno pole na wpisanie odpowiedzi
    "..."  (miejsce na wpisanie liczby).
oraz linijka weryfikacji, np.:
     "Tak, masz rację. W państwie .... istnieją 3 jednostki podziału administracyjnego poziomu ..." (to ma wypełnić sama aplikacja).

Jak widać na interfejsie użytkownika trzeba obsłużyć odmianę przez liczby. Można do tego zastosować wariantowe pobieranie tekstów z bundli. Do tego przyda się klasa ChoiceFormat. 

Do pozyskiwania danych być może trzeba będzie uruchomić kilka zapytań (patrz np. endpoint: https://api.teleport.org/api/countries/iso_alpha2:PL/admin1_divisions/)
W implementacji do wykorzystania są również klasy z pakietów java.text, java.util.

Proszę zuważyć, że we wskazanym api nie korzysta się z parametru pozwalającego określić język zwracanych danych (niektóre serwisy to umożliwiają, np. atrybutu languageCode jest używany w GeoDB Cities API: https://rapidapi.com/wirefreethought/api/geodb-cities?endpoint=59908d68e4b075a0d1d6d9ac ).

Opcjonalnie można wykorzystać jakieś inne api, jeśli tylko zachowa się przedstawioną wyżej koncepcję (parametryzowane szablony zapytań, do wypełnienia pola odpowiedzi, linijka weryfikacji z odmianą przez liczby/osoby - wszystko przynajmniej z obsługą dwóch języków: polski i angielski). Na przykład może powstać aplikacja testującą wiedzę o muzyce (ile dany kompozytor napisał jakich utworów).

## Laboratorium 4

Polecenie
---------

Napisz aplikację, która umożliwi zlecanie wykonywania zadań instancjom klas ładowanym własnym ładowaczem klas. Do realizacji tego ćwiczenia należy użyć Java Reflection API.

Tworzona aplikacja powinna udostępniać graficzny interfejs, na którym będzie można:
1. zdefiniować zadanie (zakładamy, że będzie można definiować "dowolne" zadania reprezentowane przez ciąg znaków),
2. załadować klasę wykonującą zadanie (zakładamy, że będzie można załadować więcej niż jedną taką klasę),
3. zlecić wykonanie wskazanego zadania wskazanej załadowanej klasie, monitorować przebieg wykonywania zadania, wyświetlić wynik zadania.
4. wyładować wybraną klasę z wcześniej załadowanych

Realizacja zadania powinna opierać się na wykorzystaniu API (klas i interfejsów) zdefiniowanych w archiwum Refleksja02.zip.

Należy dostarczyć przynajmniej 3 różne klasy implementujące interfejs Processor. Każda taka klasa po załadowaniu powinna oznajmić, poprzez wynik metody getInfo(), jakiego typu zadanie obsługuje. Na przykład pozyskana informacja w postaci "sumowanie" oznaczałaby, że zadanie można zdefiniować ciągiem znaków "1 + 2", gdzie oczekiwanym wynikiem byłoby "3". Informacja "zamiana małych liter na duże" oznaczałaby, że dla zadania "abc" oczekiwanym wynikiem ma być "ABC".

Klasy ładowane powinny być skompilowane w innym projekcie niż sama użytkowa aplikacja (podczas budowania aplikacja nie powinna mieć dostępu do tych klas). Można założyć, że kod bajtowy tych klas będzie umieszczany w katalogu, do którego aplikacja będzie miała dostęp. Ścieżka do tego katalogu powinna być parametrem ustawianym w aplikacji w trakcie jej działania. Wartością domyślną dla ścieżki niech będzie katalog, w którym uruchomiono aplikację. Aplikacja powinna odczytać zawartość tego katalogu i załadować własnym ładowaczem odnalezione klasy. Zakładamy, że podczas działania aplikacji będzie można "dorzucić" nowe klasy do tego katalogu (należy więc pomyśleć o pewnego rodzaju "odświeżaniu").

Wybieranie klas (a tym samym algorytmów przetwarzania) powinno odbywać się poprzez listę wyświetlającą nazwy załadowanych klas. Nazwom tym niech towarzyszą opisy pozyskane metodą getInfo() z utworzonych instancji tych klas.

Zlecanie zadań instancjom klas powinno odbywać się poprzez metodę submitTask(String task, StatusListner sl).  W metodzie tej należy podać słuchacza typu StatusListener, który będzie otrzymywał informacje o zmianie statusu przetwarzania. Do reprezentacji statusu przetwarzania służy klasa Status (klasę tę zadeklarowano tak, aby po utworzeniu statusu jego atrybuty były tylko do odczytu). 

Proszę przetwarzanie zaimplementować w wątku z opóźnieniami, by dało się monitorować bieżący status przetwarzania.Do monitorowania statusów przetwarzania i wyświetlania wyników można użyć osobnej listy (monitora zadań) wyświetlanej na interfejsie aplikacji.

Proszę napisać własny (!) ładowacz klas. Ładowacz ten może być klasą, do której przekazana zostanie ścieżka położenia kodów bajtowych ładowanych klas z algorytmami przetwarzania. Własny ładowacz nie może rozszerzać klasy URLClassLoader.

Aplikację można stworzyć korzystając z jdk1.8. Można też spróbować zaimplementować ją korzystając z jdk11 (należy zastanowić się wtedy jednak, jak powinien wyglądać ładowacz klas).

Uwaga: klasa zostanie wyładowana, gdy znikną wszystkie referencje od jej instancji oraz gdy zniknie referencja do ładowacza, który tę klasę załadował (i zadziała odśmiecanie). Proszę monitorować liczbę załadowanych i wyładowanych klas za pomocą jconsole lub jmc.

## Laboratorium 5
Polecenie
------

Zaimplementuj aplikację z graficznym interfejsem pozwalającą przeprowadzić analizę statystyczną na danych tabelarycznych.
Analiza ta może polegać na: wyznaczeniu linii trendu, obliczeniu mediany, obliczeniu odchylenia standardowego. 

Aplikacja ta powinna umożliwiać:
- wyświetlanie/edytowanie danych tabelarycznych;
- wybranie algorytmu, jakim będą one przetwarzane (należy zailplementować przynajmniej 2 algorytmy analiz statystycznych);
- wyświetlenie wyników przetwarzania.
W trakcie implementacji należy wykorzystać interfejs dostarczyciela serwisu (ang. Service Provider Interface, SPI).
Dokładniej, stosując podejście SPI należy zapewnić aplikacji możliwość załadowania klas implementujących zadany interfejs
już po zbudowaniu samej aplikacji. 
Klasy te (z zaimplementowanymi wybranymi algorytmami analizy skupień) mają być dostarczane w plikach jar umieszczanych w ścieżce. 
Należy stworzyć dwie wersje projektu: standardową oraz modularną.

Aby zapoznać się z problemem proszę sięgnąć do przykładowych projektów w archiwum udostępnionym pod adresem:
    http://tomasz.kubik.staff.iiar.pwr.wroc.pl/dydaktyka/Java/ServiceProviderInterfaceWorkspace.zip

W implementacji należy wykorzystać pakiet ex.api, zawierającym klasy o kodzie źródłowy pokazanym poniżej.

Trochę informacji o SPI można znaleźć pod adresem:
    https://www.baeldung.com/java-spi
Porównanie SPI ze SpringBoot DI zamieszczono pod adresem:
    https://itnext.io/serviceloader-the-built-in-di-framework-youve-probably-never-heard-of-1fa68a911f9b


## Laboratorium 6
Polecenie
___________


Zaimplementuj rozproszony system imitujący działanie sieci tablic reklamowych, na których cyklicznie wyświetlane są zadane teksty (tj. przez określony czas widać jedno hasło reklamowe, po czym następuje zmiana).
Wymiana danych pomiędzy elementami systemu powinna odbywać się poprzez gniazda SSL (z użyciem certyfikatów), z wykorzystaniem menadżera bezpieczeństwa i plików polityki.
(materiały do przestudiowania: 
 https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/socketfactory/index.html
 https://docs.oracle.com/en/java/javase/11/security/java-secure-socket-extension-jsse-reference-guide.html
)

W systemie tym wyróżnione mają być trzy typy aplikacji (klas z metodą main):
* Manager (Menadżer) - odpowiedzialna za przyjmowanie od klientów zamówień wyświetlanie haseł reklamowych oraz przesyłanie tych haseł na tablice reklamowe
* Client (Klient) - odpowiedzialna za zgłaszanie menadżerowi zamówień lub ich wycofywanie
* Billboard (Tablica) - odpowiedzialna za wyświetlanie haseł, dowiązująca się do menadżera, który może zatrzymać i uruchomić wyświetlanie haseł

Podczas uruchomienia systemu należy utworzyć: 1 instancję Menadżera, przynajmniej 2 instancje Klienta, przynajmniej 3 instancje Tablicy. 
Muszą to być osobne aplikacje (nie mogą korzystać z tej samej przestrzeni adresowej!!!). Aplikacje powinny być parametryzowane na interfejsie lub w linii komend (by dało się je uruchomić na różnych komputerach).


## Laboratorium 7
Polecenie
___________


Napisz program, który pozwoli zasymulować działanie narzędzia do obsługi finansów komitetu rodzicielskiego jakiejś szkolnej klasy. Główną funkcją tego narzędzia ma być obsługa przypadku zbierania funduszy na organizowane wydarzenia. Narzędzie może działać jako aplikacja desktopowa w trybie konsolowym, może też być zaimplementowane jako aplikacja z graficznym interfejsem (desktopowa lub internetowa, ale to wymagałoby więcej pracy).

Zadanie należy zrealizować wykorzystując relacyjną bazę danych. Można skorzystać z sqlite czy też h2 (zalecane, bo nie ma potrzeby instalowania żadnego dodatkowego serwisu) lub innej bazy danych (pod warunkiem, że podczas oddawania zadania będzie można połączyć się z tą bazą danych).

Podczas realizacji zadania można skorzystać z wzorca projektowego DAO (oraz możliwości, jakie daje JDBC) lub mapowania ORM (oraz możliwości, jakie daje JPA razem z frameworkiem Hibernate).

W przypadku użycia DAO (z JDBC) proszę pamiętać, by parametryzować zapytania SQL (nie wolno budować zapytań poprzez "sklejanie" kolejnych ciągów znaków). Proszę pamiętać o przewijalności zbioru wynikowego.

W przypadku zastosowania mapowania ORM proszę zadbać o automatyczne wygenerowanie schematu bazy danych oraz zastosowanie warstwy serwisów.

Zakładamy, że w bazie danych będą przechowywane następujące informacje:
* Wydarzenie - identyfikator, nazwa, miejsce, termin
* Osoba - identyfikator, imię, nazwisko
* Raty - identyfikator, identyfikator wydarzenia, numer raty, termin płatności, kwota?
* Wpłaty - identyfikator, termin wpłaty, kwota wpłaty, identyfikator osoby, identyfikator wydarzenia, numer raty
 
Program powinien:
* umożliwiać ręczne wprowadzanie danych (osób, wydarzeń, rat, wpłat) oraz ładowanie danych z plików csv.
* umożliwiać przeglądanie danych (w szczególności przeglądanie należnych i dokonanych wpłat)
* automatycznie sprawdzać terminowość i wysokość wpłat oraz wysyłać monity o kolejnych płatnościach (wystarczy, że będzie pisał do pliku z logami monitów, upływ czasu należy zasymulować).
* automatycznie eskalować monity w przypadku braku terminowej wpłaty (wystarczy, że będzie pisał do pliku z logami ekalowanych monitów, upływ czasu należy zasymulować)


## Laboratorium 8
Polecenie
___________


Zaimplementuj serwis komunikujący się protokołem SOAP, który pozwoli wykorzystać logikę biznesową (z warstwą persystencji) zaprojektowaną w ramach lab07. Serwis ten ma udostępniać API pozwalające na zarządzanie wydarzeniami, osobami, ratami, wpłatami. Klientem serwisu może być narzędzie SoapUI lub Postman (nie trzeba samemu implementować żadnego klienta).

Podczas implementacji można wykorzystać dowolną metodę przetwarzania komunikatów SOAP.
Z uwagi na dużą automatyzację zalecane jest wykorzystanie JAX-WS/Apache CXF. Można wybrać podejście bottomUp lub topDown.
Zalecane jest oparcie budowanego rozwiązania na czymś, co pozwoli obsłużyć opis interfejsu w języku WSDL.

Tworzony projekt może być projektem eclipsowym (DynamicWebProject) lub jeszcze lepiej - projektem mavenowym.

Istnieje niezły tutorial o tym, jak stworzyć projekt mavenowy, który korzysta z CXF (https://www.cse.unsw.edu.au/~cs9322/labs/lab01/index.html).
Jest też (może nawet lepszy) tutorial na stronie: https://www.tutorialspoint.com/apache_cxf/apache_cxf_with_wsdl_first.htm

Można też spróbować zrealizować laboratorium korzystając z Spring Boot (odpowiedni tutorial znajduje się pod adresem: https://www.baeldung.com/spring-boot-soap-web-service).
    

## Laboratorium 9
Polecenie
___________


Zaimplementuj aplikację pozwalającą na szyfrowanie/deszyfrowanie plików (taka aplikacja mogłaby pełnić rolę narzędzia służącego do szyfrowania/odszyfrowywania załączników do e-maili). 
Na interfejsie graficznym aplikacji użytkownik powinien mieć możliwość wskazania plików wejściowych oraz wyjściowych, jak również algorytmu szyfrowania/deszyfrowania oraz wykorzystywanych kluczy: prywatnego (do szyfrowania) i publicznego (do deszyfrowania).
Cała logika związana z szyfrowaniem/deszyfrowaniem powinna być dostarczona w osobnej bibliotece, spakowanej do podpisanego cyfrowo pliku jar.
Sama zaś aplikacja powinna również być wyeksportowana do wykonywalnego pliku jar podpisanego cyfrowo (i działać w niej ma menadżer bezpieczeństwa korzystający z dostarczonego pliku polityki).
Projekt opierać ma się na technogiach należących do Java Cryptography Architecture (JCA) i/lub Java Cryptography Extension (JCE).
Proszę zwrócić uwagę na ograniczenia związane z rozmiarem szyfrowanych danych narzuczane przez wybrane algorytmu (zależy nam, by zaszyfrować dało się pliki o dowolnym rozmiarze).
W trakcie realizacji laboratorium będzie trzeba skorzystać z repozytoriów kluczy i certyfikatów.  Ponadto proszę zapoznać się z zasadami korzystania z narzędzia jarsigner. 

Proszę w gitowym repozytorium kodu w gałęzi sources/releases stworzyć osobne podkatalogi: na bibliotekę (biblioteka) oraz na aplikację (aplikacja).


## Laboratorium 10
Polecenie
___________

Bazując na kodzie stworzonym w trakcie poprzednich laboratoriów przygotuj: a) wielowydaniowy jar, b) instalator aplikacji. Opis czym jest wielowydaniowy jar pojawi się na wykładzie. Towarzyszące mu materiały znaleźć można na stronie poświęconej kursowi. Instalator aplikacji może być wykonany dowolnym narzędziem. Ponadto dla ciekawych polecane jest sprawdzenie, jak działa narzędzie jpackage. 

Wyniki proszę dostarczyć w gitowym repozytorium lab10. W poprzednich latach należało dostarczyć również raport z realizacji zadania (zamieszczany w repozytorium). Podobnie w tym roku proszę taki raport przygotować (wymaganie to pojawiło się z opóźnieniem, proszę postarać się je wypełnić).
     
## Laboratorium 11
Polecenie
___________

Napisz program, w którym wykorzystane zostanie JNI. Zadanie do wykonania polegać ma na zadeklarowaniu klasy oferującej trzy metody natywne pozwalające posortować wskazane tablice obiektów typu Double.

## Laboratorium 12
Polecenie
___________


Celem laboratorium jest przetrenowanie możliwości dynamicznego rozszerzania funkcji programu Java przez ładowanie i wyładowywanie skryptów JavaScript (na podobieństwo ładowania klas własnym ładowaczem). Ponadto chodzi w nim o opanowanie technik przekazywania obiektów pomiędzy wirtualną maszyną Java a silnikiem JavaScript.

Rozwój silnika JavaScript, odbywający się w ramach rozwoju JDK, został w pewnym momencie zatrzymany przez Oracle. Stwierdzono bowiem, że zadanie to realizowane jest w projekcie GrallVM, a szkoda poświęcać zasoby na powielanie prac. Efekt podjętej wtedy decyzji widać w JDK 11, gdzie odpowiedni moduł dostarczający rzeczony silnik otagowano:
    @Deprecated(since="11", forRemoval=true)
    Module jdk.scripting.nashorn

Wraz z tym modułem z JDK z kolejnych wydań JDK zniknąć mają niektóre pomocnicze narzędzia, jak uruchamiane z linii komend interpreter jjs. Póki co można jednak wciąż korzystać z silnika JavaScript w JDK 11. Można też, alternatywnie, pobrać i przećwiczyć środowisko uruchomieniowe GrallVM (patrz https://www.graalvm.org/downloads/). 

GraalVM Community Edition wersja 22.1.0 (obecnie najnowsza) zbudowano na bazie OpenJDK 11.0.15 oraz OpenJDK 17.0.3. Odpowiednią paczkę oprogramowania znaleźć można na stronie: https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0

Zadanie do wykonania polega na napisaniu programu w języku Java pozwalającego na wizualizację działania automatów komórkowych, przy czym logika działania tych automatów powinna być zaimplementowana za pomocą ładowanych dynamicznie skryptów JavaScript zapisanych w plikach o znanej lokalizacji. Nazwy plików ze skryptami powinny odpowiadać nazwom automatów - by było wiadomo, co robi ładowany skrypt. Załadowane skrypty powinno dać się wyładować.

Opis przykładowych automatów komórkowych opublikowano na wiki: https://pl.wikipedia.org/wiki/Automat_kom%C3%B3rkowy.
Materiały pomocnicze można znaleźć pod adresami:
http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html
https://www.n-k.de/riding-the-nashorn/

Proszę w gitowym repozytorium kodu w gałęzi sources umieścić wszystkie wykorzystywane artefakty (skrypty JavaScript oraz źródła kodu Java). Proszę też zamieścić instrukcję użycia programu wraz z udokumentowanym wynikiem jego działania (plik Readme.md z dołączomymi zrzutami z ekranu).


## Laboratorium 13
Polecenie
___________


Znając zasady korzystania ze skryptów JS w programach napisanych w języku Java można pójść o krok dalej w zabawie z tymi technologiami. Zdobytą wiedzę można wykorzystać podczas budowy aplikacji z graficznym interfejsem użytkownika opierającym się na klasach JavaFX.

Zadanie polega na zaimplementowaniu aplikacji korzystającej z JavaFX w niestandardowy sposób. Ma to polegać na oskryptowaniu całej logiki w pliku fxml zamiast w kontrolerze napisanym w języku Java. W języku Java ma być zaimplementowane tylko ładowanie fxmla. Proszę spojrzeć na przykłady zamieszczone poniżej (Sample1.fxml, Sample1.java, application.css). 

Sama aplikacja ma pozwalać na generowanie zaproszeń na różnego rodzaju towarzyskie imprezy (parapetówki, andrzejki, sylwestra, rocznice ...) na podstawie spreparowanych wcześniej szablonów uzupełnianych niezbędnymi atrybutami (datą, miejscem, imieniem, ...). Wygenerowane zaproszenia mogą pojawiać się na interfejsie użytkownika jako tekst, który powinno dać się skopiować. Szablony powinny być zapisane w plikach konfiguracyjnych.

Do przemyślenia jest, w jaki sposób użytkownik ma przekazywać do aplikacji niezbędne atrybuty (liczba i typ atrybutów może zależeć od rodzaju szablonu).

W gitowym repozytorium w gałęzi sources należy umieścić wszystkie źródła plus plik Readme.md (z dołączonymi zrzutami i opisem, jak uruchomić aplikację), w gałęzi releases - wykonywalny plik jar (z przygotowaniem jara może być problem - trzeba sprawdzić, czy pliki fxml, css oraz szablonów będą się ładować z tego jara, ostatecznie można je dystrybuować osobno).

## Laboratorium 14
Polecenie
___________

Napisz program, który będzie grał rolę sterownik zapalającego światła w pomieszczeniach budynku (np. ramka z kilkoma kółkami reprezentującymi punkty oświetlenia). Zadaniem sterownika jest symulowanie "aktywności" podczas nieobecności gospodarzy (czyli odpalanie świateł w jakichś sekwencjach).
Zanurz w tym programie agenta obsługującego ziarenko JMX, które pozwala na zmianę sekwencji, uruchamianie lub zatrzymywanie symulacji, bądź też na indywidualne włączane/wyłączanie świateł. Ziarenko powinno generować notyfikacje przy włączeniu/wyłączeniu każdego punktu oświetlenia.
Niech więc ziareno ma:
- właściwość (pozwalającą na ustawianie/odczytywanie sekwencji),
- metodę (włączającą/wyłączającą dane źródło światła),
- metodę (uruchamiającą/zatrzymującą symulację)
- notyfikację (generowaną przy włączeniu/wyłączeniu punktów oświetlenia - i to zarówno przez użytkownika jak i przez ziarenko).
Uruchom program oraz podłącz się do niego z jmc lub jconsole by wykorzystać agenta.


## Laboratorium 15 
Polecenie
___________

Ostatnie zadanie w semestrze będzie tylko na zaliczenie. W jego trakcie należy przećwiczyć posługiwanie się "dużymi typami numerycznymi" z pakietu java.math.
Przypadkiem roboczym ma być całkowanie dowolnej ciągłej funkcji metodą parabol (Simpsona). Opis algorytmu oraz przykład jego implementacji w arkuszu kalkulacyjnym można znaleźć, odpowiednio, pod adresami:
http://www.kipo.agh.edu.pl/data/NumInt.pdf
http://vistula.pk.edu.pl/~sciezor/Kurs_TI_XP/Excel2_lekcja_3.pdf

Dokładniej: 
- proszę zaimplementować metodę całkowania Simpsona w dwóch wersjach: bez oraz z użyciem "dużych typów numerycznych" (BigDecimal, BigInteger itp.).
- proszę uruchomić całkowanie wybranej funkcji f(x) obiema metodami (testowaną funkcję należy sobie zaimplementować, może to być funkcja wielomianowa, wykładnicza itp.)
- proszę porównać wyniki całkowania i wyciągnąć wnioski
- opcjonalnie częściowe wyniki całkowania można w dowolny sposób wyświetlić na wykresie (np. obliczone całki częściowe można zrzucić do pliku, a następnie wyświetlić wykres scałkowanej funcji jakimś narzędziem)
