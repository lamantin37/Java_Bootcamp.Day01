package day01.ex05;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String massage){
        super(massage);
    }
}
