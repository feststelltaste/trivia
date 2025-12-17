package com.adaptionsoft.games.trivia;
import com.adaptionsoft.games.trivia.runner.GameRunner;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.JunitReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

@UseReporter(JunitReporter.class)
public class ApprovalTest {

    @Test
    public void testApproval()
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        GameRunner.main(new String[0]);
        
        System.out.flush();
        Approvals.verify(baos.toString());
    }
}