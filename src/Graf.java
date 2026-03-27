import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


import java.util.Scanner;



public class Graf {


    private int m;
    private int n;
    private int[][] H;
    private int[][] epsilon;

    private ArrayList<Integer> tRPomoc;
    private ArrayList<Integer> tIPomoc;
    private ArrayList<Integer> cesta;
    private int tU;
    private int[] tI;
    private int[] xI;
    private int tR;
    private int R;
    private int posledny;
    private int sprava;

    public Graf(int paPocetVrcholov, int paPocetHran) {
        this.n = paPocetVrcholov;
        this.m = paPocetHran;
        this.H = new int[1 + this.m][3];
        this.epsilon = new int[this.m + 1][2];
        this.tIPomoc = new ArrayList<Integer>();
        this.tRPomoc = new ArrayList<Integer>();
        this.tI = new int[this.n + 1];
        this.xI = new int[this.n + 1];
        this.cesta = new ArrayList<Integer>();


    }

    public static Graf nacitajSubor(String nazovSuboru)
            throws FileNotFoundException {


        Scanner s = new Scanner(new FileInputStream(nazovSuboru));

        int pocetVrcholov = 1;
        int pocetHran = 0;
        while (s.hasNext()) {
            int u = s.nextInt();
            int v = s.nextInt();
            int c = s.nextInt();

            pocetHran++;

            if (pocetVrcholov < u) {
                pocetVrcholov = u;
            }
            if (pocetVrcholov < v) {
                pocetVrcholov = v;
            }


        }
        s.close();

        Graf g = new Graf(pocetVrcholov, pocetHran);

        s = new Scanner(new FileInputStream(nazovSuboru));

        for (int j = 1; j <= pocetHran; j++) {

            int u = s.nextInt();
            int v = s.nextInt();
            int c = s.nextInt();

            if (g.tIPomoc.isEmpty() || !g.tIPomoc.contains(u)) {
                g.tIPomoc.add(u);

            }

            if (!g.tIPomoc.contains(v)) {
                g.tIPomoc.add(v);

            }

            g.H[j][0] = u;
            g.H[j][1] = v;
            g.H[j][2] = c;
        }

        return g;
    }

    public void labelSet(int zatiatocniVrchol, int konecnyVrchol) {
        this.tU = zatiatocniVrchol;
        this.posledny = konecnyVrchol;

        boolean test = false;
        boolean test2 = false;



        for (int j = 1; j <= this.m; j++) {
            if (this.tU == this.H[j][0]) {
                test = true;
            }
        }
        if (!test) {
            System.out.println("Zaciatocny Vrchol " + zatiatocniVrchol + " bud ne existuje alebo neni vystupnim vrcholom ziadnej hrany");
        } else if (test) {
            for (int i = 1; i <= this.m; i++) {
                if (konecnyVrchol == this.H[i][1]) {
                    test2 = true;
                }
            }
        }
        if (!test2) {
            System.out.println("konecnt Vrchol " + konecnyVrchol + " bud ne existuje alebo neni vstupnym vrcholom ziadnej hrany");
        } else {


            int odstranenie = 0;
            this.tIPomoc.sort(null);


            for (int j = 0; j <= this.tIPomoc.size() - 1; j++) {
                this.xI[this.tIPomoc.get(j)] = 0;


            }


            for (int j = 0; j <= this.n - (1 + odstranenie); j++) {
                if (this.tIPomoc.get(j) == this.tU) {
                    this.tIPomoc.remove(j);
                    odstranenie += 1;

                }

            }

            for (int j = 0; j <= this.tIPomoc.size() - odstranenie; j++) {
                this.tI[this.tIPomoc.get(j)] = Integer.MAX_VALUE / 2;


            }


            this.epsilon[this.tU][0] = this.tU;
            this.epsilon[this.tU][1] = 0;
            this.tRPomoc.add(0);


            for (int j = 0; j <= this.m; j++) {
                if (this.epsilon[j][0] != konecnyVrchol) {

                    if (this.epsilon[j][0] != 0 && this.epsilon[j][1] == this.tRPomoc.getFirst()) {

                        this.R = this.epsilon[j][0];
                        this.tR = this.epsilon[j][1];
                        this.tRPomoc.removeFirst();




                        this.epsilon[j][0] = 0;
                        this.epsilon[j][1] = 0;

                        for (int i = 1; i <= this.m; i++) {
                            if (this.R == this.H[i][0]) {


                                if (this.tR + this.H[i][2] < this.tI[this.H[i][1]]) {

                                    this.tI[this.H[i][1]] = this.epsilon[this.R][1] + this.H[i][2];
                                    this.xI[this.H[i][1]] = this.R;
                                    if(!this.cesta.contains(this.R)) {
                                        this.cesta.add(this.R);
                                    }
                                    this.epsilon[i][0] = this.H[i][1];
                                    this.epsilon[i][1] = this.H[i][2];
                                    this.tRPomoc.add(this.H[i][2]);


                                }
                                this.tRPomoc.sort(null);
                                j = 0;

                            }





                        }


                    }
                } else {
                    this.cesta.addLast(this.posledny);
                    for (int i = 1; i <= this.cesta.size() ; i++) {

                        System.out.print(this.cesta.get(this.cesta.size() - i) + " ");

                        j = this.m;
                    }
                    System.out.println();
                    System.out.println("pocet vrcholov " + this.cesta.size());
                    }

                }
             }  if (this.tRPomoc.isEmpty() )  {
                this.cestaNeExistuje();
            }
        }

    public void cestaNeExistuje() {
        
        if (this.sprava == 0) {
            System.out.println("K konecnemu vrcholu " + this.posledny + " nevedie cesta od vrcholu " + this.tU);
            this.sprava++;
        }
    }
}





