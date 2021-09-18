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
 * This class implements a binary search tree (BST) which stores a set of patient records. The left
 * subtree contains the patient records of all the patients who are older than the patient who's
 * PatientRecord is stored at a parent node. The right subtree contains the patient records of all
 * the patients who are younger than the patient who's PatientRecord is stored at a parent node.
 *
 */
public class PatientRecordTree {
  private PatientRecordNode root; // root of this binary search tree
  private int size = 0; // total number of patient records stored in this tree.

  /**
   * Checks whether this binary search tree (BST) is empty
   * 
   * @return true if this PatientRecordTree is empty, false otherwise
   */
  public boolean isEmpty() {
    if (root == null)// check the size of the tree to determine if it is empty
      return true;
    return false;
  }

  /**
   * Returns the number of patient records stored in this BST.
   * 
   * @return the size of this PatientRecordTree
   */
  public int size() {
    return this.size;
  }

  /**
   * Recursive helper method to add a new PatientRecord to a PatientRecordTree rooted at current.
   * 
   * @param current   The "root" of the subtree we are inserting newRecord into.
   * @param newRecord The PatientRecord to be added to a BST rooted at current.
   * @return true if the newRecord was successfully added to this PatientRecordTree, false otherwise
   */
  public static boolean addPatientRecordHelper(PatientRecord newRecord, PatientRecordNode current) {
    PatientRecordNode node = new PatientRecordNode(newRecord);// initialize the patient record as a
                                                              // node
    if (newRecord.compareTo(current.getPatientRecord()) == 0)// if there is already s person born on
                                                             // same day exists
      return false;// it cannot be added in
    else if (newRecord.compareTo(current.getPatientRecord()) < 0)// if it is older than the current
                                                                 // patient
      if (current.getLeftChild() != null) {// if there is already someone older added
        if (addPatientRecordHelper(newRecord, current.getLeftChild()))// call this method again
          return true;
      } else {// until there is a spot for this old patient
        current.setLeftChild(node);// add it to left child
        return true;
      }
    else {// if it is younger than the current patient
      if (current.getRightChild() != null) {// if there is already someone younger added
        if (addPatientRecordHelper(newRecord, current.getRightChild()))// call this method again
          return true;
      } else {// until there is a spot for this young patient
        current.setRightChild(node);// add it to right child
        return true;
      }
    }

    return false;
  }

  /**
   * Adds a new PatientRecord to this PatientRecordTree
   * 
   * @param newRecord a new PatientRecord to add to this BST.
   * @return true if the newRecord was successfully added to this BST, and returns false if there is
   *         a match with this PatientRecord already already stored in this BST.
   */
  public boolean addPatientRecord(PatientRecord newRecord) {
    if (isEmpty()) {// if there is not patient so far
      this.root = new PatientRecordNode(newRecord);// add it as a root
      size++;
      return true;
    } else {// if there is call the recursive method to add it in
      if (addPatientRecordHelper(newRecord, root) == false)// check if it already exists
        return false;
      else {
        size++;
        return true;

      }
    }

  }

  /**
   * Recursive helper method which returns a String representation of the BST rooted at current. An
   * example of the String representation of the contents of a PatientRecordTree is provided in the
   * description of the above toString() method.
   * 
   * @param current reference to the current PatientRecordNode within this BST.
   * @return a String representation of all the PatientRecords stored in the sub-tree
   *         PatientRecordTree rooted at current in increasing order with respect to the patients
   *         dates of birth. Returns an empty String "" if current is null.
   */
  public static String toStringHelper(PatientRecordNode current) {
    String s = "";
    if (current == null)
      return s;
    if (current.getPatientRecord() != null)// get the younger patient's record first
      if (current.getLeftChild() != null)
        s += toStringHelper(current.getLeftChild());// add to the string
    s += current.getPatientRecord().toString() + "\n";
    if (current.getPatientRecord() != null)// then get the older patient's record
      if (current.getRightChild() != null)
        s += toStringHelper(current.getRightChild());// ass to the string
    return s;
  }

