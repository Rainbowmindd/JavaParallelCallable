import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelComputing {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //przedzial do calkowania
        double xp = 0;//poczatek
        double xk = Math.PI;// koniec

        //parametry wpisywane w konsoli
        System.out.println("Podaj dx (szerokosc trapezu): ");
        double dx = sc.nextDouble();    // dokladnosc (szerokosc trapezu)
        System.out.println("Podaj liczbe watkow (threads): ");
        int numberOfThreads = sc.nextInt();
        System.out.println("Podaj liczbe zadan (threads): ");
        int numberOfTasks = sc.nextInt();

        double widthOfInterval = (xk -xp) / numberOfTasks;  // przedzial dla 1 tasku

        //tworzenie puli watkow
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);// odpowiada za tworzenie i obsluge puli watkow
        List<Future<Double>> futures = new ArrayList<>();// Future- umożliwia efektywne zbieranie wyników z wielu wątków działających równolegle

        //tworzenie i dodawanie zadan do puli
        for (int i = 0; i < numberOfTasks; i++) {  //[xp1 xk1] podprzedzialy
            double xp1 = xp + i * widthOfInterval;  //poczatek + przesuniecie(iteracja*szerokosc)
            double xk1 = xp1 + widthOfInterval;//poczatek + szerokosc
            Calka_callable task = new Calka_callable(xp1, xk1, dx);
            futures.add(executor.submit(task));//dodawanie do puli rozwiazan
        }

        //odbieranie wynikow zadan
        double totalResult = 0;
        try{
            for (Future<Double> future : futures) {
                totalResult += future.get();      //oczekiwanie na wynik
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error, exception" + e.getMessage());
        }

        // zamkniecie puli watkow
        executor.shutdown();


        System.out.printf("Wynik całkowania funkcji sin(x) w przedziale [%.2f, %.2f] wynosi: %.6f%n", xp, xk, totalResult);

        sc.close();
    }
}
