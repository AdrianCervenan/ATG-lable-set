import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Graf g = Graf.nacitajSubor("pr1.hrn");
        Scanner s = new Scanner(System.in);
        int prvy = Integer.parseInt(s.nextLine());
        int posledny = Integer.parseInt(s.nextLine());
        g.labelSet(prvy, posledny);

    }
}
