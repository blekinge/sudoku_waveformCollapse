package dk.blekinge.waveformcollapse;

import dk.blekinge.waveformcollapse.sudokus.Sudoku;
import dk.blekinge.waveformcollapse.sudokus.Sudoku4x4;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Solver4x4Test {


    @Test
    void solve4x4() {

        Sudoku situation = new Sudoku4x4();

        situation.s("a", "3", Sudoku4x4.V_3);
        situation.s("a", "4", Sudoku4x4.V_4);
        situation.s("d", "1", Sudoku4x4.V_4);
        situation.s("d", "2", Sudoku4x4.V_2);

        System.out.println("Initial");
        System.out.println(situation);

        Solver solver = new Solver();
        Situation solved = solver.solve(situation);
        System.out.println("\nSolved");
        System.out.println(solved);

        Assertions.assertEquals(
                "'\u001B[32m1\u001B[0m', '\u001B[32m1\u001B[0m', '\u001B[32m2\u001B[0m', '\u001B[32m3\u001B[0m', \n" +
                "'4', '\u001B[32m1\u001B[0m', '\u001B[32m3\u001B[0m', '3', \n" +
                "'\u001B[32m3\u001B[0m', '\u001B[32m4\u001B[0m', '\u001B[32m4\u001B[0m', '\u001B[32m2\u001B[0m', \n" +
                "'\u001B[32m1\u001B[0m', '4', '2', '\u001B[32m2\u001B[0m', ",
                solved.toString());
    }

}