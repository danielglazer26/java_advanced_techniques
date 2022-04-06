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

--------------------------------

*****************************