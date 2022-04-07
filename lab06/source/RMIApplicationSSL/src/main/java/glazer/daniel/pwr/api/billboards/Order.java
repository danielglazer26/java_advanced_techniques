package glazer.daniel.pwr.api.billboards;

import java.io.Serializable;
import java.time.Duration;

/**
 * Klasa reprezentująca zamówienie wyświetlania ogłoszenia o zadanej treści
 * przez zadany czas ze wskazaniem na namiastkę klienta, przez którą można
 * przesłać informacje o numerze zamówienia w przypadku jego przyjęcia
 *
 * @author tkubik
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
