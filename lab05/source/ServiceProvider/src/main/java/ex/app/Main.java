package ex.app;

import ex.app.servicecs.LogService;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<LogService> loader = ServiceLoader.load(LogService.class);
        long count = StreamSupport.stream(loader.spliterator(), false).count();
        System.out.println(count);
    }
}
