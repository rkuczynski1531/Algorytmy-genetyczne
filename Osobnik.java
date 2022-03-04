public class Osobnik {
    public int pozycja;
    public double wartosc;
    public int[] binarne;
    public double prawdopodobienstwo;
    public double dystrybuanta;

    public Osobnik(int[] binarne) {
        this.binarne = binarne;
    }

    public Osobnik(double wartosc, int[] binarne) {
        this.wartosc = wartosc;
        this.binarne = binarne;
    }

    public Osobnik(double wartosc, int[] binarne, int pozycja) {
        this.wartosc = wartosc;
        this.binarne = binarne;
        this.pozycja = pozycja;
    }

    public Osobnik(double wartosc) {
        this.wartosc = wartosc;
    }

    public Osobnik() {
    }

    public Osobnik(int indeks) {
        this.pozycja = indeks;
    }

    public Osobnik(int indeks, int[] binarne) {
        this.pozycja = indeks;
        this.binarne = binarne;
    }

    public double getWartosc() {
        return wartosc;
    }


}
