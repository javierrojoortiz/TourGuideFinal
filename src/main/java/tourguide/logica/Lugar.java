package tourguide.logica;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Lugar {

	@Id
	@GeneratedValue
	private Long id;

	private String nombreLugar;

	private String tipo;

	protected Lugar() {
	}

	public Lugar(String nombreLugar, String tipo) {
		this.nombreLugar = nombreLugar;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public String getNombreLugar() {
		return nombreLugar;
	}

	public void setNombreLugar(String nombreLugar) {
		this.nombreLugar = nombreLugar;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return String.format("Lugar[id=%d, Nombre del Lugar='%s', Tipo='%s']", id,
				nombreLugar, tipo);
	}

}
