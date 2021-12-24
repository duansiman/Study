package com.epdc.study;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for simple App.
 */
public class AppTest {

    class Student {
        public String getName() {
            return null;
        }
    }

    @Mock
    private Student student;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        Mockito.when(student.getName()).thenReturn("Devin");
        Assert.assertThat(student.getName(), Is.is("Devin"));
    }

    public void testController() {

    }
}
