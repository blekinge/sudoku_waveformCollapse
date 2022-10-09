package dk.blekinge.waveformcollapse.sudokus;

//import com.github.miachm.sods.Color;
//import com.github.miachm.sods.Sheet;
//import com.github.miachm.sods.SpreadSheet;
//import com.github.miachm.sods.Style;
import dk.blekinge.waveformcollapse.Entanglement;
import dk.blekinge.waveformcollapse.Particle;
import dk.blekinge.waveformcollapse.Value;
import org.apache.commons.lang3.Range;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Sudoku9x9 extends Sudoku{

    public Sudoku9x9() {

        Value v1 = new Value("1");
        Value v2 = new Value("2");
        Value v3 = new Value("3");
        Value v4 = new Value("4");
        Value v5 = new Value("5");
        Value v6 = new Value("6");
        Value v7 = new Value("7");
        Value v8 = new Value("8");
        Value v9 = new Value("9");
        allPossibleValues = Set.of(v1, v2, v3, v4, v5, v6, v7, v8, v9);


        //column1
        Particle a1 = p("a1"), b1 = p("b1"), c1 = p("c1");
        Particle a2 = p("a2"), b2 = p("b2"), c2 = p("c2");
        Particle a3 = p("a3"), b3 = p("b3"), c3 = p("c3");
        Particle a4 = p("a4"), b4 = p("b4"), c4 = p("c4");
        Particle a5 = p("a5"), b5 = p("b5"), c5 = p("c5");
        Particle a6 = p("a6"), b6 = p("b6"), c6 = p("c6");
        Particle a7 = p("a7"), b7 = p("b7"), c7 = p("c7");
        Particle a8 = p("a8"), b8 = p("b8"), c8 = p("c8");
        Particle a9 = p("a9"), b9 = p("b9"), c9 = p("c9");


        Particle d1 = p("d1"), e1 = p("e1"), f1 = p("f1");
        Particle d2 = p("d2"), e2 = p("e2"), f2 = p("f2");
        Particle d3 = p("d3"), e3 = p("e3"), f3 = p("f3");
        Particle d4 = p("d4"), e4 = p("e4"), f4 = p("f4");
        Particle d5 = p("d5"), e5 = p("e5"), f5 = p("f5");
        Particle d6 = p("d6"), e6 = p("e6"), f6 = p("f6");
        Particle d7 = p("d7"), e7 = p("e7"), f7 = p("f7");
        Particle d8 = p("d8"), e8 = p("e8"), f8 = p("f8");
        Particle d9 = p("d9"), e9 = p("e9"), f9 = p("f9");

        Particle g1 = p("g1"), h1 = p("h1"), i1 = p("i1");
        Particle g2 = p("g2"), h2 = p("h2"), i2 = p("i2");
        Particle g3 = p("g3"), h3 = p("h3"), i3 = p("i3");
        Particle g4 = p("g4"), h4 = p("h4"), i4 = p("i4");
        Particle g5 = p("g5"), h5 = p("h5"), i5 = p("i5");
        Particle g6 = p("g6"), h6 = p("h6"), i6 = p("i6");
        Particle g7 = p("g7"), h7 = p("h7"), i7 = p("i7");
        Particle g8 = p("g8"), h8 = p("h8"), i8 = p("i8");
        Particle g9 = p("g9"), h9 = p("h9"), i9 = p("i9");

        index = new Particle[][] {{a1, b1, c1, d1, e1, f1, g1, h1, i1},
                              {a2, b2, c2, d2, e2, f2, g2, h2, i2},
                              {a3, b3, c3, d3, e3, f3, g3, h3, i3},
                              {a4, b4, c4, d4, e4, f4, g4, h4, i4},
                              {a5, b5, c5, d5, e5, f5, g5, h5, i5},
                              {a6, b6, c6, d6, e6, f6, g6, h6, i6},
                              {a7, b7, c7, d7, e7, f7, g7, h7, i7},
                              {a8, b8, c8, d8, e8, f8, g8, h8, i8},
                              {a9, b9, c9, d9, e9, f9, g9, h9, i9}
        };
        allParticles = Arrays.stream(index).flatMap(Arrays::stream).collect(Collectors.toSet());


        allEntanglements = Set.of(
                //Boxes
                new Entanglement(allParticles, Range.between("a","c"), Range.between("1","3")),
                new Entanglement(allParticles, List.of("a", "b", "c"), List.of("4", "5", "6")),
                new Entanglement(allParticles, List.of("a", "b", "c"), List.of("7", "8", "9")),

                new Entanglement(allParticles, List.of("d", "e", "f"), List.of("1", "2", "3")),
                new Entanglement(allParticles, List.of("d", "e", "f"), List.of("4", "5", "6")),
                new Entanglement(allParticles, List.of("d", "e", "f"), List.of("7", "8", "9")),

                new Entanglement(allParticles, List.of("g", "h", "i"), List.of("1", "2", "3")),
                new Entanglement(allParticles, List.of("g", "h", "i"), List.of("4", "5", "6")),
                new Entanglement(allParticles, List.of("g", "h", "i"), List.of("7", "8", "9")),

                //Rows
                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("1")),
                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("2")),
                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("3")),

                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("4")),
                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("5")),
                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("6")),

                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("7")),
                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("8")),
                new Entanglement(allParticles, List.of("a", "b", "c", "d", "e", "f", "g", "h", "i"), List.of("9")),

                //Columns
                new Entanglement(allParticles, List.of("a"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")),
                new Entanglement(allParticles, List.of("b"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")),
                new Entanglement(allParticles, List.of("c"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")),

                new Entanglement(allParticles, List.of("d"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")),
                new Entanglement(allParticles, List.of("e"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")),
                new Entanglement(allParticles, List.of("f"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")),

                new Entanglement(allParticles, List.of("g"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")),
                new Entanglement(allParticles, List.of("h"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")),
                new Entanglement(allParticles, List.of("i"), List.of("1", "2", "3", "4", "5", "6", "7", "8", "9"))
                                                   );

    }

    /*

    public void readOds(Path odsFile) throws IOException {
        SpreadSheet spread = new SpreadSheet(odsFile.toFile());

        List<Sheet> sheets = spread.getSheets();

        Sheet sheet = spread.getSheet("sudoku");
        com.github.miachm.sods.Range range = sheet.getDataRange();
        List<Integer> rows = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> columns = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        for (Integer row : rows) {
            for (Integer column : columns) {
                com.github.miachm.sods.Range cell = range.getCell(row, column);
                s(getIndex()[row][column], toValue(cell.getValue()));
            }
        }
    }


    private Value toValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double) {
            Double d = (Double) value;
            return new Value("" + d.intValue());
        }
        return new Value("" + (value.toString()));
    }

    public void writeOds(Path odsFile) throws IOException {
        SpreadSheet spread = new SpreadSheet(odsFile.toFile());

        Style style1 = new Style();
        style1.setTextAligment(Style.TEXT_ALIGMENT.Center);


        Style style2 = new Style();
        style2.setTextAligment(Style.TEXT_ALIGMENT.Center);
        style2.setFontColor(new Color(0, 255, 0));

        Sheet sheet = new Sheet("solution");
        sheet.appendColumns(9);
        sheet.appendRows(9);
        sheet.setColumnWidths(0, 9, 4.52);
        sheet.setRowHeights(0, 9, 4.62);

        com.github.miachm.sods.Range range = sheet.getRange(0, 0, 9, 9);
        List<Integer> rows = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> columns = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        for (Integer row : rows) {
            for (Integer column : columns) {
                var cell = range.getCell(row, column);
                Particle p = getIndex()[row][column];
                if (p.getValue() != null) {
                    cell.setValue(p.getValue().getValue());
                    if (p.getDerived()) {
                        cell.setStyle(style2);
                    } else {
                        cell.setStyle(style1);
                    }
                }


            }
        }

        spread.appendSheet(sheet);

        spread.save(odsFile.toFile());
    }
    */
}
