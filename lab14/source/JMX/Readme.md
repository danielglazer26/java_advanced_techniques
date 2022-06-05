Napisz program, który będzie grał rolę sterownik zapalającego światła w pomieszczeniach budynku (np. ramka z kilkoma kółkami reprezentującymi punkty oświetlenia). Zadaniem sterownika jest symulowanie "aktywności" podczas nieobecności gospodarzy (czyli odpalanie świateł w jakichś sekwencjach).
Zanurz w tym programie agenta obsługującego ziarenko JMX, które pozwala na zmianę sekwencji, uruchamianie lub zatrzymywanie symulacji, bądź też na indywidualne włączane/wyłączanie świateł. Ziarenko powinno generować notyfikacje przy włączeniu/wyłączeniu każdego punktu oświetlenia.
Niech więc ziareno ma:
- właściwość (pozwalającą na ustawianie/odczytywanie sekwencji),
- metodę (włączającą/wyłączającą dane źródło światła),
- metodę (uruchamiającą/zatrzymującą symulację)
- notyfikację (generowaną przy włączeniu/wyłączeniu punktów oświetlenia - i to zarówno przez użytkownika jak i przez ziarenko).
  Uruchom program oraz podłącz się do niego z jmc lub jconsole by wykorzystać agenta.