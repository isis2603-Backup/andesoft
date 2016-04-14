/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package andesoft.persistence;

import andesoft.entities.UsuarioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author js.arciniegas10
 */
public class UsuarioPersistence {
 private static final Logger logger = Logger.getLogger(UsuarioPersistence.class.getName());

    @PersistenceContext(unitName = "BookStorePU")
    protected EntityManager em;

    public UsuarioEntity create(UsuarioEntity entity) {
        logger.info("Creando un usuario nuevo");
        em.persist(entity);
        logger.info("Usuario creado");
        return entity;
    }

    public UsuarioEntity update(UsuarioEntity entity) {
        logger.log(Level.INFO, "Actualizando usuario con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        logger.log(Level.INFO, "Borrando usuario con id={0}", id);
        UsuarioEntity entity = em.find(UsuarioEntity.class, id);
        em.remove(entity);
    }

    public UsuarioEntity find(Long id) {
        logger.log(Level.INFO, "Consultando usuario con id={0}", id);
        return em.find(UsuarioEntity.class, id);
    }

    public List<UsuarioEntity> findAll() {
        logger.info("Consultando todos los usuarios");
        Query q = em.createQuery("select u from UsuarioEntity u");
        return q.getResultList();
    }
}
