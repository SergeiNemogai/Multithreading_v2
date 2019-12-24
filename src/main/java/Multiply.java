import java.math.BigInteger;

public class Multiply implements Runnable {
    private BigInteger mul = BigInteger.ONE;
    private final String[][] data;
    private final int low;
    private final int high;
    private final int countOfRows;

    public Multiply(String[][] data, int numberOfThread, int countOfThreads) {
        this.data = data;
        countOfRows = data.length;
        low = (countOfRows / countOfThreads + 1) * numberOfThread;
        high = low + countOfRows / countOfThreads;
    }

    @Override
    public void run() {
        for (int i = low; i <= high; i++) {
            if (i == countOfRows) break;
            for (String element : data[i]) {
                mul = mul.multiply(BigInteger.valueOf(Long.parseLong(element)));
            }
        }
    }

    public BigInteger getMul() {
        return mul;
    }
}