package ohtu.intjoukkosovellus;

import java.security.InvalidParameterException;

public class IntJoukko {

    public final static int KAPASITEETTI = 5; // aloitustalukon koko
    public final static int OLETUSKASVATUS = 5;  // luotava uusi taulukko on näin paljon isompi kuin vanha

    private int taulukonKasvatusMaara;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukuJono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkoidenMaara;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        lukuJono = new int[KAPASITEETTI];
        this.taulukonKasvatusMaara = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        this();
        if (kapasiteetti < 0) {
            throw new InvalidParameterException("Kapasitteetti ei saa olla pienempi kuin 0. Nyt se oli " + kapasiteetti);
        } else {
            lukuJono = new int[kapasiteetti];
        }
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        this(kapasiteetti);
        if (kasvatuskoko < 0) {
            throw new InvalidParameterException("Kasvatuskoko ei saa olla pienempi kuin 0. Nyt se oli " + kasvatuskoko);
        } else {
            this.taulukonKasvatusMaara = kasvatuskoko;
        }
    }

    public boolean lisaa(int luku) {
        if (alkoidenMaara == 0 || !lukuOnAlkoidenJoukossa(luku)) {
            lukuJono[alkoidenMaara] = luku;
            alkoidenMaara++;
            tarkistaArraynKasvatusTarve();
            return true;
        }
        return false;
    }

    private void tarkistaArraynKasvatusTarve() {
        if (alkoidenMaara == lukuJono.length) {
            int[] apu = lukuJono;
            lukuJono = new int[alkoidenMaara + taulukonKasvatusMaara];
            kopioiTaulukko(apu, lukuJono);
        }
    }

    public boolean lukuOnAlkoidenJoukossa(int luku) {
        for (int i = 0; i < alkoidenMaara; i++) {
            if (luku == lukuJono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int apu;
        int indeksi = etsiIndeksi(luku);
        if (indeksi != -1) {
            poistaJaUudista(indeksi);
            return true;
        }
        return false;
    }

    private void poistaJaUudista(int indeksi) {
        for (int i = indeksi; i < alkoidenMaara - 1; i++) {
            int apu = lukuJono[i];
            lukuJono[i] = lukuJono[i + 1];
            lukuJono[i + 1] = apu;
        }
        alkoidenMaara--;
    }

    public int etsiIndeksi(int luku) {
        int indeksi = -1;
        for (int i = 0; i < alkoidenMaara; i++) {
            if (luku == lukuJono[i]) {
                indeksi = i;
                lukuJono[indeksi] = 0;
                break;
            }
        }
        return indeksi;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        System.arraycopy(vanha, 0, uusi, 0, vanha.length);
    }

    public int getAlkioidenMaara() {
        return alkoidenMaara;
    }

    @Override
    public String toString() {
        String tuloste = "";
        if (alkoidenMaara > 0) {
            for (int i = 0; i < alkoidenMaara - 1; i++) {
                tuloste += lukuJono[i] + ", ";
            }
            tuloste += lukuJono[alkoidenMaara - 1];
        }
        return "{" + tuloste + "}";
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkoidenMaara];
        System.arraycopy(lukuJono, 0, taulu, 0, taulu.length);
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdisteTaulu = new IntJoukko();
        lisaaTaulu(yhdisteTaulu, a.toIntArray());
        lisaaTaulu(yhdisteTaulu, b.toIntArray());
        return yhdisteTaulu;
    }

    private static void lisaaTaulu(IntJoukko joukko, int[] taulukko) {
        for (int i = 0; i < taulukko.length; i++) {
            joukko.lisaa(taulukko[i]);
        }
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        return conditionalYhdiste(a, b, true);
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        return conditionalYhdiste(a, b, false);
    }

    private static IntJoukko conditionalYhdiste(IntJoukko a, IntJoukko b, boolean condition) {
        IntJoukko newTaulu = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            if (b.sisaltaaLuvun(aTaulu[i]) == condition) {
                newTaulu.lisaa(aTaulu[i]);
            }
        }
        return newTaulu;
    }

    public boolean sisaltaaLuvun(int luku) {
        for (int i = 0; i < alkoidenMaara; i++) {
            if (luku == lukuJono[i]) {
                return true;
            }
        }
        return false;
    }

}
