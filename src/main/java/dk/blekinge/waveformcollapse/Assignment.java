package dk.blekinge.waveformcollapse;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Assignment {

    Particle particle;

    dk.blekinge.waveformcollapse.Value value;
}
