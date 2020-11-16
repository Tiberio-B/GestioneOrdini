package it.gestioneordini.test.casuale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;
import it.gestioneordini.service.MyServiceFactory;


public class GeneratoreCasuale extends Random {

	private static final long serialVersionUID = 1L;

	private <T> T elementoCasuale(T[]... arrays) {
		List<T> lista = new ArrayList<>();
		for (T[] array : arrays) {
			for (T t : array) {
				lista.add(t);
			}
		}
		return elementoCasuale(lista);
	}
	
	private <T> T elementoCasuale(T[] array) {
		return array[nextInt(array.length)];
	}

	public <T> T elementoCasuale(List<T> lista) {
		return lista.get(nextInt(lista.size()));
	}

	public int nextInt(int min, int max) {
		return (nextInt(max - min + 1) + min);
	}

	boolean coinFlip() {
		return (nextInt(2) > 0) ? true : false;
	}

	String nomeLocaleCasuale() {
		return elementoCasuale(LoremIpsum.LOCALI) + " Da " + nomeCasuale();
	}

	public String nomeCasuale() {
		return elementoCasuale(LoremIpsum.NOMI);
	}

	public String cognomeCasuale() {
		return elementoCasuale(LoremIpsum.COGNOMI);
	}
	
	public String nomeCompletoCasuale() {
		return nomeCasuale() + " " + cognomeCasuale();
	}
	
	public Articolo articoloCasuale() throws Exception {
		return elementoCasuale(MyServiceFactory.getArticoloServiceInstance().elenca());
	}
	
	public Ordine ordineCasuale() throws Exception {
		return elementoCasuale(MyServiceFactory.getOrdineServiceInstance().elenca());
	}
	
	public Categoria categoriaCasuale() throws Exception {
		return elementoCasuale(MyServiceFactory.getCategoriaServiceInstance().elenca());
	}

	@SuppressWarnings("deprecation")
	Date dataCasuale(int giornoMin, int giornoMax, int meseMin, int meseMax, int annoMin,
			int annoMax) {
		int giorno = nextInt(giornoMin, giornoMax);
		int mese = nextInt(meseMin, meseMax);
		int anno = nextInt(annoMin, annoMax);
		return new Date(anno - 1900, mese - 1, giorno);
	}

	public String recapitoCasuale() {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < 10; i++)
			ret.append(nextInt(10));
		return ret.toString();
	}

	public float prezzoCasuale() {
		float prezzo = nextFloat();
		prezzo = coinFlip() ? prezzo * 100 : prezzo * 10;
		return ((int) ((prezzo + 0.005f) * 100)) / 100f;
	}

	public String nomeMenuCasuale() {
		return elementoCasuale(LoremIpsum.MENU);
	}
	
	public String indirizzoCasuale() {
		return elementoCasuale(LoremIpsum.DUG) + " " + nomeCompletoCasuale() + ", " + nextInt(1, 100);
	}
	
	public String numeroRomanoCasuale() {
		return elementoCasuale(LoremIpsum.NUMERI_ROMANI);
	}
	
	public String nomeMunicipioCasuale() {
		return "Municipio " + numeroRomanoCasuale();
	}

	public String nomeCategoria() {
		return elementoCasuale(LoremIpsum.CATEGORIE_ECOMMERCE);
	}

	public String nomeArticolo() {
		return elementoCasuale(LoremIpsum.CVCVCVCV);
	}
	
	/*
	 * int coinFlip() { return nextInt(2); }
	 * 
	 * Valutatore valutatoreCasuale(Ufficio ufficio) { Genere genere =
	 * genereCasuale(); String nome = nomeCasuale(genere); String cognome =
	 * cognomeCasuale(); Date ddn = ddnCasuale(); return new Valutatore(nome,
	 * cognome, ddn, genere, ufficio); }
	 * 
	 * Dipendente dipendenteCasuale(Ufficio ufficio) { Genere genere =
	 * genereCasuale(); String nome = nomeCasuale(genere); String cognome =
	 * cognomeCasuale(); Date ddn = ddnCasuale(); return new Dipendente(nome,
	 * cognome, ddn, genere, ufficio); }
	 * 
	 * Valutazione valutazioneCasuale(Date data, Dipendente dip) { return new
	 * Valutazione(data, dip, nextInt(6), nextInt(6), nextInt(6)); }
	 * 
	 * Genere genereCasuale() { switch (nextInt(3)) { case 0: return Genere.M; case
	 * 1: return Genere.F; } return Genere.NB; }
	 * 
	 */
}
