/**
 This program finds common baby names in a certain year and the equivalent
 popular baby name in other years.

 This was creates as apart of the  Java Programming: Solving Problems with Software. course
 on Coursera.

 */

import edu.duke.*;
import java.io.File;
import org.apache.commons.csv.*;

public class BabyBirths {
    public void printNames () {
      FileResource fr = new FileResource();
      for (CSVRecord rec : fr.getCSVParser(false)) {
        int numBorn = Integer.parseInt(rec.get(2));
        if (numBorn <= 100) {
           System.out.println("Name " + rec.get(0) +
                              " Gender " + rec.get(1) +
                              " Num Born " + rec.get(2));
        }
      }
    }

  public void totalBirths (FileResource fr) {
       int totalBirths = 0;
       int totalGirls = 0;
       int totalBoys = 0;
    for (CSVRecord rec : fr.getCSVParser(false)) {

      if (rec.get(1).equals("M")){
           totalBoys += 1;
      }
      if (rec.get(1).equals("F")) {
           totalGirls += 1;

      }
    }
  System.out.println("Total Births" + totalBirths);
  System.out.println("Total Boys = " + totalBoys );
  System.out.println("Total girls =" + totalGirls);
 }

  public int getRank (int year, String name, String gender) {
     int rankF = 0;
     int rankM = 0;
     int endRank = 0;
     FileResource fr = new FileResource();

    for (CSVRecord rec : fr.getCSVParser(false)){
        String FileS = fr.asString();
        if (rec.get(1).equals(gender)){
          rankF += 1;
        }
        if (rec.get(1).equals(gender)) {
          rankM +=1;
        }

        if ( rec.get(0).equals(name) && rec.get(1).equals(gender)) {
          endRank = rankF;
          break;
        }

        if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
          endRank = rankM;
          break;
        }

        if (FileS.indexOf(name) == -1) {
          return -1;
        }
    }
    System.out.println(endRank);
    return endRank;
  }


  public int getRankF (int year, String name, String gender, File f) {
    int rankF = 0;
    int rankM = 0;
    int endRank = 0;
    FileResource fr = new FileResource(f);

    for (CSVRecord rec : fr.getCSVParser(false)){
      String FileS = fr.asString();
        if (rec.get(1).equals(gender)){
           rankF += 1;
        }

        if (rec.get(1).equals(gender)) {
           rankM +=1;
        }

        if ( rec.get(0).equals(name) && rec.get(1).equals(gender)) {
            endRank = rankF;
            break;
        }

        if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
            endRank = rankM;
            break;
        }

        if (FileS.indexOf(name) == -1) {
            return -1;
        }

    }
    return endRank;
  }



  public String getName ( int year, int rank, String gender) {
    int rankCount=0;
    int rankBirths=Integer.MAX_VALUE+1;
    String name="";
    boolean find = true;
    FileResource fr = new FileResource();
      for (CSVRecord rec : fr.getCSVParser(false)) {
        String genderN =rec.get(1);
        name=rec.get(0);
        int curNumOfBirths=Integer.parseInt(rec.get(2));
        if(gender.equals(genderN)){
              rankCount++;
        }
        if(curNumOfBirths<rankBirths){
            rank=rankCount;
            rankBirths=curNumOfBirths;
        }
      }

      if(rank==rankCount){
        find=false;
        break;
      }
      }
      if(find){
          System.out.println("NO NAME");
          return "NO NAME";

      }
      else{
          System.out.println(name);
          return name;
      }


  }

  public void whatIsNameInYear ( String name, int year,int newYear, String gender){
    int firstNameRank = getRank(year, name, gender);
    String secondName = getName(newYear, firstNameRank,gender);
    System.out.println( name + "born in " + year + "would be" + secondName + "if born in " + newYear);

  }

  public int yearOfHighestRank ( String name, String gender) {
      DirectoryResource dr = new DirectoryResource();
      int year = 0;
      int year2 = 0;
      int currYear = Integer.MAX_VALUE;
      int prevRank = 0;
      for (File f : dr.selectedFiles()) {
          FileResource fr = new FileResource(f);
          for (CSVRecord rec : fr.getCSVParser(false)){
              if (rec.get(1).equals(gender)) {
                  int currRank = Integer.parseInt(rec.get(2));
                  String fyear = f.getName();
                  if ( currRank < currYear && rec.get(0).equals(name) && currRank > prevRank){
                      year = Integer.parseInt(fyear.substring(3,7));
                     prevRank = currRank;
                  };
              }
          }
      }

      if (year > 0) {
        System.out.println(year + "" + year2);
        return year;

      } else {
        System.out.println(-1);
        return -1;
      }

  }

  public double getAverageRank( String name, String gender) {
      //select a range of files
      DirectoryResource dr = new DirectoryResource();
      //something to add the count of rank
      double avgRank = 0.0;
      int rank = 0;

      int endRank = 0;
      int prevEndRank = 0;
      double nofF = 0.0;
      // something to add the count of files
      // for each file
      for (File f : dr.selectedFiles()){
          nofF += 1.0;
          FileResource fr = new FileResource(f);
          String fyear = f.getName();
          int fintyear = Integer.parseInt(fyear.substring(3,7));
          for (CSVRecord  rec : fr.getCSVParser(false)){

          if ( rec.get(0).equals(name) && rec.get(1).equals(gender)) {
              System.out.println(rec.get(0));
              rank += getRankF(fintyear,rec.get(0), rec.get(1), f);

          }


      }
      }
      avgRank = rank/nofF;
       // for each record go through and find the name and gender

      if (avgRank> 0){
          System.out.println(avgRank);
          return avgRank;
      } else {
          System.out.println(-1);
        return -1;
      }
  }

  public int getTotalBirthsRankedHigher( int year, String name, String gender){
       FileResource fr = new FileResource();
       int rankHigherBoys = 0;
       int rankHigherGirls = 0;
       int finalGirls = 0;
       int finalBoys =0;
   for (CSVRecord rec : fr.getCSVParser(false)) {
       if(rec.get(1).equals("M")){
       rankHigherBoys += Integer.parseInt(rec.get(2));

       }
        if(rec.get(1).equals("F")){
        rankHigherGirls += Integer.parseInt(rec.get(2));

       }
       if (rec.get(1).equals(gender) && rec.get(0).equals(name) && rec.get(1).equals("M")){
         finalBoys = rankHigherBoys - Integer.parseInt(rec.get(2));
         break;

       }

       if (rec.get(1).equals(gender ) && rec.get(0).equals(name) && rec.get(1).equals("F")){
          finalGirls = rankHigherGirls - Integer.parseInt(rec.get(2));
         break;

     }

   }
 if (finalGirls >0) {
     System.out.println(finalGirls);
     return finalGirls;
  } else {
     System.out.println(finalBoys);
     return finalBoys;
    }
}

  public void yearWithHighestBirths () {
      DirectoryResource dr = new DirectoryResource();
      int year =0;

      for (File f : dr.selectedFiles()){
          FileResource fr  = new FileResource(f);
      }
  }
  public void testTotalBirths () {
    FileResource fr = new FileResource();
    totalBirths(fr);
  }

  public void testGetRank () {
    getRank(1971, "Frank", "M");
  }


  public void testGetName () {
    getName(1982,450, "M");
  }

  public void testwhatIsNameInYear() {
    whatIsNameInYear("Owen", 1974, 2014, "M");
  }


  public void testYearOfHighest() {
  yearOfHighestRank ("Mich", "M");
  }

  public void testAverageRank() {
    getAverageRank("Robert", "M");
  }


  public void testGetTotalBirthsRankedHigher(){
      getTotalBirthsRankedHigher(1990,"Emily", "F");
  }
}
