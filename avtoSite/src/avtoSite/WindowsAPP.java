package avtoSite;

import java.awt.*;
import java.awt.event.*;

public class WindowsAPP {

   private Frame mainFrame;
   private Label headerLabel;
   private Label statusLabel;
   private Panel controlPanel;
   private Panel controlPanelText;
   private Panel controlPanelStart;
   private float koefToCalc = 0;
   private String filenametoopen = new String("");
   
   public WindowsAPP(){
      prepareGUI();
   }

   public static void main(String[] args){
	   WindowsAPP  WindowsAPP1 = new WindowsAPP();
	   WindowsAPP1.showButtonDemo();
   }

   private void prepareGUI(){
      mainFrame = new Frame("Update from DBF file");
      mainFrame.setSize(600,600);
      mainFrame.setLayout(new GridLayout(4, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new Label();
      headerLabel.setAlignment(Label.CENTER);
      statusLabel = new Label();        
      statusLabel.setAlignment(Label.CENTER);
      statusLabel.setSize(350,100);

      controlPanel = new Panel();
      controlPanel.setLayout(new FlowLayout());

      controlPanelText = new Panel();
      controlPanelText.setLayout(new GridLayout(3,3));

      controlPanelStart = new Panel();
      controlPanelStart.setLayout(new FlowLayout());
      
      mainFrame.add(headerLabel);
     // mainFrame.add(statusLabel);
      mainFrame.add(controlPanelText);
      mainFrame.add(controlPanel);
      mainFrame.add(controlPanelStart);
      mainFrame.setVisible(true);  
   }

   private void showButtonDemo(){
      headerLabel.setText("Вводите націнку, вираховуєте коеф, вводите файлик і натискаєте старт:"); 
	  statusLabel.setText("______");
      Button okButton = new Button("Load File");
      Button submitButton = new Button("Calculate K");
      Button cancelButton = new Button("Start Update!");
      Label cursLabel = new Label(); 
      cursLabel.setText("$:");
      cursLabel.setAlignment(Label.RIGHT);
      Label koefLabel = new Label(); 
      koefLabel.setText("Koef:");
      koefLabel.setAlignment(Label.RIGHT);
      final TextField textfieldCurs=new TextField("25.4",10);
      final TextField textfieldKoef=new TextField("1.3",10);
      final Label koefLabel1 = new Label();
      koefLabel1.setAlignment(Label.LEFT);
      koefLabel1.setText("0");
      final Label FileLabel = new Label();
      FileLabel.setAlignment(Label.LEFT);
	  FileLabel.setText("___________________________________________________________________________"); 
      FileLabel.setSize(350,100);		 
      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 FileDialog fd = new FileDialog(mainFrame, "Choose a file", FileDialog.LOAD);
        	 //fd.setDirectory("C:\\");
        	 fd.setFile("*.dbf");
        	 fd.setVisible(true);
        	 String filename = fd.getFile();
        	 String textS = fd.getDirectory()+filename;
        	 if (filename == null){
        	 	 filenametoopen = "";
        		 FileLabel.setText("___________________________________________________________________________");
        	 }
        	 else{
        	 	 filenametoopen = textS;
        		 FileLabel.setText(filenametoopen);
        	 }

         }
      });

      submitButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	String textS = textfieldKoef.getText()+"/"+textfieldCurs.getText();;
            statusLabel.setText(textS);
            float tempcurs = Float.parseFloat(textfieldCurs.getText());
            float tempkoef = Float.parseFloat(textfieldKoef.getText());
            koefToCalc = tempkoef/tempcurs;
            koefLabel1.setText(textS+" = "+Float.toString(koefToCalc));
         }
      });

      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Cancel Button clicked.");
         }
      });
      
      controlPanel.add(FileLabel);
      controlPanelStart.add(okButton);
      controlPanelText.add(cursLabel);
      controlPanelText.add(textfieldCurs);
      controlPanelText.add(koefLabel);
      controlPanelText.add(textfieldKoef);
      controlPanelText.add(submitButton);
      controlPanelText.add(koefLabel1);
      controlPanelStart.add(cancelButton);       
      mainFrame.setVisible(true);  
   }
}