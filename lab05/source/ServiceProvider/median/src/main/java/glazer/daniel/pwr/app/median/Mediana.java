package glazer.daniel.pwr.app.median;

import glazer.daniel.pwr.app.api.AnalysisException;
import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.api.ClusteringException;
import glazer.daniel.pwr.app.api.DataSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Mediana implements AnalysisService {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private int upperBound;
    private int lowerBound;
    private DataSet ds = null;

    @Override
    public void setOptions(String[] options) throws AnalysisException {
        try {
            switch (options.length) {
                case 0:
                    break;
                case 1:
                    upperBound = Integer.parseInt(options[0]);
                    break;
                case 2:
                    lowerBound = Integer.parseInt(options[0]);
                    upperBound = Integer.parseInt(options[1]);
                    if (lowerBound > upperBound)
                        throw new AnalysisException("The lower limit cannot be greater than the upper limit");
                    break;
            }
        } catch (NumberFormatException e) {
            throw new AnalysisException("Incorrect number format");
        }
    }

    @Override
    public String getName() {
        return "This class calculate Mediana";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        this.ds = ds;
        lowerBound = 0;
        upperBound = ds.getData().length;

        /*if (!executorService.isShutdown())

        else
            throw new AnalysisException("Data is still processed");*/
    }

    @Override
    public DataSet retrieve(boolean clear) throws ClusteringException {
        //Thread.isRunning or data is null then null
        String[] medians = makeCalculation();
        if (clear) {
            DataSet dataSet = new DataSet();
            dataSet.setData(new String[][]{medians});
            return dataSet;
        } else {
            String[] s = new String[ds.getHeader().length + 1];
            s[0] = ds.getHeader()[0];
            s[1] = ds.getHeader()[1];
            s[2] = "Mediana";
            ds.setHeader(s);
            return ds;
        }
    }

    private String[] makeCalculation() throws ClusteringException {
        ArrayList<ArrayList<Double>> listOfNumber = new ArrayList<>();

        for (int j = 0; j < ds.getData()[0].length; j++) {
            listOfNumber.add(new ArrayList<>());
        }

        try {
            for (int i = 0; i < ds.getData().length; i++) {
                for (int j = 0; j < ds.getData()[i].length; j++) {
                    listOfNumber.get(j).add(Double.parseDouble(ds.getData()[i][j]));
                }
            }
            ArrayList<String> mediansString = new ArrayList<>();
            listOfNumber.forEach(doubles -> {
                doubles.sort(Comparator.naturalOrder());
                if (doubles.size() % 2 == 0) {
                    int i = doubles.size() / 2;
                    double numbers = doubles.get(i) + doubles.get(i - 1);
                    mediansString.add(String.valueOf(numbers / 2));
                } else
                    mediansString.add(doubles.get((doubles.size() / 2 + 1)).toString());
            });
            return mediansString.toArray(new String[0]);

        } catch (NumberFormatException e) {
            throw new ClusteringException("Incorrect number format");
        }
    }
}
