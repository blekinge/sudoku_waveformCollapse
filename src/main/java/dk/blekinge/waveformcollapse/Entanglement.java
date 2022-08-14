package dk.blekinge.waveformcollapse;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@lombok.Value
public class Entanglement {

    Set<Particle> particles;


    public Entanglement(Particle... particles) {
        this.particles = SetUtils.hashSet(particles);
    }
    public Entanglement(Set<Particle> allParticles, List<String> columns, List<String> rows){
        particles = allParticles.stream()
                .filter(particle -> columns.contains(particle.getColumn()))
                .filter(particle -> rows.contains(particle.getRow()))
                .collect(Collectors.toSet());
    }

    public Entanglement(Set<Particle> allParticles, Range<String> columns, Range<String> rows){
        particles = allParticles.stream()
                                .filter(particle -> columns.contains(particle.getColumn()))
                                .filter(particle -> rows.contains(particle.getRow()))
                                .collect(Collectors.toSet());
    }

    public Set<Assignment> valueAssigned(Value value, Set<Value> allPossibleValues) {

        Set<Assignment> assignments1 = particles.stream()
                                                .map(p -> p.restrictValue(value))
                                                .filter(Optional::isPresent)
                                                .map(Optional::get)
                                                .collect(Collectors.toSet());

        allPossibleValues.removeAll(particles.stream()
                                             .map(Particle::getValue)
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.toSet()));
        Map<Value, Set<Particle>> map = particles.stream()
                                                 .filter(particle -> particle.getValue() == null)
                                                 .flatMap(particle -> particle.getPossibleValues()
                                                                              .stream()
                                                                              .filter(allPossibleValues::contains)
                                                                              .map(v -> Pair.of(v, particle)))
                                                 .collect(Collectors.toMap(Pair::getKey,
                                                                           pair -> Set.of(pair.getValue()),
                                                                           SetUtils::union));
        Set<Assignment> assignments2 = map.entrySet().stream()
                                          .filter(entry -> entry.getValue().size() == 1)
                                          .map(entry -> Pair.of(entry.getKey(),
                                                                entry.getValue()
                                                                     .stream()
                                                                     .findFirst()
                                                                     .orElse(null)))
                                          .filter(pair -> pair.getValue() != null)
                                          .map(entry -> Assignment.builder()
                                                                  .value(entry.getKey())
                                                                  .particle(entry.getValue())
                                                                  .build())
                                          .collect(Collectors.toSet());
        return SetUtils.union(assignments1, assignments2);
    }
}
