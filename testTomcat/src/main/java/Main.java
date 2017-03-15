import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
    private static final CyclicBarrier BARRIER = new CyclicBarrier(20, new Sender());

    public static class Request implements Runnable {
        private int reqNumber;

        Request(int reqNumber) {
            this.reqNumber = reqNumber;
        }

        public void run() {
            try {
//                System.out.printf("Req #%d goes\n", reqNumber);
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpGet httpget = new HttpGet("http://localhost:8080/");

                BARRIER.await();
                try {
                    long start = System.currentTimeMillis();
                    CloseableHttpResponse response = httpclient.execute(httpget);
                    System.out.println(this.reqNumber + " | Time: " + (System.currentTimeMillis() - start) + " ms" + " - " + response);
                } catch (IOException e) {
                    System.out.println(this.reqNumber + " REFUSED");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 120; i++) {
            new Thread(new Request(i)).start();
            Thread.sleep(400);
        }
    }

    public static class Sender implements Runnable {
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println("20 requests were sent");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
