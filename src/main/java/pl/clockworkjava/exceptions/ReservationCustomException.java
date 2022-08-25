package pl.clockworkjava.exceptions;

abstract public class ReservationCustomException extends RuntimeException{
    ReservationCustomException(String message) {
        super(message);
    }

    abstract public int getCode();
}
