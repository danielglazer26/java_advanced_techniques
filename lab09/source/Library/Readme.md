Zaimplementuj aplikację pozwalającą na szyfrowanie/deszyfrowanie plików (taka aplikacja mogłaby pełnić funkcję narzędzia służącego do szyfrowania/odszyfrowywania załączników do e-maili).
Na interfejsie graficznym aplikacji użytkownik powinien mieć możliwość wskazania plików wejściowych oraz wyjściowych, jak również algorytmu szyfrowania/deszyfrowania oraz wykorzystywanych kluczy: prywatnego (do szyfrowania) i publicznego (do deszyfrowania).
Cała logika związana z szyfrowaniem/deszyfrowaniem powinna być dostarczona w osobnej bibliotece, spakowanej do podpisanego cyfrowo pliku jar.
Sama zaś aplikacja powinna również być wyeksportowana do wykonywalnego pliku jar podpisanego cyfrowo (i działać w niej ma menadżer bezpieczeństwa korzystający z dostarczonego pliku polityki).
Projekt opierać ma się na technologiach należących do Java Cryptography Architecture (JCA) i/lub Java Cryptography 
Extension (JCE).
Proszę zwrócić uwagę na ograniczenia związane z rozmiarem szyfrowanych danych narzucane przez wybrane algorytmu 
(zależy nam, by dało się zaszyfrować pliki o dowolnym rozmiarze).
W trakcie realizacji laboratorium będzie trzeba skorzystać z repozytoriów kluczy i certyfikatów.  Ponadto proszę zapoznać się z zasadami korzystania z narzędzia jarsigner.

Proszę w gitowym repozytorium kodu w gałęzi sources/releases stworzyć osobne podkatalogi: na bibliotekę (biblioteka) oraz na aplikację (aplikacja).