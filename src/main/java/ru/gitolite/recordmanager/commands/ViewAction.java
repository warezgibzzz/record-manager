package ru.gitolite.recordmanager.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import ru.gitolite.recordmanager.dao.DaoInterface;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.service.StateManager;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;

public class ViewAction<T> extends EntityAction<T> {
    private final DaoInterface<T> dao;

    public ViewAction(DaoInterface<T> dao) {
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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        String json = null;

        try {
            T object = this.getEntity(searchVal);

            if (object != null) {
                json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);

                System.out.println(json);
            } else {
                System.out.println("No such record");
            }
        } catch (JsonProcessingException | NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "view";
    }

    @Override
    public String getDescription() {
        return "Shows selected " + dao.toString() + " data by id or by name";
    }
}
