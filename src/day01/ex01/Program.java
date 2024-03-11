package day01.ex01;

public class Program {
    public static void main(String[] args) {
        User user;
        for (int i = 0; i < 21; i++) {
            user = new User("User" + (i + 1), (i + 1000) * 2 / 5);
            System.out.println(user);
        }
    }
}
