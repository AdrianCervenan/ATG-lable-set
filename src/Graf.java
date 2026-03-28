import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


import java.util.Scanner;



public class Graf {


    private int m;  //pocet hran v grafe
    private int n;  //pocet vrcholov v grafe

    private int[][] H; //ulozisko hran

    private int[][] epsilon; //pole pre ulozenie vrcholov na spracovanie a ich cenu

    private ArrayList<Integer> tIPomoc; //list vsetkych najdenich vrcholov zo suboru bez prveho

    private ArrayList<Integer> epsilonPomoc; //list na ulozenie cien najdenich vrcholov

    private ArrayList<Integer> cesta; // zoznam vrcholov tvoriacich vyslednu cestu

    private int tU; //pociatocni vrchol

    private int[] tI;//pole cien vrcholov

    private int[] xI; //pole predchodcov

    private int tR; //aktualne spracovana cena

    private int R; //aktualne spracovany vrchol

    private int posledny; //hladany vrchol

    private int cenaCesty; //celkova suma dlzok hran najdenej cesty

    private int sprava; //pomoc aby sa chybova sprava vypisala len raz
    private int predchodca;
    private int pocetvrcholovVceste;
    private int prvy;

    private boolean[] pouzity;



    public Graf(int paPocetVrcholov, int paPocetHran) {
        this.n = paPocetVrcholov;
        this.m = paPocetHran;
        this.H = new int[1 + this.m][3];
        this.epsilon = new int[this.m + 1][2];
        this.tIPomoc = new ArrayList<Integer>();
        this.epsilonPomoc = new ArrayList<Integer>();
        this.tI = new int[this.n + 1];
        this.xI = new int[this.n + 1];
        this.cesta = new ArrayList<Integer>();
        this.cenaCesty = 0;
        this.pouzity = new boolean[this.n +1 ];
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

            //naplni ArrayList tiPomoc vsetkymi vrcholmy
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

        this.prvy = zatiatocniVrchol;
        this.posledny = konecnyVrchol;



        boolean test = false;
        boolean test2 = false;

        this.tU = this.prvy;

        //test pre existenciu prveho vrchola a ci je vystupnim vrcholom niakej hrany
        for (int j = 1; j <= this.m; j++) {
            if (this.tU == this.H[j][0]) {
                test = true;
            }
        }

        if (!test) {
            System.out.println("Zaciatocny Vrchol " + zatiatocniVrchol + " bud ne existuje alebo neni vystupnim vrcholom ziadnej hrany");
        } else {
            //test pre existenciu posledneho vrchola a ci je vstupnim vrcholom niakej hrany
            for (int i = 1; i <= this.m; i++) {
                if (konecnyVrchol == this.H[i][1]) {
                    test2 = true;
                    break;
                }
            }
        }
        if (!test2) {
            System.out.println("konecnt Vrchol " + konecnyVrchol + " bud ne existuje alebo neni vstupnym vrcholom ziadnej hrany");
        } else {

            krok1();


        }

    }


    public void krok1() {

        int odstranenie = 0;
        //roztriedy pole vsetkych najdenich vrcholov od najmensieho po najvetsi
        this.tIPomoc.sort(null);

        //inicializuje pole predchodcou a naplni ho nulami
        for (int j = 0; j <= this.tIPomoc.size() - 1; j++) {
            this.xI[this.tIPomoc.get(j)] = 0;

        }
        //odstrani prvy vrchol z ArrayListu tIPomoc
        for (int j = 0; j <= this.n - (1 + odstranenie); j++) {
            if (this.tIPomoc.get(j) == this.tU) {
                this.tIPomoc.remove(j);
                odstranenie += 1;
            }
        }
        //inicializuje pole cienVrcholov s hodnotou "nekonecno" pre kazdy vrchol
        for (int j = 0; j <= this.tIPomoc.size() - odstranenie; j++) {
            this.tI[this.tIPomoc.get(j)] = Integer.MAX_VALUE / 2;
        }
        if (this.tU == this.posledny){
            System.out.println(this.tU);
            System.out.println("cena cesty = 0" );
            System.out.println("pocet vrcholov v ceste = 1" );
            System.out.println("pocet hran v ceste = 0" );
        }else {

        //vlozy do mnoziny epsilon prvy vrchol a da mu cenu 0
        this.epsilon[this.tU][0] = this.tU;
        this.epsilon[this.tU][1] = 0;
        //vlozi do pomocneho pola cenu prveho vrchola
        this.epsilonPomoc.add(0);

        this.krok2();
    }
    }


