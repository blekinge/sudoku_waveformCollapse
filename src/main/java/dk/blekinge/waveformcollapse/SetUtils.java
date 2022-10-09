package dk.blekinge.waveformcollapse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetUtils {


    public static <K> Set<K> union(Set<K> assignments1, Set<K> assignments2) {
        HashSet<K> set = new HashSet<>();
        set.addAll(assignments1);
        set.addAll(assignments2);
        return set;
    }

    public static Set<Particle> hashSet(Particle... particles) {
        return new HashSet<>(Arrays.asList(particles));
    }
}
