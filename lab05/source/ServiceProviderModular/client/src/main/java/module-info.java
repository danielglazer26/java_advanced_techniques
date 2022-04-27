import glazer.daniel.pwr.app.api.AnalysisService;

module client {
    requires api;
    requires java.desktop;
    uses AnalysisService;
}