import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Graf g = Graf.nacitajSubor("TEST_mini.hrn");
        Scanner s = new Scanner(System.in);
        g.sort();
        g.ocislovanie();

    }
}
