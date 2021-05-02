//Usually you will require both swing and awt packages
// even if you are working with just swings.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Calculator {
    public static JFrame frame;
    public static JTextArea  textArea;
    public static JButton button7, button8, button9;
    public static boolean equalsClicked = false;
    public static boolean invalidExpression = false;

    public static void main(String args[]) {

        Calculator myCalc = new Calculator();

        myCalc.creatFrame();

        // JTextArea textArea = new JTextArea();
        
        textArea = new JTextArea(2,1);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEnabled(false);
        Font font1 = new Font("Arial", Font.BOLD, 30);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.WHITE);

        
        textArea.setFont(font1);
        JPanel panel = new JPanel();
        
        panel.setBackground(Color.DARK_GRAY);
        frame.setContentPane(panel);
        frame.setVisible(true);
        // frame.add(panel);
        
        GridBagLayout frameLayout = new GridBagLayout();
        panel.setLayout(frameLayout);

        GridBagConstraints gbc = myCalc.getDisplayGBC();
        panel.add(textArea, gbc);
        ArrayList<JButton> numberButtons = new ArrayList<JButton>();
        for (int i=0; i<10; i++){
            int row, col;
            if (i==0){
                row=4;
                col=0;
            } else{
                row = 3 - (i-1)/3;
                col = (i-1)%3;
            }
            gbc = myCalc.getButtonGBC(row, col);
            JButton button = new JButton(""+i);
            myCalc.setNumberListener(button);
            button = myCalc.setNumberColors(button);
            panel.add(button, gbc);
            numberButtons.add(button);
            
        }
  
        gbc = myCalc.getButtonGBC(4, 1);
        JButton buttonDot = new JButton(".");
        myCalc.setNumberListener(buttonDot);
        buttonDot = myCalc.setNumberColors(buttonDot);

        panel.add(buttonDot, gbc);

        gbc = myCalc.getButtonGBC(4, 2);
        JButton buttonEqual = new JButton("=");
        buttonEqual = myCalc.setNumberColors(buttonEqual);
        myCalc.setEqualListener(buttonEqual);
        panel.add(buttonEqual, gbc);

        gbc = myCalc.getButtonGBC(0, 0);
        JButton buttonClear = new JButton("C");
        myCalc.setClearListener(buttonClear);
        panel.add(buttonClear, gbc);

        gbc = myCalc.getButtonGBC(0, 1);
        JButton buttonLeftPar = new JButton("(");
        myCalc.setNumberListener(buttonLeftPar);
        panel.add(buttonLeftPar, gbc);

        gbc = myCalc.getButtonGBC(0, 2);
        JButton buttonRightPar = new JButton(")");
        myCalc.setNumberListener(buttonRightPar);
        panel.add(buttonRightPar, gbc);

        gbc = myCalc.getButtonGBC(0, 3);
        JButton buttonDel = new JButton("<-");
        myCalc.setDeleteListener(buttonDel);
        panel.add(buttonDel, gbc);


        gbc = myCalc.getButtonGBC(1, 3);
        JButton buttonDivide = new JButton("/");
        myCalc.setNumberListener(buttonDivide);
        panel.add(buttonDivide, gbc);

        gbc = myCalc.getButtonGBC(2, 3);
        JButton buttonMultiply = new JButton("*");
        myCalc.setNumberListener(buttonMultiply);
        panel.add(buttonMultiply, gbc);

        gbc = myCalc.getButtonGBC(3, 3);
        JButton buttonSubtract = new JButton("-");
        myCalc.setNumberListener(buttonSubtract);
        panel.add(buttonSubtract, gbc);

        gbc = myCalc.getButtonGBC(4, 3);
        JButton buttonAdd = new JButton("+");
        myCalc.setNumberListener(buttonAdd);
        panel.add(buttonAdd, gbc);

        frame.setVisible(true);
    }

    public void creatFrame(){
        //Creating the Frame
        frame = new JFrame("Calculator");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Move window to center
        frame.setSize(300, 400);
        frame.setResizable(false);
    }
    public GridBagConstraints getDisplayGBC(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.25;
        // gbc.weightx = 4;
        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        return gbc;
    }
    public GridBagConstraints getButtonGBC(int row, int col){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;

        gbc.gridx = col;
        gbc.gridy = row+1;
        return gbc;
    }
    
    public void setNumberListener(JButton button){
        ActionListener aListener =  new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            String str = textArea.getText();
            if (Calculator.invalidExpression){
                str = "";
            } else if (Calculator.equalsClicked){
                Character bChar = button.getText().charAt(0);
                if (!Validator.arightmeticOps.contains(bChar)){
                    str="";
                }
            }
            String newStr = str.concat(button.getText());
            if (Validator.checkValidity(newStr)){
                str = newStr;
            }
            textArea.setText(str);
            Calculator.equalsClicked = false;
            Calculator.invalidExpression = false;
          }
        };
        button.addActionListener(aListener);
    } 
    
    public void setDeleteListener(JButton button){
        ActionListener aListener =  new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            String str  = textArea.getText();
            if (str.length()>0){
                textArea.setText(str.substring(0, str.length()-1));
            }
          }
        };
        button.addActionListener(aListener);
    }
    
    public void setClearListener(JButton button){
        ActionListener aListener =  new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            textArea.setText("");
          }
        };
        button.addActionListener(aListener);
    }    
    
    public void setEqualListener(JButton button){
        ActionListener aListener =  new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            if (Calculator.equalsClicked){
                return;
            }
            String str = textArea.getText();
            if (str.length()>0){
                try{
                    double result = Evaluator.eval(str);
                    String prettyResult = prettifyResult(result);
                    textArea.setText(prettyResult);
                    Calculator.equalsClicked = true;
                } catch(RuntimeException runExc){
                    System.out.println(runExc.getMessage());
                    textArea.setText("Invalid Expression");
                    Calculator.invalidExpression=true;
                }
                

            } 

          }
        };
        button.addActionListener(aListener);
    }
    public String prettifyResult(double result){
        String prettyResult = String.format("%1.4f", result);
        String[] parts = prettyResult.split("\\."); 

        int numNonZero = 4;
        for (int i=3; i>=0;i--){
            if (parts[1].charAt(i)!='0'){
                break;
            }
            numNonZero--;
        }
        if (numNonZero==0){
            prettyResult = parts[0];
        } else{
            prettyResult = parts[0] + "." + parts[1].substring(0,numNonZero);
        }
        return prettyResult;
    }
    
    public JButton setNumberColors(JButton button){
        button.setBackground(Color.LIGHT_GRAY);
        // button.setOpaque(true);
        // button.setBorderPainted(true);
        return button;
    }
}
