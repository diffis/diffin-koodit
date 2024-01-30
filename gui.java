import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.filechooser.* ;
import java.io.* ;

public class gui {
  private JFrame frame ; //koko ikkuna
  private JPanel openPanel ; //tiedoston avaamispaneeli
  private JLabel openedFileLabel ; //onko joku filu auki vai eikö
  private JPanel contentPanel ; //hakujuttujen paneeli
  private JButton submitButton ; //tilastointipainike
  private JPanel searchPanel ; //hakukentän jne. paneeli
  private JLabel searchLabel ; //Hakukehote
  private JTextField searchField ; //tekstikenttä hakusanalle
  private JCheckBox searchBox ; //checkbox jos haetaan sanan alusta
  private JButton searchButton ; //hakunappi
  private JTextArea resultArea ; //tekstialue tulosten näyttöön
  private JScrollPane scrollPane ; //scorillialua, pitkät tulokset näkyy
  private JPanel controlPanel ; //lopetusnapin paneeli
  static String filename ; //käsiteltävä tiedosto
  static String show = "" ; //näytettävät tulokset
  static int N ; //taulukkojen pituus
  static String[] data, sanat ; //sanadata, pelkät sanat
  static int[] lkm ; //lukumäärätaulukko
  
  public gui() { //gui constructor
    prepareGUI() ;
  }
  
  private void prepareGUI() { //elementtien perusasetukset
    frame = new JFrame("Sanalaskuri") ;
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
    frame.setSize(420,300) ; //ikkunan koko
    frame.setLocationRelativeTo(null) ; //ikkuna keskelle näyttöä
    
    openPanel = new JPanel() ;
    openPanel.setLayout(new FlowLayout()) ;
    
    openedFileLabel = new JLabel("", JLabel.LEFT) ;
    openedFileLabel.setText("Ei valittua tiedostoa.") ;
    
    contentPanel = new JPanel() ;
    contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.PAGE_AXIS)) ;
    
    searchPanel = new JPanel() ;
    searchPanel.setLayout(new FlowLayout()) ;
    searchLabel = new JLabel("", JLabel.LEFT) ;
    searchPanel.add(searchLabel) ;
    searchField = new JTextField(16) ; //hakukentän pituus
    searchBox = new JCheckBox("Sanan alusta") ;

    resultArea = new JTextArea(7,30) ; //tuloskentän koko
    scrollPane = new JScrollPane(resultArea) ; //scrollialueen sisälle
    resultArea.setEditable(false) ; //tuloksia ei voi muokata
    resultArea.setLineWrap(true) ; //pitkät rivit rivitetään
    resultArea.setWrapStyleWord(true) ; //rivitys välilyönnin kohdalta

    controlPanel = new JPanel() ;
    controlPanel.setLayout(new FlowLayout()) ;
    
    frame.add(openPanel,BorderLayout.PAGE_START) ;
    frame.add(contentPanel,BorderLayout.CENTER) ;
    frame.add(controlPanel,BorderLayout.PAGE_END) ;
    frame.setVisible(true) ;
  }

  public static void main(String[] args) {
    gui gui = new gui() ;
    gui.showEvent() ; 
  }

  private void showEvent() { //nappien määrittelyt
    
    JButton openButton = new JButton("Avaa tiedosto") ;
    submitButton = new JButton("Tilastoi sanat") ;
    searchButton = new JButton("Hae") ;
    JButton okButton = new JButton("OK") ;
    
    openButton.setActionCommand("Avaa") ;
    submitButton.setActionCommand("Submit") ;
    searchButton.setActionCommand("Hae") ;
    okButton.setActionCommand("OK") ;
    
    openButton.addActionListener(new ButtonClickListener()) ;
    submitButton.addActionListener(new ButtonClickListener()) ;
    searchButton.addActionListener(new ButtonClickListener()) ;
    okButton.addActionListener(new ButtonClickListener()) ;
    
    openPanel.add(openButton) ;
    openPanel.add(openedFileLabel) ;
    controlPanel.add(okButton) ;
    frame.setVisible(true) ;
  }
  
  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand() ;
      
      if(command.equals("Avaa")) { //avaustoiminto
        UIManager.put("FileChooser.readOnly", Boolean.TRUE) ; //tiedostoavaajan kautta ei voi muokata tiedostoja
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()) ; //avaaja aluksi käyttäjän kotihakemistossa
        fileChooser.setAcceptAllFileFilterUsed(false) ; //ei hyväksytä mitä vain
        fileChooser.setDialogTitle("Valitse .txt-tiedosto") ;
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Vain -txt-tiedosto", "txt") ; //sallitaan vain tekstitiedosto
        fileChooser.addChoosableFileFilter(restrict) ; 
        int option = fileChooser.showOpenDialog(frame) ; //avataan avaaja
        if(option == JFileChooser.APPROVE_OPTION) { //jos sallittu operaatio tehtiin
          File file = fileChooser.getSelectedFile() ;
          String showFilename = file.getName() ;
          openedFileLabel.setText(showFilename) ; //näytetään avattu tiedosto
          filename = file.getAbsolutePath() ; //käsiteltävä tiedosto
          submitButton.setAlignmentX(Component.CENTER_ALIGNMENT) ; //näytetään tilastointinappi keskellä
          contentPanel.add(submitButton) ; //lisätään nappi näkyväksi
          frame.setVisible(true) ;
        }
      } else if(command.equals("Submit")) { //kun tilastointinappi painettu
        filename = sanalaskuri.laskeSanat(filename) ; //tilastoidaan sanat ja tallennetaan tilastotiedoston nimi muuttujaan
        N = sanalaskuri.koko(filename) ; //tutkitaan, montako riviä dataa
        data = new String[N] ;
        data = sanalaskuri.readData(filename) ; //luetaan data
        sanat = new String[N] ;
        sanat = sanalaskuri.listaaSanat(N, data) ; //otetaan sanat erilleen
        lkm = new int[N] ;
        lkm = sanalaskuri.listaaLkm(N, data) ; //otetaan numerot erilleen
        searchLabel.setText("Kirjoita hakusana:") ; //hakukehote
        searchPanel.add(searchField) ; //hakukenttä näkyviin
        searchPanel.add(searchButton) ; //hakunappi näkyviin
        searchPanel.add(searchBox) ; //sanan alusta haku näkyviin
        searchPanel.add(scrollPane) ; //tulosalue näkyviin
        contentPanel.add(searchPanel) ; //koko hakujuttu näkyviin
        frame.setVisible(true) ;
      } else if(command.equals("Hae")) { //kun hakunappia painetaan
        String hakusanat = searchField.getText() ; //hakusanat hakukentästä
        Boolean alusta = searchBox.isSelected() ; //tutkitaan onko sanan alusta haku valittu
        if(hakusanat.length() > 0) { //jos hakusana on annettu
          String result ;
          if(alusta) {
            result = "alku " + hakusanat + "\n" ; //hakukomento
            result = result + sanalaskuri.hakuToiminto(N, sanat, lkm, "alku", hakusanat) ; //hakutulos
          } else {
            result = "haku " + hakusanat + "\n" ; //hakukomento
            result = result + sanalaskuri.hakuToiminto(N, sanat, lkm, "haku", hakusanat) ; //hakutulos
          }
          show = show + result + "\n"; //kaikki hakutulokset
          resultArea.setText(show) ; //näytetään tulosalueessa
        }
      } else if(command.equals("OK")) { //ok-nappi painettu -> lopetetaan
        System.exit(0) ;
      }
    }
  }

}
