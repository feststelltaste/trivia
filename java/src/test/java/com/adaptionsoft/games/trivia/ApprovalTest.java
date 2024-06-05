package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.UglyGameRunner;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This class contains a test that uses ApprovalTests to verify the output of the GameRunner.
 * It ensures the game's output remains consistent and correct.
 */
public class ApprovalTest {

    /**
     * Captures the output of the GameRunner and verifies it using ApprovalTests.
     *
     * - Validates that the game's output matches the expected output.
     * - Ensures any changes in the game's output are intentional and reviewed.
     * - Facilitates easy detection of unintended changes or bugs in the game's output.
     */
    @Test
    public void testApproval() {
        // Setup: Capture the standard output to verify it later.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        // Execution: Run the game, which writes its output to the standard output.
        UglyGameRunner.main(new String[0]);

        // Verification: Compare the captured output with the approved output.
        // This helps in identifying any changes in the output and confirms
        // if they are expected or need further review.
        Approvals.verify(baos.toString());
    }
}
