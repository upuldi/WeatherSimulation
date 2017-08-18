package com.upuldi;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Created by udoluweera on 8/18/17.
 */
public class WatherSimulatorTest {

    private WatherSimulator application = new WatherSimulator();


    @Test
    public void shouldReadLocationFile() throws Exception {
        List<Location> locationList = application.readLocationData("locations.txt");
        assertThat(locationList.size(), Is.is(11));
    }

}