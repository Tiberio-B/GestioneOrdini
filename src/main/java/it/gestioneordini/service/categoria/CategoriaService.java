package it.gestioneordini.service.categoria;

import java.util.List;

import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;
import it.gestioneordini.service.GenericService;

public interface CategoriaService extends GenericService<Categoria> {
	
	List<Categoria> filtraPer(Ordine ordine) throws Exception;
	
	int valore(Categoria input) throws Exception;

	void aggiungi(Categoria categoria, Articolo articolo) throws Exception;

}
