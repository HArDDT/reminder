package ua.tihiy.reminder.entities;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class SimplifiedUser implements Serializable {

    private int id;
    private String email;
    private String name;

    public SimplifiedUser(int id, @NotNull String email, @NotNull String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
