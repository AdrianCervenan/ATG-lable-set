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
    public Graf(int paPocetVrcholov, int paPocetHran) {
        this.n = paPocetVrcholov;
        this.m = paPocetHran;
        this.H = new int[1 + this.m][3];
        this.K = new int[1 + this.n];
        this.Kpomoc = new ArrayList<>();
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

        for(int i = 1; 1 <= m; i++){
            int u = this.H[i][0];
            int v = this.H[i][1];


            if(this.K[u] != this.K[v] ){


                pocetPouzitichHran +=1;
            }

            if(pocetPouzitichHran == n - 1){
                break;
            }

        }


}
}








