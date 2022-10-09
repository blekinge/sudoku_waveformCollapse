package dk.blekinge.waveformcollapse;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class Solver {


    Queue<Assignment> workQueue = new ConcurrentLinkedDeque<>();


    public Situation solve(Situation situation) {
        //reset possible values as we have to start fresh here
        situation.getAllParticles().forEach(p -> p.setPossibleValues(new HashSet<>(situation.getAllPossibleValues())));
        return solve(situation, loadInitialWork(situation));
    }

    public Situation solve(Situation situation, Assignment newAssignment) {
        return solve(situation, List.of(newAssignment));
    }

    public Situation solve(Situation situation, List<Assignment> newAssignment) {
        workQueue.addAll(newAssignment);
        while (!workQueue.isEmpty()) {
            Assignment assignment = workQueue.poll();

            Collection<Assignment> newAssignments = oneStep(assignment, situation);
            workQueue.addAll(newAssignments);
        }

        return situation;
    }

    private Collection<Assignment> oneStep(Assignment assignment, Situation situation) {
        Particle particle = assignment.getParticle();
        if (particle.getValue() != null && particle.getDerived()) {
            return Set.of();
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

        return relevantEntanglements.stream().flatMap(ent -> ent.valueAssigned(value,
                                                                               new HashSet<>(
                                                                                       situation.getAllPossibleValues()))
                                                                .stream())
                                    .collect(Collectors.toSet());
    }

    private List<Assignment> loadInitialWork(Situation situation) {
        return situation.getAllParticles().stream()
                        .filter(p -> Objects.nonNull(p.value))
                        .filter(particle1 -> !particle1.getDerived())
                        .map(p -> Assignment.builder()
                                               .particle(p)
                                               .value(p.getValue())
                                               .build())
                        .collect(Collectors.toList());
    }

}
