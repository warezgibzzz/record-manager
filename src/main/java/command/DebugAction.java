package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import service.StateManager;

public class DebugAction implements Action {
    private final ObjectMapper mapper;

    public DebugAction() {
        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    private void debug(Object data) throws JsonProcessingException {
        String json = mapper.writeValueAsString(data);
        System.out.println(json);
    }

    @Override
    public void apply() {
        try {
            debug(StateManager.getState());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    
    public void apply(String[] args) throws JsonProcessingException {
        for (String arg : args) {
            debug(StateManager.getState().get(arg));
        }
    }
}
