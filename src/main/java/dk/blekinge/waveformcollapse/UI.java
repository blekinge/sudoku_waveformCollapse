package dk.blekinge.waveformcollapse;

import dk.blekinge.waveformcollapse.sudokus.Sudoku9x9;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UI extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final Solver solver = new Solver();

    Situation situation = new Sudoku9x9();

    Font validValue = Font.font("Arial", FontWeight.NORMAL, 10);
    Font derivedValue = Font.font("Arial", FontWeight.NORMAL, 24);
    Font specifiedValue = Font.font("Arial", FontWeight.BOLD, 24);

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {

        ////https://www.websudoku.com/?level=1&set_id=7490854017
        //Path odsFile = Path.of(Thread.currentThread().getContextClassLoader().getResource("sampleSudoku2.ods").toURI());
        //situation.readOds(odsFile);

        Situation solved = solver.solve(situation);

        Scene scene = new Scene(box9x9(solved));

        stage.setScene(scene);
        stage.show();
    }


    public GridPane box9x9(Situation situation) {

        GridPane grid = new GridPane();

        grid.setBorder(Border.stroke(null));
        grid.setPadding(new Insets(0));


        Set<Particle> particles = situation.getAllParticles();

        List<List<Particle>> boxes = Stream.of( new Pair<>("123", "abc"),
                                                new Pair<>("123", "def"),
                                                new Pair<>("123", "ghi"),
                                                new Pair<>("456", "abc"),
                                                new Pair<>("456", "def"),
                                                new Pair<>("456", "ghi"),
                                                new Pair<>("789", "abc"),
                                                new Pair<>("789", "def"),
                                                new Pair<>("789", "ghi")
                                              )
                                           .map(t -> particles.stream()
                                                              .filter(p -> t.getKey().contains(p.getRow()))
                                                              .filter(p -> t.getValue().contains(p.getColumn()))
                                                              .sorted(Comparator.comparing(Particle::getRow)
                                                                                .thenComparing(Particle::getColumn))
                                                              .collect(Collectors.toList())
                                               ).toList();

        grid.setOnMouseClicked(mouseEvent -> {
            grid.getChildren().removeAll(grid.getChildren());
            populate9x9(grid, boxes);
        });

        populate9x9(grid, boxes);
        makeGrowable(grid);

        return grid;
    }

    private void populate9x9(GridPane grid, List<List<Particle>> boxes) {
        int row = 0, column = 0;
        for (List<Particle> box : boxes) {
            grid.add(box3x3(box), column++, row);
            if (column >= 3) {
                row++;
                column = 0;
            }
        }
    }

    private static void makeGrowable(GridPane grid) {
        for (int i = 0; i < grid.getColumnCount(); i++) {
            final ColumnConstraints columnConstraints = new ColumnConstraints(Control.USE_PREF_SIZE,
                                                                              Control.USE_COMPUTED_SIZE,
                                                                              Double.MAX_VALUE);
            columnConstraints.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < grid.getRowCount(); i++) {
            final RowConstraints rowConstraints = new RowConstraints(Control.USE_PREF_SIZE,
                                                                     Control.USE_COMPUTED_SIZE,
                                                                     Double.MAX_VALUE);
            rowConstraints.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rowConstraints);
        }
    }

    public GridPane box3x3(List<Particle> box) {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setBorder(Border.stroke(null));

        int row = 0, column = 0;
        for (Particle particle : box) {
            grid.add(particle(particle), column++, row);
            if (column >= 3) {
                row++;
                column = 0;
            }
        }
        makeGrowable(grid);

        return grid;
    }

    public GridPane particle(Particle particle) {
        GridPane grid = new GridPane();

        if (particle.getDerived() != null) {
            setExplicitValue(particle, grid);
        } else {
            setVariableValue(particle, grid);
        }
        grid.setAlignment(Pos.CENTER);
        makeGrowable(grid);

        return grid;
    }


    private void setVariableValue(Particle particle, GridPane grid) {
        int row = 0, column = 0;
        List<Value> values = situation.getAllPossibleValues()
                                      .stream()
                                      .sorted(Comparator.comparing(Value::getValue))
                                      .toList();
        for (Value possibleValue : values) {
            Label text = label(possibleValue.getValue());
            if (particle.getPossibleValues().contains(possibleValue)) {
                text.setOnMouseClicked(mouseEvent -> {
                    particle.setValue(possibleValue);
                    particle.setDerived(false);

                    situation = solver.solve(
                            situation, Assignment.builder()
                                                 .particle(particle)
                                                 .value(possibleValue)
                                                 .build()
                                            );
                });
            } else {
                text.setText(" ");
            }
            grid.add(text, column++, row);
            GridPane.setHalignment(text, HPos.CENTER);
            GridPane.setValignment(text, VPos.CENTER);
            if (column >= 3) {
                row++;
                column = 0;
            }
        }
    }

    private Label label(String possibleValue) {
        Label text = new Label(possibleValue);
        text.setFont(validValue);
        return text;
    }

    private void setExplicitValue(Particle particle, GridPane grid) {
        grid.getChildren().removeAll(grid.getChildren());

        Label text = new Label(particle.getValue().getValue());
        if (!particle.getDerived()) {
            text.setFont(specifiedValue);
            text.setOnMouseClicked(mouseEvent -> {
                particle.setValue(null);
                particle.setDerived(null);
                situation = solver.solve(situation);
            });
        } else {
            text.setFont(derivedValue);
        }

        int column = 0, row = 0;
        for (int i = 0; i < 9; i++) {
            Label label = label(" ");
            grid.add(label, column++, row);
            if (column >= 3) {
                row++;
                column = 0;
            }
        }
        grid.add(text, 0, 0, 3, 3);

        GridPane.setHalignment(text, HPos.CENTER);
        GridPane.setValignment(text, VPos.CENTER);
    }
}