package com.myl.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una expeción lanzada desde la capa de negocio
 * 
 * @version 1.0 - 02/06/14
 * @author Israel Gómez Marbán
 */
public class BusinessException extends Exception {

	/**
	 * Lista de mensajes contenidos en la excepción
	 */
	private final List<String> mensajes = new ArrayList<String>();

	/**
	 * Identificador autogenerado para la serialización
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto Business Exception
	 */
	public BusinessException() {
	}

	/**
	 * Agrega un mensaje a la lista
	 * 
	 * @param {@link String} key: Llave con la que es identificada el mensaje a
	 *        agregar
	 */
	public void addMessage(String key) {
		mensajes.add(key);
	}

	/**
	 * Obtiene la lista de mensajes
	 * 
	 * @return {@link List} Lista de mensajes
	 */
	public List<String> getMensajes() {
		return mensajes;
	}

}