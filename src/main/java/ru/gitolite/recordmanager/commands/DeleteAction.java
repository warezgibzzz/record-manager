package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.dao.DaoInterface;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.service.StateManager;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;

public class DeleteAction<T> extends EntityAction<T> {
    private final DaoInterface<T> dao;

    public DeleteAction(DaoInterface<T> dao) {
        super(dao);
        this.dao = dao;
    }

    @Override
    public void apply() throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        String searchVal = String.join(" ", (String[]) args);
        Map<String, Action> actionMap = new HashMap<String, Action>();
        actionMap.put("list", new ListAction<T>(dao));
        actionMap.put("view", new ViewAction<T>(dao));
        actionMap.put("delete", new DeleteAction<T>(dao));
        StateManager.setActions(StateManager.viewContextActions(actionMap));

        try {
            T object = this.getEntity(searchVal);

            if (object != null) {
                dao.delete(object);
                System.out.println("OK!");
            } else {
                System.out.println("No such record");
            }
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }
}
