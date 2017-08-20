package com.upuldi.app;

import java.text.DecimalFormat;

/**
 * Created by udoluweera on 8/20/17.
 */
public class test {

    public static void main(String[] args) {

        DecimalFormat df = new DecimalFormat("#");
        System.out.println(df.format(-34.5678));

    }

}
