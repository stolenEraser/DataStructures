import edu.princeton.cs.algs4.*;
import java.util.concurrent.Callable;
import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

class Test {

    //////////////////////////////////////////////////////////// EXERCISE 1 TEST
    static private Void testEx1 () throws TestException {
      String[] files = {"tests/all.txt", "tests/even.txt", "tests/odd.txt"};
      String[] tests = {"+1-2+3c", "+3-2+1c", "+3-2+1", "c-1-2+3c", "ci2i3c+3-3-2i2ci3-3+3"};
      
      ProgrammingAssignment2 pa2 = new ProgrammingAssignment2 ();
      ProgrammingAssignment2.FileUnionDifference f =
        pa2.new FileUnionDifference (Arrays.asList (files));

      for (String test : tests) {
        TreeSet<String> set = new TreeSet<> (f.query (test));
        File file = new File ("tests/" + test + ".txt");
        In in = new In (file);
        while (!in.isEmpty ()) {
          String s = in.readString (),
            s2 = set.pollFirst ();
          if (!s.equals (s2))
            throw new TestException ("string " + s2 + " returned, expected " + s);
        }
        if (!set.isEmpty ())
          throw new TestException ("more elements in returned set than expected");
      }
      return null;
    }
    
    static private void testingFUDiff() {
    	String[] files1 = {"tests/all.txt", "tests/even.txt", "tests/odd.txt"};
    	ProgrammingAssignment2 PA2= new ProgrammingAssignment2();
    	ProgrammingAssignment2.FileUnionDifference diff= PA2.new FileUnionDifference(Arrays.asList(files1));
    	
    	//diff.OpenFile(Arrays.asList(files1));
    
    }

    //////////////////////////////////////////////////////////// EXERCISE 2 TEST
    static private Void testEx2 () throws TestException {
      TwoThreeTree<Integer> tree;

      for (int i = 0; i < testSuite2.length; ++i) {
        tree = TwoThreeTree.fromString (testSuite2[i][0]);
        for (int j = 1; j < testSuite2[i].length - 1; ++j) {
          int n = Integer.parseInt (testSuite2[i][j]);
          tree.delete (n);
          if (tree.contains (n))
            throw new TestException ("contains() failed on delete(" + n + ") in tree " + i);
          if (!tree.is23 ())
            throw new TestException ("tree is not 2-3 after delete(" + n + ") in tree " + i);
        }

        // Check that the tree obtained is correct.
        if (! (!tree.contains (-10) &&
               !tree.contains (1 << 30) &&
               testSuite2[i][testSuite2[i].length - 1].equals (tree.toString ())))
          throw new TestException ("deletion in tree " + i + " failed.\n" +
                                   "  expected: " + testSuite2[i][testSuite2[i].length - 1] + "\n" +
                                   "  produced: " + tree);
      }
      return null;
    }

    //////////////////////////////////////////////////////////// EXERCISE 3 TEST
    static class HashedChar {
        final char c;
        final int h;
        public HashedChar (char c, int h) { this.c = c; this.h = h; }
        public String toString () { return "" + this.c + "(" + h + ")"; }
        public int hashCode () { return h; }
        public boolean equals (Object o) {
          if (this == o) return true;
          if (!(o instanceof HashedChar)) return false;
          HashedChar hc = (HashedChar) o;
          return (hc.c == c && hc.h == h);
        }
    }
    static private HashedChar hc (char c, int h) { return new HashedChar (c, h); }

    static private Void testEx3 () throws TestException {
      HashST<HashedChar, Object> st = new HashST<> ();
      Object o = new Object ();
      st.put (hc ('a', 0), o);
      st.put (hc ('b', 0), o);
      st.put (hc ('c', 2), o);
      st.put (hc ('d', 0), o);
      st.delete (hc ('b', 0));
      if (!st.toString ().equals ("a(0) d(0) c(2) null null null null null "))
        throw new TestException ("failed, got " + st.toString ());
      return null;
    }


