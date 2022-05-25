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