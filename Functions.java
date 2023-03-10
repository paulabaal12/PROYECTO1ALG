import java.util.Stack;

public class Functions {
    Addition add= new Addition();
    Division div= new Division();
    Multiplication mult = new Multiplication();
    Substraction sub= new Substraction();
    public String predicados(String exp){
        Boolean resultado=false;
        String exp2;
        String exp3="";
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        String pred = tokens[0];
        exp2= tokens[1];
        if( tokens.length>=3){
            exp3= tokens[2];
        }
        switch(pred){
            case "atom":
                resultado = atom(exp2);
                break;
            case "list":
                resultado = list(exp2);
                break;
            case "equal":
                resultado = equal(exp2, exp3);
                break;
            case "<":
                resultado= lessThan(Integer.parseInt(exp2), Integer.parseInt(exp3));
                break;
            case ">":
                resultado = greaterThan(Integer.parseInt(exp2), Integer.parseInt(exp3));
                break;

        }
    
        return resultado.toString();


    }

    public static boolean atom(Object obj) {
        return obj instanceof Integer || obj instanceof String;
    }

    public static boolean list(Object obj) {
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            while (pair != null) {
                if (!(pair.cdr() instanceof Pair || pair.cdr() == null)) {
                    return false;
                }
                pair = (Pair) pair.cdr();
            }
            return true;
        }
        return false;
    }

    public static boolean equal(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        if (obj1.equals(obj2)) {
            return true;
        }
        if (obj1 instanceof Pair && obj2 instanceof Pair) {
            Pair pair1 = (Pair) obj1;
            Pair pair2 = (Pair) obj2;
            while (pair1 != null && pair2 != null) {
                if (!equal(pair1.car(), pair2.car())) {
                    return false;
                }
                pair1 = (Pair) pair1.cdr();
                pair2 = (Pair) pair2.cdr();
            }
            return pair1 == null && pair2 == null;
        }
        return false;
    }

    public static boolean lessThan(Object obj1, Object obj2) {
        if (obj1 instanceof Integer && obj2 instanceof Integer) {
            return ((Integer) obj1) < ((Integer) obj2);
        }
        return false;
    }

    public static boolean greaterThan(Object obj1, Object obj2) {
        if (obj1 instanceof Integer && obj2 instanceof Integer) {
            return ((Integer) obj1) > ((Integer) obj2);
        }
        return false;
    }
    
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
