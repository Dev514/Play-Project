package models;

import play.db.Database;
import play.db.NamedDatabase;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPAUserRepository implements UserRepository {

    @NamedDatabase("user")
    private Database db;
    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;


    @Inject
    public JPAUserRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    @Override
    public CompletionStage<User> add(User user) {
        return supplyAsync(() -> wrap(em -> insert(em, user)), executionContext);
    }

    private User insert(EntityManager em, User user) {
        em.persist(user);

        return user;
    }



    @Override
    public CompletionStage<Stream<User>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }


    private Stream<User> list(EntityManager em) {
        List<User> user = em.createQuery("select u from User u", User.class).getResultList();

        return user.stream();
    }

    @Override
    public void delete(String id) {
         supplyAsync(() -> wrap(em -> delete(em,id)), executionContext);
    }


    private User delete(EntityManager em,String id) {
        User user = em.find(User.class,id);
        em.remove(user);
        return user;
    }



    @Override
    public CompletionStage<User> update(User user) {
        return supplyAsync(() -> wrap(em -> update(em, user)), executionContext);
    }

    private User update(EntityManager em, User user) {
        User tempUser = em.find(User.class,user.getId());
        tempUser.setName(user.getName());
        tempUser.setPassword(user.getPassword());
        em.persist(tempUser);
        return user;
    }
}