    public void krok2() {

        while (!this.epsilonPomoc.isEmpty()) {


        for (int j = 0; j <= this.m; j++) {

            //pokial epsilon neni prazdni najde vstupni vrchol s najmešiou cenou
            if (this.epsilon[j][0] != 0 && this.epsilon[j][1] == this.epsilonPomoc.getFirst()) {

                //ulozime najdeni vrchol a jeho cenu z epsilonu R a tR
                this.R = this.epsilon[j][0];
                if (this.R == this.posledny) {
                    krok3();
                    return;
                }
                this.tR = this.epsilon[j][1];

                //odstranime ulozeni vrchol z epsilonu
                this.epsilonPomoc.removeFirst();
                this.epsilon[j][0] = 0;
                this.epsilon[j][1] = 0;
                break;
            }
        }
            if (this.pouzity[this.R]) continue;
            this.pouzity[this.R] = true;

                for (int i = 1; i <= this.m; i++) {
                    //porovnavame naš pouzivany vrchol zo všetkymi vystupnimy hranamy digrafe
                    if (this.R == this.H[i][0]) {

                        //pokial je cena aktualneho vrchola + aktualnej hrany menšia ulozena cena v poli cien všetkych vrcholov
                        if (this.tI[this.R] + this.H[i][2] < this.tI[this.H[i][1]]) {

                            //ulozi cenu aktualneho vrchola do mnoziny cien vsetkych vrcholov
                            this.tI[this.H[i][1]] = this.tI[this.R] + this.H[i][2];
                            //ulozi aktualny vrchol do mnoziny predchodcov
                            this.xI[this.H[i][1]] = this.R;

                            //prida vstupne vrcholy do mnoziny epsilon


                                this.epsilon[i][0] = this.H[i][1];
                                this.epsilon[i][1] = this.tI[this.H[i][1]];

                                this.epsilonPomoc.add(this.tI[this.H[i][1]]);

                        }
                        }
                        //roztriedy Array list epsilon pomoc od najmensieho po najvetči


                    }
            this.epsilonPomoc.sort(null);






        }
        krok3();
        }




    public void krok3() {
        boolean pravda = false;
        //testuje či mnozina epsilon je prazdna
        if (this.epsilonPomoc.isEmpty()) {
            this.cestaNeExistuje();

            //pokial posledny ma predchodcu
        } else if (this.xI[this.posledny] !=0 )  {
                    //do cesty pridame posledneho
                    this.cesta.add(posledny);
                    //do predchodcu ulozime predchodcu posledneho
                    this.predchodca = this.xI[this.posledny];
                    //pridame predchodcu
                    this.cesta.add(this.predchodca);
                    //do cesty sme pridali 2 vrcholi
                    this.pocetvrcholovVceste = 2;

                    //upravime cenu cesty cenou vrcholov ktore sme pridali do cesty
                    this.cenaCesty = this.tI[this.posledny];

                    System.out.println("(" + this.prvy  +", " + this.posledny +  ")");

                    //v cykle pridavame predchodcov do mnoziny cesta a cenu cesty
                    for(int j = 1; j <= this.n + 1; j++) {



                        if(this.xI[this.predchodca] != 0){

                            this.cesta.add(this.xI[this.predchodca]);
                            this.predchodca = this.xI[this.predchodca];
                            this.pocetvrcholovVceste += 1;

                        }

                    }
                    //v cykle vypiseme vsetky hodnoty
            System.out.println("zoradenie ako (v, u)");
                for (int i = 0; i <= this.cesta.size() - 1; i++) {

                    System.out.print(this.cesta.get(i) + " ");
                }
            System.out.println();
                System.out.println();

            System.out.println("zoradenie ako (u, v)");
            for (int i = 1; i <= this.cesta.size() ; i++) {

                System.out.print(this.cesta.get(this.cesta.size() - i) + " ");
            }
                System.out.println();
                System.out.println("cena cesty = " + this.cenaCesty);
                System.out.println();
                System.out.println("pocet hran v ceste " + (pocetvrcholovVceste - 1) );
                System.out.println("pocet vrcholov v ceste " + pocetvrcholovVceste);
            }


        }



    public void cestaNeExistuje() {

        if (this.sprava == 0) {
            System.out.println("K konecnemu vrcholu " + this.posledny + " nevedie cesta od vrcholu " + this.tU);
            this.sprava++;
        }
    }
}




