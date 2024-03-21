import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.concurrent.SynchronousQueue;
import javax.swing.*;

public class Calculator extends JFrame {
    public class nod{
        public nod next;
        public String val;
        public nod(String a)
        {
            val=a;
        }

    };

    public boolean isGreater(String a,String b)//a ii ce ii pe stiva; b ii ce vreau sa pun pe stiva
    {
        if(b.equals("(")||a.equals("("))
            return false;
        if((a.equals("+")||a.equals("-"))&&(b.equals("*")||b.equals("/")))
            return false;
        return true;

    }
    public class Stiva
    {
        private nod cap;
        public Stiva()
        {
            cap=null;
        }
        public void push(String a)
        {
            if(cap==null)
            {
                cap=new nod(a);
                cap.next=null;
            }
            else
            {
                nod tmp=new nod(a);
                tmp.next=cap;
                cap=tmp;
            }
        }
        public String top()
        {
            if(!this.isEmpty())
                return cap.val;
            System.out.println("Coada goala");
            return "";
        }
        public boolean isEmpty()
        {
            return cap==null;
        }
        public String pop()
        {
            if(this.isEmpty())
            {
                System.out.println("Coada goala!");
                return "";

            }
            else
            {
                String a=cap.val;
                cap=cap.next;
                return a;
            }

        }
        public void print()
        {
            nod parc=this.cap;
            while(parc!=null) {
                System.out.println(parc.val);
                parc = parc.next;
            }
        }

    };
    JButton digits[] = {
            new JButton(" 0 "),
            new JButton(" 1 "),
            new JButton(" 2 "),
            new JButton(" 3 "),
            new JButton(" 4 "),
            new JButton(" 5 "),
            new JButton(" 6 "),
            new JButton(" 7 "),
            new JButton(" 8 "),
            new JButton(" 9 ")
    };

    JButton operators[] = {
            new JButton(" + "),
            new JButton(" - "),
            new JButton(" * "),
            new JButton(" / "),
            new JButton(" ( "),
            new JButton(" ) "),
            new JButton(" = "),
            new JButton(" C ")
    };

    String oper_values[] = {"+", "-", "*", "/","(",")", "=", ""};

    String value;
    char operator;
    public boolean isop(String c)
    {
        for(int i=0;i<oper_values.length;i++)
            if(oper_values[i].equals(c))
                return true;
        return false;
    }
    JTextArea area = new JTextArea(3, 5);

    public static void main(String[] args) {


        Calculator calculator = new Calculator();
        calculator.setSize(230, 260);
        calculator.setTitle(" Java-Calc, PP Lab1 ");
        calculator.setResizable(true);
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Calculator() {
        add(new JScrollPane(area), BorderLayout.NORTH);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout());

        for (int i=0;i<10;i++)
            buttonpanel.add(digits[i]);

        for (int i=0;i<8;i++)
            buttonpanel.add(operators[i]);

        add(buttonpanel, BorderLayout.CENTER);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        for (int i=0;i<10;i++) {
            int finalI = i;
            digits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    area.append(Integer.toString(finalI));
                }
            });
        }

        for (int i=0;i<8;i++){
            int finalI = i;
            operators[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (finalI == 7)
                        area.setText("");
                    else
                    if (finalI == 6) {
                        Stiva operand=new Stiva();
                        Stiva operator=new Stiva();
                        String s2=new String();
                        String ss=area.getText();
                        for(int i=0;i<ss.length();i++)
                        {

                            if(!isop(ss.charAt(i)+"")) {

                                while (ss.length() > i && !isop(ss.charAt(i)+"")  ) {

                                    s2 = s2 + ss.charAt(i);
                                    i++;
                                }
                                i--;
                                operand.push(s2);
                                s2="";
                            }
                            else
                            {

                                switch(ss.charAt(i)) {
                                    case '(':
                                    {
                                        operator.push("(");
                                        break;
                                    }
                                    case '+':
                                    {
                                        while(isGreater(operator.top(),"+") && !operator.isEmpty())
                                            operand.push(operator.pop());
                                        operator.push("+");
                                        break;
                                    }
                                    case '-':
                                    {
                                        while(isGreater(operator.top(),"-")&&!operator.isEmpty())
                                            operand.push(operator.pop());
                                        operator.push("-");
                                        break;
                                    }
                                    case '*':
                                    {
                                        while(isGreater(operator.top(),"*")&&!operator.isEmpty())
                                            operand.push(operator.pop());
                                        operator.push("*");
                                        break;
                                    }
                                    case '/':
                                    {
                                        while(isGreater(operator.top(),"/")&&!operator.isEmpty())
                                            operand.push(operator.pop());
                                        operator.push("/");
                                        break;
                                    }
                                    case ')':
                                    {
                                        while(!operator.top().equals("("))
                                        {

                                            operand.push(operator.pop());

                                        }
                                        operator.pop();
                                    }
                                }
                            }
                        }

                        while(!operator.isEmpty())
                        {
                            operand.push(operator.pop());
                        }
                        while(!operand.isEmpty())
                            operator.push(operand.pop());
                        double rez;
                        while(!operator.isEmpty())
                        {
                            if(!isop(operator.top()))
                                operand.push(operator.pop());
                            else
                            {
                                String op=operator.pop();
                                double aux1,aux2;
                                aux1=Double.parseDouble(operand.pop());
                                aux2=Double.parseDouble(operand.pop());
                                switch(op) {
                                    case "+":
                                    {
                                        rez=aux1+aux2;
                                        operand.push(rez+"");
                                        break;
                                    }
                                    case "-":
                                    {
                                        rez=aux1-aux2;
                                        operand.push(rez+"");
                                        break;
                                    }
                                    case "*":
                                    {
                                        rez=aux1*aux2;
                                        operand.push(rez+"");
                                        break;
                                    }
                                    case "/":
                                    {
                                        rez=aux1/aux2;
                                        operand.push(rez+"");
                                        break;
                                    }
                                }

                            }
                        }
                        area.append("="+operand.pop());
                    }
                    else {
                        area.append(oper_values[finalI]);
                        operator = oper_values[finalI].charAt(0);
                    }
                }
            });
        }
    }
}