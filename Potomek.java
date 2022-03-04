public class Potomek extends Osobnik{
    int indeks;

    public Potomek(int[] binarne) {
        super(binarne);
    }

    public Potomek() {
    }

    public Potomek(int indeks, int[] binarne) {
        super(binarne);
        this.indeks = indeks;
    }

    public Potomek(int indeks) {
        this.indeks = indeks;
    }
}
