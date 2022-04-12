package glazer.daniel.pwr.app.billboard;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class Advertisement {
    private String advertisementText;
    private Duration displayPeriod;
    private Duration usedTime;

    public Advertisement(String advertisementText, Duration displayPeriod) {
        this.advertisementText = advertisementText;
        this.displayPeriod = displayPeriod;
        usedTime = Duration.ZERO;
    }
}
