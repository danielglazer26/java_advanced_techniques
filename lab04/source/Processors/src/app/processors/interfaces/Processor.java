package app.processors.interfaces;

import app.status.StatusListener;

public interface Processor {
    /**
     * Metoda służąca do zlecania zadań
     *
     * @param task - tekst reprezentujący zadanie
     * @param sl   - słuchacz, który będzie informowany o statusie przetwarzania
     * @return - wartość logiczną mówiącą o tym, czy zadanie przyjęto do
     * przetwarzania (nie można zlecić kolejnych zadań dopóki bieżące
     * zadanie się nie zakończyło i nie został pobrany wynik przetwarzania
     */
    boolean submitTask(String task, StatusListener sl);

    /**
     * Metoda służąca do pobierania informacji o algorytmie przetwarzania
     *
     * @return - informacja o algorytmie przetwarzania (czytelna dla człowieka)
     */
    String getInfo();

    /**
     * Metoda służąca do pobierania wyniku przetwarzania
     *
     * @return - tekst reprezentujący wynik przetwarzania
     */
    String getResult();
}