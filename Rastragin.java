import java.util.ArrayList;
import java.util.Random;

public class Rastragin {

    public int[] wyznaczM(double[] a, double[] b, int[] d) {
        int[] m = new int[b.length];
        for(int i = 0; i < b.length; i++) {
            double warunek = (b[i] - a[i]) * Math.pow(10, d[i]) + 1;
            while (Math.pow(2, m[i] - 1) <= warunek) {
                m[i]++;
                if (Math.pow(2, m[i]) > warunek) {
                    break;
                }
            }
        }
        return m;
    }

    public static int[][] stworzMacierz(int sumaM, int il) {
        int[][] macierz = new int[il][sumaM];
        Random rand = new Random();
        for(int i = 0; i < il; i++){
            for(int j = 0; j < sumaM; j++){
                macierz[i][j] = rand.nextInt(2);
            }
        }
        return macierz;
    }

    public static int[][] zamienNaDziesietne(int[] m, int il, int[][] macierz){
        int[][] dziesietne = new int[il][m.length];
        int pozycja = -1;
        for(int k = 0; k < m.length; k++) {
            pozycja += m[k];
            for (int i = 0; i < il; i++) {
                int potega = 0;
                for (int j = pozycja; j >= pozycja - m[k] + 1; j--) {
                    dziesietne[i][k] += macierz[i][j] * Math.pow(2, potega);
                    potega++;
                }
            }
        }
        return dziesietne;
    }

    public static int[][] zamienNaDziesietne(int[] m, int il, ArrayList<Osobnik> osobniki){
        int[][] dziesietne = new int[osobniki.size()][m.length];
        int pozycja = -1;
        for(int k = 0; k < m.length; k++) {
            pozycja += m[k];
            for (int i = 0; i < osobniki.size(); i++) {
                int potega = 0;
                for (int j = pozycja; j >= pozycja - m[k] + 1; j--) {
                    dziesietne[i][k] += osobniki.get(i).binarne[j] * Math.pow(2, potega);
                    potega++;
                }
            }
        }
        return dziesietne;
    }

    public static double[][] przesunDoPrzedzialu(double[] a, double[] b, int[] m, int[][] dziesietne){
        double[][] x = new double[dziesietne.length][dziesietne[0].length];
        for (int i = 0; i < x[0].length; i++){
            for(int j = 0; j < x.length; j++) {
                x[j][i] = a[i] + (b[i] - a[i]) * dziesietne[j][i] / (Math.pow(2, m[i]) - 1);
            }
        }
        return x;
    }

    public static double[] ocenOsobnikow(double[][] x){
        double[] fx = new double[x.length];
        for(int i = 0; i < fx.length; i++){
            fx[i] = 10 * x[0].length;
            for(int j = 0; j < x[0].length; j++) {
                fx[i] += Math.pow(x[i][j], 2) - 10 * Math.cos(20 * Math.PI * x[i][j]);
            }
        }
        return fx;
    }

    public static ArrayList<Osobnik> ocenOsobnikow(double[] a, double[] b, int[] m, int il, ArrayList<Osobnik> osobniki){
        int[][] dziesietne = zamienNaDziesietne(m, il, osobniki);
        double[][] x = przesunDoPrzedzialu(a, b, m, dziesietne);
        double[] fx = new double[x.length];
        for(int i = 0; i < fx.length; i++){
            fx[i] = 10 * x[0].length;
            for(int j = 0; j < x[0].length; j++) {
                fx[i] += Math.pow(x[i][j], 2) - 10 * Math.cos(20 * Math.PI * x[i][j]);
            }
            osobniki.get(i).wartosc = fx[i];
        }
        return osobniki;
    }

}
