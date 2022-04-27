Opis zawartości katalogów:

W niniejszym folderze zamieszczono kod źródłowy następujących projektów:

- projekt z definicją api, które należy wykorzystać podczas realizacji zadania na laboratorium
  serviceloader-java-example (jest to przykładowy projekt, który ilustrujący przykład użycia klasy DataSet z tego api);

- dwa przykłady rozszerzania aplikacji Java poprzez wykorzystanie Service Provider Interface, SPI:
  serviceloader-java-standard (grupa mavenowych projektów, w których wykorzystano SPI w sposób standardowy);
  serviceloader-java-modular (grupa mavenowych projektów, w których wykorzystano SPI posługując się system modułów java)
  ;

- kod źródłowy projektu SpringBoot, w którym pokazano przykład użycia DI oraz SPI serviceloader-java-spring (projekt
  zależny od biblioteki wygenerowanej w projekcie serviceloader-java-standard).

Wspomniane dwa przykłady aplikacji Java powstały na bazie opisu oraz źródeł kodu opublikowanych pod adresami:
https://blog.frankel.ch/migrating-serviceloader-java-9-module-system/
https://github.com/ajavageek/serviceloader-migration

W oryginalnym źródle kodu brakowało dyrektywy uses w module-info.java w projekcie client.

Informacie ogólne:

SPI pojawiło się w JDK 1.6. Bazuje ono na wzorcu, w skład którego wchodzą: Service (abstrakcyjna definicja interfejsu
serwisu), ServiceProvider (implementacja interfejsu serwisu), ServiceLoader (klasa odnajdująca i ładująca implementację
serwisu "lazily") oraz Client (klasa używająca załadowane implementacje serwisu).

Wzorzec ten można zaimplementować standardowo (wtedy plik jar musi zawierać w META-INF odpowiednie podkatalogi i pliki)
albo modularnie (wtedy zależności muszą pojawić się w module-info.java). Podobny efekt można osiagnąć poprzez DI (
wstrzykiwanie zależności) w springu.

* Standardowo, tj. podczas budowy jara dla bibliotek dostarczających implementacje serwisów, należy przygotować pliki i
  katalogi o odpowiedniej strukturze oraz zawierające odpowiednią konfigurację. Dokładniej - aby jakiś serwis został
  znaleziony przez ServiceLoader, wynikowy jar musi zawierać plik o nazwie pokrwającej się z kwalifikowalną nazwa
  interfejsu serwisu umieszczony w katalogu META-INF/services:

  META-INF/services/pl.edu.pwr.tkubik.serviceloader.api.LogService

Zawartością tego pliku musi być w pełni kwalifikowana nazwa klasy będącej implementacją serwisu:

    pl.edu.pwr.tkubik.serviceloader.impl02.LogStdOut

Aby to osiągnąć z poziomu mavena wystarczy w projekcie dodać nowy folder źródłowy: src/main/resources, a potem utworzyć
w nim: META-INF/services. Następnie w utworzonym folderze należy umieścić plik o nazwie i zawartości jak wyżej.

Uruchomienie z linii komend (po zbudowaniu projektu mavenem z celem package):

E:\Development\Java\jdk-11.0.1\bin\java.exe -classpath
.\client-std\target\client-std-2.0-SNAPSHOT.jar;.\api-std\target\api-std-2.0-SNAPSHOT.jar;.\impl01-std\target\impl01-std-2.0-SNAPSHOT.jar;.\impl02-std\target\impl02-std-2.0-SNAPSHOT.jar
pl.edu.pwr.tkubik.serviceloader.client.Client

Zadanie stworzenia folderu oraz pliku z zawartością jak wyżej można zlecić specjalnie do tego celu służącemu pluginowi.
Odpowiedni przykład opisano pod adresem:
https://www.baeldung.com/google-autoservice
Sama idea korzystania z tego pluginy polega na umieszczeniu w pliku pom.xml zależności:
<dependency>
<groupId>com.google.auto.service</groupId>
<artifactId>auto-service</artifactId>
<version>1.0-rc5</version>
<optional>true</optional>
</dependency>

