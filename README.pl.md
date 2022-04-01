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

--------------------------------
```java
package ex.api;

/**
* Interfejs serwisu pozwalającego przeprowadzić analizę statystyczną.
* Zakładamy, że serwis działa asynchronicznie.
* Na początek należy do serwisu załadować dane.
* Potem można z serwisu pobrać wyniki analizy.
* W przypadku niepowodzenia wykonania jakiejś metody wyrzucony zostanie wyjątek.
*
* @author tkubik
*
*/
public interface AnalysisService {
  // metoda ustawiająca opcje algorytmu (jeśli takowe są potrzebne)
  public void setOptions(String[] options) throws AnalysisException;

  // metoda zwracająca nazwę algorytmu
  public String getName();                                   

  // metoda przekazująca dane do analizy, wyrzucająca wyjątek jeśli aktualnie trwa przetwarzanie danych
  public void submit(DataSet ds) throws AnalysisException;

  // metoda pobierająca wynik analizy, zwracająca null jeśli trwa jeszcze przetwarzanie lub nie przekazano danych do analizy
  // wyrzucająca wyjątek - jeśli podczas przetwarzania doszło do jakichś błędów
  // clear = true - jeśli wyniki po pobraniu mają zniknąć z serwisu
  public DataSet retrieve(boolean clear) throws ClusteringException;   
}
```
--------------------------------
```java
package ex.api;

public class AnalysisException extends Exception {
private static final long serialVersionUID = 1L;
    AnalysisException(String msg){
        super(msg);
    }
}
```
--------------------------------
```java
package ex.api;
/**
* Klasa reprezentująca zbiór danych w postaci tabelarycznej.
* Przechowuje nagłówek (jednowymiarowa tablica z nazwami kolumn)
* oraz dane (dwuwymiarowa tablica, której wiersze reprezentują wektory danych).
* Zakładamy, że będą zawsze istnieć przynajmniej dwie kolumny o nazwach:
* "RecordId" - w kolumnie tej przechowywane są identyfikatory rekordów danych;
* "CategoryId" - w kolumnie tej przechowywane są identyfikatory kadegorii rekordów danych (wynik analizy skupień).
*
* @author tkubik
**/

public class DataSet {
  private String[] header = {};
  private String[][] data = {{}};

	private <T> T[][] deepCopy(T[][] matrix) {
	    return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray(i -> matrix.clone());
	}
	
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header.clone();
	}
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = deepCopy(data);
	}
} 
```