    /////////////////////////////////////////////////// TESTING FRAMEWORK & MAIN
    static class TestException extends Exception  {
        static final long serialVersionUID = 314;
        public TestException (String msg) {
          super (msg);
        }
    }

    static private void runTest (String name, Callable<Void> test) {
      StdOut.println ("TESTING " + name);
      try {
        test.call ();
        StdOut.println ("SUCCESS");
      }
      catch (TestException e) {
        StdOut.println ("TEST FAILED: " + e.getMessage ());
      }
      catch (Exception | Error e) {
        StdOut.print ("FATAL: ");
        e.printStackTrace ();
      }
    }

    static public void main(String[] args) {
      runTest ("EX 1", Test::testEx1);
      runTest ("EX 2", Test::testEx2);
      runTest ("EX 3", Test::testEx3);
    }

    ///////////////////////////////////////////////////////////// TEST DATABASES
    static String[][] testSuite2 =
    {{"51 84 -- {19 37 -- {1 14 -- {0, 9 11, 17 18}, 27 -- {21 24, 31 36}, 45 -- {39, 47 49}}, 71 -- {59 65 -- {53 58, 60, 70}, 74 80 -- {72, 79, 83}}, 95 -- {86 88 -- {85, 87, 94}, 97 -- {96, 98}}}",
      "72", "71", "70", "39", "74", "72", "86", "31", "88", "85", "37", "31", "59", "96", "65", "65", "60", "31", "9", "0", 
      "45 -- {19 -- {14 -- {1 11, 17 18}, 27 -- {21 24, 36}}, 79 94 -- {51 -- {47 49, 53 58}, 84 -- {80 83, 87}, 97 -- {95, 98}}}"},
     {"35 71 -- {7 20 -- {5 -- {2 4, 6}, 13 18 -- {10, 15, 19}, 33 -- {27, 34}}, 55 -- {42 49 -- {39 41, 44 47, 51}, 66 -- {61 62, 68 69}}, 86 -- {80 -- {77 78, 81}, 94 -- {89, 99}}}",
      "19", "34", "34", "44", "33", "20", "33", "44", "77", "77", "19", "10", "39", "20", "62", "49", "86", "18", "2", "39", 
      "35 -- {7 -- {5 -- {4, 6}, 15 -- {13, 27}}, 55 71 -- {42 -- {41, 47 51}, 66 -- {61, 68 69}, 80 89 -- {78, 81, 94 99}}}"},
     {"32 69 -- {17 -- {2 12 -- {0 1, 10, 13 14}, 25 -- {20 22, 30}}, 45 56 -- {35 40 -- {34, 39, 42}, 50 -- {47, 52}, 67 -- {57 63, 68}}, 86 -- {75 79 -- {72, 76, 83}, 89 93 -- {87, 91, 94}}}",
      "79", "56", "40", "57", "45", "32", "50", "69", "76", "20", "12", "87", "45", "32", "83", "56", "52", "25", "89", "2", 
      "34 -- {13 -- {1 -- {0, 10}, 17 -- {14, 22 30}}, 47 72 -- {39 -- {35, 42}, 67 -- {63, 68}, 86 93 -- {75, 91, 94}}}"},
     {"21 56 -- {3 -- {1 -- {0, 2}, 7 -- {5 6, 9 11}}, 38 -- {24 35 -- {22 23, 26, 37}, 42 51 -- {40, 44, 55}}, 76 95 -- {61 65 -- {59, 63, 66 70}, 89 -- {77 84, 90}, 97 -- {96, 98}}}",
      "37", "3", "51", "44", "95", "59", "38", "89", "59", "7", "55", "63", "70", "65", "40", "42", "2", "56", "63", "23", 
      "61 -- {21 -- {5 9 -- {0 1, 6, 11}, 24 -- {22, 26 35}}, 84 -- {76 -- {66, 77}, 96 -- {90, 97 98}}}"},
     {"50 -- {23 -- {11 -- {3 -- {0 1, 4 8}, 16 -- {15, 19}}, 41 -- {33 38 -- {31, 34 35, 39}, 47 -- {44, 48 49}}}, 73 -- {58 -- {54 -- {53, 55}, 62 -- {59, 64 68}}, 87 -- {81 -- {78, 82 86}, 94 96 -- {92, 95, 97}}}}",
      "95", "33", "38", "68", "8", "0", "41", "62", "55", "16", "41", "87", "64", "8", "49", "11", "59", "34", "35", "78", 
      "50 -- {23 -- {3 15 -- {1, 4, 19}, 39 47 -- {31, 44, 48}}, 73 92 -- {54 -- {53, 58}, 82 -- {81, 86}, 96 -- {94, 97}}}"},
     {"29 66 -- {10 19 -- {3 -- {0 2, 7}, 13 -- {12, 14}, 21 25 -- {20, 24, 28}}, 52 -- {43 -- {30 40, 47}, 63 -- {58 61, 64}}, 73 85 -- {68 -- {67, 69 70}, 82 -- {78 80, 83}, 91 -- {88, 93}}}",
      "47", "13", "7", "10", "73", "80", "30", "24", "28", "80", "13", "40", "61", "7", "68", "67", "0", "66", "43", "21", 
      "69 -- {19 52 -- {12 -- {2 3, 14}, 25 -- {20, 29}, 63 -- {58, 64}}, 85 -- {78 -- {70, 82 83}, 91 -- {88, 93}}}"},
     {"51 82 -- {12 29 -- {10 -- {3 7, 11}, 17 22 -- {15, 20 21, 24}, 35 39 -- {32 34, 37 38, 47 48}}, 68 -- {56 63 -- {54, 57, 64 66}, 72 80 -- {69, 75, 81}}, 94 -- {86 -- {83 85, 88}, 97 -- {96, 99}}}",
      "7", "37", "72", "85", "80", "34", "68", "69", "38", "7", "29", "51", "88", "34", "66", "54", "34", "22", "47", "88", 
      "32 -- {12 -- {10 -- {3, 11}, 17 21 -- {15, 20, 24}}, 56 82 -- {39 -- {35, 48}, 63 75 -- {57, 64, 81}, 94 97 -- {83 86, 96, 99}}}"},
     {"51 75 -- {22 33 -- {11 -- {2 4, 21}, 31 -- {25 26, 32}, 39 44 -- {34 35, 41 42, 50}}, 60 -- {56 58 -- {55, 57, 59}, 69 72 -- {65, 71, 74}}, 87 -- {78 -- {77, 85}, 90 -- {89, 93}}}",
      "89", "71", "2", "32", "74", "11", "59", "42", "11", "31", "69", "56", "2", "31", "31", "31", "65", "33", "85", "50", 
      "51 -- {34 -- {22 -- {4 21, 25 26}, 39 -- {35, 41 44}}, 60 78 -- {57 -- {55, 58}, 75 -- {72, 77}, 90 -- {87, 93}}}"},
     {"39 56 -- {14 30 -- {2 9 -- {0, 5, 10}, 22 -- {16 18, 24}, 33 -- {32, 34}}, 46 -- {43 -- {41, 45}, 53 -- {47, 55}}, 70 87 -- {64 66 -- {57 63, 65, 67}, 81 85 -- {74 80, 83, 86}, 96 -- {94, 98 99}}}",
      "85", "96", "55", "66", "96", "74", "57", "74", "55", "39", "18", "55", "87", "64", "0", "24", "56", "65", "83", "66", 
      "63 -- {30 41 -- {9 14 -- {2 5, 10, 16 22}, 33 -- {32, 34}, 46 -- {43 45, 47 53}}, 81 -- {70 -- {67, 80}, 94 -- {86, 98 99}}}"},
     {"50 74 -- {22 32 -- {13 -- {7 12, 14 15}, 27 30 -- {24 25, 28, 31}, 36 39 -- {33 34, 38, 41}}, 67 -- {59 -- {52, 63}, 71 -- {69, 73}}, 89 -- {79 -- {77 78, 81 88}, 91 95 -- {90, 94, 97}}}",
      "41", "71", "25", "36", "94", "73", "31", "22", "24", "34", "74", "41", "81", "59", "15", "30", "30", "30", "91", "28", 
      "77 -- {32 50 -- {13 -- {7 12, 14 27}, 38 -- {33, 39}, 67 -- {52 63, 69}}, 89 -- {79 -- {78, 88}, 95 -- {90, 97}}}"},
     {"14 43 -- {4 -- {1 -- {0, 2}, 7 -- {6, 11}}, 25 -- {22 -- {16, 24}, 28 34 -- {26, 30 31, 40}}, 54 73 -- {48 52 -- {44 47, 51, 53}, 62 71 -- {56 59, 63, 72}, 88 91 -- {86, 89 90, 97 99}}}",
      "22", "72", "72", "71", "28", "73", "72", "4", "40", "25", "62", "0", "53", "47", "4", "88", "40", "62", "26", "71", 
      "43 -- {14 -- {6 -- {1 2, 7 11}, 24 31 -- {16, 30, 34}}, 54 86 -- {48 -- {44, 51 52}, 59 -- {56, 63}, 91 -- {89 90, 97 99}}}"},
     {"45 -- {19 -- {10 -- {6 -- {0 3, 7}, 15 -- {11, 16}}, 33 -- {25 -- {21 24, 31}, 39 -- {37, 40 41}}}, 72 -- {62 -- {49 54 -- {47 48, 52, 60}, 68 -- {65, 71}}, 81 -- {77 -- {73 74, 80}, 91 -- {85 87, 94 98}}}}",
      "0", "40", "77", "73", "0", "87", "3", "37", "16", "74", "47", "47", "65", "91", "16", "11", "45", "54", "15", "62", 
      "48 -- {19 -- {7 -- {6, 10}, 25 33 -- {21 24, 31, 39 41}}, 72 -- {52 68 -- {49, 60, 71}, 81 94 -- {80, 85, 98}}}"},
     {"17 56 -- {11 -- {2 5 -- {0, 3, 9}, 14 -- {13, 15}}, 41 -- {25 36 -- {22, 29, 37 40}, 43 53 -- {42, 46, 54 55}}, 72 -- {60 65 -- {57, 61 64, 67 69}, 82 87 -- {77 81, 86, 95 99}}}",
      "61", "77", "13", "54", "0", "87", "41", "53", "37", "42", "11", "54", "61", "86", "9", "65", "37", "14", "29", "67", 
      "56 -- {36 -- {3 17 -- {2, 5 15, 22 25}, 43 -- {40, 46 55}}, 72 -- {60 -- {57, 64 69}, 95 -- {81 82, 99}}}"},
     {"50 -- {24 -- {9 -- {1 -- {0, 5 7}, 12 -- {11, 21}}, 40 -- {33 -- {25, 37 39}, 45 -- {41 44, 47}}}, 70 -- {65 -- {54 -- {52 53, 55 58}, 67 -- {66, 68}}, 81 96 -- {75 -- {72, 79 80}, 85 -- {82, 87 93}, 98 -- {97, 99}}}}",
      "1", "82", "68", "39", "87", "0", "65", "44", "58", "54", "39", "33", "55", "55", "1", "53", "70", "41", "81", "87", 
      "50 -- {12 -- {9 -- {5 7, 11}, 24 40 -- {21, 25 37, 45 47}}, 72 85 -- {66 -- {52, 67}, 79 -- {75, 80}, 96 98 -- {93, 97, 99}}}"},
     {"38 72 -- {21 28 -- {15 17 -- {0 1, 16, 18}, 25 -- {24, 26}, 32 35 -- {31, 34, 37}}, 46 57 -- {41 -- {40, 44}, 53 -- {48 52, 54}, 64 67 -- {59, 65, 71}}, 88 -- {78 -- {77, 85 86}, 92 -- {89, 94 95}}}",
      "37", "88", "64", "18", "71", "77", "59", "24", "71", "64", "89", "85", "78", "21", "89", "59", "40", "64", "48", "88", 
      "38 -- {28 -- {15 25 -- {0 1, 16 17, 26}, 32 -- {31, 34 35}}, 53 72 -- {46 -- {41 44, 52}, 57 -- {54, 65 67}, 94 -- {86 92, 95}}}"},
     {"43 -- {21 -- {14 -- {4 -- {2, 5 6}, 16 -- {15, 17}}, 32 -- {26 -- {25, 27}, 36 -- {35, 38 41}}}, 67 -- {50 -- {48 -- {45 47, 49}, 58 60 -- {54 57, 59, 64 66}}, 84 -- {73 77 -- {70, 74, 80}, 90 -- {87, 96 99}}}}",
      "84", "67", "17", "16", "50", "64", "70", "84", "25", "4", "49", "38", "36", "73", "50", "26", "67", "15", "70", "60", 
      "43 -- {14 -- {5 -- {2, 6}, 32 -- {21 27, 35 41}}, 54 74 -- {47 -- {45, 48}, 58 -- {57, 59 66}, 87 96 -- {77 80, 90, 99}}}"},
     {"61 -- {22 -- {12 -- {1 -- {0, 8}, 14 17 -- {13, 15, 20}}, 48 -- {25 39 -- {24, 33, 41 45}, 51 58 -- {50, 53 56, 59}}}, 75 -- {66 -- {63 -- {62, 64 65}, 70 -- {69, 73 74}}, 86 -- {80 -- {76, 85}, 93 -- {89, 97 98}}}}",
      "53", "45", "64", "76", "17", "33", "41", "69", "50", "70", "76", "70", "63", "89", "69", "64", "20", "1", "66", "8", 
      "61 -- {22 48 -- {12 14 -- {0, 13, 15}, 25 -- {24, 39}, 58 -- {51 56, 59}}, 86 -- {73 75 -- {62 65, 74, 80 85}, 97 -- {93, 98}}}"},
     {"62 86 -- {26 47 -- {2 9 -- {1, 4 6, 11 24}, 35 38 -- {28, 36, 39 43}, 50 54 -- {49, 53, 61}}, 76 -- {66 71 -- {64 65, 67 70, 74}, 80 84 -- {79, 81 82, 85}}, 91 -- {88 -- {87, 90}, 98 -- {95, 99}}}",
      "36", "35", "24", "80", "2", "65", "53", "49", "39", "38", "67", "86", "95", "39", "4", "88", "67", "2", "87", "54", 
      "62 -- {26 -- {9 -- {1 6, 11}, 47 -- {28 43, 50 61}}, 76 90 -- {66 71 -- {64, 70, 74}, 81 84 -- {79, 82, 85}, 98 -- {91, 99}}}"},
     {"42 73 -- {16 30 -- {7 12 -- {2, 9, 15}, 21 -- {18, 24 28}, 36 39 -- {31 32, 37, 40 41}}, 54 -- {46 50 -- {44 45, 47, 52}, 61 68 -- {56 58, 65 66, 72}}, 95 -- {83 -- {76 80, 84 88}, 97 -- {96, 99}}}",
      "39", "84", "88", "15", "40", "68", "12", "12", "80", "31", "32", "95", "39", "30", "24", "21", "39", "96", "2", "83", 
      "42 -- {28 -- {16 -- {7 9, 18}, 37 -- {36, 41}}, 54 73 -- {46 50 -- {44 45, 47, 52}, 61 66 -- {56 58, 65, 72}, 97 -- {76, 99}}}"},
     {"65 -- {21 -- {13 -- {5 -- {2 4, 7 11}, 15 -- {14, 20}}, 40 53 -- {28 -- {22, 39}, 42 -- {41, 50}, 60 -- {58, 63}}}, 80 -- {74 -- {72 -- {66 71, 73}, 76 -- {75, 78}}, 93 -- {83 -- {81 82, 87 88}, 95 -- {94, 98}}}}",
      "80", "94", "72", "76", "93", "39", "60", "13", "21", "13", "2", "20", "82", "15", "75", "94", "72", "83", "63", "82", 
      "65 -- {22 42 -- {5 11 -- {4, 7, 14}, 40 -- {28, 41}, 53 -- {50, 58}}, 87 -- {71 74 -- {66, 73, 78 81}, 95 -- {88, 98}}}"},
     {"35 67 -- {16 26 -- {2 12 -- {0 1, 5 6, 13}, 22 -- {18 19, 24}, 31 -- {28, 33}}, 47 -- {39 -- {38, 43 44}, 59 -- {50, 61}}, 78 90 -- {71 -- {68, 76}, 85 -- {80 81, 89}, 98 -- {93, 99}}}",
      "80", "93", "81", "28", "6", "76", "1", "44", "18", "28", "81", "13", "85", "5", "89", "81", "2", "89", "93", "67", 
      "35 -- {22 -- {16 -- {0 12, 19}, 26 -- {24, 31 33}}, 47 68 -- {39 -- {38, 43}, 59 -- {50, 61}, 90 -- {71 78, 98 99}}}"},
     {"18 43 -- {7 -- {5 -- {0 3, 6}, 14 -- {10, 15 16}}, 27 -- {22 -- {20, 23 25}, 33 -- {28 29, 41}}, 56 72 -- {45 -- {44, 52 53}, 59 65 -- {58, 63, 67}, 77 89 -- {73 75, 78 86, 95}}}",
      "5", "14", "53", "25", "25", "22", "86", "78", "7", "29", "5", "22", "72", "18", "59", "59", "25", "41", "5", "44", 
      "56 -- {20 -- {3 10 -- {0, 6, 15 16}, 27 43 -- {23, 28 33, 45 52}}, 73 -- {65 -- {58 63, 67}, 89 -- {75 77, 95}}}"},
     {"34 77 -- {12 -- {1 8 -- {0, 7, 10 11}, 18 23 -- {17, 22, 24 30}}, 50 55 -- {42 -- {35 36, 44 45}, 53 -- {52, 54}, 58 69 -- {56, 67, 70 71}}, 88 -- {85 -- {83, 86}, 93 -- {91, 97}}}",
      "77", "1", "30", "58", "52", "22", "35", "86", "12", "50", "56", "8", "22", "12", "23", "67", "12", "58", "69", "54", 
      "55 -- {17 42 -- {10 -- {0 7, 11}, 34 -- {18 24, 36}, 45 -- {44, 53}}, 88 -- {83 -- {70 71, 85}, 93 -- {91, 97}}}"},
     {"42 -- {19 -- {9 -- {5 -- {1, 8}, 16 -- {15, 17}}, 28 -- {24 -- {20 21, 27}, 33 35 -- {31, 34, 39 40}}}, 73 -- {63 -- {51 -- {47 50, 58 59}, 65 -- {64, 68}}, 88 -- {84 -- {74, 87}, 93 97 -- {90 91, 95, 99}}}}",
      "39", "59", "59", "47", "50", "5", "35", "28", "88", "40", "19", "59", "15", "28", "16", "51", "1", "74", "21", "90", 
      "42 -- {20 -- {9 -- {8, 17}, 31 -- {24 27, 33 34}}, 65 91 -- {63 -- {58, 64}, 73 -- {68, 84 87}, 97 -- {93 95, 99}}}"},
     {"53 76 -- {16 35 -- {3 13 -- {0, 9 12, 15}, 24 30 -- {19, 26, 32}, 38 48 -- {37, 39 47, 51}}, 67 -- {60 -- {57 59, 63}, 72 -- {70 71, 73}}, 89 -- {79 -- {77, 84 85}, 98 -- {91, 99}}}",
      "59", "38", "53", "9", "79", "51", "16", "0", "19", "72", "37", "71", "53", "26", "71", "37", "3", "76", "3", "89", 
      "57 -- {35 -- {13 24 -- {12, 15, 30 32}, 47 -- {39, 48}}, 77 -- {67 -- {60 63, 70 73}, 85 98 -- {84, 91, 99}}}"},
     {"49 73 -- {14 41 -- {1 5 -- {0, 2, 6 7}, 18 29 -- {16 17, 23, 30 31}, 45 -- {43, 47 48}}, 63 -- {51 59 -- {50, 54, 60}, 66 68 -- {65, 67, 71}}, 87 -- {77 -- {75, 79 80}, 95 -- {93, 96 99}}}",
      "77", "31", "95", "87", "16", "95", "2", "16", "80", "49", "54", "73", "47", "87", "67", "77", "43", "63", "18", "71", 
      "50 -- {6 29 -- {1 -- {0, 5}, 14 -- {7, 17 23}, 41 -- {30, 45 48}}, 75 -- {59 65 -- {51, 60, 66 68}, 96 -- {79 93, 99}}}"},
     {"44 -- {22 -- {8 -- {1 4 -- {0, 2, 7}, 20 -- {14 18, 21}}, 36 -- {34 -- {28 30, 35}, 39 -- {38, 41 43}}}, 71 -- {48 55 -- {46 -- {45, 47}, 50 -- {49, 54}, 60 63 -- {58, 62, 66}}, 76 -- {74 -- {72 73, 75}, 89 -- {83 84, 92}}}}",
      "39", "47", "43", "74", "43", "60", "14", "28", "2", "74", "83", "4", "58", "72", "43", "71", "50", "48", "36", "22", 
      "44 -- {8 30 -- {1 -- {0, 7}, 20 -- {18, 21}, 38 -- {34 35, 41}}, 55 73 -- {49 -- {45 46, 54}, 63 -- {62, 66}, 76 89 -- {75, 84, 92}}}"},
     {"35 77 -- {9 21 -- {3 7 -- {1, 4 6, 8}, 13 -- {11, 14}, 28 -- {24 26, 31 32}}, 43 62 -- {37 39 -- {36, 38, 42}, 50 -- {46 49, 58}, 73 -- {65, 76}}, 92 -- {83 87 -- {78 82, 84, 90 91}, 96 -- {94, 97}}}",
      "7", "26", "78", "37", "50", "84", "76", "4", "83", "37", "91", "50", "6", "39", "49", "91", "65", "21", "42", "39", 
      "24 77 -- {9 -- {3 -- {1, 8}, 13 -- {11, 14}}, 35 -- {31 -- {28, 32}, 43 58 -- {36 38, 46, 62 73}}, 92 -- {87 -- {82, 90}, 96 -- {94, 97}}}"},
     {"26 57 -- {13 -- {1 9 -- {0, 6, 11 12}, 19 23 -- {14, 21, 25}}, 43 -- {30 36 -- {28, 35, 38 42}, 50 -- {44 49, 53}}, 82 -- {68 76 -- {59 62, 72 74, 80}, 86 89 -- {85, 87, 93 95}}}",
      "14", "6", "14", "6", "28", "9", "89", "14", "59", "30", "25", "28", "12", "49", "59", "43", "30", "35", "6", "21", 
      "57 -- {13 38 -- {1 -- {0, 11}, 26 -- {19 23, 36}, 44 -- {42, 50 53}}, 82 -- {68 76 -- {62, 72 74, 80}, 86 93 -- {85, 87, 95}}}"},
     {"32 49 -- {24 -- {16 20 -- {2 12, 17 19, 21}, 27 -- {25, 28}}, 41 -- {35 -- {34, 37 38}, 46 -- {42 43, 48}}, 58 66 -- {55 -- {54, 56}, 62 -- {59, 64}, 77 82 -- {69 75, 78 80, 92 97}}}",
      "27", "49", "35", "49", "82", "43", "2", "34", "56", "56", "43", "34", "82", "21", "49", "58", "38", "58", "20", "19", 
      "54 -- {24 41 -- {16 -- {12, 17}, 32 -- {25 28, 37}, 46 -- {42, 48}}, 66 -- {62 -- {55 59, 64}, 77 92 -- {69 75, 78 80, 97}}}"}};
}
