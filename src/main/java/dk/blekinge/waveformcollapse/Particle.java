package dk.blekinge.waveformcollapse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@ToString
public class Particle {

    String column;
    String row;

    Value value = null;

    transient Boolean derived = null;

    transient Set<Value> possibleValues = Set.of();

    public Particle(String name, Set<Value> possibleValues) {
        this.column = name.replaceAll("\\d","");
        this.row = name.replaceAll("[a-z]","");
        this.possibleValues = possibleValues;
    }

    public Optional<Assignment> restrictValue(Value value) {
        if (this.value == null) {
            boolean removed = possibleValues.remove(value);
            if (removed) {
                if (possibleValues.size() == 1) {
                    Value lastValue = possibleValues.stream().findFirst().get();
                    return Optional.of(Assignment.builder()
                                                 .particle(this)
                                                 .value(lastValue)
                                                 .build());
                }
            }
        }
        return Optional.empty();
    }

}
