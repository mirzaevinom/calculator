package com.imirzaev.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Calculator {
    public JFrame frame;
    public JTextArea  textArea;
    public boolean equalsClicked = false;
    public boolean invalidExpression = false;
    public JPanel panel;
    public ArrayList<JButton> numberButtons;
    public JButton buttonDot, buttonEqual, buttonClear, 
    buttonLeftPar, buttonRightPar, buttonDivide, buttonMultiply,
    buttonSubtract, buttonAdd, buttonDel;
    public GridBagConstraints gbc;

    public static void main(String args[]) {

        Calculator myCalc = new Calculator();

        myCalc.initializeUI();

    }
    public void initializeUI(){
        this.initFrame();
        this.initPanel();
        this.initDisplayArea();
        this.initNumberButtons();
        this.initDot();
        this.initEqual();
        this.initClearDelete();
        this.initParanthesis();
        this.initOperators();
        frame.setVisible(true);        

    }

    public void initFrame(){
        //Creating the Frame
        frame = new JFrame("Calculator");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Move window to center
        frame.setSize(300, 400);
        frame.setResizable(false);
        frame.setVisible(true);        
    }


    public void initPanel(){
        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        GridBagLayout frameLayout = new GridBagLayout();
        panel.setLayout(frameLayout);

        frame.setContentPane(panel);
    }

    public void initDisplayArea(){
        textArea = new JTextArea(2,1);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEnabled(false);
        Font font1 = new Font("Arial", Font.BOLD, 30);
        textArea.setBackground(Color.WHITE);
        textArea.setFont(font1);

        gbc = this.getDisplayGBC();
        panel.add(textArea, gbc);
    }

    public void initNumberButtons(){

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
            gbc = this.getButtonGBC(row, col);

            JButton button = new JButton(""+i);
            this.setNumberListener(button);
            button = this.setNumberColors(button);
            panel.add(button, gbc);
            numberButtons.add(button);
            
        }
    }

    public void initDot(){
        GridBagConstraints gbc = this.getButtonGBC(4, 1);
        buttonDot = new JButton(".");
        this.setNumberListener(buttonDot);
        buttonDot = this.setNumberColors(buttonDot);
        panel.add(buttonDot, gbc);

    }

    public void initEqual(){

        gbc = this.getButtonGBC(4, 2);
        buttonEqual = new JButton("=");
        buttonEqual = this.setNumberColors(buttonEqual);
        this.setEqualListener(buttonEqual);
        panel.add(buttonEqual, gbc);
    }

    public void initClearDelete(){
        gbc = this.getButtonGBC(0, 0);
        buttonClear = new JButton("C");
        this.setClearListener(buttonClear);
        panel.add(buttonClear, gbc);

        gbc = this.getButtonGBC(0, 3);
        buttonDel = new JButton("←");
        this.setDeleteListener(buttonDel);
        panel.add(buttonDel, gbc);        
    }
    public void initParanthesis(){

        gbc = this.getButtonGBC(0, 1);
        buttonLeftPar = new JButton("(");
        this.setNumberListener(buttonLeftPar);
        panel.add(buttonLeftPar, gbc);

        gbc = this.getButtonGBC(0, 2);
        buttonRightPar = new JButton(")");
        this.setNumberListener(buttonRightPar);
        panel.add(buttonRightPar, gbc);
    }

    public void initOperators(){

        gbc = this.getButtonGBC(1, 3);
        buttonDivide = new JButton("÷");
        this.setNumberListener(buttonDivide);
        panel.add(buttonDivide, gbc);

        gbc = this.getButtonGBC(2, 3);
        buttonMultiply = new JButton("×");
        this.setNumberListener(buttonMultiply);
        panel.add(buttonMultiply, gbc);

        gbc = this.getButtonGBC(3, 3);
        buttonSubtract = new JButton("-");
        this.setNumberListener(buttonSubtract);
        panel.add(buttonSubtract, gbc);

        gbc = this.getButtonGBC(4, 3);
        buttonAdd = new JButton("+");
        this.setNumberListener(buttonAdd);
        panel.add(buttonAdd, gbc);
    }

    public GridBagConstraints getDisplayGBC(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.25;
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
            if (invalidExpression){
                str = "";
            } else if (equalsClicked){
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
            equalsClicked = false;
            invalidExpression = false;
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
            if (equalsClicked){
                return;
            }
            String str = textArea.getText();
            str = str.replace("×", "*");
            str = str.replace("÷", "/");

            if (str.length()>0){
                try{
                    double result = Evaluator.eval(str);
                    String prettyResult = prettifyResult(result);
                    textArea.setText(prettyResult);
                    equalsClicked = true;
                } catch(RuntimeException runExc){
                    System.out.println(runExc.getMessage());
                    textArea.setText("Invalid Expression");
                    invalidExpression=true;
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
        button.setFont(new Font("Arial", Font.BOLD, 18));
        // button.setOpaque(true);
        // button.setBorderPainted(true);
        return button;
    }
}
