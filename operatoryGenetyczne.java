import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

class operatoryGenetyczne {

    public static ArrayList<Osobnik> mutacja(ArrayList<Osobnik> osobniki){
        double prawdMutacji = 0.1;
        Random rand = new Random();
        ArrayList<Osobnik> potomkowie = new ArrayList<>();
        ArrayList<Osobnik> temp = new ArrayList<>();
        for (int j = 0; j < osobniki.size(); j ++){
            temp.add(new Osobnik());
            temp.get(j).binarne = osobniki.get(j).binarne;
            temp.get(j).pozycja = osobniki.get(j).pozycja;
        }
        int licznik = 0;
        double r = 0;
        for (Osobnik o :
                temp) {
            licznik = 0;
            Osobnik p = new Osobnik(o.pozycja, new int[o.binarne.length]);

            for (int j = 0; j < o.binarne.length; j ++){
                p.binarne[j] = o.binarne[j];
            }
            for (int i = 0; i < o.binarne.length; i++){
                r = rand.nextDouble();
                if (r < prawdMutacji){
                    licznik++;
//                    System.out.println("Wiersz: " + temp.indexOf(o) + ", Kolumna: " + i);
                    if (o.binarne[i] == 0)
                        p.binarne[i] = 1;
                    else
                        p.binarne[i] = 0;
                }
            }
            if (licznik != 0)
                potomkowie.add(p);
        }
        return potomkowie;
    }

    public static ArrayList<Osobnik> inwersja(ArrayList<Osobnik> osobniki){
        double prawdInwersji = 0.1;
        Random rand = new Random();
        double r = 0;
        int poczatek = 0;
        int koniec = 0;
        ArrayList<Osobnik> temp = new ArrayList<>();
        for (int j = 0; j < osobniki.size(); j ++){
            temp.add(new Osobnik());
            temp.get(j).binarne = osobniki.get(j).binarne;
            temp.get(j).pozycja = osobniki.get(j).pozycja;
        }

        ArrayList<Osobnik> potomkowie = new ArrayList<>();
        for (Osobnik o :
                temp) {
            r = rand.nextDouble();
            if (r < prawdInwersji){
                Osobnik p = new Osobnik(o.pozycja);
                int[] binarne = o.binarne;
                poczatek = rand.nextInt(o.binarne.length);
                do {
                    koniec = rand.nextInt(o.binarne.length);
                }while (poczatek == koniec);
                if (poczatek > koniec){
                    int temp2 = poczatek;
                    poczatek = koniec;
                    koniec = temp2;
                }
//                System.out.println("INDEX: " + temp.indexOf(o) + ", POCZATEK: " + poczatek + ", Koniec: " + koniec);
                for (int i = 0; i <= (koniec - poczatek) / 2; i++){
                    int temp2 = binarne[poczatek + i];
                    binarne[poczatek + i] = binarne[koniec - i];
                    binarne[koniec - i] = temp2;
                }
                p.binarne = binarne;
                potomkowie.add(p);
            }
        }
        return potomkowie;
    }

