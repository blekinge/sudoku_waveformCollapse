module dk.blekinge.waveformcollapse {
    requires javafx.controls;
    requires javafx.fxml;

    //requires org.apache.commons.collections4;
    //requires org.apache.commons.lang3;
    //requires com.github.miachm.sods;
    requires lombok;
    requires commons.lang;

    exports dk.blekinge.waveformcollapse;
    opens dk.blekinge.waveformcollapse to javafx.fxml;

    exports dk.blekinge.waveformcollapse.sudokus;
    opens dk.blekinge.waveformcollapse.sudokus to javafx.fxml;
}