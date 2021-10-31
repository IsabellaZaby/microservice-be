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

    public List<String> readAllDistinct() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT DISTINCT s.sensor_id FROM Sensor s";
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

    public Sensor readSensor(String id) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT s FROM Sensor s WHERE s.id = :id";
        TypedQuery<Sensor> tp = em.createQuery(query, Sensor.class);
        tp.setParameter("id", Integer.valueOf(id));
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
        String query = "SELECT s FROM Sensor s WHERE s.sensor_id = :sensorId ORDER BY s.timestamp";
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

}
