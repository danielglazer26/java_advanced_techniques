package glazer.daniel.pwr.app.trend;

import com.google.auto.service.AutoService;
import glazer.daniel.pwr.app.api.AnalysisException;
import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.api.ClusteringException;
import glazer.daniel.pwr.app.api.DataSet;


@AutoService(AnalysisService.class)
public class TrendLine implements AnalysisService {
    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Make trend line";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {

    }

    @Override
    public DataSet retrieve(boolean clear) throws ClusteringException {
        return null;
    }
}
