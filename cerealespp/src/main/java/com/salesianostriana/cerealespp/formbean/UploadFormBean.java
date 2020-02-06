package com.salesianostriana.cerealespp.formbean;

import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 * Recoge toda la información de un producto, excepto la imagen, en un formulario.
 * @author Esperanza M Escacena M.
 */
@Data
@NoArgsConstructor
public class UploadFormBean {
	
	private String nombre;
	private boolean procesado;
	private double precio;
	private int stock;
	
	

}
