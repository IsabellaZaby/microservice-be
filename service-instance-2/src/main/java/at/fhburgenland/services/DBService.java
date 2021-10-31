package at.fhburgenland.services;

import at.fhburgenland.models.DBError;
import at.fhburgenland.models.Sensor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class DBService {

    private static DBService instance;
    private final EntityManagerFactory emf;

    private DBService() {
        emf = Persistence.createEntityManagerFactory("sensors");
    }

    public static DBService getInstance() {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    public void addSensor(Sensor sensor) throws DBError {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = em.getTransaction();
            entityTransaction.begin();
            em.persist(sensor);
            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DBError("Adding of sensor failed, try again.");
        } finally {
            em.close();
        }
    }

    public List<Sensor> readAll() throws DBError {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT s FROM Sensor s ORDER BY s.id";
        TypedQuery<Sensor> tp = em.createQuery(query, Sensor.class);
        List<Sensor> sensorList = null;
        try {
            sensorList = tp.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBError("Reading of data failed.");
        } finally {
            em.close();
        }
        return sensorList;
    }

    public void updateSensor(Integer id, LocalDateTime timestamp, Double temperature, Double humidity) throws DBError {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            Sensor sensor = em.find(Sensor.class, id);

            if (sensor != null) {
                sensor.setTimestamp(timestamp);
                sensor.setTemperature(temperature);
                sensor.setHumidity(humidity);
            }

            em.persist(sensor);
            et.commit();
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            throw new DBError("Update of sensor failed, try again.");
        } finally {
            em.close();
        }
    }

    public void deleteSensor(Integer id) throws DBError {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = em.getTransaction();
            entityTransaction.begin();
            Sensor sensor = em.find(Sensor.class, id);
            em.remove(sensor);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DBError("Deletion of sensor failed, try again.");
        } finally {
            em.close();
        }
    }

}
