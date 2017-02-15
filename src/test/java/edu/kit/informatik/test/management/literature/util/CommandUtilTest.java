package edu.kit.informatik.test.management.literature.util;

import edu.kit.informatik.management.literature.util.CommandUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class CommandUtilTest {
    @Test
    public void calculateHIndexTest() throws Exception {
        ArrayList<Integer> l1  = new ArrayList<>();
        l1.add(17);
        l1.add(3);
        l1.add(1);
        l1.add(5);
        System.out.println(CommandUtil.calculateHIndex(l1));

        l1 = new ArrayList<>();
        l1.add(8);
        l1.add(6);
        l1.add(8);
        l1.add(4);
        l1.add(8);
        l1.add(6);
        System.out.println(CommandUtil.calculateHIndex(l1));

        l1 = new ArrayList<>();
        l1.add(100);
        l1.add(100);
        l1.add(9);
        l1.add(8);
        l1.add(3);
        l1.add(2);
        l1.add(2);
        l1.add(1);
        l1.add(1);
        l1.add(0);
        System.out.println(CommandUtil.calculateHIndex(l1));
    }

    @Test
    public void calculateJaccardTest() throws Exception {
        ArrayList<String> l1  = new ArrayList<>();
        ArrayList<String> l2  = new ArrayList<>();

        l1.add("a");
        l1.add("b");
        l1.add("c");

        l2.add("d");
        l2.add("e");

        String res1 = CommandUtil.calculateJaccard(l1, l2);

        l1.add("d");
        l1.add("e");

        l2.add("b");
        l2.add("c");
        l2.add("f");
        String res2 = CommandUtil.calculateJaccard(l1, l2);
        System.out.println(res1);
        System.out.println(res2);
    }

}