package ohtu.matkakortti;

import ohtu.matkakortti.Matkakortti;
import ohtu.matkakortti.Kassapaate;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KassapaateTest {

    Kassapaate kassa;
    Matkakortti kortti;

//	kassapäätteen metodin lataa kutsu lisää matkakortille ladattavan rahamäärän käyttäen kortin metodia lataa jos ladattava summa on positiivinen
//	kassapäätteen metodin lataa kutsu ei tee matkakortille mitään jos ladattava summa on negatiivinen
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = mock(Matkakortti.class);
    }

    @Test
    public void kassallaVoiLadataRahaa() {
        when(kortti.getSaldo()).thenReturn(10);
        kassa.lataa(kortti, 10);
        
        verify(kortti, times(1)).lataa(10);
    }
    
        @Test
    public void kassallaEiVoiLadataNegatiivistaRahaa() {
        when(kortti.getSaldo()).thenReturn(10);
        kassa.lataa(kortti, 10);

        verify(kortti, times(0)).lataa(-10);
    }
    

    @Test
    public void kortiltaVelotetaanHintaJosRahaaOn() {
        when(kortti.getSaldo()).thenReturn(10);
        kassa.ostaLounas(kortti);

        verify(kortti, times(1)).getSaldo();
        verify(kortti).osta(eq(Kassapaate.HINTA));
    }

    @Test
    public void kortiltaEiVelotetaJosRahaEiRiita() {
        when(kortti.getSaldo()).thenReturn(4);
        kassa.ostaLounas(kortti);

        verify(kortti, times(1)).getSaldo();
        verify(kortti, times(0)).osta(anyInt());
    }

}
