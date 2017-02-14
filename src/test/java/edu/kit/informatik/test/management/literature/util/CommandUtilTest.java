package edu.kit.informatik.test.management.literature.util;

import edu.kit.informatik.management.literature.util.CommandUtil;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class CommandUtilTest {
    @Test
    public void calculateJaccardTest() throws Exception {
        ArrayList<String> l1  = new ArrayList<>();
        ArrayList<String> l2  = new ArrayList<>();

        l1.add("a");
        l1.add("b");
        l1.add("c");

        l2.add("d");
        l2.add("e");

        Double res1 = CommandUtil.calculateJaccard(l1, l2);

        l1.add("d");
        l1.add("e");

        l2.add("b");
        l2.add("c");
        l2.add("f");
        Double res2 = CommandUtil.calculateJaccard(l1, l2);
        System.out.println(String.format("%.3f",res1));
        System.out.println(String.format("%.3f",res2));
    }

}