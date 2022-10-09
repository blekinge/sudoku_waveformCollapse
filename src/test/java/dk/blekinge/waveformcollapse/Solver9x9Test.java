package dk.blekinge.waveformcollapse;
//
//import com.github.miachm.sods.Color;
//import com.github.miachm.sods.Range;
//import com.github.miachm.sods.Sheet;
//import com.github.miachm.sods.SpreadSheet;
//import com.github.miachm.sods.Style;
//import dk.blekinge.waveformcollapse.sudokus.Sudoku;
import dk.blekinge.waveformcollapse.sudokus.Sudoku9x9;
//import org.apache.commons.collections4.set.ListOrderedSet;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver9x9Test {

    @Test
    void solve() throws URISyntaxException, IOException {

        Sudoku9x9 situation = new Sudoku9x9();

        //https://www.websudoku.com/?level=1&set_id=7490854017
        Path odsFile = Path.of(Thread.currentThread().getContextClassLoader().getResource("sampleSudoku2.ods").toURI());
        //situation.readOds(odsFile);

        Situation solved = new Solver().solve(situation);


        //situation.writeOds(odsFile);
        //System.out.println(solved);
    }



}