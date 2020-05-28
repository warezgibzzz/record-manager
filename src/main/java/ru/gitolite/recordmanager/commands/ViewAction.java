package ru.gitolite.recordmanager.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.gitolite.recordmanager.dao.DaoInterface;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.service.StateManager;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ViewAction<T> implements Action {
    private final DaoInterface<T> dao;

    public ViewAction(DaoInterface<T> dao) {
        this.dao = dao;
    }

    @Override
    public void apply() throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        String searchVal = String.join(" ", (String[])args);
        Map<String, Action> actionMap = new HashMap<String, Action>();
        actionMap.put("list", new ListAction<>(dao));
        actionMap.put("view", new ViewAction<>(dao));
        StateManager.setActions(StateManager.viewContextActions(actionMap));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            Optional<T> objectById = Optional.empty();
            try {
                objectById = dao.findOneBy("id", searchVal);
            } catch (NumberFormatException ignored) {}
            Optional<T> objectByName = dao.findOneBy("name", searchVal);

            T object = null;
            if (objectById.isPresent()) {
                object = objectById.get();
            } else if (objectByName.isPresent()) {
                object = objectByName.get();
            }
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
}
