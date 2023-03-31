import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Level4 {

  static int N = 0; // Numero tornei 
  static int M = 0; // Numero fighters
  static int amountR, amountP, amountS = 0;
  public static void main(String[] args) {
    var lines = parser();
    int i = 1;
    for (String line : lines) {
        // System.out.println();
        // System.out.println("Trying lines: " + i++);
        amountR = Integer.parseInt(line.split(" ")[0].replace("R", ""));
        amountP = Integer.parseInt(line.split(" ")[1].replace("P", ""));
        amountS = Integer.parseInt(line.split(" ")[2].replace("S", ""));
        System.out.println(fillContainers(amountP, amountR, amountS));
    }
  }

  public static String fillContainers(int p, int r, int s) {
    int index = 0;
    int tot = M;
    int totttt = M;

    char[] containers = new char[tot];

    // System.out.println(r);
    // System.out.println(p);
    // System.out.println(s);

    while (r > 0) {
      containers[index] = 'R';
      index++;
      r--;
    }
    while (p > 0) {
      containers[index] = 'P';
      index++;
      p--;
    }
    while (s > 0) {
      containers[index] = 'S';
      index++;
      s--;
    }

    var resString = Arrays.toString(containers).replace("[", "").replace(", ", "").replace("]", "");
    var valid = checkCombination(resString);

    while (!valid) {
      resString = scramble(resString);
      valid = checkCombination(resString);
    }

    // System.out.println(Solver.solve(resString));

    long rres = resString.chars().filter(ch -> ch == 'R').count();
    long pres = resString.chars().filter(ch -> ch == 'P').count();
    long sres = resString.chars().filter(ch -> ch == 'S').count();

    // System.out.print(amountR + "R ");
    // System.out.print(amountP + "P ");
    // System.out.println(amountS + "S");

    // System.out.print(rres + "R ");
    // System.out.print(pres + "P ");
    // System.out.println(sres + "S");

    // return Arrays.toString(containers);
    return resString;
  }

  public static String scramble(String inputString) {
      Random r = new Random(System.currentTimeMillis());
      // Convert your string into a simple char array:
      char a[] = inputString.toCharArray();

      // Scramble the letters using the standard Fisher-Yates shuffle, 
      for( int i=0 ; i<a.length ; i++ )
      {
          int j = r.nextInt(a.length);
          // Swap letters
          char temp = a[i]; a[i] = a[j];  a[j] = temp;
      }       

      return new String( a );
  }

  public static boolean checkCombination(String combination) {
    long rres = combination.chars().filter(ch -> ch == 'R').count();
    long pres = combination.chars().filter(ch -> ch == 'P').count();
    long sres = combination.chars().filter(ch -> ch == 'S').count();

    if (rres != amountR) {
      return false;
    }

    if (pres != amountP) {
      return false;
    }

    if (sres != amountS) {
      return false;
    }

    String res = Solver.solve(combination);

    if (winner(res.charAt(0), res.charAt(1)) != 'S') {
      return false;
    }

    return true;
  } 



  public static String createOutString(List<String> list){
    StringBuilder sb = new StringBuilder();
    for (var item : list) {
        sb.append(item);
    }
    return sb.toString();
      }
  
  
  public static String runLine(String fullLine) {
    var res = splitRounds(fullLine);
    for (int i = 0; i < 2; i++) {
      res = parseLine(res);
    }
    return createOutString(res);
  }

  public static List<String> splitRounds(String fullLine) {
    List<String> out = new ArrayList<String>();
    char[] arrayChar = fullLine.toCharArray();
    for(int i = 0; i < M; i+=2) {
      out.add(String.format("%s%s", arrayChar[i], arrayChar[i + 1]));
    }
    return out;
  }

  public static List<String> parseLine(List<String> parsedLine){
    var newLine = new ArrayList<String>(parsedLine.size()/2);
      for (int i = 0; i < parsedLine.size(); i+=2) {
        var item1 = parsedLine.get(i);
        var item2 = parsedLine.get(i+1);

        // System.out.println("i1" + item1);
        // System.out.println("i2" + item2);

        var winner1 = winner(item1.charAt(0), item1.charAt(1));
        var winner2 = winner(item2.charAt(0), item2.charAt(1));
        newLine.add(String.format("%c%c", winner1, winner2) );
      }
      // System.out.println(newLine);
      return newLine;
  }

  public static List<String> parser() {
    Scanner scanner = new Scanner(System.in);
    var lines = new ArrayList<String>();
    int lineIndex = 0;
    while (scanner.hasNext()) {
      
      var line = scanner.nextLine();
      if (lineIndex == 0) {
        N = Integer.parseInt(line.split(" ")[0]);
        M = Integer.parseInt(line.split(" ")[1]);
        
      } else {
        lines.add(line);
      }
      lineIndex++;
    }
    scanner.close();
    return lines;

  }

  public static char winner(char a, char b) {
    if (a == b)
      return a;

    if (a == 'R') {
      if (b == 'P')
        return b;
      if (b == 'S')
        return a;
    }

    if (a == 'P') {
      if (b == 'R')
        return a;
      if (b == 'S')
        return b;
    }

    if (a == 'S') {
      if (b == 'P')
        return a;
      if (b == 'R')
        return b;
    }

    return ' ';
  }
}
