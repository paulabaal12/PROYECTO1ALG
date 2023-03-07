import java.util.Stack;

public class Functions {
    Addition add= new Addition();
    Division div= new Division();
    Multiplication mult = new Multiplication();
    Substraction sub= new Substraction();
    
    public String aritmetricas(String exp){
        Stack<Integer> stack = new Stack<Integer>();

        String[] tokens = exp.split("[() ]+");

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];

            if (isNumeric(token)) {
                stack.push(Integer.parseInt(token));
            } else if(token.equals("+")||token.equals("-")||token.equals("*")||token.equals("/")||token.equals("mod")||token.equals("rem")){
                int operand1 = stack.pop();
                int operand2 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(add.add(operand1, operand2));
                        break;
                    case "-":
                        stack.push(sub.substract(operand1, operand2));
                        break;
                    case "*":
                        stack.push(mult.multiply(operand1, operand2));
                        break;
                    case "/":
                        stack.push(div.divide(operand1, operand2));
                        break;
                    case "mod":
                        stack.push(operand1 % operand2);
                        break;
                    case "rem":
                        stack.push(operand1 - (operand2 * (operand1 / operand2)));
                        break;
                }
            }
        }

        return stack.pop().toString();
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
