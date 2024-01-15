package model.exceptions;

public class TooLittleEnergyToReproduceException extends RuntimeException{
    public TooLittleEnergyToReproduceException(){
        super("Podjeto probe rozmnozenia zwierzecie mimo, ze nie ma ono dosc energii");
    }
}
