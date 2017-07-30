package tourguide.logica;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.vaadin.ui.Image;

@Entity
public class Lugar {

	@Id
	@GeneratedValue
	private Long id;

	private String nombreLugar;

	private String tipo;

	private String imagenRecurso;

	private String direccion;

	private String telefono;
	
	private String horario;
	
	private boolean visitaGuiada;
	
	private String descripcion;
	
	private Double latitud;
	
	private Double longitud;

	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public Lugar() {
	}
	public Lugar(String nombreLugar, String tipo,String imagenRecurso,String direccion,
			String telefono, String horario, boolean visitaGuiada, String descripcion, double latitud, double longitud) {
		this.descripcion = descripcion;
		this.horario = horario;
		this.telefono = telefono;
		this.direccion = direccion;
		this.visitaGuiada = visitaGuiada;	
		this.nombreLugar = nombreLugar;
		this.tipo = tipo;
		this.imagenRecurso=imagenRecurso;
		this.latitud=latitud;
		this.longitud=longitud;
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
		return String.format("Lugar[id=%d, Nombre del Lugar='%s', Tipo='%s',]", id, nombreLugar, tipo);
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public boolean isVisitaGuiada() {
		return visitaGuiada;
	}

	public void setVisitaGuiada(boolean visitaGuiada) {
		this.visitaGuiada = visitaGuiada;
	}

	public String getImagenRecurso() {
		return imagenRecurso;
	}

	public void setImagenRecurso(String imagenRecurso) {
		this.imagenRecurso = imagenRecurso;
	}

}
