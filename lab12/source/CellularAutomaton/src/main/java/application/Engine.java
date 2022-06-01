package application;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Engine {

    private Invocable invocable;

    public void loadScript(String script) throws FileNotFoundException, ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader(script));
        invocable = (Invocable) engine;
    }

    public ArrayList<ArrayList<Double>> createMap(int size, int x, int y) throws ScriptException, NoSuchMethodException {
        return (ArrayList<ArrayList<Double>>) invocable.invokeFunction("createMap", size, x, y);
    }

    public ArrayList<ArrayList<Double>> iteration() throws ScriptException, NoSuchMethodException {
        return (ArrayList<ArrayList<Double>>) invocable.invokeFunction("nextIteration");
    }
}
