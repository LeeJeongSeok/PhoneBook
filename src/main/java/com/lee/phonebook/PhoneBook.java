package com.lee.phonebook;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.HashMap;

public class PhoneBook {


    JFrame jFrame;
    JPanel jPanel;
    JTextArea menuArea, resultArea;
    JTextField inputTextField;

    HashMap<String, Employee> empList;
    Employee employee;

    int MODE = 0;
    int AUTOINCREASE = 0;

    String temp = "";

    public PhoneBook() {

        drawingPhoneBook();
        readFile();

    }


    public void drawingPhoneBook() {
        jFrame = new JFrame();
        jFrame.setSize(600, 300);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);


        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        menuArea = new JTextArea();
        menuArea.setText(Properties.menu_EX1);
        menuArea.setBorder(new TitledBorder("menuArea"));

        resultArea = new JTextArea();
        resultArea.setBorder(new TitledBorder("resultArea"));
        resultArea.append("원하는 메뉴를 누르고 Enter 입력\n");

        inputTextField = new JTextField();
        inputTextField.addKeyListener(textInput());


        jPanel.add(menuArea, new BorderLayout().WEST);
        jPanel.add(resultArea, new BorderLayout().CENTER);
        jPanel.add(inputTextField, new BorderLayout().SOUTH);

        jFrame.setContentPane(jPanel);
        jFrame.setVisible(true);
    }

    public void readFile() {

       try {
           BufferedReader bufferedReader = new BufferedReader(new FileReader(Properties.filePath));

           empList = new HashMap<String, Employee>();

           while (true) {

               String txt = bufferedReader.readLine();

               if (txt == null) {
                   break;
               }

               employee = new Employee(txt.split(","));

               empList.put(employee.name, employee);

           }

           bufferedReader.close();
       } catch (IOException e) {
           e.printStackTrace();
       }


    }

    public void result_menu1() {

        for(String keys : empList.keySet()) {

            resultArea.append(empList.get(keys).toString() + "\n");
        }
    }


    // 중복처리 로직
    public String chkName(String name) {

        for(String keys : empList.keySet()) {

            if (name.equals(keys)) {
                AUTOINCREASE += 1;
                name += AUTOINCREASE;
            }
        }

        return name;

    }


    public void chooseMode() {

        String str = inputTextField.getText();

        if (str.equals("1")) {
            resultArea.append("1번 전화번호 전체보기를 선택하셨습니다.\n");
            result_menu1();
            inputTextField.setText("");
            MODE = 0;
            resultArea.append("추가적으로 다른 모드를 선택할려면 번호를 입력하세요.\n");
        } else if (str.equals("2")) {
            resultArea.append("2번 전화번호 입력하기를 선택하셨습니다.\n");
            resultArea.append("이름을 입력해주세요.\n");
            temp = chkName(inputTextField.getText());
            inputTextField.setText("");
        } else if (str.equals("3")) {
            resultArea.append("3번 전화번호 지우기를 선택하셨습니다.\n");
            resultArea.append("지울려는 사용자의 이름을 입력해주세요.\n");
            inputTextField.setText("");
        } else if (str.equals("4")) {
            resultArea.append("4번 전화번호 수정하기를 선택하셨습니다.\n");
            resultArea.append("수정할려는 사용자의 이름을 입력해주세요.\n");
            result_menu1();
            inputTextField.setText("");
        }
    }


    public KeyListener textInput() {

        KeyListener keyListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
//				System.out.println(e.getKeyCode());

                /*
                1~4 케이스별로 분거처리를 따로해야할거같음
                 */

                // 1번에 관한 로직


               // 2번에 관한 로직
                if (MODE == 0 && e.getKeyCode() == 10) {
                    MODE += 1;
                    chooseMode();
                    System.out.println("Mode : " + MODE);
                } else if (MODE == 1 && e.getKeyCode() == 10) {
                    System.out.println("name : " + inputTextField.getText());
                    changeMode();
                    System.out.println("Mode : " + MODE);
                } else if (MODE == 2 && e.getKeyCode() == 10) {
                    System.out.println("age : " + inputTextField.getText());
                    changeMode();
                    System.out.println("Mode : " + MODE);
                } else if (MODE == 3 && e.getKeyCode() == 10) {
                    System.out.println("department : " + inputTextField.getText());
                    changeMode();
                    System.out.println("Mode : " + MODE);
                } else if (MODE == 4 && e.getKeyCode() == 10) {
                    System.out.println("phone : " + inputTextField.getText());
                    changeMode();
                    System.out.println("Mode : " + MODE);
                } else if (MODE == 5) {
                    System.out.println("final : " + MODE);
                    System.out.println("temp : " + temp);
                    writeHashMap();
                }
            }

        };

        return keyListener;
    }

    public void changeMode() {
        temp += inputTextField.getText() + ",";
        MODE += 1;
        switch (MODE) {
            case 2 :
                resultArea.append(inputTextField.getText() + "\n");
                resultArea.append("나이를 입력해주세요.\n");
                break;
            case 3 :
                resultArea.append(inputTextField.getText() + "\n");
                resultArea.append("부서를 입력해주세요.\n");
                break;
            case 4 :
                resultArea.append(inputTextField.getText() + "\n");
                resultArea.append("번호를 입력해주세요.\n");
                break;
            default:
                resultArea.append(inputTextField.getText() + "\n");
                resultArea.append("엔터를 한번 더 입력하면 저장됩니다.");

        }
        inputTextField.setText("");
    }

    public void writeHashMap() {

        employee = new Employee(temp.split(","));

        empList.put(employee.name, employee);

        writeFile();

    }

    public void writeFile() {

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Properties.filePath, true));
            bufferedWriter.write(temp + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
