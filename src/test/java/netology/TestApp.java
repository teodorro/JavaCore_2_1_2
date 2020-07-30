package netology;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


public class TestApp {

    @Test
    public void test_GetNotEmptyListOfEmployeesFromXmlFile(){
        String fileName = "data.xml";

        List<Employee> employees = App.parseXml(fileName);

        assertThat(employees, notNullValue());
        assertThat(employees.isEmpty(), is(false));
    }

    @Test
    public void test_ConvertEmployeesToJsonNotEmpty(){
        List<Employee> employees = Arrays.asList(
                new Employee(1, "a", "b", "c", 2),
                new Employee(2, "d", "e", "f", 3));

        String json = App.employeesToJson(employees);

        assertThat(json, notNullValue());
        assertThat(json.isEmpty(), is(false));
    }

    @Test
    public void test_ConvertEmployeesToJsonTwoElements(){
        List<Employee> employees = Arrays.asList(
                new Employee(1, "a", "b", "c", 2),
                new Employee(2, "d", "e", "f", 3));

        String json = App.employeesToJson(employees);

        assertThat(json, startsWith("["));
        assertThat(json, endsWith("]"));
        assertThat(json.chars().filter(x -> x == '{').count(), is(2L));
        assertThat(json.chars().filter(x -> x == '}').count(), is(2L));
    }

    @Test
    public void test_SaveJson(){
        String filename = "asd";
        String filenameJson = "data.json";
        App.saveJson(filename);
        File asd = new File(filenameJson);

        assertThat(asd.exists(), is(true));

        (new File(filenameJson)).delete();
    }

    @Test
    public void test_MapIntString(){
        int val = 123;

        Integer mapVal = App.mapValueString(String.valueOf(val), Integer.class);

        assertThat(val, is(mapVal));
    }

    @Test
    public void test_MapLongString(){
        long val = 123;

        Long mapVal = App.mapValueString(String.valueOf(val), Long.class);

        assertThat(val, is(mapVal));
    }

    @Test
    public void test_MapStringString(){
        String val = "asd";

        String mapVal = App.mapValueString(val, String.class);

        assertThat(val, is(mapVal));
    }
}
