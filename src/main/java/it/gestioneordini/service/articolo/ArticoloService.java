package it.gestioneordini.service.articolo;

import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.service.GenericService;

public interface ArticoloService extends GenericService<Articolo>  {

	void aggiungi(Articolo articolo, Categoria categoria) throws Exception;

}
