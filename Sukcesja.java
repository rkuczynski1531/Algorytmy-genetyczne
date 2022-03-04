

import java.util.*;
import java.util.stream.Collectors;

public class Sukcesja {

    public static ArrayList<Osobnik> skopiuj(ArrayList<Osobnik> osobniki){
        ArrayList<Osobnik> kopia = new ArrayList<>();
        for (Osobnik o :
                osobniki) {
            Osobnik c = new Osobnik(new int[o.binarne.length]);
            for (int j = 0; j<o.binarne.length; j++)
                c.binarne[j] = o.binarne[j];
            c.pozycja = o.pozycja;
            kopia.add(c);
        }
        return kopia;
    }

    public static ArrayList<Osobnik> trywialna(ArrayList<Osobnik> osobniki, int[] m, int il, double[] a, double[] b, int epoki){
        int iloscEpok = epoki;

        for (int i = 0; i < iloscEpok; i++) {
            osobniki = Selekcja.rankingowa(osobniki, true);
            int indeks = 0;
            for (Osobnik o :
                    osobniki) {
                o.pozycja = indeks;
                indeks++;
            }
            ArrayList<Osobnik> mutacja = operatoryGenetyczne.mutacja(osobniki);
            for (Osobnik o :
                    mutacja) {
                osobniki.get(o.pozycja).binarne = o.binarne;
            }
            ArrayList<Osobnik> kopia = skopiuj(osobniki);
            ArrayList<Osobnik> inwersja = operatoryGenetyczne.inwersja(kopia);

            for (Osobnik o :
                    inwersja) {
                osobniki.get(o.pozycja).binarne = o.binarne;
            }
            ArrayList<Osobnik> krzyzowanie = operatoryGenetyczne.krzyzowanie(osobniki, false);
            for (Osobnik o :
                    krzyzowanie) {
                osobniki.get(o.pozycja).binarne = o.binarne;
            }
            Rastragin.ocenOsobnikow(a, b, m, il, osobniki);

        }
        return osobniki;
    }

    public static ArrayList<Osobnik> elitarna(ArrayList<Osobnik> osobniki, int[] m, int il, double[] a, double[] b, boolean czyMax, int epoki){
        int iloscEpok = epoki;
        ArrayList<Osobnik> populacja = new ArrayList<>();
        ArrayList<Osobnik> temp = new ArrayList<>();
        for (int i = 0; i < iloscEpok; i++) {
            int indeks = 0;
            for (Osobnik o :
                    osobniki) {
                o.pozycja = indeks;
                indeks++;
            }
            populacja.clear();
            populacja.addAll(osobniki);
            ArrayList<Osobnik> selekcja;
            if(czyMax)
                selekcja = Selekcja.rankingowa(osobniki, true);
            else
                selekcja = Selekcja.rankingowa(osobniki, false);
//            System.out.println("Selekcja");
//            operatoryGenetyczne.wypiszWynik(selekcja);
            ArrayList<Osobnik> kopia = skopiuj(selekcja);
            ArrayList<Osobnik> mutacja = operatoryGenetyczne.mutacja(selekcja);
            populacja.addAll(mutacja);
            ArrayList<Osobnik> inwersja = operatoryGenetyczne.inwersja(kopia);
            populacja.addAll(inwersja);
            kopia.clear();
            kopia = skopiuj(selekcja);
//            System.out.println("Kopia");
//            operatoryGenetyczne.wypiszWynik(kopia);
            ArrayList<Osobnik> krzyzowanie = operatoryGenetyczne.krzyzowanie(kopia, false);
            populacja.addAll(krzyzowanie);

            populacja = Rastragin.ocenOsobnikow(a, b, m, il, populacja);
            if (czyMax) {
                while (populacja.size() != il) {
                    populacja.remove(populacja
                            .stream()
                            .min(Comparator.comparing(Osobnik::getWartosc))
                            .orElseThrow(NoSuchElementException::new));
                }
            }
            else{
                while (populacja.size() != il) {
                    populacja.remove(populacja
                            .stream()
                            .max(Comparator.comparing(Osobnik::getWartosc))
                            .orElseThrow(NoSuchElementException::new));
                }
            }
            for (int j = 0; j < populacja.size(); j ++){
                osobniki.get(j).binarne = populacja.get(j).binarne;
                osobniki.get(j).wartosc = populacja.get(j).wartosc;
            }

        }
        return osobniki;
    }
    public static ArrayList<Osobnik> zeSciskiem(ArrayList<Osobnik> osobniki, int[] m, int il, double[] a, double[] b, int epoki){
        int iloscEpok = epoki;
        ArrayList<Osobnik> populacja = new ArrayList<>();
        for (int i = 0; i < iloscEpok; i++) {
            int indeks = 0;
            for (Osobnik o :
                    osobniki) {
                o.pozycja = indeks;
                indeks++;
            }
            populacja.clear();
            populacja.addAll(osobniki);

            ArrayList<Osobnik> selekcja = Selekcja.rankingowa(osobniki, true);
            ArrayList<Osobnik> kopia = skopiuj(selekcja);
            ArrayList<Osobnik> mutacja = operatoryGenetyczne.mutacja(selekcja);
            populacja.addAll(mutacja);
            ArrayList<Osobnik> inwersja = operatoryGenetyczne.inwersja(kopia);
            populacja.addAll(inwersja);
            kopia.clear();
            kopia = skopiuj(selekcja);
            ArrayList<Osobnik> krzyzowanie = operatoryGenetyczne.krzyzowanie(kopia, false);
            populacja.addAll(krzyzowanie);

            populacja = Rastragin.ocenOsobnikow(a, b, m, il, populacja);
            populacja = (ArrayList<Osobnik>) populacja.stream()
                    .sorted(Comparator.comparing(Osobnik::getWartosc))
                    .collect(Collectors.toList());
            while (populacja.size() != il){
//                System.out.println(populacja.size());
                ArrayList<Double> roznica = new ArrayList<>();
                for (int j = 0; j < populacja.size() - 1; j++){
                    roznica.add(populacja.get(j + 1).wartosc - populacja.get(j).wartosc);
                }
                int indekss = roznica.indexOf(Collections.min(roznica));
                populacja.remove(indekss);
                roznica.clear();
            }
            for (int j = 0; j < populacja.size(); j ++){
                osobniki.get(j).binarne = populacja.get(j).binarne;
                osobniki.get(j).wartosc = populacja.get(j).wartosc;
            }

        }
        return osobniki;
    }