oraz zamieszczeniu w kodzie źródłowym klasy dostarczającej implementację interfejsu razem z adnotacją @AutoService:

    @AutoService(LogService.class)
    public class LogStdOut implements LogService {

      @Override
      public void log(String message) {
          System.out.println(message+"1");
      }
    }

* Modularnie, tj. podczas budowy modułowych jarów dla bibliotek dostarczających implementacje serwisów, należy
  odpowiednio przygotować pliki module-info.java. Moduł klienta powinien wymagać modułu definiującego interfejs
  serwisu (requires) oraz powinien używać interfejs serwisu (uses). Moduł api ma eksportować pakiet z definicją
  interfejsu. Moduł implementujący interfejs serwisu ma wymagać modułu definiującego interfejs serwisu (requires),
  powinien eksportować pakiet z implementacją serwisu (exports), ma dostarczać implementację interfejsu (provides ...
  with ...).

Uruchomienie z linii komend (po zbudowaniu projektu mavenem z celem package):

E:\Development\Java\jdk-11.0.1\bin\java.exe -p
.\client\target\client-2.0-SNAPSHOT.jar;.\api\target\api-2.0-SNAPSHOT.jar;.\impl02\target\impl02-2.0-SNAPSHOT.jar;.\impl01\target\impl01-2.0-SNAPSHOT.jar
-m log.client/pl.edu.pwr.tkubik.serviceloader.client.Client

* Poprzez DI (w spring)
  Wstrzykiwanie zależności to coś, z czego korzysta nagminnie framework SpringBoot. Można dzięki niemu osiagną ten sam
  efekt, co przy zastosowaniu SPI. Wstrzykiwanie zależności jest dużo jednak cięższe (wnosi dużo zależności). Można o
  tym poczytać w artykule porównującym SPI ze SpringBoot DI:
  https://itnext.io/serviceloader-the-built-in-di-framework-youve-probably-never-heard-of-1fa68a911f9b

Klasę implementującą interfejs serwisu oznacza się adnotacją @Component, zaś jej wstrzyknięcie zapewnia adnotacja
@Autowired. Jeśli wstrzykiwane klasy mają być znalezione, SpringBoot musi być jeszcze poinformowany, gdzie ich szukać (
to można zrobić w konfiguracji).

Przykładowe kody ilustrujące koncepcję DI (zadziałają, jeśli znajdą się w tym samym pakiecie) zamieszczono poniżej.

```java
public interface SimpleService {

    String echo(String value);

}
```

------------------

```java

@Component
public class LogStdOut implements LogService {

    @Override
    public void log(String message) {
        System.out.println(message + "3");
    }
}
```

------------------

```java

@SpringBootApplication
public class SpringExample implements CommandLineRunner {

    @Autowired
    List<SimpleService> simpleServices;

    public static void main(String[] args) {
        SpringApplication.run(SpringExample.class, args);

    }

    public void run(final String... strings) throws Exception {
        for (SimpleService simpleService : simpleServices) {
            log.info("Echo: " + simpleService.echo(strings[0]));
        }
    }
}
```

------------------

* Poprzez zastosowanie frameworku PF4J Framework PF4J służy do budowy rozszerzeń. Wykorzystuje wzorzec projektowy, w
  którym rozszerza się interfejs bazowy ExtensionPoint, a potem dostarcza implementacji tego rozszerzenia, adnotując
  stworzoną klasę @Extension. Takie klasy mogą być rozprowadzane jako biblioteki jar (z odpowiednimi wpisami w
  MANIFEST.MF). Aby z nich skorzystać w klienckiej aplikacji należy użyć którejś z implementacji PluginManager.
  Implementacje te pozwalają dany plugin: załadować, wystartować, zatrzymać, wyładować. Framework ma rozszerzenia:
  pf4j-update (update mechanism for PF4J)
  pf4j-spring (PF4J - Spring Framework integration)
  pf4j-wicket (PF4J - Wicket integration)
  pf4j-web (PF4J in web applications)
  Więcej informacji można znaleźć na stronach:
  https://github.com/pf4j/pf4j
  https://github.com/pf4j/pf4j-spring
