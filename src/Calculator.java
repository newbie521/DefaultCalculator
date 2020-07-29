import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {

    final JTextField textField;
    private String result;
    private double numA;
    private double numB;
    private String operator;
    private double memory;

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }

    public Calculator(){
        super();
        setTitle("计算器");
        setBounds(500,500,300,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gbl = new GridBagLayout();
        //设置计算器头部
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(gbl);
        viewPanel.setPreferredSize(new Dimension(200,100));
        getContentPane().add(viewPanel,BorderLayout.NORTH);
        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);//设置文本框字体方向
        textField.setFont(new Font("Microsoft Uighur",Font.ITALIC,30));//设置文本框字体大小
        textField.setEditable(false);
        gbl.setConstraints(textField,new GBS(4,1,1,1,0,0).getGbs());
        viewPanel.add(textField);
        result = "";
        //设置按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(gbl);
        //组件设置
        JButton[][] buttons = new JButton[6][5];
        String[][] names = {{"MC","MR","MS","M+","M-"},{"←","CE","C","±","√"},{"7","8","9","/","%"},{"4","5","6","*","1/x"},{"1","2","3","-","="},{"0",".","+","",""}};
        //按钮设置
        for(int row=0;row<6;row++){
            for(int col=0;col<5;col++){
                buttons[row][col] = new JButton(names[row][col]);
                buttonPanel.add(buttons[row][col]);
                gbl.setConstraints(buttons[row][col],new GBS(1,1,1,1,col,row).getGbs());
                buttons[row][col].addActionListener(e->{
                    String command = e.getActionCommand();
                    try{
                    doHandle(command);
                    }catch (Exception err){
                        System.out.println(err.getMessage());
                    }
                });
            }
        }
        buttons[5][4].setVisible(false);//删除按钮
        buttons[5][3].setVisible(false);//删除按钮
        gbl.setConstraints(buttons[4][4],new GBS(1,2,1,1,4,4).getGbs());//=号重新设置
        gbl.setConstraints(buttons[5][2],new GBS(1,1,1,1,3,5).getGbs());//+号移位
        gbl.setConstraints(buttons[5][1],new GBS(1,1,1,1,2,5).getGbs());//.号移位
        gbl.setConstraints(buttons[5][0],new GBS(2,1,1,1,0,5).getGbs());//0按钮移位重新设置
        getContentPane().add(buttonPanel,BorderLayout.CENTER);
    }
    public void doHandle(String command) throws Exception{
        switch (command) {
            case "=":
                numB = Double.parseDouble(result);
                Expression expression = new Expression(numA, numB, operator);
                switch (expression.getOperator()) {
                    case "+":
                        result = Double.toString(expression.add());
                        break;
                    case "-":
                        result = Double.toString(expression.subtract());
                        break;
                    case "*":
                        result = Double.toString(expression.multiply());
                        break;
                    case "/":
                        result = Double.toString(expression.divide());
                        break;
                }
                textField.setText(result);
                break;
            case "C":
                numA = 0;
                numB = 0;
                result = "";
                textField.setText(result);
                break;
            case "CE":
                result = "";
                textField.setText(result);
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                operator = command;
                numA = Double.parseDouble(result);
                result = "";
                textField.setText(result);
                break;
            case "MC":
                this.memory = 0;
                result = "";
                textField.setText(result);
                break;
            case "MR":
                result = Double.toString(this.memory);
                textField.setText(result);
                break;
            case "MS":
                this.memory = Double.parseDouble(result);
                result = "";
                textField.setText(result);
                break;
            case "M+":
                double madd = (this.memory+Double.parseDouble(result));
                result = Double.toString(madd);
                textField.setText(result);
                break;
            case "M-":
                double msub = (this.memory-Double.parseDouble(result));
                result = Double.toString(msub);
                textField.setText(result);
                break;
            case "±":
                double nvnum = Double.parseDouble(result);
                if(nvnum<0){
                    nvnum = Math.abs(nvnum);
                }else if(nvnum>=0){
                    nvnum = 0-Math.abs(nvnum);
                }
                result = Double.toString(nvnum);
                textField.setText(result);
                break;
            case "√":
                double sqrtnum = Double.parseDouble(result);
                result = Double.toString(Math.sqrt(sqrtnum));
                textField.setText(result);
                break;
            case "←":
                int numLength = result.length();
                if(numLength==1){
                    result = "";
                }else {
                    result = result.substring(0,numLength-1);
                }
                textField.setText(result);
                break;
            case "%":
                double percentage = (Double.parseDouble(result))/100;
                result = Double.toString(percentage);
                textField.setText(result);
                break;
            case "1/x":
                double fraction = 1/(Double.parseDouble(result));
                result = Double.toString(fraction);
                textField.setText(result);
                break;
            default:
                result += command;
                textField.setText(result);
                break;
        }
    }
}


class GBS{
    GridBagConstraints gbs = new GridBagConstraints();
    public GBS(int gw,int gh,int wx,int wy,int gx,int gy) {
        this.gbs.fill=GridBagConstraints.BOTH;
        this.gbs.gridwidth=gw;
        this.gbs.gridheight=gh;
        this.gbs.insets=new Insets(1, 1, 1, 1);
        this.gbs.weightx=wx;
        this.gbs.weighty=wy;
        this.gbs.gridx=gx;
        this.gbs.gridy=gy;
    }
    public GridBagConstraints getGbs(){
        return gbs;
    }
}

class Expression{
    private double NumA;
    private double NumB;
    private String operator;

    public Expression(double numA, double numB, String operator) {
        NumA = numA;
        NumB = numB;
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
    public double add(){
        return NumA+NumB;
    }
    public double subtract(){
        return NumA-NumB;
    }
    public double multiply(){
        return NumA*NumB;
    }
    public double divide(){
        return NumA/NumB;
    }
}
