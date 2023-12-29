package model.exceptions;

public class SimulationWindowCreationException extends RuntimeException{
    public SimulationWindowCreationException(){
        super("Nie udalo sie stworzyc symulacji");
    }
}
