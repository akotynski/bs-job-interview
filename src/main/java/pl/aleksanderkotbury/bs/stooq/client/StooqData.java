package pl.aleksanderkotbury.bs.stooq.client;

import java.util.Objects;

public class StooqData {

    private final String name;
    private final Double value;

    public StooqData(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StooqData stooqData = (StooqData) o;
        return Objects.equals(name, stooqData.name) &&
                Objects.equals(value, stooqData.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "StooqData{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
