package controllers;

import com.sun.java.browser.plugin2.liveconnect.v1.Result;
import models.User;
import models.UserRepository;
import play.core.j.HttpExecutionContext;
import play.data.FormFactory;
import play.mvc.Call;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;
import static sun.plugin2.util.PojoUtil.toJson;

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
                .thenApplyAsync(p -> redirect(routes.UserController.index()), ec);
    }

    public CompletionStage<Result> getUsers() {
        return userRepository
                .list()
                .thenApplyAsync(personStream -> ok(toJson(personStream.collect(Collectors.toList()))), ec);
    }
}
