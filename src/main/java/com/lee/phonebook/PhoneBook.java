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
    JScrollPane jScrollPane;

    HashMap<String, Employee> empList;
    Employee employee;

    static int nameId = 0;

    int MODE = 0; // Enter 구분 MODE
    String MENUMODE = ""; // 1~4번 까지 선택할 수 있는 메뉴
    int index = 0;

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

        jScrollPane = new JScrollPane(resultArea);

        inputTextField = new JTextField();
        inputTextField.addKeyListener(textInput());



        jPanel.add(menuArea, new BorderLayout().WEST);
        jPanel.add(jScrollPane, new BorderLayout().CENTER);
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

                // MENU 모드 입력받기
                String str = inputTextField.getText();

                if (e.getKeyCode() == 10) {
                    if (str.equals("1")) {
                        MENUMODE = str;
                        MODE += 1;
                    } else if (str.equals("2")) {
                        MENUMODE = str;
                        MODE += 1;
                    } else if (str.equals("3")) {
                        MENUMODE = str;
                        MODE += 1;
                    } else if (str.equals("4")) {
                        MENUMODE = str;
                        MODE += 1;
                    }
                }


                if (MENUMODE.equals("1")) {
                    inputTextField.setText("");
                    resultArea.append("1번 전화번호 전체보기를 선택하셨습니다.\n");
                    result_menu1();
                    clear();
                } else if (MENUMODE.equals("2")) {
                    System.out.println("현재 선택한 메뉴 모드 : " + MENUMODE);

                    if (MODE == 1 && e.getKeyCode() == 10) {
                        inputTextField.setText("");
                        resultArea.append("2번 전화번호 입력하기를 선택하셨습니다.\n");
                        resultArea.append("이름을 입력해주세요.\n");
                        MODE += 1;
                    } else if (MODE == 2 && e.getKeyCode() == 10) {
                        if (empList.containsKey(inputTextField.getText())) {
                            temp = (temp + inputTextField.getText() + nameId++) + ","; // 중복처리 로직이 들어가야
                        } else {
                            temp += inputTextField.getText() + ",";
                        }
                        resultArea.append(inputTextField.getText() + "\n");
                        inputTextField.setText("");
                        resultArea.append("나이를 입력해주세요.\n");
                        MODE += 1;
                    } else if (MODE == 3 && e.getKeyCode() == 10) {
                        temp += inputTextField.getText() + ",";
                        resultArea.append(inputTextField.getText() + "\n");
                        inputTextField.setText("");
                        resultArea.append("부서를 입력해주세요.\n");
                        MODE += 1;
                    } else if (MODE == 4 && e.getKeyCode() == 10) {
                        temp += inputTextField.getText() + ",";
                        resultArea.append(inputTextField.getText() + "\n");
                        inputTextField.setText("");
                        resultArea.append("번호를 입력해주세요.\n");
                        MODE += 1;
                    } else if (MODE == 5 && e.getKeyCode() == 10) {
                        temp += inputTextField.getText();
                        resultArea.append(inputTextField.getText() + "\n");
                        inputTextField.setText("");
                        resultArea.append("한번더 엔터 누르면 입력완료 \n");
                        MODE += 1;
                    } else if (MODE == 6 && e.getKeyCode() == 10) {
                        writeHashMap();
                    }
                } else if (MENUMODE.equals("3")) {

                    System.out.println("현재 선택한 메뉴 모드 : " + MENUMODE);

                    if (MODE == 1 && e.getKeyCode() == 10) {
                        inputTextField.setText("");
                        resultArea.append("3번 전화번호 수정하기를 선택하셨습니다.\n");
                        resultArea.append("이름을 입력해주세요.\n");
                        MODE += 1;
                    } else if (MODE == 2 && e.getKeyCode() == 10) {
                        if (empList.containsKey(inputTextField.getText())) {
                            temp += inputTextField.getText() + ",";
                            resultArea.append(inputTextField.getText() + "\n");
                            inputTextField.setText("");
                            resultArea.append("나이를 입력해주세요.\n");
                            MODE += 1;
                        } else {
                            resultArea.append("해당 정보가 없습니다. 다시 입력해주세요. \n");
                            MODE = 2;
                            temp = "";
                            inputTextField.setText("");
                        }
                    } else if (MODE == 3 && e.getKeyCode() == 10) {
                        temp += inputTextField.getText() + ",";
                        resultArea.append(inputTextField.getText() + "\n");
                        inputTextField.setText("");
                        resultArea.append("부서를 입력해주세요.\n");
                        MODE += 1;
                    } else if (MODE == 4 && e.getKeyCode() == 10) {
                        temp += inputTextField.getText() + ",";
                        resultArea.append(inputTextField.getText() + "\n");
                        inputTextField.setText("");
                        resultArea.append("번호를 입력해주세요.\n");
                        MODE += 1;
                    } else if (MODE == 5 && e.getKeyCode() == 10) {
                        temp += inputTextField.getText();
                        resultArea.append(inputTextField.getText() + "\n");
                        inputTextField.setText("");
                        resultArea.append("수정하기 완료\n");
                        resultArea.append(temp);
                        resultArea.append("한번더 엔터 누르면 입력완료 \n");
                        MODE += 1;
                    } else if (MODE == 6 && e.getKeyCode() == 10) {
                        writeHashMap();

                    }
                } else if (MENUMODE.equals("4")) {
                    System.out.println("현재 선택한 메뉴 모드 : " + MENUMODE);
                    if (MODE == 1 && e.getKeyCode() == 10) {
                        inputTextField.setText("");
                        resultArea.append("4번 전화번호 지우기를 선택하셨습니다.\n");
                        resultArea.append("지울려는 이름을 입력해주세요.\n");
                        MODE += 1;
                    } else if (MODE == 2 && e.getKeyCode() == 10) {
                        if (empList.containsKey(inputTextField.getText())) {
                            deleteInfo(empList.get(inputTextField.getText()));
                            empList.remove(inputTextField.getText());
                            resultArea.append("해당 정보가 삭제되었습니다. 처음으로 돌아갑니다. \n");
                            MODE = 0;
                            MENUMODE = "";
                            temp = inputTextField.getText();
                            inputTextField.setText("");
                        } else {
                            resultArea.append("해당 정보가 없습니다. 다시 입력해주세요. \n");
                            MODE = 1;
                            temp = "";
                            inputTextField.setText("");
                        }
                    }
                }
            }
        };

        return keyListener;
    }


//    public void modifyInfo(String data) {
//
//        employee = new Employee(data.split(","));
//        empList.put(employee.name, empList.get(employee.name));
//        writeFile();
//    }

    public void writeHashMap() {

        employee = new Employee(temp.split(","));
        empList.put(employee.name, employee);
        writeFile();

    }

    public void writeFile() {

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Properties.filePath, true));
            bufferedWriter.write(temp + "\r\n");
            clear();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteInfo(Object key) {

        System.out.println("delete values : " + key.toString());

        try {

            File inputFile = new File("phone.txt");
            File tempFile = new File(inputFile.getAbsolutePath() + ".txt");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            PrintWriter printWriter = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            while((line = bufferedReader.readLine()) != null) {

                System.out.println("여기 들어옴?ㅋ");

                if (!line.equals(key.toString())) {
                    printWriter.println(line);
                    printWriter.flush();
                }
            }

            printWriter.close();
            bufferedReader.close();

            if (!inputFile.delete()) {

            }

            if (!tempFile.renameTo(inputFile)) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clear() {
        MODE = 0;
        MENUMODE = "";
        temp = "";
        inputTextField.setText("");
        resultArea.append("원하는 메뉴를 선택하세요.\n");
    }
}
