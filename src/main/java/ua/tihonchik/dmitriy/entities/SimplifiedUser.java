package ua.tihonchik.dmitriy.entities;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class SimplifiedUser implements Serializable {

    private Object id;
    private String email;
    private String name;

    public SimplifiedUser(@NotNull Object id, @NotNull String email, @NotNull String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplifiedUser that = (SimplifiedUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
