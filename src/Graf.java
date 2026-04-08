import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


import java.util.Scanner;



public class Graf {


    private int m;
    private int n;
    private int[][] H;
    private ArrayList<Integer> Kpomoc;
    private int[] K;
    private int[][] HranaKostry;
    private int cenaKostry;
    public Graf(int paPocetVrcholov, int paPocetHran) {
        this.n = paPocetVrcholov;
        this.m = paPocetHran;
        this.H = new int[1 + this.m][3];
        this.K = new int[1 + this.n];
        this.Kpomoc = new ArrayList<>();
        this.HranaKostry = new int[1 + this.m][2];
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
            if (g.Kpomoc.isEmpty() || !g.Kpomoc.contains(u)) {
                g.Kpomoc.add(u);

            }

            if (!g.Kpomoc.contains(v)) {
                g.Kpomoc.add(v);

            }


            g.H[j][0] = u;
            g.H[j][1] = v;
            g.H[j][2] = c;
        }
        return g;

    }

    public void sort(){
        java.util.Arrays.sort(H, 1, m + 1, new java.util.Comparator<int[]>() {
    @Override
    public int compare(int[] a, int[] b) {
        if (b[2] < a[2]) return -1;
        if (b[2] > a[2]) return 1;
        return 0;
    }
});
        this.Kpomoc.sort(null);
}

public void ocislovanie(){
        for(int i = 1; i <= n; i++){
            this.K[i] = this.Kpomoc.get(i - 1);
        }

}
public void krok3(){
        int pocetPouzitichHran = 0;

        for(int i = 1; i <= m; i++){
            int u = this.H[i][0];
            int v = this.H[i][1];
            int c = this.H[i][2];

            if(this.K[u] != this.K[v] ){
                this.HranaKostry[i][0] = u;
                this.HranaKostry[i][1] = v;

                this.cenaKostry += c;
                if(this.K[u] > this.K[v]){
                    for(int j = 1; j <= n; j++){
                        if(this.K[j] == this.K[u]){
                            this.K[j] = this.K[v];

                        }
                    }
                } else {
                    for(int j = 1; j <= n; j++){
                        if(this.K[j] == this.K[v]){
                            this.K[j] = this.K[u];

                        }
                    }
                }

                pocetPouzitichHran +=1;
            }

            if(pocetPouzitichHran == n - 1){
                break;
            }

        }


}
public void vypis(){
        System.out.println("cena kostry");
        System.out.println(this.cenaKostry);
        System.out.println("vypis Hran");
        for(int i = 1; i <= this.HranaKostry.length - 1; i++ ) {
            System.out.print("{ " + this.HranaKostry[i][0] + " "+ this.HranaKostry[i][1] + " }  ");
        }
        System.out.println(" ");
        for(int i = 1; i <= this.K.length - 1; i++ ) {
            System.out.print(i + " ");

        }
        System.out.println(" ");
        for(int i = 1; i <= this.K.length - 1; i++ ) {


            System.out.print(this.K[i] + " ");

        }


}
}








