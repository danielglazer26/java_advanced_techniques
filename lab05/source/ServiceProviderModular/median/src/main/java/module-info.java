import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.median.Mediana;

module median {
    requires api;
    exports glazer.daniel.pwr.app.median;
    provides AnalysisService
            with Mediana;
}