package dk.blekinge.waveformcollapse;

import lombok.Builder;

@lombok.Value
@Builder
public class Value {

    public Value(String value) {
        this.value = value;
    }

    String value;

    @Override
    public String toString() {
        return value;
    }
}
