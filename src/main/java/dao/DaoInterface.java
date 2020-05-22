package dao;

import javax.persistence.Entity;
import java.util.Optional;

public interface DaoInterface {
    Optional<?> findOneBy(String param, Object value);
}
