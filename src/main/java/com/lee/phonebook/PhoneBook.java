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
    JTextArea menuTextArea, resultTextArea;
    JTextField userInputTextField;
    JScrollPane jScrollPane;

    HashMap<String, Employee> empList;
    Employee employee;

    static int nameId = 0;

    int enterMode = 0; // Enter 구분 enterMode
    String menuMode = ""; // 1~4번 까지 선택할 수 있는 메뉴

    String userData = "";

    public PhoneBook() {

        drawingPhoneBook();
        readPhoneBookFile();

    }


    public void drawingPhoneBook() {
        jFrame = new JFrame();
        jFrame.setSize(600, 300);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);


        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());



        menuTextArea = new JTextArea();
        menuTextArea.setText(Properties.menu_EX1);
        menuTextArea.setBorder(new TitledBorder("menuTextArea"));

        resultTextArea = new JTextArea();
        resultTextArea.setBorder(new TitledBorder("resultTextArea"));
        resultTextArea.append("원하는 메뉴를 누르고 Enter 입력\n");

        jScrollPane = new JScrollPane(resultTextArea);

        userInputTextField = new JTextField();
        userInputTextField.addKeyListener(textInput());



        jPanel.add(menuTextArea, new BorderLayout().WEST);
        jPanel.add(jScrollPane, new BorderLayout().CENTER);
        jPanel.add(userInputTextField, new BorderLayout().SOUTH);

        jFrame.setContentPane(jPanel);
        jFrame.setVisible(true);
    }

    public void readPhoneBookFile() {

       try {

           BufferedReader bufferedReader = new BufferedReader(new FileReader(Properties.filePath));

           empList = new HashMap<String, Employee>();

           while (true) {

               String line = bufferedReader.readLine();

               if (line == null) {
                   break;
               }
               employee = new Employee(line.split(","));
               empList.put(employee.name, employee);
           }

           bufferedReader.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    public void resultForMenu1() {

        userInputTextField.setText("");
        resultTextArea.append("1번 전화번호 전체보기를 선택하셨습니다.\n");

        for(String keys : empList.keySet()) {
            resultTextArea.append(empList.get(keys).toString() + "\n");
        }

        clear();
    }

    public void resultForMenu2(int keyCode) {

        if (enterMode == 1 && keyCode == 10) {
            userInputTextField.setText("");
            resultTextArea.append("2번 전화번호 입력하기를 선택하셨습니다.\n");
            resultTextArea.append("이름을 입력해주세요.\n");
            enterMode += 1;
        } else if (enterMode == 2 && keyCode == 10) {
            if (empList.containsKey(userInputTextField.getText())) {
                userData = (userData + userInputTextField.getText() + nameId++) + ","; // 중복처리 로직이 들어가야
            } else {
                userData += userInputTextField.getText() + ",";
            }
            resultTextArea.append(userInputTextField.getText() + "\n");
            userInputTextField.setText("");
            resultTextArea.append("나이를 입력해주세요.\n");
            enterMode += 1;
        } else if (enterMode == 3 && keyCode == 10) {
            userData += userInputTextField.getText() + ",";
            resultTextArea.append(userInputTextField.getText() + "\n");
            userInputTextField.setText("");
            resultTextArea.append("부서를 입력해주세요.\n");
            enterMode += 1;
        } else if (enterMode == 4 && keyCode == 10) {
            userData += userInputTextField.getText() + ",";
            resultTextArea.append(userInputTextField.getText() + "\n");
            userInputTextField.setText("");
            resultTextArea.append("번호를 입력해주세요.\n");
            enterMode += 1;
        } else if (enterMode == 5 && keyCode == 10) {
            userData += userInputTextField.getText();
            resultTextArea.append(userInputTextField.getText() + "\n");
            userInputTextField.setText("");
            resultTextArea.append("한번더 엔터 누르면 입력완료 \n");
            enterMode += 1;
        } else if (enterMode == 6 && keyCode == 10) {
            writeUserDataToHashMap();
            writeUserDataToFile();
            clear();
        }
    }

    public void resultForMenu3(int keyCode) {

        if (enterMode == 1 && keyCode == 10) {
            userInputTextField.setText("");
            resultTextArea.append("3번 전화번호 수정하기를 선택하셨습니다.\n");
            resultTextArea.append("이름을 입력해주세요.\n");
            enterMode += 1;
        } else if (enterMode == 2 && keyCode == 10) {
            if (empList.containsKey(userInputTextField.getText())) {
                userData += userInputTextField.getText() + ",";
                resultTextArea.append(userInputTextField.getText() + "\n");
                userInputTextField.setText("");
                resultTextArea.append("나이를 입력해주세요.\n");
                enterMode += 1;
            } else {
                resultTextArea.append("해당 정보가 없습니다. 다시 입력해주세요. \n");
                enterMode = 2;
                userData = "";
                userInputTextField.setText("");
            }
        } else if (enterMode == 3 && keyCode == 10) {
            userData += userInputTextField.getText() + ",";
            resultTextArea.append(userInputTextField.getText() + "\n");
            userInputTextField.setText("");
            resultTextArea.append("부서를 입력해주세요.\n");
            enterMode += 1;
        } else if (enterMode == 4 && keyCode == 10) {
            userData += userInputTextField.getText() + ",";
            resultTextArea.append(userInputTextField.getText() + "\n");
            userInputTextField.setText("");
            resultTextArea.append("번호를 입력해주세요.\n");
            enterMode += 1;
        } else if (enterMode == 5 && keyCode == 10) {
            userData += userInputTextField.getText();
            resultTextArea.append(userInputTextField.getText() + "\n");
            userInputTextField.setText("");
            resultTextArea.append("수정하기 완료\n");
            resultTextArea.append(userData + "\n");
            resultTextArea.append("한번더 엔터 누르면 입력완료 \n");
            enterMode += 1;
        } else if (enterMode == 6 && keyCode == 10) {
            modifyUserDataToHashMap(userData);
            modifyUserDataToFile(userData);
            clear();
        }
    }

    public void resultForMenu4(int keyCode) {

        if (enterMode == 1 && keyCode == 10) {
            userInputTextField.setText("");
            resultTextArea.append("4번 전화번호 지우기를 선택하셨습니다.\n");
            resultTextArea.append("지울려는 이름을 입력해주세요.\n");
            enterMode += 1;
        } else if (enterMode == 2 && keyCode == 10) {
            if (empList.containsKey(userInputTextField.getText())) {
                deleteUserData(empList.get(userInputTextField.getText()));
                empList.remove(userInputTextField.getText());
                resultTextArea.append("해당 정보가 삭제되었습니다. 처음으로 돌아갑니다. \n");
                enterMode = 0;
                menuMode = "";
                userData = userInputTextField.getText();
                clear();
            } else {
                resultTextArea.append("해당 정보가 없습니다. 다시 입력해주세요. \n");
                enterMode = 1;
                userData = "";
                userInputTextField.setText("");
            }
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
                String str = userInputTextField.getText();

                if (e.getKeyCode() == 10) {
                    if (str.equals("1")) {
                        menuMode = str;
                        enterMode += 1;
                    } else if (str.equals("2")) {
                        menuMode = str;
                        enterMode += 1;
                    } else if (str.equals("3")) {
                        menuMode = str;
                        enterMode += 1;
                    } else if (str.equals("4")) {
                        menuMode = str;
                        enterMode += 1;
                    }
                }


                if (menuMode.equals("1")) {
                    resultForMenu1();
                } else if (menuMode.equals("2")) {
                    System.out.println("현재 선택한 메뉴 모드 : " + menuMode);
                    resultForMenu2(e.getKeyCode());
                } else if (menuMode.equals("3")) {
                    System.out.println("현재 선택한 메뉴 모드 : " + menuMode);
                    resultForMenu3(e.getKeyCode());
                } else if (menuMode.equals("4")) {
                    System.out.println("현재 선택한 메뉴 모드 : " + menuMode);
                    resultForMenu4(e.getKeyCode());
                }
            }
        };

        return keyListener;
    }


    public void writeUserDataToHashMap() {

        employee = new Employee(userData.split(","));
        empList.put(employee.name, employee);

    }


    public void writeUserDataToFile() {

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Properties.filePath, true));
            bufferedWriter.write(userData + "\r\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyUserDataToHashMap(String info) {

        employee = new Employee(info.split(","));
        String name = employee.name;
        empList.put(name, employee);

    }

    public void modifyUserDataToFile(String info) {

        System.out.println("수정 전 : " + info);

        String[] newUserData = info.split(",");

        try {

            File inputFile = new File("phone.txt");
            File tempFile = new File(inputFile.getAbsolutePath() + ".txt");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            PrintWriter printWriter = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            while((line = bufferedReader.readLine()) != null) {

                String[] oldUserDate = line.split(",");

                if (!newUserData[0].equals(oldUserDate[0])) {
                    printWriter.println(line);
                } else {
                    printWriter.append(info);
                    printWriter.println("");
                }
            }

            if (!inputFile.delete()) {

            }

            if (!tempFile.renameTo(inputFile)) {

            }

            printWriter.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserData(Object key) {

        System.out.println("delete values : " + key.toString());

        try {

            File inputFile = new File("phone.txt");
            File tempFile = new File(inputFile.getAbsolutePath() + ".txt");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            PrintWriter printWriter = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            while((line = bufferedReader.readLine()) != null) {

                if (!line.equals(key.toString())) {
                    printWriter.println(line);
                    printWriter.flush();
                }
            }

            if (!inputFile.delete()) {

            }

            if (!tempFile.renameTo(inputFile)) {

            }

            printWriter.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clear() {
        enterMode = 0;
        menuMode = "";
        userData = "";
        userInputTextField.setText("");
        resultTextArea.append("원하는 메뉴를 입력하세요.\n");
    }
}
