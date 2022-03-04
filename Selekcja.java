import java.util.*;
import java.util.stream.Collectors;

public class Selekcja {

    static public ArrayList<Osobnik> turniejowa(ArrayList<Osobnik> osobniki, boolean czyMax){
        int il = osobniki.size();
        int wielkoscGrup = 3;
        ArrayList<Osobnik> wynik = new ArrayList<>();
        Random random = new Random();
        List<Osobnik> grupa = new ArrayList<>();
        for (int i = 0; i < il; i++){
            while(grupa.size() != wielkoscGrup){
                int index = random.nextInt(il);
                if (grupa.contains(osobniki.get(index)))
                    continue;
                grupa.add(osobniki.get(index));
            }
//            for (Osobnik o :
//                    grupa) {
//                System.out.println(o.wartosc);
//            }
            Osobnik wygrany;
            if (czyMax) {
                wygrany = grupa
                        .stream()
                        .max(Comparator.comparing(Osobnik::getWartosc))
                        .orElseThrow(NoSuchElementException::new);
//                System.out.println("Maksymalny w grupie: " + wygrany.wartosc);
            }
            else {
                wygrany = grupa
                        .stream()
                        .min(Comparator.comparing(Osobnik::getWartosc))
                        .orElseThrow(NoSuchElementException::new);
//                System.out.println("Minimalny w grupie: " + wygrany.wartosc);
            }
//            wynik.add(wygrany);
            wynik.add(new Osobnik(wygrany.wartosc, wygrany.binarne));
            grupa.clear();
        }
//        for (Osobnik o :
//                wynik) {
//            System.out.println(wynik.indexOf(o));
//        }
        return wynik;
    }

    static ArrayList<Osobnik> rankingowa(ArrayList<Osobnik> osobniki, boolean czyMax){
        if (czyMax)
            osobniki = (ArrayList<Osobnik>) osobniki.stream()
                    .sorted(Comparator.comparing(Osobnik::getWartosc).reversed())
                    .collect(Collectors.toList());
        else
            osobniki = (ArrayList<Osobnik>) osobniki.stream()
                    .sorted(Comparator.comparing(Osobnik::getWartosc))
                    .collect(Collectors.toList());

        Random rand = new Random();
//        for (Osobnik o :
//                osobniki) {
//            System.out.println(o.getWartosc());
//        }
        Osobnik wygrany;
//        Osobnik wygrany = new Osobnik(new int[osobniki.get(0).binarne.length]);
        ArrayList<Osobnik> wynik = new ArrayList<>();
        for (int i = 0; i < osobniki.size(); i++){
            int pierwsze = rand.nextInt(osobniki.size());
            if (pierwsze == 0){
//                System.out.println("INDEKS: " + pierwsze);
//                wygrany = osobniki.get(pierwsze);
                wygrany = new Osobnik(osobniki.get(pierwsze).wartosc, osobniki.get(pierwsze).binarne);
            }
            else{
                int drugie = rand.nextInt(pierwsze);
//                System.out.println("INDEKS: " + drugie);
//                wygrany = osobniki.get(drugie);
                wygrany = new Osobnik(osobniki.get(drugie).wartosc, osobniki.get(drugie).binarne);
            }
//            System.out.println("WYGRANY: " + wygrany.getWartosc());
            wynik.add(wygrany);
        }
        return wynik;
    }

    public static ArrayList<Osobnik> ruletka(ArrayList<Osobnik> osobniki, boolean czyMax){
        double sumaF = 0;
        for (Osobnik o :
                osobniki) {
            sumaF += o.wartosc;
        }

        for (int i = 0; i < osobniki.size(); i++){
            Osobnik o = osobniki.get(i);
            o.prawdopodobienstwo = o.wartosc / sumaF;
            if (!czyMax)
                o.prawdopodobienstwo = (1 - o.prawdopodobienstwo) / (osobniki.size() - 1);
            if (i == 0)
                o.dystrybuanta = o.prawdopodobienstwo;
            else
                o.dystrybuanta = o.prawdopodobienstwo + osobniki.get(i - 1).dystrybuanta;
        }
//        System.out.println("DYSTRYBUANTA: ");
//        for (Osobnik o :
//                osobniki) {
//            System.out.println(o.dystrybuanta);
//        }
        Random rand = new Random();
        ArrayList<Osobnik> wynik = new ArrayList<>();
        for (int i = 0; i < osobniki.size(); i++){
            double r = rand.nextDouble();
//            System.out.println("R: " + r);
            for (Osobnik o :
                    osobniki) {
                if (r <= o.dystrybuanta) {
//                    System.out.println("DYSTRYBUANTA: " + o.dystrybuanta + ", WARTOSC: " + o.wartosc + ", INDEKS: " + osobniki.indexOf(o));
                    wynik.add(o);
                    break;
                }
            }
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
