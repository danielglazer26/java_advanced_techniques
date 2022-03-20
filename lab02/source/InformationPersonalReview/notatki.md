#1. Testowanie parametrów ograniczających testowaną pamięć
##1.1 Parametry -Xms512m i -Xmx1024m
Do momentu aż pamięć aplikacji nie przekroczy minimum, zawartości nie są usuwane. W momencie, w którym zostaje 
przekroczona wartość minimalnej pamięci, usuwane są wszystkie słabe referencje. Jeśli zostaną ponownie wczytane 
katalogi, 
które wcześniej już były dodane, to GC nie będzie ich usuwał, mimo że przekroczona została pamięć minimalna. W 
przypadku, w którym ma zostać przekroczona górne ograniczenie, GC będzie usuwał wszystkie słabe referencje.
##1.2 Parametr -Xmx1024m
GC usuwa, w różnych odstępach jedną lub kilka słabych referencji po wczytaniu nowego katalogu.
##1.3 Parametr -Xms300m
Podobnie jak w pierwszym przypadku
#2. Testowanie regulowania zachowania algorytmów odśmiecania
##2.1 Parametr -XX:+ShrinkHeapInSteps
Parametr stopniowo zwiększa stertę do podanego rozmiaru.
##2.2 Parametr -XX:-ShrinkHeapInSteps
Parametr stopniowo redukuje stertę do podanego rozmiaru.
##2.3 Parametr -XX:+UseSerialGC
Parametr odpowiedzialny za używanie seryjnego GC. Zmniejszył się rozmiar sterty
##2.4 Parametr -XX:+UseParNewGC
Parametr umożliwia wykorzystanie równoległych wątków dla GC
##2.5 Parametr -XX:+UseParallelGC
Parametr odpowiedzialny za równoległy GC. Posiada jeszcze mniejszy rozmiar sterty niż SerialGC 
##2.6 Parametr -XX:+UseG1GC
Parametr odpowiedzialny za używanie Pierwszego GC