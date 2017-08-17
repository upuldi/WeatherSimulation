package com.upuldi;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Created by udoluweera on 8/18/17.
 */
public class AppTest {

    private App application = new App();


    @Test
    public void shouldReadLocationFile() throws Exception {
        List<Location> locationList = application.readLocationData("locations.txt");
        assertThat(locationList.size(), Is.is(11));
    }

}