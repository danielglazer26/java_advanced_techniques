package glazer.daniel.pwr.app.median;

import glazer.daniel.pwr.app.api.AnalysisException;
import glazer.daniel.pwr.app.api.AnalysisService;
import glazer.daniel.pwr.app.api.ClusteringException;
import glazer.daniel.pwr.app.api.DataSet;

import java.util.ArrayList;
import java.util.Comparator;

public class Mediana implements AnalysisService {

    private Thread dataThread = new Thread();
    private int upperBound = 0;
    private int lowerBound;
    private ArrayList<ArrayList<Double>> listOfNumber;
    private DataSet ds = null;

    @Override
    public void setOptions(String[] options) throws AnalysisException {
        try {
            switch (options.length) {
                case 0:
                    lowerBound = 0;
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

        checkUpperBound(ds);


        if (dataThread.isAlive()) {
            throw new AnalysisException("Data processing in progress");
        }

        listOfNumber = new ArrayList<>();
        dataThread = new Thread(() -> {
            try {

                for (int j = 0; j < ds.getData()[0].length; j++) {
                    listOfNumber.add(new ArrayList<>());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = lowerBound; i < upperBound; i++) {
                    for (int j = 0; j < ds.getData()[i].length; j++) {
                        listOfNumber.get(j).add(Double.parseDouble(ds.getData()[i][j]));
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (NumberFormatException e) {
                try {
                    throw new AnalysisException("Incorrect number format");
                } catch (AnalysisException ex) {
                    ex.printStackTrace();
                }
            }
        });

        dataThread.start();
    }

    private void checkUpperBound(DataSet ds) throws AnalysisException {
        if (upperBound == 0)
            upperBound = ds.getData().length;
        else if (upperBound > ds.getData().length)
            throw new AnalysisException("The upper limit cannot be greater than column size");
    }

    @Override
    public DataSet retrieve(boolean clear) throws ClusteringException {

        if (dataThread.isAlive() || ds == null)
            return null;


        DataSet dataSet = new DataSet();

        String[] medians = makeCalculation();
        dataSet.setData(new String[][]{medians});

        dataSet.setHeader(ds.getHeader());

        if (clear)
            ds = null;
        return dataSet;

    }

    private String[] makeCalculation() throws ClusteringException {
        try {
            ArrayList<String> mediansString = new ArrayList<>();

            listOfNumber.forEach(doubles -> {
                doubles.sort(Comparator.naturalOrder());
                if (doubles.size() % 2 == 0) {
                    int i = doubles.size() / 2 - 1;
                    double numbers = doubles.get(i) + doubles.get(i + 1);
                    mediansString.add(String.valueOf(numbers / 2));
                } else
                    mediansString.add(doubles.get(((doubles.size() - 1) / 2)).toString());
            });
            return mediansString.toArray(new String[0]);
        } catch (Exception exception) {
            throw new ClusteringException("Incorrect operation");
        }
    }
}
