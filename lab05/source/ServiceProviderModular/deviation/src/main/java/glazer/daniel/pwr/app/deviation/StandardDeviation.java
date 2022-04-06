package glazer.daniel.pwr.app.deviation;

import glazer.daniel.pwr.app.api.AnalysisException;
import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.api.ClusteringException;
import glazer.daniel.pwr.app.api.DataSet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;


public class StandardDeviation implements AnalysisService {

    private Thread dataThread = new Thread();
    private ArrayList<ArrayList<Double>> listOfNumber;
    private int precision;
    private DataSet ds = null;

    @Override
    public void setOptions(String[] options) throws AnalysisException {
        try {
            switch (options.length) {
                case 0:
                    precision = 0;
                    break;
                case 1:
                    precision = Integer.parseInt(options[0]);
                    break;
            }
        } catch (NumberFormatException e) {
            throw new AnalysisException("Incorrect number format");
        }
    }

    @Override
    public String getName() {
        return "This class calculate standard deviation";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        this.ds = ds;

        if (dataThread.isAlive()) {
            throw new AnalysisException("Data processing in progress");
        }

        listOfNumber = new ArrayList<>();
        dataThread = new Thread(() -> {

            for (int j = 0; j < ds.getData()[0].length; j++) {
                listOfNumber.add(new ArrayList<>());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < ds.getData().length; i++) {
                for (int j = 0; j < ds.getData()[i].length; j++) {
                    listOfNumber.get(j).add(Double.parseDouble(ds.getData()[i][j]));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        dataThread.start();
    }

    @Override
    public DataSet retrieve(boolean clear) throws ClusteringException {
        if (dataThread.isAlive() || ds == null)
            return null;

        DataSet dataSet = new DataSet();

        String[] deviations = makeCalculation();
        dataSet.setData(new String[][]{deviations});

        dataSet.setHeader(ds.getHeader());

        if (clear)
            ds = null;
        return dataSet;

    }

    private String[] makeCalculation() throws ClusteringException {
        try {
            ArrayList<String> deviationString = new ArrayList<>();

            listOfNumber.forEach(doubles -> {
                OptionalDouble avg = doubles.stream().mapToDouble(Double::doubleValue).average();
                double w = doubles.stream().mapToDouble(aDouble -> (Math.pow(aDouble - avg.getAsDouble(), 2))).sum();

                BigDecimal bd = new BigDecimal(Math.sqrt(w / doubles.size()));
                if (precision > 0)
                    bd = bd.setScale(precision, RoundingMode.HALF_UP);

                deviationString.add(bd.toString());
            });
            return deviationString.toArray(new String[0]);

        } catch (NumberFormatException | NoSuchElementException e) {
            throw new ClusteringException("Incorrect number format");
        }
    }
}
