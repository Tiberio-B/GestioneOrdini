package it.gestioneordini.service.ordine;

import java.util.List;

import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;
import it.gestioneordini.service.GenericService;

public interface OrdineService extends GenericService<Ordine> {
	
	List<Ordine> filtraPer(Categoria categoria) throws Exception;

	void aggiungi(Ordine ordine, Articolo articolo) throws Exception;

}
