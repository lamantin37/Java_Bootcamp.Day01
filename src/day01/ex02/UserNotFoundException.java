package day01.ex02;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String massage){
        super(massage);
    }
}
