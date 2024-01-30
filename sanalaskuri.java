import java.util.* ;
import java.io.File ;
import java.io.FileWriter ;
import java.io.BufferedReader ;
import java.io.FileReader ;
import java.io.IOException ;

public class sanalaskuri {
  
  public static String kysytiedosto() { //kysyy avattavan tiedoston
    Scanner myScanner = new Scanner(System.in) ; //stdin-lukija
    System.out.println("Syötä tiedoston nimi:") ;
    String filename = myScanner.nextLine() ;
    return filename ;
  }
  
  public static String laskeSanat(String filename){ //avaa tiedoston ja tekee sanalistan tiedostoon tilasto-etuliitteellä.
    
    File myFile = new File(filename) ;
    if(!myFile.exists()){ //jos tiedostoa ei ole, lopetetaan
      System.out.println("Tiedostoa ei löydy.") ;
      System.exit(0) ;
    }
    
    BufferedReader myReader = null ;
    List<String> sanat = new ArrayList<String>() ;
    //luetaan sisältö
    try {
      myReader = new BufferedReader(new FileReader(filename)) ;
      String currentLine ; //luettu rivi
      while (true) {
        currentLine = myReader.readLine() ;
        if(currentLine == null) {
          break ;
        } 
        //
        //kun kaikki toimii, tämä suoritetaan
        //
        //poistetaan kaikki muut kuin alfanumeeriset merkit
        currentLine = currentLine.replaceAll("[^a-zA-Z0-9åäöÅÄÖ]", " ") ;
                
        //pilkotaan rivi sanoiksi välilyöntien perusteella
        String[] rivinSanat = currentLine.toLowerCase().split("\\s+") ;
        for (String s : rivinSanat) {
          if(s.length() > 0) {
            sanat.add(s.trim()) ;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace() ;
    } finally {
      try {
        if (myReader != null) { //sulkee tiedoston
          myReader.close() ;
        }
      } catch (IOException ex) {
        ex.printStackTrace() ;
      }
    }
    //listan pituus
    int lp = sanat.size() ;
//    System.out.println("\n" + "Sanamäärä: " + lp + "\n") ;
    //muunnetaan lista arrayksi
    String[] sanalista = sanat.toArray(new String[lp]) ;
    //muodosta lukumäärälista/array
    int[] lkm = new int[lp] ;
    for(int i = 0 ; i < lkm.length ; i++) {
      lkm[i] = 1 ;
    }
    //analysoi sanat
    //käydään sanalista läpi
    for(int i = 0 ; i < lkm.length - 1 ; i++) {
      if(sanalista[i] != null) {
        String ref = sanalista[i] ;
        //tutkitaan, onko näitä sanoja listassa muitakin
        for(int j = i + 1 ; j < lkm.length ; j++) {
          if(sanalista[j] != null && sanalista[j].equals(ref)) {
            sanalista[j] = null ;
            lkm[i]++ ;
            lkm[j] = 0 ;
          }
        }
      }
    }
    //Siivotaan listat turhasta roinasta ennen järjestämistä
    //epätyhjien alkioiden määrä
    int nonZero = 0 ;
    for(int i = 0 ; i < lkm.length ; i++) {
      if(lkm[i] > 0) {
        nonZero++ ;
      }
    }
    String[] words = new String[nonZero] ;
    int[] c = new int[nonZero] ;
    
    //alustetaan järjestämättömät listat
    int ii = 0 ;
    for(int i = 0 ; i < lkm.length ; i++) {
      if(lkm[i] > 0) {
        words[ii] = sanalista[i] ;
        c[ii] = lkm[i] ;
        ii++ ;
      }
    }
    
    int mc, imax ;
    String apus ;
    
    //järjestä sanat
    for(int i = 0 ; i < c.length ; i++) {
      mc = 0 ;
      imax =  -1 ;
      //käydään alkiot läpi alkaen i:stä ja laitetaan paikkaan i löytynyt suurin arvo.
      for(int j = i ; j < c.length ; j++) {
        if(c[j] > mc) {
          mc = c[j] ;
          imax = j ;
        }
      }
      c[imax] = c[i] ;
      c[i] = mc ;
      apus = words[i] ;
      words[i] = words[imax] ;
      words[imax] = apus ;
    }
    //tulostetaan tiedostoon...
    //missä on viimeinen /-viiva?
    int kautta = filename.lastIndexOf("/") ;
    filename = filename.substring(0,kautta) + "/tilasto-" + filename.substring(kautta+1) ;
    try {
      File outputFile = new File(filename) ;
      if(!outputFile.exists()){ //jos tiedostoa ei ole, luodaan se
        outputFile.createNewFile() ;
      }
      FileWriter outputWriter = new FileWriter(outputFile) ;
      outputWriter.write(c.length + "\n") ;
      for(int i = 0 ; i < c.length ; i++) {
        outputWriter.write(words[i] + " " + c[i] + "\n") ;
      }
      outputWriter.close() ;
    } catch (IOException e) {
      System.out.println("Virhe kirjoitettaessa tiedostoon.") ;
      e.printStackTrace() ;
    }
    return filename ;
  }

  public static int koko(String filename){ // lukee tiedoston ensimmäisen rivin
    File myFile = new File(filename) ;
    if(!myFile.exists()){ //jos tiedostoa ei ole, lopetetaan
      System.out.println("Tiedostoa ei löydy.") ;
      System.exit(0) ;
    }

    BufferedReader myReader = null ;
    int N = 0 ;

    try {
      myReader = new BufferedReader(new FileReader(filename)) ;
      String currentLine ; //luettu rivi
      currentLine = myReader.readLine() ;
      N = Integer.valueOf(currentLine) ;
    } catch (IOException e) {
      System.out.println("Virhe luettaessa tiedostoa.") ;
      System.exit(0) ;
    } finally {
      try {
        if (myReader != null) { //sulkee tiedoston
          myReader.close() ;
        }
      } catch (IOException ex) {
        ex.printStackTrace() ;
      }
    }
    return N ;
  }

  public static String[] readData(String filename) { //lukee datan tiedostosta
    File myFile = new File(filename) ;
    if(!myFile.exists()){ //jos tiedostoa ei ole, lopetetaan
      System.out.println("Tiedostoa ei löydy.") ;
      System.exit(0) ;
    }
    BufferedReader myReader = null ;

    //luetaan tiedoston ensimmäinen rivi -> määritellään paikallisen taulukon pituus
    
    int N = 0 ;
    try {
      myReader = new BufferedReader(new FileReader(filename)) ;
      String currentLine ; //luettu rivi
      currentLine = myReader.readLine() ;
      N = Integer.valueOf(currentLine) ;
    } catch (IOException e) {
      System.out.println("Virhe luettaessa tiedostoa.") ;
      System.exit(0) ;
    }
    
    String[] data = new String[N] ;
    
    try {
      myReader = new BufferedReader(new FileReader(filename)) ;
      String currentLine ; //luettu rivi
      currentLine = myReader.readLine() ;
      for(int i = 0 ; i < N ; i++) {
        data[i] = myReader.readLine() ;
      }
    } catch (IOException e) {
      System.out.println("Virhe luettaessa tiedostoa.") ;
      e.printStackTrace() ;
    } finally {
      try {
        if (myReader != null) { //sulkee tiedoston
          myReader.close() ;
        }
      } catch (IOException ex) {
        ex.printStackTrace() ;
      }
    }
    return data ;
  }

  public static String[] listaaSanat(int N, String[] data){ //palauttaa sanat datasta
    String[] sanat = new String[N] ;
    for(int i = 0 ; i < N ; i++) {
      String[] rivinSanat = data[i].split("\\s+") ;
      sanat[i] = rivinSanat[0] ;
    }
    return sanat ;
  }

  public static int[] listaaLkm(int N, String[] data){ //palauttaa lukumäärät datasta
    int[] lkm = new int[N] ;
    for(int i = 0 ; i < N ; i++) {
      String[] rivinSanat = data[i].split("\\s+") ;
      lkm[i] = Integer.valueOf(rivinSanat[1]) ;
    }
    return lkm ;
  }

  //metodi, joka ottaa syötteen ja suorittaa haun
  
  public static String hakuToiminto(int N, String[] sanat, int[] lkm, String command, String hakusanat) {
    int osumia = 0, p ;
    String sana, output ;
    output = "" ;
    
    if(command.equals("haku")){
      p = hakusanat.indexOf(" ") ;          
      while(p > 0){
        sana = hakusanat.substring(0,p) ;
        hakusanat = hakusanat.substring(p+1) ;
        for(int i = 0 ; i < N ; i++) {
          if(sanat[i].indexOf(sana) > -1) {
            osumia = osumia + lkm[i] ;
            //outputWriter.write(sanat[i] + " (" + lkm[i] + ") " ) ;
            output = output + sanat[i] + " (" + lkm[i] + ") " ;
          }
        }
        p = hakusanat.indexOf(" ") ;
      }
      sana = hakusanat ;
      for(int i = 0 ; i < N ; i++) {
        if(sanat[i].indexOf(sana) > -1) {
          osumia = osumia + lkm[i] ;
          //outputWriter.write(sanat[i] + " (" + lkm[i] + ") ") ;
          output = output + sanat[i] + " (" + lkm[i] + ") " ;
        }
      }
    }
    if(command.equals("alku")){
          
      sana = hakusanat ;
      for(int i = 0 ; i < N ; i++) {
        if(sanat[i].indexOf(sana) == 0) {
          //outputWriter.write(sanat[i] + " (" + lkm[i] + ") ") ;
          osumia = osumia + lkm[i] ;
          output = output + sanat[i] + " (" + lkm[i] + ") " ;
        }
      }
    }
    output = output + "\n" + "Osumia: " + osumia + "\n";
    return output ;
  }
  
  public static void main(String[] args){
  
    String fname = kysytiedosto() ;
    fname = laskeSanat(fname) ;
    int N = koko(fname) ;
    String[] data = new String[N] ; //koko tiedoston data
    String[] sanat = new String[N] ; //sanalista
    int[] lkm = new int[N] ; //lukumäärälista
    
    data = readData(fname) ; //koko data tiedostosta
    sanat = listaaSanat(N, data) ; //ottaa sanat datasta
    lkm = listaaLkm(N, data) ; //ottaa lukumäärät datasta
    
    //valmistellaan tiedosto, jonne tulostetaan tulokset
    try {
      int kautta = fname.lastIndexOf("/") ;
      String filename = fname.substring(0,kautta) + "/tulokset-" + fname.substring(kautta+1) ;
      
      File outputFile = new File(filename) ;
      if(!outputFile.exists()){ //jos tiedostoa ei ole, luodaan se
        outputFile.createNewFile() ;
      }
      FileWriter outputWriter = new FileWriter(outputFile) ;
    
      //tässä välissä hakusyötteet 
      Scanner myScanner = new Scanner(System.in) ; //stdin-lukija
      System.out.println("Syötä 'haku sana' hakeaksesi osamerkkijonolla tai 'alku sana' hakeaksesi sanojen alusta. Syötä 'q' lopettaaksesi") ;
      System.out.print("> ") ;
      String input = myScanner.nextLine() ;
      int p ;
      while(!(input.equals("q"))) {
        outputWriter.write(input + "\n") ;
        
        p = input.indexOf(" ") ;
        String command = input.substring(0,p) ;
        String hakusanat = input.substring(p+1) ;
        
        String results = hakuToiminto(N, sanat, lkm, command, hakusanat) ;
        outputWriter.write(results) ;
        
        System.out.print("> ") ;
        input = myScanner.nextLine() ;
      }
      ///////////////
    
      outputWriter.close() ;  
    } catch (IOException e) {
      System.out.println("Tulostiedoston käsittely epäonnistui.") ;
      e.printStackTrace() ;
    }
  }

}