  /**
   * Returns a String representation of all the PatientRecords stored within this BST in the
   * increasing order, separated by a newline "\n". For instance: "Sarah(1/2/1935)" + "\n" +
   * "George(5/27/1943)" + "\n" + "Adam(8/12/1972)" + "\n" + "Norah(11/23/1985)" + "\n" +
   * "William(6/4/1998)" + "\n" + "Nancy(9/12/2003)" + "\n" + "Sam(4/20/2019)" + "\n"
   * 
   * @return a String representation of all the PatientRecords stored within this BST sorted in an
   *         increasing order with respect to the dates of birth of the patients (i.e. from the
   *         oldest patient to the youngest patient). Returns an empty string "" if this BST is
   *         empty.
   */
  public String toString() {
    String string = toStringHelper(root);// call the recursive method and return the result
    return string;
  }

  /**
   * Search for a patient record (PatientRecord) given the date of birth as lookup key.
   * 
   * @param date a String representation of the date of birth of a patient in the format mm/dd/yyyy
   * @return the PatientRecord of the patient born on date.
   * @throws a NoSuchElementException with a descriptive error message if there is no PatientRecord
   *           found in this BST having the provided date of birth
   */
  public PatientRecord lookup(String date) {
    PatientRecord findRecord = new PatientRecord("", date);// initialize a patient with no name and
                                                           // the provided birthday
    return this.lookupHelper(findRecord, root);// call the recursive method to look up this patient
  }

  /**
   * Recursive helper method to lookup a PatientRecord given a reference PatientRecord with the same
   * date of birth in the subtree rooted at current
   * 
   * @param findRecord a reference to a PatientRecord target we are lookup for a match in the BST
   *                   rooted at current.
   * @param current    "root" of the subtree we are looking for a match to findRecord within it.
   * @return reference to the PatientRecord stored stored in this BST which matches findRecord.
   * @throws NoSuchElementException with a descriptive error message if there is no patient record
   *                                whose date of birth matches date, stored in this BST.
   */
  private PatientRecord lookupHelper(PatientRecord findRecord, PatientRecordNode current) {
    if (current == null)// if there is no patient to be looked up
      throw new NoSuchElementException(// throw a warning
          "there is no patient record whose date of birth matches date, stored in this BST.");
    else if (findRecord.compareTo(current.getPatientRecord()) == 0)// compare the birthday
      return current.getPatientRecord();// if they are same, return the result
    else if (findRecord.compareTo(current.getPatientRecord()) < 0) {// if the provided birthday is
                                                                    // older than the current one
      return lookupHelper(findRecord, current.getLeftChild());// check the left child of the tree
    } else {// if it is younger
      return lookupHelper(findRecord, current.getRightChild());// check the right one
    }
  }

  /**
   * Computes and returns the height of this BST, counting the number of nodes (PatientRecordNodes)
   * from root to the deepest leaf.
   * 
   * @return the height of this Binary Search Tree
   */
  public int height() {
    return heightHelper(root);
  }

  /**
   * Recursive helper method that computes the height of the subtree rooted at current
   * 
   * @param current pointer to the current PatientRecordNode within a PatientRecordTree
   * @return height of the subtree rooted at current, counting the number of PatientRecordNodes
   */
  public static int heightHelper(PatientRecordNode current) {
    int deep = 0;// initialize the height as zero
    if (current != null) {
      int lh = heightHelper(current.getLeftChild());// get the number of left child in the tree
      int rh = heightHelper(current.getRightChild());// get the number of right child in the tree
      if (lh > rh)// compare the result
        deep = lh + 1;// add the root
      else
        deep = rh + 1;// add the root
    }
    return deep;

  }


  /**
   * Returns the PatientRecord of the youngest patient in this BST.
   * 
   * @return the PatientRecord of the youngest patient in this BST and null if this tree is empty.
   */
  public PatientRecord getRecordOfYoungestPatient() {
    PatientRecordNode current;
    if ((isEmpty()))// if there is no patient
      return null;// return null
    else {
      current = root;
      while (current.getRightChild() != null)// in the loop, keep finding the right child of the
                                             // tree until null
        current = current.getRightChild();
      return current.getPatientRecord();// return the result
    }

  }

  /**
   * Returns the PatientRecord of the oldest patient in this BST.
   * 
   * @return the PatientRecord of the oldest patient in this BST, and null if this tree is empty.
   */
  public PatientRecord getRecordOfOldestPatient() {
    PatientRecordNode current;
    if ((isEmpty()))// if there is no patient
      return null;// return null
    else {
      current = root;
      while (current.getLeftChild() != null)// in the loop, keep finding the left child of the tree
                                            // until null
        current = current.getLeftChild();
      return current.getPatientRecord();// return the result
    }

  }
}
