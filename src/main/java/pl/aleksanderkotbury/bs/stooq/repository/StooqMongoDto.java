package pl.aleksanderkotbury.bs.stooq.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

public class StooqMongoDto {

    @Id
    private final String name;
    private final Double value;
    private final LocalDateTime updateTime;

    public StooqMongoDto(@JsonProperty("name") String name,
                         @JsonProperty("value") Double value,
                         @JsonProperty("updateTime") LocalDateTime updateTime) {
        this.name = name;
        this.value = value;
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StooqMongoDto that = (StooqMongoDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, updateTime);
    }

    @Override
    public String toString() {
        return "StooqMongoDto{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", updateTime=" + updateTime +
                '}';
    }
}
