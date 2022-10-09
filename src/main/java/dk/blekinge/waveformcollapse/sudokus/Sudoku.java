package dk.blekinge.waveformcollapse.sudokus;

import dk.blekinge.waveformcollapse.Particle;
import dk.blekinge.waveformcollapse.Situation;
import dk.blekinge.waveformcollapse.Value;
import lombok.Getter;
import lombok.NonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Getter
public abstract class Sudoku extends Situation {

    @NonNull
    protected Particle[][] index;

    protected Particle p(String name) {
        return new Particle(name, new HashSet<>(allPossibleValues));
    }

    public void s(Particle p, Value v) {
        if (v != null) {
            p.setValue(v);
            p.setDerived(false);
        }
    }

    public void s(String column, String row, Value v) {
        Particle p = allParticles.stream()
                                 .filter(p2 -> Objects.equals(p2.getRow(), row))
                                 .filter(p2 -> Objects.equals(p2.getColumn(), column))
                                 .findFirst()
                                 .orElseThrow(() -> new IndexOutOfBoundsException("Index " +
                                                                                  row +
                                                                                  "," +
                                                                                  column +
                                                                                  " out of bounds"));
        s(p, v);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        long width = Math.round(Math.sqrt(allParticles.size()));
        long boxWidth = Math.round(Math.sqrt(Math.sqrt(allParticles.size())));
        int line = 0;
        int i = 0;
        List<Particle> particles = allParticles.stream()
                                               .sorted(Comparator.comparing(Particle::getRow)
                                                                 .thenComparing(Particle::getColumn))
                                               .toList();
        for (Particle particle : particles) {
            if (i % width == 0) {
                result.append("\n");
                line++;
                if ((line) % boxWidth == 1) {
                    result.append(LongStream.range(0, (3 * boxWidth + 2 * (boxWidth - 1) + 1) * boxWidth)
                                            .mapToObj(l -> "-")
                                            .collect(Collectors.joining()))
                          .append("\n");
                }
            }
            String finish = ", ";
            if ((i + 1) % boxWidth == 0) {
                finish = "|";
            }
            Value value = particle.getValue();
            if (value == null) {
                result.append("' '");
            } else {

                result.append("'");
                if (particle.getDerived()) {
                    result.append(ANSI_GREEN).append(particle.getValue().getValue()).append(ANSI_RESET);
                } else {
                    result.append(particle.getValue().getValue());
                }
                result.append("'");
            }
            result.append(finish);
            i++;
        }
        return result.toString().trim();
    }

}
