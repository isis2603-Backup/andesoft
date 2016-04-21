/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package andesoft.persistence;

import andesoft.entities.ItinerarioEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(Arquillian.class)
public class ItinerarioPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ItinerarioEntity.class.getPackage())
                .addPackage(PuntoInteresPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private ItinerarioPersistence itinerarioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private final PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() 
    {
        em.createQuery("delete * from ItinerarioEntity").executeUpdate();
    }

    private List<ItinerarioEntity> data = new ArrayList<>();

    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
            ItinerarioEntity entity = factory.manufacturePojo(ItinerarioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createItinerarioTest() 
    {
        ItinerarioEntity newEntity = factory.manufacturePojo(ItinerarioEntity.class);
        ItinerarioEntity result = itinerarioPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ItinerarioEntity entity = em.find(ItinerarioEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    @Test
    public void getItinerariosTest() 
    {
        List<ItinerarioEntity> list = itinerarioPersistence.findAll(Long.parseLong("0"));
        //Assert.assertEquals(data.size(), list.size());
        for (ItinerarioEntity ent : list) {
            ItinerarioEntity actual = null;
            for (ItinerarioEntity entityy : data) {
                if (ent.getId().equals(entityy.getId())) {
                    actual = entityy;
                }
            }
            Assert.assertEquals(actual.getUsuario(),0);
        }
    }

    @Test
    public void getItinerarioTest()
    {
        ItinerarioEntity entity = data.get(0);
        ItinerarioEntity newEntity = itinerarioPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getFechaIni(), newEntity.getFechaIni());
        Assert.assertEquals(entity.getFechaFin(), newEntity.getFechaFin());
    }

    @Test
    public void deleteItinerarioTest() 
    {
        ItinerarioEntity entity = data.get(0);
        itinerarioPersistence.delete(entity.getId());
        ItinerarioEntity deleted = em.find(ItinerarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateItinerarioTest() {
        ItinerarioEntity entity = data.get(0);
        ItinerarioEntity newEntity = factory.manufacturePojo(ItinerarioEntity.class);

        newEntity.setId(entity.getId());

        itinerarioPersistence.update(newEntity);

        ItinerarioEntity resp = em.find(ItinerarioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }
}

