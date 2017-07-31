package tourguide.logica;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hotel {

	@Id
	@GeneratedValue
	private Long id;

	private String nombre;

	private String direccion;
	
	private int estrellas;
	
	private int valoracion;
	
	private String descripcion;
	
	private String imagenRecurso;
	
	private double km_playa;
	
	private double precio_medio_noche;
	
	private double latitud;

	private double longitud;

	public Hotel() {
		
	}
	
	public Hotel(String nombre, double km_playa,String imagenRecurso,String direccion,
			int estrellas, int valoracion, String descripcion, double latitud, double longitud) {
		this.descripcion = descripcion;
		this.valoracion = valoracion;
		this.estrellas = estrellas;
		this.direccion = direccion;
		this.nombre = nombre;
		this.km_playa = km_playa;
		this.imagenRecurso=imagenRecurso;
		this.latitud=latitud;
		this.longitud=longitud;
	}
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public int getEstrellas() {
		return estrellas;
	}



	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}



	public int getValoracion() {
		return valoracion;
	}



	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public double getKm_playa() {
		return km_playa;
	}



	public void setKm_playa(double km_playa) {
		this.km_playa = km_playa;
	}



	public double getPrecio_medio_noche() {
		return precio_medio_noche;
	}



	public void setPrecio_medio_noche(double precio_medio_noche) {
		this.precio_medio_noche = precio_medio_noche;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}



	@Override
	public String toString() {
		return String.format("Hotel: '%s'\n Direccion: '%s'\n",
				nombre, direccion);
	}
	public String getImagenRecurso() {
		return imagenRecurso;
	}

	public void setImagenRecurso(String imagenRecurso) {
		this.imagenRecurso = imagenRecurso;
	}

}
