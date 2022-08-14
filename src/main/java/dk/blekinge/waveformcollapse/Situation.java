package dk.blekinge.waveformcollapse;

import lombok.Getter;

import java.util.Objects;
import java.util.Set;

@Getter
public abstract class Situation {
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_GREEN = "\u001B[32m";
    protected Set<Particle> allParticles;

    protected Set<Entanglement> allEntanglements;

    protected Set<Value> allPossibleValues;

    public boolean isSolved() {
        return allParticles.stream().allMatch(p -> Objects.nonNull(p.getValue()));
    }


}
