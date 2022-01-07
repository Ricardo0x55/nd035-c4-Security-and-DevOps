package com.udacity.examples.Testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@RunWith(Parameterized.class)
public class HelperParameterizedTest {

    private String input;
    private String output;

    public HelperParameterizedTest(String input, String output) {
        super();
        this.input = input;
        this.output = output;
    }

    @Parameterized.Parameters
    public static Collection initData(){
        String empNames[][]={{"Sareeta", "Sareeta"},{"Sareeta", "Jeff"}};
        return Arrays.asList(empNames);
    }

    @Test
    public void verify_input_different_output(){
        Assert.assertNotEquals(input,output);
    }

}
