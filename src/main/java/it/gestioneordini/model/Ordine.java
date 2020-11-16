package it.gestioneordini.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.gestioneordini.service.MyServiceFactory;

@Entity
@Table(name = "ordine")
public class Ordine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "destinatario")
	private String destinatario;
	@Column(name = "destinazione")
	private String destinazione;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ordine")
	private Set<Articolo> articoli  = new HashSet<>(0);
	
	public Ordine() {}
	
	public Ordine(String destinatario, String destinazione) {
		this.destinatario = destinatario;
		this.destinazione = destinazione;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDestinatario() {
		return destinatario;
	}
	
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	public String getDestinazione() {
		return destinazione;
	}
	
	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}
	
	public Set<Articolo> getArticoli() {
		return articoli;
	}
	
	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}
	
	public void addArticolo(Articolo articolo) throws Exception {
		MyServiceFactory.getOrdineServiceInstance().aggiungi(this, articolo);
	}
	
	@Override
	public String toString() {
		String s = "Ordine " + id + ".";
		s += (articoli.size() == 0) ? " (vuoto)\n" : "\n";
		for (Articolo articolo : articoli) {
			s += "\t"+articolo.toString()+"\n";
		}
		s += "Da:" + destinatario + ", Verso: " + destinazione;
		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ordine) {
			Ordine that = (Ordine) obj;
			return this.getId() == that.getId() &&
					this.getDestinatario() == that.getDestinatario() &&
					this.getDestinazione() == that.getDestinazione();
		}
		return false;
	}
}
