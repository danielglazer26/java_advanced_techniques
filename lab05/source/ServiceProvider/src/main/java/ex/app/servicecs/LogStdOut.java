package ex.app.servicecs;

import com.google.auto.service.AutoService;

@AutoService(LogService.class)
public class LogStdOut implements LogService {

    @Override
    public void log(String message) {
        System.out.println(message+"1");
    }
}