    public static ArrayList<Osobnik> krzyzowanie(ArrayList<Osobnik> osobniki, boolean czyRownomierne){
        Random rand = new Random();
        double r = 0;
        double prawdKrzyzowania = 0.5;
        int licznik = 0;

        int m = osobniki.stream().findFirst().get().binarne.length;
        ArrayList<Osobnik> wybrane = new ArrayList<>();
        ArrayList<Osobnik> temp = osobniki;
        for (Osobnik o :
                temp) {
            r = rand.nextDouble();
            if (r < prawdKrzyzowania)
                wybrane.add(new Osobnik(o.pozycja, o.binarne));
        }
        if (wybrane.size() % 2 == 1){
            wybrane.remove(rand.nextInt(wybrane.size()));
        }
        for (Osobnik o :
                wybrane) {
            temp.remove(o);
        }
        Osobnik[][] pary = new Osobnik[wybrane.size() / 2][2];
        Osobnik[][] potomkowie = new Osobnik[wybrane.size() / 2][2];
        for (int i = 0; i < pary.length; i++){
            int indeks = rand.nextInt(wybrane.size());
//            System.out.println("INDEKS: " + indeks);
            pary[i][0] = new Osobnik(wybrane.get(indeks).pozycja);
            potomkowie[i][0] = new Osobnik(wybrane.get(indeks).pozycja);
            pary[i][0].binarne = new int[wybrane.get(indeks).binarne.length];
            potomkowie[i][0].binarne = new int[wybrane.get(indeks).binarne.length];
            for (int j = 0; j < wybrane.get(indeks).binarne.length; j++){
                pary[i][0].binarne[j] = wybrane.get(indeks).binarne[j];
            }
            wybrane.remove(indeks);
            indeks = rand.nextInt(wybrane.size());
            pary[i][1] = new Osobnik(wybrane.get(indeks).pozycja);
            potomkowie[i][1] = new Osobnik(wybrane.get(indeks).pozycja);
//            System.out.println("INDEKS: " + indeks);
            pary[i][1].binarne = new int[wybrane.get(indeks).binarne.length];
            potomkowie[i][1].binarne = new int[wybrane.get(indeks).binarne.length];
            for (int j = 0; j < wybrane.get(indeks).binarne.length; j++){
                pary[i][1].binarne[j] = wybrane.get(indeks).binarne[j];
            }
            wybrane.remove(indeks);
        }
//        System.out.println("PARY");
//        for (int i = 0; i < pary.length; i++){
//            for (int j = 0; j < pary[0].length; j++){
//                System.out.println(Arrays.toString(pary[i][j].binarne));
//            }
//        }

        if (!czyRownomierne){
            int iloscPunktow;
            ArrayList<Integer> punkty = new ArrayList<>();

            for (int i = 0; i < pary.length; i++){
                iloscPunktow = rand.nextInt(m);

                while (punkty.size() != iloscPunktow){
                    int punkt = rand.nextInt(m);
                    if (punkty.contains(punkt))
                        continue;
                    punkty.add(punkt);
                }
                Collections.sort(punkty);
//                System.out.println(punkty);
                for (int j = 0; j < m; j++){
                    if (punkty.contains(j))
                        licznik++;
                    if (licznik % 2 == 0){
                        potomkowie[i][0].binarne[j] = pary[i][0].binarne[j];
                        potomkowie[i][1].binarne[j] = pary[i][1].binarne[j];
                    }
                    else{
                        potomkowie[i][0].binarne[j] = pary[i][1].binarne[j];
                        potomkowie[i][1].binarne[j] = pary[i][0].binarne[j];
                    }
                }
                punkty.clear();
                licznik = 0;
            }
//            for (int i = 0; i < pary.length; i++){
//                for (int j = 0; j < pary[0].length; j++){
//                    System.out.println(Arrays.toString(potomkowie[i][j].binarne));
//
//                }
//            }
        }

        else{
            int[] wzorzec = new int[m];
            for (int i = 0; i < pary.length; i++){
                for (int k = 0; k < wzorzec.length; k++){
                    wzorzec[k] = rand.nextInt(2);
                }
                for (int j = 0; j < m; j++){
                    if (j == 0)
//                        System.out.println("Wzorzec: " + '\n' + Arrays.toString(wzorzec) + "\n-------------");
                    if (wzorzec[j] == 0){
                        potomkowie[i][0].binarne[j] = pary[i][0].binarne[j];
                        potomkowie[i][1].binarne[j] = pary[i][1].binarne[j];
                    }
                    else{
                        potomkowie[i][0].binarne[j] = pary[i][1].binarne[j];
                        potomkowie[i][1].binarne[j] = pary[i][0].binarne[j];
                    }
                }
            }
//            for (int i = 0; i < pary.length; i++){
//                for (int j = 0; j < pary[0].length; j++){
//                    System.out.println(Arrays.toString(potomkowie[i][j].binarne));
//
//                }
//            }
        }
        ArrayList<Osobnik> wynik = new ArrayList<>();
        for (int i = 0; i < potomkowie.length; i++){
            wynik.add(potomkowie[i][0]);
            wynik.add(potomkowie[i][1]);
        }
        return wynik;
    }

    public static void wypiszWynik(ArrayList<Osobnik> wynik){
        for (Osobnik o :
                wynik) {
            System.out.println(Arrays.toString(o.binarne) + " " + o.wartosc);
        }
    }

}
