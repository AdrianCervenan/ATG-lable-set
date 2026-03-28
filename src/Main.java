import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        //vloz String s nazvom suboru
        Graf g  = Graf.nacitajSubor("");

        Scanner s = new Scanner(System.in)
                ;
        System.out.println("Do konzoly napis prvy vrchol");

        int prvy = Integer.parseInt(s.nextLine());

        System.out.println("Do konzoly napis posledny vrchol");

        int posledny = Integer.parseInt(s.nextLine());

        g.labelSet(prvy, posledny);

    }
}
