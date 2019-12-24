import java.io.*;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new FileReader("in1.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));
        BigInteger mul = BigInteger.ONE;

        int numberOfThreads = 8; //Runtime.getRuntime().availableProcessors();

        long time = System.currentTimeMillis();

        String[][] data = new String[800][100];
        readFile(reader, data);

        Thread[] threads = new Thread[numberOfThreads];
        Multiply[] tasks = new Multiply[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            tasks[i] = new Multiply(data, i, numberOfThreads);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for(Thread thread : threads) {
            thread.join();
        }

        for (Multiply task : tasks) {
            mul = mul.multiply(task.getMul());
        }

        writer.write(mul.toString() + "\n" + (double) (System.currentTimeMillis() - time) / 1000);
        reader.close();
        reader = new BufferedReader(new FileReader("in1.txt"));

        time = System.currentTimeMillis();

        data = new String[800][100];
        readFile(reader, data);
        Multiply multiply = new Multiply(data, 0, 1);
        Thread thread = new Thread(multiply);
        thread.start();
        thread.join();
        writer.write("\n" + multiply.getMul().toString() + "\n" + (double) (System.currentTimeMillis() - time) / 1000);

        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile(BufferedReader reader, String[][] data) throws IOException {
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null && index < 800) {
            data[index] = line.split("\t");
            index++;
        }
    }
}