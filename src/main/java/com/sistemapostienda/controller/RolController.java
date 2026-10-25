/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

/**
 *
 * @author monky Controlador encargado de gestionar las operaciones relacionadas
 * con los roles del sistema.
 *
 */
import com.sistemapostienda.model.Rol;
import com.sistemapostienda.service.RolService;
import java.util.List;
/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Controller encargado de gestionar las operaciones
 * relacionadas con los roles del sistema.
 *
 * Se comunica con:
 *
 * Controller
 *      |
 *      ↓
 * RolService
 *      |
 *      ↓
 * RolDAO
 *
 * ============================================================
 */
public class RolController {

    private final RolService rolService;

    /**
     * Constructor.
     */
    public RolController() {

        this.rolService = new RolService();

    }
    /**
     * Registra un nuevo rol.
     *
     * @param rol Objeto rol.
     *
     * @return true si fue creado correctamente.
     */
    public boolean crear(Rol rol) {


        return rolService.guardar(rol);

    }
    /**
     * Actualiza la información de un rol.
     *
     * @param rol Rol con los cambios.
     *
     * @return true si fue actualizado.
     */
    public boolean actualizar(Rol rol) {


        return rolService.actualizar(rol);

    }
   /**
     * Elimina un rol.
     *
     * @param id Identificador del rol.
     *
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {


        return rolService.eliminar(id);

    }
    /**
     * Busca un rol mediante su ID.
     *
     * @param id Identificador.
     *
     * @return Rol encontrado.
     */
    public Rol buscarPorId(Long id) {


        return rolService.buscarPorId(id);

    }
    /**
     * Busca un rol mediante su nombre.
     *
     * @param nombre Nombre del rol.
     *
     * @return Rol encontrado.
     */
    public Rol buscarPorNombre(String nombre) {


        return rolService.buscarPorNombre(nombre);

    }
    /**
     * Obtiene todos los roles registrados.
     *
     * @return Lista de roles.
     */
    public List<Rol> listarTodos() {


        return rolService.listarTodos();

    }
    /**
     * Obtiene solamente roles activos.
     *
     * @return Lista de roles activos.
     */
    public List<Rol> listarActivos() {


        return rolService.listarActivos();

    }
    /**
     * Verifica si existe un rol mediante nombre.
     *
     * @param nombre Nombre del rol.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {


        return rolService.existeNombre(nombre);

    }


}