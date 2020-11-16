package it.gestioneordini.dao.ordine;

import java.util.List;

import it.gestioneordini.dao.IBaseDAO;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {
	
	public List<Ordine> findBy(Categoria categoria);

}
