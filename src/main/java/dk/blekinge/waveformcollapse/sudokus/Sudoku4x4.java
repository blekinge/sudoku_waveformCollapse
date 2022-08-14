package dk.blekinge.waveformcollapse.sudokus;

import dk.blekinge.waveformcollapse.Entanglement;
import dk.blekinge.waveformcollapse.Particle;
import dk.blekinge.waveformcollapse.Value;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Sudoku4x4 extends Sudoku {

    public static final Value V_1 = new Value("1");
    public static final Value V_2 = new Value("2");
    public static final Value V_3 = new Value("3");
    public static final Value V_4 = new Value("4");

    public Sudoku4x4() {
        allPossibleValues = Set.of(V_1, V_2, V_3, V_4);

        Particle a1 = p("a1"), b1 = p("b1"), c1 = p("c1"), d1 = p("d1");
        Particle a2 = p("a2"), b2 = p("b2"), c2 = p("c2"), d2 = p("d2");
        Particle a3 = p("a3"), b3 = p("b3"), c3 = p("c3"), d3 = p("d3");
        Particle a4 = p("a4"), b4 = p("b4"), c4 = p("c4"), d4 = p("d4");

        //allParticles = ListOrderedSet.listOrderedSet(List.of(a1, b1, c1, d1,
        //                                                     a2, b2, c2, d2,
        //                                                     a3, b3, c3, d3,
        //                                                     a4, b4, c4, d4));

        index = new Particle[][]{{a1, b1, c1, d1},
                              {a2, b2, c2, d2},
                              {a3, b3, c3, d3},
                              {a4, b4, c4, d4}
        };
        allParticles = Arrays.stream(index).flatMap(Arrays::stream).collect(Collectors.toSet());

        allEntanglements = Set.of(
                //Boxes
                new Entanglement(a1, b1,
                                 a2, b2),
                new Entanglement(c1, d1,
                                 c2, d2
                ),
                new Entanglement(a3, b3,
                                 a4, b4
                ),
                new Entanglement(c3, d3,
                                 c4, d4
                ),
                //Rows
                new Entanglement(index[0]),
                new Entanglement(index[1]),
                new Entanglement(index[2]),
                new Entanglement(index[3]),
                //Columns
                new Entanglement(a1,
                                 a2,
                                 a3,
                                 a4
                ),
                new Entanglement(b1,
                                 b2,
                                 b3,
                                 b4
                ),
                new Entanglement(c1,
                                 c2,
                                 c3,
                                 c4
                ),
                new Entanglement(d1,
                                 d2,
                                 d3,
                                 d4)
                                 );

    }



}
