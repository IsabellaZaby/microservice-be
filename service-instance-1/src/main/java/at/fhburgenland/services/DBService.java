package at.fhburgenland.services;

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

    public void addSensor(Sensor sensor) {
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
        } finally {
            em.close();
        }
    }

    public List<String> readAllDistinct() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT DISTINCT s.sensor_id FROM Sensor s ORDER BY s.id";
        TypedQuery<String> tp = em.createQuery(query, String.class);
        List<String> sensorList = null;
        try {
            sensorList = tp.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return sensorList;
    }

    public List<Sensor> readAll() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT s FROM Sensor s ORDER BY s.id";
        TypedQuery<Sensor> tp = em.createQuery(query, Sensor.class);
        List<Sensor> sensorList = null;
        try {
            sensorList = tp.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return sensorList;
    }

    public Sensor readSensor(Integer id) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT s FROM Sensor s WHERE s.id = :id";
        TypedQuery<Sensor> tp = em.createQuery(query, Sensor.class);
        tp.setParameter("id", id);
        Sensor sensor = null;

        try {
            sensor = tp.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return sensor;
    }


    public List<Sensor> readSensorEntries(String sensorId) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT s.temperature, s.humidity, s.timestamp FROM Sensor s WHERE s.sensor_id = :sensorId";
        TypedQuery<Sensor> tp = em.createQuery(query, Sensor.class);
        tp.setParameter("sensorId", sensorId);
        List<Sensor> sensor = null;

        try {
            sensor = tp.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return sensor;
    }

    public void updateSensor(Integer id, LocalDateTime timestamp, Double temperature, Double humidity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Sensor sensor = null;

        try {
            et = em.getTransaction();
            et.begin();

            sensor = readSensor(id);

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
        } finally {
            em.close();
        }
    }

    public void deleteSensor(Sensor sensor) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = em.getTransaction();
            entityTransaction.begin();
            em.remove(sensor);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            em.close();
        }
    }

}
