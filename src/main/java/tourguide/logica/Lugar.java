package tourguide.logica;

import java.util.ArrayList;
import java.util.List;

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

	protected Lugar() {
	}

	public Lugar(String nombreLugar, String tipo) {
		this.nombreLugar = nombreLugar;
		this.tipo = tipo;
	}
	public Lugar(String nombreLugar, String tipo,String imagenRecurso) {
		this.nombreLugar = nombreLugar;
		this.tipo = tipo;
		this.imagenRecurso=imagenRecurso;
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
