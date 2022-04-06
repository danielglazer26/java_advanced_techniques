import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.deviation.StandardDeviation;

module deviation {
    requires api;
    exports glazer.daniel.pwr.app.deviation;
    provides AnalysisService
            with StandardDeviation;
}