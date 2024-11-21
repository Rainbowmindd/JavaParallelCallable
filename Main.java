import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // parametry calkowania
        double xp = 0;          //poczatek przedzialu
        double xk = Math.PI;    // koniec przedzialu

        System.out.println("Podaj szerokosc trapezu: ");
        double dx = sc.nextDouble();    // dokladnosc szerokosc trapezu

        if (dx <= 0){ //dx szerokosc
            System.out.println("Nieprawidlowe dane wejsciowe");
            return;
        }

        // worzenie obiektu calka_callable
        Calka_callable calka = new Calka_callable(xp, xk, dx);

        //obliczanie calki
        double wynik = calka.compute_integral();


        System.out.printf("Wynik całkowania funkcji sin(x) w przedziale [%.2f; %.2f] wynosi: %f", xp, xk, wynik);

        sc.close();
    }
}
