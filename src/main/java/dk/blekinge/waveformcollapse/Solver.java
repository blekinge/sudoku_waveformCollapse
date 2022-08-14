package dk.blekinge.waveformcollapse;

import java.util.HashSet;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class Solver {


    Queue<Assignment> workQueue = new ConcurrentLinkedDeque<>();

    public Situation solve(Situation situation) {

        Set<Particle> allParticles = situation.getAllParticles();
        allParticles.stream()
                    .filter(p -> Objects.nonNull(p.value))
                    .filter(particle1 -> !particle1.getDerived())
                    .map(p -> Assignment.builder()
                                        .particle(p)
                                        .value(p.getValue())
                                        .build())
                    .forEach(a -> workQueue.add(a));

        while (!workQueue.isEmpty()) {
            Assignment assignment = workQueue.poll();
            Particle particle = assignment.getParticle();
            if (particle.getValue() != null && particle.getDerived()) {
                continue;
            }
            Value value = assignment.getValue();
            particle.setValue(value);
            if (particle.getDerived() == null) {
                particle.setDerived(true);
            }

            Set<Entanglement> relevantEntanglements = situation.getAllEntanglements().stream()
                                                               .filter(entanglement -> entanglement.getParticles()
                                                                                                   .contains(particle))
                                                               .collect(Collectors.toSet());

            for (Entanglement relevantEntanglement : relevantEntanglements) {
                Set<Assignment> newAssignments = relevantEntanglement.valueAssigned(value,
                                                                                    new HashSet<>(situation.getAllPossibleValues()));
                workQueue.addAll(newAssignments);
            }
        }

        return situation;
    }

}
