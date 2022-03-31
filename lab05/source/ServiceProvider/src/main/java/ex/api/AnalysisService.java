package ex.api;

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
   // public DataSet retrieve(boolean clear) throws ClusteringException;
}