    public static ArrayList<Osobnik> losowa(ArrayList<Osobnik> osobniki, int[] m, int il, double[] a, double[] b, int epoki){
        int iloscEpok = epoki;
        ArrayList<Osobnik> populacja = new ArrayList<>();
        for (int i = 0; i < iloscEpok; i++) {
            int indeks = 0;
            for (Osobnik o :
                    osobniki) {
                o.pozycja = indeks;
                indeks++;
            }
            populacja.clear();
            populacja.addAll(osobniki);

            ArrayList<Osobnik> selekcja = Selekcja.rankingowa(osobniki, true);
            ArrayList<Osobnik> kopia = skopiuj(selekcja);
            ArrayList<Osobnik> mutacja = operatoryGenetyczne.mutacja(selekcja);
            populacja.addAll(mutacja);
            ArrayList<Osobnik> inwersja = operatoryGenetyczne.inwersja(kopia);
            populacja.addAll(inwersja);
            kopia.clear();
            kopia = skopiuj(selekcja);
            ArrayList<Osobnik> krzyzowanie = operatoryGenetyczne.krzyzowanie(kopia, false);
            populacja.addAll(krzyzowanie);

            populacja = Rastragin.ocenOsobnikow(a, b, m, il, populacja);
//            populacja = (ArrayList<Osobnik>) populacja.stream()
//                    .sorted(Comparator.comparing(Osobnik::getWartosc))
//                    .collect(Collectors.toList());
            Random rand = new Random();
            while (populacja.size() != il){
                populacja.remove(rand.nextInt(populacja.size()));
            }
            for (int j = 0; j < populacja.size(); j ++){
                osobniki.get(j).binarne = populacja.get(j).binarne;
                osobniki.get(j).wartosc = populacja.get(j).wartosc;
            }

        }
        return osobniki;
    }

    public static void main(String[] args) {
        double[] a = {-1, -1};
        double[] b = {1, 1};
        int[] d = {2, 3};
        Rastragin rastragin = new Rastragin();
        int[] m = rastragin.wyznaczM(a, b, d);

        int il = 2;
        int sumaM = 0;

        for(int i = 0; i < m.length; i++){
            sumaM += m[i];
        }

        int[][] macierz = Rastragin.stworzMacierz(sumaM, il);
        int[][] dziesietne = Rastragin.zamienNaDziesietne(m, il, macierz);
        double[][] x = Rastragin.przesunDoPrzedzialu(a, b, m, dziesietne);
        double[] fx = Rastragin.ocenOsobnikow(x);

        ArrayList<Osobnik> osobniki = new ArrayList<>();
        for (int i = 0; i < fx.length; i++){
            osobniki.add(new Osobnik(fx[i], macierz[i], i));
        }
        operatoryGenetyczne.wypiszWynik(osobniki);
        int epoki = 100;

        osobniki = trywialna(osobniki, m, il, a, b, epoki);
//        osobniki = elitarna(osobniki, m, il, a, b, true, epoki);
//        osobniki = elitarna(osobniki, m, il, a, b, false, epoki);
//        osobniki = zeSciskiem(osobniki, m, il, a, b, epoki);
//        osobniki = losowa(osobniki, m, il, a, b, epoki);
        System.out.println("WYNIK:");
        operatoryGenetyczne.wypiszWynik(osobniki);

    }
}
