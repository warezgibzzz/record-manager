package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.dao.*;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.model.*;
import ru.gitolite.recordmanager.service.StateManager;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class MenuAction implements Action {
    private final Map<String, Action> CRUDServiceMap;

    public MenuAction() {
        CRUDServiceMap = new HashMap<String, Action>();

        DaoInterface<Author> authors = new AuthorDao();
        DaoInterface<Book> books = new BookDao();
        DaoInterface<Category> categories = new CategoryDao();
        DaoInterface<Country> countries = new CountryDao();
        DaoInterface<Tag> tags = new TagDao();
        DaoInterface<University> universities = new UniversityDao();
        DaoInterface<User> users = new UserDao();

        CRUDServiceMap.put("authors", (new ListAction<>(authors)));
        CRUDServiceMap.put("books", (new ListAction<>(books)));
        CRUDServiceMap.put("categories", (new ListAction<>(categories)));
        CRUDServiceMap.put("countries", (new ListAction<>(countries)));
        CRUDServiceMap.put("tags", (new ListAction<>(tags)));
        CRUDServiceMap.put("universities", (new ListAction<>(universities)));
        CRUDServiceMap.put("users", (new ListAction<>(users)));
    }

    @Override
    public void apply() {
        Map<String, Action> actionMap = StateManager.listContextActions(CRUDServiceMap);
        StateManager.setActions(actionMap);
        StateManager.getState().replace("entity", null);
        listActions();
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }

    protected void listActions() {
        System.out.println("You are in menu. Allowed commands are:");
        for (Map.Entry<String, Action> entry : StateManager.getActions().entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }
    }

    @Override
    public String toString() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Go back to menu";
    }
}
