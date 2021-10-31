package at.fhburgenland.models;

public class DBError extends Exception {

    public DBError(String errorMessage) {
        super(errorMessage);
    }

}
