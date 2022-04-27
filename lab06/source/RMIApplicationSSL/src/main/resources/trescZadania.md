Lab06 (08.04.2022)

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

Poniżej znajdują się kody interfejsów oraz kod klasy, które należy użyć we własnej implementacji. Kody te zawierają opisy, które powinny pomóc w zrozumieniu ich zastosowania.

Uwaga - Proszę uważać na niebezpieczeństwo konfliktu portów.
- Proszę użyć dokładnie tego samego kodu co niżej (bez żadnych modyfikacji!!)

--------------------------------
```java
package bilboards;

import java.io.Serializable;
import java.time.Duration; // available since JDK 1.8

/**
* Klasa reprezentująca zamówienie wyświetlania ogłoszenia o zadanej treści
* przez zadany czas ze wskazaniem na namiastkę klienta, przez którą można
* przesłać informacje o numerze zamówienia w przypadku jego przyjęcia
*
* @author tkubik
*
*/
public class Order implements Serializable {
/**
*
*/
private static final long serialVersionUID = 1L;
public String advertText;
public Duration displayPeriod;
public IClient client;
}
```
```java
package bilboards;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Duration;

/**
* Interfejs, który powinna implementować aplikacja pełniąca rolę tablicy ogłoszeniowej (nazwijmy ją Tablica).
* Aplikacja ta powinna wyświetlać cyklicznie teksty ogłoszeń dostarczane metodą addAdvertisement.
* Za wyświetlanie tych ogłoszeń powinien odpowiadać osobny wątek.
* Wątek powinien mieć dostęp do bufora na ogłoszenia, którego pojemność i liczbę wolnych miejsc
* zwraca metoda getCapacity.
* Za dodawanie ogłoszenia do bufora odpowiada metoda addAdvertisment.
* Z chwilą pierwszego wyświetlenia ogłoszenia na tablicy powinien zacząć zliczać się czas jego wyświetlania.
* Usuwanie ogłoszenia może nastąpić z dwóch powodów: i) ogłoszenie może zostać usunięte na skutek
* wywołania metody removeAdvertisement (przez Menadżera); ii) ogłoszenie może zostać usunięte, gdy skończy się przyznany
* mu czas wyświetlania na tablicy (przez wątek odpowiedzialny w Tablicy za cykliczne wyświetlanie testów).
* Usuwanie ogłoszeń z bufora i ich dodawanie do bufora wiąże się z odpowiednim zarządzaniem
* podległą strukturą danych
* W "buforze" mogą się robić "dziury", bo ogłoszenia mogą mieć przyznany różny czas wyświetlania.
* Należy więc wybrać odpowiednią strukturę danych, która pozwoli zapomnieć o nieregularnym występowaniu tych "dziur").
* Metoda start powinna dać sygnał aplikacji, że należy rozpocząć cykliczne wyświetlanie ogłoszeń.
* Metoda stop zatrzymuje wyświetlanie ogłoszeń.
* Metody start i stop można odpalać naprzemiennie, przy czym nie powinno to resetować zliczonych czasów wyświetlania
* poszczególnych ogłoszeń.
* Uwaga: Tablica powininna być sparametryzowany numerem portu i hostem rejestru
* rmi, w którym zarejestrowano namiastkę Menadżera, oraz nazwa, pod którą
* zarejestrowano tę namiastkę.
* Jest to potrzebne, by Tablica mogła dowiązać się do Menadżera.
* Tablica robi to wywołując metodę bindBillboard (przekazując jej swoją namiastkę typu IBillboard).
* Otrzymuje przy tym identyfikator, który może użyć, by się mogła wypisać z Menadżera
* (co może stać się podczas zamykania tablicy).
*
* @author tkubik
*
*/

public interface IBillboard extends Remote {
/**
* Metoda dodająca tekst ogłoszenia do tablicy ogłoszeniowej (wywoływana przez
* Menadżera po przyjęciu zamówienia od Klienta)
*
* @param advertText   - tekst ogłoszenia, jakie ma pojawić się na tablicy
*                      ogłoszeniowej
* @param displayPeriod - czas wyświetlania ogłoszenia liczony od pierwszego
*                      jego ukazania się na tablicy ogłoszeniowej
* @param orderId       - numer ogłoszenia pod je zarejestrowano w menadżerze
*                      tablic ogłoszeniowych
* @return - zwraca true, jeśli udało się dodać ogłoszenie lub false w
*         przeciwnym wypadku (gdy tablica jest pełna)
* @throws RemoteException
*/
public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException;

	/**
	 * Metoda usuwająca ogłoszenie z tablicy (wywoływana przez Menadżera po
	 * wycofaniu zamówienia przez Klienta)
	 * 
	 * @param orderId - numer ogłoszenia pod jakim je zarejestrowano w menadżerze
	 *                tablic ogłoszeniowych
	 * @return - zwraca true, jeśli operacja się powiodła lub false w przeciwnym
	 *         wypadku (gdy nie ma ogłoszenia o podanym numerze)
	 * @throws RemoteException
	 */
	public boolean removeAdvertisement(int orderId) throws RemoteException;

	/**
	 * Metoda pobierająca informację o zajętości tablicy (wywoływana przez
	 * Menadżera)
	 * 
	 * @return - zwraca tablicę dwóch liczb całkowitych - pierwsza to pojemność
	 *         bufora tablicy, druga to liczba wolnych w nim miejsc
	 * @throws RemoteException
	 */
	public int[] getCapacity() throws RemoteException;

	/**
	 * Metoda pozwalająca ustawić czas prezentacji danego tekstu ogłoszenia na
	 * tablicy w jednym cyklu (wywoływana przez Menadżera). Proszę nie mylić tego z
	 * czasem, przez jaki ma być wyświetlany sam tekst ogłoszenia. Prezentacja
	 * danego hasła musi być powtórzona cyklicznie tyle razy, aby sumaryczny czas
	 * prezentacji był równy lub większy zamówionemu czasowi wyświetlania tekstu
	 * ogłoszenia.
	 * 
	 * @param displayInterval - definiuje czas, po którym następuje zmiana hasła
	 *                        wyświetlanego na tablicy. Odwrotność tego parametru
	 *                        można interpetować jako częstotliwość zmian pola
	 *                        reklamowego na Tablicy.
	 * @throws RemoteException
	 */
	public void setDisplayInterval(Duration displayInterval) throws RemoteException;

	/**
	 * Metoda startująca cykliczne wyświetlanie ogłoszeń (wywoływana przez
	 * Menadżera)
	 * 
	 * @return - zwraca true, jeśli zostanie poprawnie wykonana lub false w
	 *         przeciwnym wypadku
	 * @throws RemoteException
	 */
	public boolean start() throws RemoteException;

	/**
	 * Metoda stopująca cykliczne wyświetlanie ogłoszeń (wywoływana przez Menadżera)
	 * 
	 * @return - zwraca true, jeśli zostanie poprawnie wykonana lub false w
	 *         przeciwnym wypadku
	 * @throws RemoteException
	 */
	public boolean stop() throws RemoteException;
}
```
```java
package bilboards;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* Interfejs, który powinien zaimplementować klient (nazwijmy tę alikację
* Klient) komunikujący się z menadżerem tablic. Klient powinien mieć interfejs
* pozwalający: i) definiować zamówienia wyświetlania ogłoszenia (tekst
* ogłoszenia, czas wyświetlania) ii) składać zamówienia wyświetlania ogłoszenia
* Menadżerowi, iii) wycofywać złożone zamówienia.
*
* Przy okazji składania zamówienia wyświetlania ogłoszenia Klient przekazuje
* Menadżerowi namiastkę IClient. Menagdżer użyje tej namiastki, by zwrotnie
* przekazać klientowi numer zamówienia (jeśli oczywiście zamówienie zostanie
* przyjęte). Ma to działać podobnie jak ValueSetInterface z przykładu RMITest.
*
* Numery zamówień i treści ogłoszeń przyjętych przez Menadżera powinny być
* widoczne na interfejsie Klienta. Klient powinien sam zadbać o usuwanie
* wpisów, których okres wyświetlania zakończył się (brak synchronizacji w tym
* względzie z menadżerem)
*
* Uwaga: Klient powinien być sparametryzowany numerem portu i hostem rejestru
* rmi, w którym zarejestrowano namiastkę Menadżera, oraz nazwa, pod którą
* zarejestrowano tę namiastkę.
*
* @author tkubik
*
*/
public interface IClient extends Remote { // host, port, nazwa
/**
* Metoda służąca do przekazania numeru przyjętego zamówienia (wywoływana przez
* Menadżera na namiastce klienta przekazanej w zamówieniu)
*
* @param orderId
* @throws RemoteException
*/
public void setOrderId(int orderId) throws RemoteException;
}
```
```java
package bilboards;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* Interfejs, który powinna implementować aplikacja pełniąca rolę menadżera
* tablic (nazwijmy ją Menadżer). Menadżer powinien wyświetlać wszystkie
* dowiązane do niego tablice oraz ich bieżący stan. Tablice dowiązują się do i
* odwiązują od menadżera wywołując jego metody bindBillboard oraz
* unbindBillboard. Z menadżerem może połączyć się Klient przekazując mu
* zamówienie wyświetlania danego ogłoszenia przez zadany czas. Robi to
* wywołując metodę placeOrder. Menadżer, jeśli przyjmie zamówienie, zwraca
* Klientowi numer zamówienia wykorzystując przy tym przekazaną w zamówieniu
* namiastke. Klient rozpoznaje, czy przyjęto zamówienie, sprawdzając wynik
* zwracany z metody placeOrder.
* Zamówienia natychmiast po przyjęciu trafiają na dowiązane Tablice mogące w danej chwili przyjąć ogłoszenie do wyświetlania.
* Jeśli w danej chwili nie ma żadnej wolnej Tablicy zamówienie nie powinno być przyjęte do realizacji.
* Aby przekonać się o stanie tablic Menadżer wywołuje metody ich namiastek getCapacity.
* Wystarczy, że istnieje jedna wolna tablica by przyjąć zamówienie.
* Na ile tablic trafi dane zamówienie decyduje dostępność wolnych miejsc w chwili zamówienia.
*
* Uwaga: Menadżer powinien utworzyć lub połączyć się z rejestrem rmi o
* wskazanym numerze portu. Zakładamy, że rejestr rmi działa na tym samym
* komputerze, co Menadżer (może być częścią aplikacji Menadżera).
* Menadżer rejestruje w rejestrze rmi posiadaną
* namiastke IManager pod zadaną nazwą (nazwa ta nie może być na twardo
* zakodowanym ciągiem znaków). Nazwa namiastki menadżera, host i port na którym
* działa rejest rmi powinny być dostarczone Klientowi (jako parmetry) oraz Tablicom.
*
* @author tkubik
*
*/
public interface IManager extends Remote { // port, nazwa, GUI

	/**
	 * Metoda dowiązywania namiastki Tablicy do Menadżera (wywoływana przez Tablicę)
	 * 
	 * @param billboard - dowiązywana namiastka
	 * @return - zwraca numer przyznany namiastce w Menadżerze
	 * @throws RemoteException
	 */
	public int bindBillboard(IBillboard billboard) throws RemoteException;

	/**
	 * Metoda odwiązująca namiastkę Tablicy z Menadżera (wywoływana przez Tablicę)
	 * @param billboardId - numer odwiązywanej namiastki
	 * @return
	 * @throws RemoteException
	 */
	public boolean unbindBillboard(int billboardId) throws RemoteException;

	/**
	 * Metoda służąca do składania zamówienia wyświetlania ogłoszenia (wywoływana przez Klienta)
	 * @param order - szczegóły zamówienia (wraz z tekstem ogłoszenia, czasem jego wyświetlania i namiastką klienta)
	 * @return - zwraca true jeśli przyjęto zamówienie oraz false w przeciwnym wypadku
	 * @throws RemoteException
	 */
	public boolean placeOrder(Order order) throws RemoteException;

	/**
	 * Metoda służąca do wycofywania zamówienia (wywoływana przez Klienta)
	 * @param orderId - numer wycofywanego zamówienia 
	 * @return - zwraca true jeśli wycofano zamówienie oraz false w przeciwnym wypadku
	 * @throws RemoteException
	 */
	public boolean withdrawOrder(int orderId) throws RemoteException;
}
```