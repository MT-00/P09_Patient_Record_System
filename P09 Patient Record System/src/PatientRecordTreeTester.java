//////////////// FILE HEADER //////////////////////////
//
// Title: P09 Patient Record System
// Files: PatientRecord, PatientRecordNode, PatientRecordTree, PatientRecordTreeTester
// Course: CS300,Spring,2020
//
// Author: Meng Tian
// Email: mtian42@wisc.edu
// Lecturer's Name: Gary Dahl
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Students who get help from sources other than their partner and the course
// staff must fully acknowledge and credit those sources here. If you did not
// receive any help of any kind from outside sources, explicitly indicate NONE
// next to each of the labels below.
//
// Persons: NONE (identify each person and describe their help in detail)
// Online Sources: NONE (identify each URL and describe their assistance in detail)
//
///////////////////////////////////////////////////////////////////////////////
import java.util.NoSuchElementException;

/**
 * This class checks the correctness of the implementation of the methods defined in the class
 * PatientRecordTree.
 *
 */

public class PatientRecordTreeTester {

  /**
   * Checks the correctness of the implementation of both addPatientRecord() and toString() methods
   * implemented in the PatientRecordTree class. This unit test considers at least the following
   * scenarios. (1) Create a new empty PatientRecordTree, and check that its size is 0, it is empty,
   * and that its string representation is an empty string "". (2) try adding one patient record and
   * then check that the addPatientRecord() method call returns true, the tree is not empty, its
   * size is 1, and the .toString() called on the tree returns the expected output. (3) Try adding
   * another patientRecord which is older that the one at the root, (4) Try adding a third patient
   * Record which is younger than the one at the root, (5) Try adding at least two further patient
   * records such that one must be added at the left subtree, and the other at the right subtree.
   * For all the above scenarios, and more, double check each time that size() method returns the
   * expected value, the add method call returns true, and that the .toString() method returns the
   * expected string representation of the contents of the binary search tree in an ascendant order
   * from the oldest patient to the youngest one. (6) Try adding a patient whose date of birth was
   * used as a key for a patient record already stored in the tree. Make sure that the
   * addPatientRecord() method call returned false, and that the size of the tree did not change.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddPatientRecordToStringSize() {
    // Create a new empty PatientRecordTree, and check that its size is 0, it is empty,
    // and that its string representation is an empty string "".
    PatientRecordTree test1 = new PatientRecordTree();
    if (test1.isEmpty() != true)// check the isEmpty()
      return false;
    if (test1.size() != 0)// check the size()
      return false;
    if (!test1.toString().equals(""))// check the toString()
      return false;

    // try adding one patient record and
    // then check that the addPatientRecord() method call returns true, the tree is not empty, its
    // size is 1, and the .toString() called on the tree returns the expected output.
    boolean test2 = test1.addPatientRecord(new PatientRecord("A", "12/4/1997"));
    if (test2 != true)
      return false;
    if (test1.size() != 1)
      return false;
    if (!test1.toString().equals("A(12/4/1997)" + "\n"))
      return false;

    // Try adding another patientRecord which is older that the one at the root
    boolean test3 = test1.addPatientRecord(new PatientRecord("B", "2/15/1957"));
    if (test3 != true)
      return false;
    if (test1.size() != 2)
      return false;
    if (!test1.toString().equals("B(2/15/1957)" + "\n" + "A(12/4/1997)" + "\n"))
      return false;
    // Try adding a third patientRecord which is younger than the one at the root
    boolean test4 = test1.addPatientRecord(new PatientRecord("C", "2/15/1999"));
    if (test4 != true)
      return false;
    if (test1.size() != 3)
      return false;
    if (!test1.toString()
        .equals("B(2/15/1957)" + "\n" + "A(12/4/1997)" + "\n" + "C(2/15/1999)" + "\n"))
      return false;
    // Try adding at least two further patient records such that
    // one must be added at the left subtree, and the other at the right subtree.
    boolean test5 = test1.addPatientRecord(new PatientRecord("D", "2/14/1930"));
    if (test5 != true)
      return false;
    if (test1.size() != 4)
      return false;
    if (!test1.toString().equals("D(2/14/1930)" + "\n" + "B(2/15/1957)" + "\n" + "A(12/4/1997)"
        + "\n" + "C(2/15/1999)" + "\n"))
      return false;
    boolean test6 = test1.addPatientRecord(new PatientRecord("E", "2/15/2000"));
    if (test6 != true)
      return false;
    if (test1.size() != 5)
      return false;
    if (!test1.toString().equals("D(2/14/1930)" + "\n" + "B(2/15/1957)" + "\n" + "A(12/4/1997)"
        + "\n" + "C(2/15/1999)" + "\n" + "E(2/15/2000)" + "\n"))
      return false;

    // Try adding a patient whose date of birth was
    // used as a key for a patient record already stored in the tree.
    boolean test7 = test1.addPatientRecord(new PatientRecord("D", "2/14/1930"));
    if (test7 != false)
      return false;
    if (test1.size() != 5)
      return false;
    return true;
  }

  /**
   * This method checks mainly for the correctness of the PatientRecordTree.lookup() method. It must
   * consider at least the following test scenarios. (1) Create a new PatientRecordTree. Then, check
   * that calling the lookup() method with any valid date must throw a NoSuchElementException. (2)
   * Consider a PatientRecordTree of height 3 which consists of at least 5 PatientRecordNodes. Then,
   * try to call lookup() method to search for the patient record at the root of the tree, then a
   * patient records at the right and left subtrees at different levels. Make sure that the lookup()
   * method returns the expected output for every method call. (3) Consider calling .lookup() method
   * on a non-empty PatientRecordTree with a date of birth not stored in the tree, and ensure that
   * the method call throws a NoSuchElementException.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddPatientRecordAndLookup() {
    // Create a new PatientRecordTree. Then, check
    // that calling the lookup() method with any valid date must throw a NoSuchElementException.
    PatientRecordTree test1 = new PatientRecordTree();
    try {
      test1.lookup("11/2/1999");
      System.out.println("No exception thrown.");// if there is no exception thrown, return false
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || !e.getMessage().toLowerCase()
          .contains("no patient record whose date of birth matches date")) {// if the expected
                                                                            // exception is thrown
                                                                            // with correct warning
                                                                            // message
        System.out.println("Problem detected. Does not contain an appropriate error message.");
        return false;
      }
    } catch (Exception e2) {// if there is other exceptions
      e2.printStackTrace();
      System.out.println("There is other exception. ");
      return false;
    }
    // Consider a PatientRecordTree of height 3 which consists of at least 5 PatientRecordNodes.
    // Then,
    // try to call lookup() method to search for the patient record at the root of the tree, then a
    // patient records at the right and left subtrees at different levels.
    test1.addPatientRecord(new PatientRecord("A", "12/4/1997"));
    test1.addPatientRecord(new PatientRecord("B", "12/4/1998"));
    test1.addPatientRecord(new PatientRecord("C", "12/4/1999"));
    test1.addPatientRecord(new PatientRecord("D", "12/4/1947"));
    test1.addPatientRecord(new PatientRecord("E", "12/4/1937"));
    try {
      PatientRecord root = test1.lookup("12/4/1997");
      PatientRecord leaf1 = test1.lookup("12/4/1998");
      PatientRecord leaf2 = test1.lookup("12/4/1937");
      if (!root.equals(new PatientRecord("A", "12/4/1997")))
        return false;
      if (!leaf1.equals(new PatientRecord("B", "12/4/1998")))
        return false;
      if (!leaf2.equals(new PatientRecord("E", "12/4/1937")))
        return false;
    } catch (NoSuchElementException e) {
      System.out.println("Unexpected exception.");
      return false;
    } catch (Exception e2) {
      e2.printStackTrace();
      System.out.println("There is other exception. ");
      return false;
    }

    // Consider calling .lookup() method on a non-empty PatientRecordTree with a date of birth not
    // stored in the tree
    try {
      PatientRecord leaf3 = test1.lookup("4/15/1997");
      System.out.println("No exception thrown.");
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || !e.getMessage().toLowerCase()
          .contains("no patient record whose date of birth matches date")) {
        System.out.println("Problem detected. Does not contain an appropriate error message.");
        return false;
      }
    } catch (Exception e2) {
      e2.printStackTrace();
      System.out.println("There is other exception. ");
      return false;
    }
    return true;
  }

  /**
   * Checks for the correctness of PatientRecordTree.height() method. This test must consider
   * several scenarios such as, (1) ensures that the height of an empty patient record tree is zero.
   * (2) ensures that the height of a tree which consists of only one node is 1. (3) ensures that
   * the height of a PatientRecordTree with the following structure for instance, is 4. (*) / \ (*)
   * (*) \ / \ (*) (*) (*) / (*)
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testHeight() {
    // ensures that the height of an empty patient record tree is zero.
    PatientRecordTree test1 = new PatientRecordTree();
    if (test1.height() != 0)
      return false;
    // ensures that the height of a tree which consists of only one node is 1.
    test1.addPatientRecord(new PatientRecord("A", "12/4/1997"));
    if (test1.height() != 1)
      return false;
    // ensures that the height of a PatientRecordTree with the following structure for instance, is
    // 4.
    test1.addPatientRecord(new PatientRecord("B", "12/4/1947"));
    test1.addPatientRecord(new PatientRecord("C", "12/4/1999"));
    test1.addPatientRecord(new PatientRecord("D", "12/4/1970"));
    test1.addPatientRecord(new PatientRecord("E", "12/4/1998"));
    test1.addPatientRecord(new PatientRecord("F", "12/4/2005"));
    test1.addPatientRecord(new PatientRecord("G", "12/4/2003"));
    if (test1.height() != 4)
      return false;
    return true;
  }

  /**
   * Checks for the correctness of PatientRecordTree.getRecordOfYoungestPatient() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetRecordOfYoungestPatient() {
    PatientRecordTree test1 = new PatientRecordTree();
    // search in an empty tree
    if (test1.getRecordOfYoungestPatient() != null)
      return false;
    // search in an one-node tree
    test1.addPatientRecord(new PatientRecord("A", "12/4/1998"));
    if (!test1.getRecordOfYoungestPatient().equals(new PatientRecord("A", "12/4/1998")))
      return false;
    // search in an multuple-nodes tree
    test1.addPatientRecord(new PatientRecord("A", "12/4/1999"));
    test1.addPatientRecord(new PatientRecord("A", "2/4/1999"));
    test1.addPatientRecord(new PatientRecord("A", "2/4/1998"));
    test1.addPatientRecord(new PatientRecord("A", "12/4/1947"));
    test1.addPatientRecord(new PatientRecord("A", "12/4/1957"));
    if (!test1.getRecordOfYoungestPatient().equals(new PatientRecord("A", "12/4/1999")))
      return false;
    return true;
  }

  /**
   * Checks for the correctness of PatientRecordTree.getRecordOfOldestPatient() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetRecordOfOldestPatient() {
    PatientRecordTree test1 = new PatientRecordTree();
    // search in an empty tree
    if (test1.getRecordOfOldestPatient() != null)
      return false;
    // search in an one-node tree
    test1.addPatientRecord(new PatientRecord("A", "12/4/1998"));
    if (!test1.getRecordOfOldestPatient().equals(new PatientRecord("A", "12/4/1998")))
      return false;
    // search in an multiple-nodes tree
    test1.addPatientRecord(new PatientRecord("A", "12/4/1999"));
    test1.addPatientRecord(new PatientRecord("A", "2/4/1999"));
    test1.addPatientRecord(new PatientRecord("A", "2/4/1998"));
    test1.addPatientRecord(new PatientRecord("A", "12/4/1947"));
    test1.addPatientRecord(new PatientRecord("A", "12/4/1957"));
    if (!test1.getRecordOfOldestPatient().equals(new PatientRecord("A", "12/4/1947")))
      return false;
    return true;
  }

  /**
   * Calls the test methods
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println(testAddPatientRecordToStringSize());
    System.out.println(testAddPatientRecordAndLookup());
    System.out.println(testHeight());
    System.out.println(testGetRecordOfYoungestPatient());
    System.out.println(testGetRecordOfOldestPatient());
  }

}
