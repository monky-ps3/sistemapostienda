/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;



import java.sql.SQLException;
import java.util.List;

/**
 * SistemaPOSTienda
 *Interfaz genérica para las operaciones CRUD.
 * @param <T>  Tipo de entidad.
 * @param <ID> Tipo de la llave primaria.
 *
 */


public interface GenericDAO<T, ID> {

    /**
     * Inserta una nueva entidad.
     *
     * @param entidad Entidad a insertar.
     * @return true si la operación fue exitosa.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    boolean insertar(T entidad) throws SQLException;

    /**
     * Actualiza una entidad existente.
     *
     * @param entidad Entidad a actualizar.
     * @return true si la operación fue exitosa.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    boolean actualizar(T entidad) throws SQLException;

    /**
     * Elimina una entidad por su identificador.
     *
     * @param id Identificador de la entidad.
     * @return true si la operación fue exitosa.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    boolean eliminar(ID id) throws SQLException;

    /**
     * Busca una entidad por su identificador.
     *
     * @param id Identificador de la entidad.
     * @return Entidad encontrada o null si no existe.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    T buscarPorId(ID id) throws SQLException;

    /**
     * Obtiene todas las entidades.
     *
     * @return Lista de entidades.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    List<T> listar() throws SQLException;

}