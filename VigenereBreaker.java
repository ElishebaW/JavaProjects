/**
 This program breakes encrypted files using VigenereBreaker.

 This was creates as apart of the  Java Programming: Arrays, Lists, and Structured Data course
 on Coursera.

 */
import java.util.*;
import edu.duke.*;
import java.io.File;

public class VigenereBreaker {
  public String sliceString(String message, int whichSlice, int totalSlices) {
      StringBuilder newString = new StringBuilder();

      for (int i = whichSlice; i< message.length(); i= i+totalSlices){
          newString.append(message.charAt(i));

      }
      return newString.toString();
  }

  public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
      int[] key = new int[klength];

      for ( int i=0; i < key.length; i++) {
          String slice = sliceString(encrypted,i,klength);
          CaesarCracker cc = new CaesarCracker();
          int gkey = cc.getKey(slice);
          key[i] = gkey;

      }

      return key;
  }

  public HashSet<String>  readDictionary(FileResource fr){
      HashSet<String> hs = new HashSet();
      for (String s : fr.lines()) {
          String lower = s.toLowerCase();
          hs.add(lower);

      }
      return hs;
  }

  public int  countWords (String message, HashSet<String> dictionary) {
      String[] wordSplit= message.split("\\W+");
      int count = 0;
      for (int i = 0; i < wordSplit.length; i++) {
          if (dictionary.contains(wordSplit[i].toLowerCase())){
              count++;

          }

      }

      return count;
  }

  public String breakForLanguage(String encrypted, HashSet<String> hs) {

       String decryption = "";
       int prevde = 1;
      for (int i = 1; i< 100; i++ ) {
          String cChar = mostCommonCharln(hs);
          int [] keylength = tryKeyLength(encrypted, i,  cChar.charAt(0));

          VigenereCipher vc = new VigenereCipher(keylength);
          String dec =  vc.decrypt(encrypted);
          int count = countWords(dec,hs);
          if (count > prevde) {
              //System.out.println(keylength);
              decryption = dec;

              prevde = count;

          }

      }
      return decryption;

  }

  public String mostCommonCharln(HashSet<String> dictionary) {
      HashMap<String,Integer> letters = new  HashMap<String,Integer>();
      int count = 0;
      int prevCount = 0;
      String mostCommon = "";


      for(String s : dictionary) {


          String lower = s.toLowerCase();
          for(int i = 0; i< lower.length(); i++) {
             if( !letters.containsKey(lower)){
                 letters.put(Character.toString(lower.charAt(i)) , 1);
              } else {
                  letters.put(lower,letters.get(lower)+1);

              }

          }

      }

      for (String s : letters.keySet()){
           if (letters.get(s)> prevCount){
                 count = letters.get(s);
                 prevCount = count;
                 mostCommon = s;
              }

        }

      return mostCommon;
  }

  public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> language){

     for ( String s : language.keySet()) {
         String decrypted = breakForLanguage(encrypted, language.get(s));
         System.out.println(s);
         System.out.println("Lang :" + s + " " + decrypted.substring(0, 70));
      }



  }

  public void testMostCommonChar () {
      FileResource english = new FileResource();
      HashSet<String> lang = readDictionary(english);
      String common = mostCommonCharln(lang);
      System.out.println("The most common is" + common);
  }

  public void breakVigenere () {
      DirectoryResource dr = new DirectoryResource();
      HashMap<String, HashSet<String>>  allLangs = new  HashMap<String, HashSet<String>> ();
      for (File f : dr.selectedFiles()){
       String name = f.getName();
       FileResource fr = new FileResource (f);
       HashSet<String> rd = readDictionary(fr);
       allLangs.put(name,rd);

      }
      FileResource fr = new FileResource ();
      String message = fr.asString();
     // FileResource english = new FileResource();
     // HashSet<String> rd = readDictionary(english);
      //String test = breakForLanguage(message,rd);
      breakForAllLangs(message, allLangs);
      //System.out.println("This is the decrypted message " + test.substring(0,60));
  }

  public void testBreakForAllLangs () {

      FileResource file = new FileResource();
      String fileN = file.asString();

      //FileResource english = new FileResource();

  }
}
