import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Running experiments, this may take a minute...");
        new Experiment().runAll("results/results.csv");
    }
}