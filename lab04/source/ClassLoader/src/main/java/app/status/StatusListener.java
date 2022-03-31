package app.status;

public interface StatusListener {

    /**
     * Listener method
     *
     * @param s - status przetwarzania zadania
     */
    void statusChanged(Status s);
}
