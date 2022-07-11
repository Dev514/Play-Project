package models;


import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface UserRepository {
        CompletionStage<User> add(User user);
        CompletionStage<Stream<User>> list();
}
