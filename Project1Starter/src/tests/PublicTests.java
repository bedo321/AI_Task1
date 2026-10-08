package tests;

import code.CaveExplorer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//import static org.testng.Assert.assertFalse;
//import static org.testng.Assert.assertTrue;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
public class PublicTests {

    @Test
    public void test_plan_id1() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "4,5;0,3;180,10;6,12,8,15,10;5,0,0,0,9;7,11,4,6,8;3,5,7,9,6;4,0;1,2;";
            String sol = CaveExplorer.solve(initialState, "ID");

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }
    @Test
    public void test_plan_id2() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "5,5;2,4;250,12;8,14,6,22,9;7,0,0,0,10;5,11,18,0,12;9,0,6,0,8;4,5,7,6,3;0,0;2,2;";
            String sol = CaveExplorer.solve(initialState, "ID");

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }

    @Test
    public void test_plan_id3() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "5,6;0,4;300,15;6,10,14,8,28,12;5,0,0,7,0,9;4,8,24,18,6,11;7,0,12,0,0,13;3,5,9,15,35,7;5,0;3,2;";            String sol = CaveExplorer.solve(initialState, "ID");

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }
    @Test
    public void test_plan_id4() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "6,6;0,5;350,15;6,10,14,9,20,12;8,0,0,0,0,11;5,0,0,0,0,10;7,0,0,0,0,12;4,5,8,0,0,7;3,0,0,0,0,6;0,1,5,0;0,3,2,0;";            String sol = CaveExplorer.solve(initialState, "ID");

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }
    @Test
    public void test_plan_id5() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "6,7;0,5;350,20;6,8,10,12,14,16,10;7,0,0,0,0,0,12;5,0,0,0,0,0,14;4,0,0,0,0,0,16;3,6,8,45,0,0,20;2,0,0,0,0,0,14;6,0;0,2;";            String sol = CaveExplorer.solve(initialState, "ID");

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }

    @Test
    public void test_plan_id6() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "7,8;0,1;120,5;0,3,0,0,8,4,0,2;4,9,5,0,2,0,7,0;0,1,8,8,6,9,6,0;0,2,7,3,4,4,0,2;7,0,1,3,2,0,3,9;7,0,2,6,0,1,4,0;5,0,9,4,4,3,8,5;3,2,5,6;1,0,5,3,0,4;";
            String sol = CaveExplorer.solve(initialState, "ID");

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }

    @Test
    public void test_plan_uc1() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "4,5;0,3;180,10;6,12,8,15,10;5,0,0,0,9;7,11,4,6,8;3,5,7,9,6;4,0;1,2;";

            String sol = CaveExplorer.solve(initialState, "UC");


            Checker.ValidationResult validation =
                    Checker.validateSolution(initialState, sol);

            assertTrue(
                    validation.isValid,
                    "Solution failed: " + validation.errorMessage
            );
        });
    }
    @Test
    public void test_plan_uc2() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "5,5;2,4;250,12;8,14,6,22,9;7,0,0,0,10;5,11,18,0,12;9,0,6,0,8;4,5,7,6,3;0,0;2,2;";
            String sol = CaveExplorer.solve(initialState, "UC");

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }

    @Test
    public void test_plan_uc3() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {
            String initialState = "5,6;0,4;300,15;6,10,14,8,28,12;5,0,0,7,0,9;4,8,24,18,6,11;7,0,12,0,0,13;3,5,9,15,35,7;5,0;3,2;";
            String sol = CaveExplorer.solve(initialState, "UC");
            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }
    @Test
    public void test_plan_uc4() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {
            String initialState = "6,6;0,5;350,15;6,10,14,9,20,12;8,0,0,0,0,11;5,0,0,0,0,10;7,0,0,0,0,12;4,5,8,0,0,7;3,0,0,0,0,6;0,1,5,0;0,3,2,0;";
            String sol = CaveExplorer.solve(initialState, "UC");
            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }
    @Test
    public void test_plan_uc5() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "6,7;0,5;350,20;6,8,10,12,14,16,10;7,0,0,0,0,0,12;5,0,0,0,0,0,14;4,0,0,0,0,0,16;3,6,8,45,0,0,20;2,0,0,0,0,0,14;6,0;0,2;";            String sol = CaveExplorer.solve(initialState, "UC");

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }


    @Test
    public void test_plan_uc6() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {


            String initialState = "7,8;0,1;120,2;0,3,0,0,8,4,0,2;4,9,5,0,2,0,7,0;0,1,8,8,6,9,6,0;0,2,7,3,4,4,0,2;7,0,1,3,2,0,3,9;7,0,2,6,0,1,4,0;5,0,9,4,4,3,8,5;3,2,5,6;1,0,5,3,0,4;";
            String sol = CaveExplorer.solve(initialState, "UC");
assertEquals("No Solution", sol);
        });
    }

    @Test
    public void test_plan_uc_cost1() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "5,6;0,4;300,15;6,10,14,8,28,12;5,0,0,7,0,9;4,8,24,18,6,11;7,0,12,0,0,13;3,5,9,15,35,7;5,0;3,2;";
            int expectedPathCostLives = 0;
            int expectedPathCostEnergy = 99;

            String sol = CaveExplorer.solve(initialState, "UC");
            int  actualPathCostLives = Integer.parseInt(sol.split(";")[1]);
            int  actualPathCostEnergy = Integer.parseInt(sol.split(";")[2]);

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(expectedPathCostLives==actualPathCostLives && expectedPathCostEnergy==actualPathCostEnergy, "wrong cost (goal reached should be optimal)");

        });
    }

    @Test
    public void test_plan_uc_cost2() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(240), () -> {

            String initialState = "7,8;0,1;120,5;0,3,0,0,8,4,0,2;4,9,5,0,2,0,7,0;0,1,8,8,6,9,6,0;0,2,7,3,4,4,0,2;7,0,1,3,2,0,3,9;7,0,2,6,0,1,4,0;5,0,9,4,4,3,8,5;3,2,5,6;1,0,5,3,0,4;";
            int expectedPathCostLives = 2;
            int expectedPathCostEnergy = 75;

            String sol = CaveExplorer.solve(initialState, "UC");
            int  actualPathCostLives = Integer.parseInt(sol.split(";")[1]);
            int  actualPathCostEnergy = Integer.parseInt(sol.split(";")[2]);

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(expectedPathCostLives==actualPathCostLives && expectedPathCostEnergy==actualPathCostEnergy, "wrong cost (goal reached should be optimal)");

        });
    }



    @Test
    public void test_plan_as1() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "4,5;0,3;180,10;6,12,8,15,10;5,0,0,0,9;7,11,4,6,8;3,5,7,9,6;4,0;1,2;";
            String sol = CaveExplorer.solve(initialState, "AS");
            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

        });
    }
   @Test
   public void test_plan_as2() {
       Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

           String initialState = "5,5;2,4;250,12;8,14,6,22,9;7,0,0,0,10;5,11,18,0,12;9,0,6,0,8;4,5,7,6,3;0,0;2,2;";
           String sol = CaveExplorer.solve(initialState, "AS");
           System.out.println(sol);
           Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
           assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

            });
        }

        @Test
        public void test_plan_as3() {
            Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

                String initialState = "5,6;0,4;300,15;6,10,14,8,28,12;5,0,0,7,0,9;4,8,24,18,6,11;7,0,12,0,0,13;3,5,9,15,35,7;5,0;3,2;";                String sol = CaveExplorer.solve(initialState, "AS");
    
                Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
                assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

            });
        }
        @Test
        public void test_plan_as4() {
            Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

                String initialState = "6,6;0,5;350,15;6,10,14,9,20,12;8,0,0,0,0,11;5,0,0,0,0,10;7,0,0,0,0,12;4,5,8,0,0,7;3,0,0,0,0,6;0,1,5,0;0,3,2,0;";                String sol = CaveExplorer.solve(initialState, "AS");
    
                Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
                assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

            });
        }
        @Test
        public void test_plan_as5() {
            Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

                String initialState = "6,7;0,5;99,5;6,8,10,12,14,16,10;7,0,0,0,0,0,12;5,0,0,0,0,0,14;4,0,0,0,0,0,16;3,6,8,45,0,0,20;2,0,0,0,0,0,14;6,0;0,2;";
                String sol = CaveExplorer.solve(initialState, "AS");
    
                Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
                assertTrue(validation.isValid, "Solution failed: " + validation.errorMessage);

            });
        }
        

    @Test
    public void test_plan_as_cost1() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(120), () -> {

            String initialState = "5,6;0,4;300,15;6,10,14,8,28,12;5,0,0,7,0,9;4,8,24,18,6,11;7,0,12,0,0,13;3,5,9,15,35,7;5,0;3,2;";
            int expectedPathCostLives = 0;
            int expectedPathCostEnergy = 99;

            String sol = CaveExplorer.solve(initialState, "AS");
            int  actualPathCostLives = Integer.parseInt(sol.split(";")[1]);
            int  actualPathCostEnergy = Integer.parseInt(sol.split(";")[2]);

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(expectedPathCostLives==actualPathCostLives && expectedPathCostEnergy==actualPathCostEnergy, "wrong cost (goal reached should be optimal)");

        });
    }

    @Test
    public void test_plan_as_cost2() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(240), () -> {

            String initialState = "7,8;0,1;80,5;0,3,0,0,8,4,0,2;4,9,5,0,2,0,7,0;0,1,8,8,6,9,6,0;0,2,7,3,4,4,0,2;7,0,1,3,2,0,3,9;7,0,2,6,0,1,4,0;5,0,9,4,4,3,8,5;3,2,5,6;1,0,5,3,0,4;";
            int expectedPathCostLives = 2;
            int expectedPathCostEnergy = 75;

            String sol = CaveExplorer.solve(initialState, "AS");
            int  actualPathCostLives = Integer.parseInt(sol.split(";")[1]);
            int  actualPathCostEnergy = Integer.parseInt(sol.split(";")[2]);

            Checker.ValidationResult validation = Checker.validateSolution(initialState, sol);
            assertTrue(expectedPathCostLives==actualPathCostLives && expectedPathCostEnergy==actualPathCostEnergy, "wrong cost (goal reached should be optimal)");

        });
    }



    }



