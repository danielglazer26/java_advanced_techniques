package application;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Engine {

    private ScriptEngine engine;
    private Invocable invocable;

    public void loadScript(String script) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader(script));
        invocable = (Invocable) engine;
        createMap(10, 5, 5);
    }

    public void createMap(int size, int x, int y) throws ScriptException, NoSuchMethodException {
        invocable.invokeFunction("createMap", size, x, y);
    }

    public ArrayList<ArrayList<Double>> iterationAnt() throws ScriptException, NoSuchMethodException {
        return (ArrayList<ArrayList<Double>>) invocable.invokeFunction("nextIteration");
    }

    public ArrayList<ArrayList<Boolean>> iterationTheGameOfLife() throws ScriptException, NoSuchMethodException {
        return (ArrayList<ArrayList<Boolean>>) invocable.invokeFunction("nextIteration");
    }
}
