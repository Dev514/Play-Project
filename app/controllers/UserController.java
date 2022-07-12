package controllers;

import models.User;
import models.UserRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;
import static play.mvc.Results.ok;

public class UserController {


    private final FormFactory formFactory;

    private final UserRepository userRepository;
    private final HttpExecutionContext ec;


    @Inject
    public UserController(FormFactory formFactory, UserRepository userRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.userRepository = userRepository;
        this.ec = ec;
    }

    public play.mvc.Result index() {
        return ok(views.html.index.render());
    }

    public CompletionStage<Result> addUser(final Http.Request request) {
        User person = formFactory.form(User.class).bindFromRequest(request).get();
        return userRepository
                .add(person)
                .thenApplyAsync(p -> ok(
                //        Json.toJson(p)
                        "Created"
                ), ec.current());
    }

    public CompletionStage<Result> getUsers() {
        return userRepository
                .list()
                .thenApplyAsync(personStream -> ok(toJson(personStream.collect(Collectors.toList()))), ec.current());

    }

    public Result delete(String id) {
         userRepository
                .delete(id);
         return ok("Deleted");
    }

    public CompletionStage<Result> update(final Http.Request request) {
        User person = formFactory.form(User.class).bindFromRequest(request).get();
        return userRepository
                .update(person)
                .thenApplyAsync(p -> ok(
                        toJson(p)
                ), ec.current());
    }
}
