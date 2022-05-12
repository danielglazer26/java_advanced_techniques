Napisz program, w którym wykorzystane zostanie JNI. Zadanie do wykonania polegać ma na zadeklarowaniu klasy oferującej
trzy metody natywne pozwalające posortować wskazane tablice obiektów typu Double. Schemat implementacji tej klasy
powinien odpowiadać poniższemu wzorcowi.

```java
class .....{
        .....
public Double[]a;
public Double[]b;
public Boolean order;


public native Double[]sort01(Double[]a,Boolean order);
// zakładamy, że po stronie kodu natywnego będzie sortowana przekazana tablica a
// (order=true oznacza rosnąco, order=false oznacza malejąco)
// metoda powinna zwrócić posortowaną tablicę
public native Double[]sort02(Double[]a);
// zakładamy, że drugi atrybut będzie pobrany z obiektu przekazanego do metody natywnej (czyli będzie brana wartość pole order)
public native void sort03();
// zakładamy, że po stronie natywnej utworzone zostanie okienko pozwalające zdefiniować zawartość tablicy do sortowania
// oraz warunek określający sposób sortowania order.
// wczytana tablica powinna zostać przekazana do obiektu Javy na pole a, zaś warunek sortowania powinien zostać przekazany
// do pola orded
// Wynik sortowania (tablica b w obiekcie Java) powinna wyliczać metoda Javy multi04
// (korzystająca z parametrów a i order, wstawiająca wynik do b).

public void sort04(){
        ..... // sortuje a według order, a wynik wpisuje do b
        }
        }

```