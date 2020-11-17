package it.gestioneordini.test;

import java.util.ArrayList;
import java.util.List;

import it.gestioneordini.dao.EntityManagerUtil;
import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;
import it.gestioneordini.service.MyServiceFactory;
import it.gestioneordini.service.articolo.ArticoloService;
import it.gestioneordini.service.categoria.CategoriaService;
import it.gestioneordini.service.ordine.OrdineService;
import it.gestioneordini.test.casuale.GeneratoreCasuale;

public class GestioneOrdiniTest {
	
	private static final ArticoloService articoloService = MyServiceFactory
			.getArticoloServiceInstance();
	private static final OrdineService ordineService = MyServiceFactory
			.getOrdineServiceInstance();
	private static final CategoriaService categoriaService = MyServiceFactory
			.getCategoriaServiceInstance();

	private static final GeneratoreCasuale r = new GeneratoreCasuale();
	
	private static List<Articolo> articoli;
	private static List<Ordine> ordini;
	private static List<Categoria> categorie;

	private static final int numCategorie = 5;
	private static final int numArticoli = 20;
	private static final int numOrdini = 5;
	private static final int numOrdinativi = 10;
	private static final int numCategorizzazioni = 10;
	
	public static void stampa(Object obj) {
		System.out.println(obj);
	}
	
	public static void init(int numArticoli, int numOrdini, int numCategorie) throws Exception {
		initArticoli(numArticoli);
		initOrdini(numOrdini);
		initCategorie(numCategorie);
	}

	public static void initArticoli(int numArticoli) throws Exception {
		for (int a = 0; a < numArticoli; a++) {
			Articolo art = new Articolo(r.nomeArticolo(), r.nextInt(10,100));
			articoloService.inserisci(art);
			System.out.println("Articolo appena creato: " + art);
		}
		articoli = articoloService.elenca();
	}
	
	public static void initOrdini(int numOrdini) throws Exception {
		for (int o = 0; o < numOrdini; o++) {
			Ordine ord = new Ordine(r.nomeCompletoCasuale(), r.indirizzoCasuale());
			ordineService.inserisci(ord);
			System.out.println("Ordine appena creato: " + ord);
		}
		ordini = ordineService.elenca();
	}
	
	public static void initCategorie(int numCategorie) throws Exception {
		for (int m = 0; m < numCategorie; m++) {
			Categoria cat = new Categoria(r.nomeCategoria());
			categoriaService.inserisci(cat);
			System.out.println("Categoria appena creata: " + cat);
		}
		categorie = categoriaService.elenca();
	}
	
	public static void doOrdinativi(int numOrdinativi) throws Exception {
		for (int j = 0; j < numOrdinativi; j++) {
			Articolo articolo = r.articoloCasuale();
			Ordine ordine = r.ordineCasuale();
			if (r.coinFlip()) {
				articolo.addOrdine(ordine);
			}
			else { ordine.addArticolo(articolo); }
		}
	}
	
	public static void doCategorizzazioni(int numCategorizzazioni) throws Exception{
		for (int k = 0; k < numCategorizzazioni; k++) {
			Articolo articolo = r.articoloCasuale();
			Categoria categoria = r.categoriaCasuale();
			if (r.coinFlip()) {
				articolo.addCategoria(categoria);
			}
			else { categoria.addArticolo(articolo); }
		}
	}
	
	public static List<Ordine> getOrdiniFromCategoriaCasuale() throws Exception {
		List<Ordine> ordiniFiltrati = ordineService.filtraPer(r.categoriaCasuale());
		for (Ordine ord : ordiniFiltrati) {
			System.out.println(ord);
		}
		return ordiniFiltrati;	
	}
	
	private static List<Categoria> getCategorieFromOrdineCasuale() throws Exception {
		List<Categoria> categorieFiltrate = categoriaService.filtraPer(r.ordineCasuale());
		for (Categoria cat : categorieFiltrate) {
			System.out.println(cat);
		}
		return categorieFiltrate;
	}
	
	private static int valoreCategoriaCasuale() throws Exception {
		categorie = categoriaService.elenca();
		Categoria categoriaCasuale = categorie.get(r.nextInt(categorie.size()));
		return categoriaService.valore(categoriaCasuale);
	}
	
	public static void feedback() {
		try {
			articoli = articoloService.elenca();
			ordini = ordineService.elenca();
			categorie = categoriaService.elenca();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Object> all = new ArrayList<>();
		all.addAll(articoli);
		all.addAll(ordini);
		all.addAll(categorie);
		for (Object obj : all) {
			System.out.println(obj);
		}
	}
	
	public static void main(String[] args) {
		
		try {
		
			init(numArticoli, numOrdini, numCategorie); // inizializza tutte le tabelle
			
			doCategorizzazioni(numCategorizzazioni); // collega Categorie e Articoli
			
			doOrdinativi(numOrdinativi); // collega Ordini e Articoli
			
			// getOrdiniFromCategoriaCasuale(); // query per casa no#1
			
			// getCategorieFromOrdineCasuale(); // query per casa no#2
			
			// valoreCategoriaCasuale(); // query per casa no#3
			
			// feedback(); // stampa a schermo tutti i records nel DB
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}
}
