package day01.ex01;

public class UserIdsGenerator {
    private static UserIdsGenerator generator;
    private static int identifier = 0;

    private UserIdsGenerator() {}
    public static UserIdsGenerator getInstance() {
        return generator = generator == null ? new UserIdsGenerator(): generator;
    }
    public int generateId() { return identifier++; }
}
