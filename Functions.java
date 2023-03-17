
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Functions {
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();
    public HashMap<String, String> funciones = new HashMap<String, String>();
    private HashMap<String, ArrayList<String>> paramfunciones = new HashMap<String, ArrayList<String>>();
    
    Addition add= new Addition();
    Division div= new Division();
    Multiplication mult = new Multiplication();
    Substraction sub= new Substraction();

    public void setq(String exp) {
        int valor;
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        String variable = tokens[1];
        valor= Integer.parseInt(tokens[2]);
        variables.put(variable, valor);
    }
    
    public void cond(String exp) {
        String condicion;
        Boolean resultado1=false;
        Boolean resultado2=false;
        String[] tokens;
        String[] lines = exp.split("\\n");
        ArrayList<String> arrayList = new ArrayList<>();
        for (String line : lines) {
            arrayList.add(line.trim());
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        for(int i=1;i<arrayList.size();i++){
            Pattern pattern = Pattern.compile("\\((.*)\\)\\s\"(.*)\"|^(T)\\s\"(.*)\"");
            Matcher matcher = pattern.matcher(arrayList.get(i));
            if (matcher.find()) {
                if (matcher.group(1) != null) {
                    arrayList2.add(matcher.group(1).trim());
                    arrayList2.add(matcher.group(2));
                } else if (matcher.group(3) != null) {
                    arrayList2.add(matcher.group(3).trim());
                    arrayList2.add(matcher.group(4));
                }
            }
            else if(arrayList.get(i).startsWith("(T")){
                int startIdx = arrayList.get(i).indexOf('"') + 1;
                int endIdx = arrayList.get(i).lastIndexOf('"');
                String message = arrayList.get(i).substring(startIdx, endIdx);
                arrayList2.add("T");
                arrayList2.add(message);
            }

        }
        for(int j=0;j<arrayList2.size();j+=2){
            condicion= arrayList2.get(j).replaceAll("[()]", "");
            tokens = condicion.split("[\\s']+");
            if(tokens[0].equals("=")||tokens[0].equals("<")||tokens[0].equals(">")){
                if(variables.containsKey(tokens[1])){
                    tokens[1]=variables.get(tokens[1]).toString();
                }
                if(variables.containsKey(tokens[2])){
                    tokens[2]=variables.get(tokens[2]).toString();
                }
            }
            else if(tokens[0].equals("and")||tokens[0].equals("or")){
                if(variables.containsKey(tokens[1])){
                    tokens[1]=variables.get(tokens[1]).toString();
                }
                if(variables.containsKey(tokens[2])){
                    tokens[2]=variables.get(tokens[2]).toString();
                }
                if(variables.containsKey(tokens[3])){
                    tokens[3]=variables.get(tokens[3]).toString();
                }
                if(variables.containsKey(tokens[5])){
                    tokens[5]=variables.get(tokens[5]).toString();
                }
                if(variables.containsKey(tokens[6])){
                    tokens[6]=variables.get(tokens[6]).toString();
                }
            }
            else if(tokens[0].equals("not")){
                if(variables.containsKey(tokens[1])){
                    tokens[1]=variables.get(tokens[1]).toString();
                }
                if(variables.containsKey(tokens[2])){
                    tokens[2]=variables.get(tokens[2]).toString();
                }
                if(variables.containsKey(tokens[3])){
                    tokens[3]=variables.get(tokens[3]).toString();
                }
            }
            
            
            switch(tokens[0]){
                case "=":
                    if(equal(tokens[1],tokens[2])){
                        System.out.println(arrayList2.get(j+1));
                        j=arrayList2.size();
                    }
                    break;
                case ">":
                    if(greaterThan(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]))){
                        System.out.println(arrayList2.get(j+1));
                        j=arrayList2.size();
                    }
                    break;
                case "<": 
                    if(lessThan(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]))){
                        System.out.println(arrayList2.get(j+1));
                        j=arrayList2.size();
                    }
                    break;
                case "and":
                    if(tokens[1].equals("=")){
                        resultado1=equal(tokens[2], tokens[3]);

                    }
                    else if(tokens[1].equals("<")){
                        resultado1=lessThan(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    }
                    else if(tokens[1].equals(">")){
                        resultado1=greaterThan(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    }
                    if(tokens[4].equals("=")){
                        resultado2=equal(tokens[5], tokens[6]);
                    }
                    else if(tokens[4].equals("<")){
                        resultado2= lessThan(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                    }
                    else if(tokens[4].equals(">")){
                        resultado2=greaterThan(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                    }

                    if(resultado1==true&&resultado2==true){
                        System.out.println(arrayList2.get(j+1));
                        j=arrayList2.size();
                    }
                    break;
                case "or":
                    if(tokens[1].equals("=")){
                        resultado1=equal(tokens[2], tokens[3]);

                    }
                    else if(tokens[1].equals("<")){
                        resultado1=lessThan(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    }
                    else if(tokens[1].equals(">")){
                        resultado1=greaterThan(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    }
                    if(tokens[4].equals("=")){
                        resultado2=equal(tokens[5], tokens[6]);
                    }
                    else if(tokens[4].equals("<")){
                        resultado2= lessThan(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                    }
                    else if(tokens[4].equals(">")){
                        resultado2=greaterThan(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                    }

                    if(resultado1==true||resultado2==true){
                        System.out.println(arrayList2.get(j+1));
                        j=arrayList2.size();
                    }
                    break;
                case "not":
                    if(tokens[1].equals("=")){
                        resultado1=equal(tokens[2], tokens[3]);

                    }
                    else if(tokens[1].equals("<")){
                        resultado1=lessThan(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    }
                    else if(tokens[1].equals(">")){
                        resultado1=greaterThan(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    }
                    if(resultado1==false){
                        System.out.println(arrayList2.get(j+1));
                        j=arrayList2.size();
                    }
                    break;
                case "T":
                    System.out.println(arrayList2.get(j+1));
                    break;
            }
        }
        
    }

    public String predicados(String exp){
        String resultado="";
        ArrayList<Object> lista= new ArrayList<>();
        String exp2;
        String exp3="";
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        String pred = tokens[0];
        exp2= tokens[1];
        if(variables.containsKey(exp2)){
            exp2=variables.get(exp2).toString();
        }
        if( tokens.length>=3){
            exp3= tokens[2];
            if(variables.containsKey(exp3)){
                exp3=variables.get(exp3).toString();
            }
        }
        if(pred.equals("list")){
            resultado="(";
            for(int i=1;i<tokens.length-1;i++){
                resultado+=tokens[i]+" ";
                lista.add(tokens[i]);

            }
            lista.add(tokens[tokens.length-1]);
            resultado+=tokens[tokens.length-1]+")";

        }else{
            switch(pred){
                case "atom":
                    if(tokens.length>=3){
                        resultado="false";
                    }
                    else{
                        resultado = Boolean.toString(atom(exp2));
                    }
                    break;
                case "equal":
                    resultado = Boolean.toString(equal(exp2, exp3));
                    break;
                case "<":
                    resultado= Boolean.toString(lessThan(Integer.parseInt(exp2), Integer.parseInt(exp3)));
                    break;
                case ">":
                    resultado = Boolean.toString(greaterThan(Integer.parseInt(exp2), Integer.parseInt(exp3)));
                    break;
    
            }

        }
        
    
        return resultado;


    }

    public static boolean atom(Object objeto) {
        if (objeto instanceof Integer || objeto instanceof Double || objeto instanceof String) {
            return true; // Es un átomo
        }
        return false; // No es un átomo
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
            if(variables.containsKey(token)){
                token=variables.get(token).toString();
            }
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
    public void create_function (String exp){
        ArrayList<String> param = new ArrayList<>();
        String[] lines = exp.split("\\n");
        ArrayList<String> arrayList = new ArrayList<>();
        for (String line : lines) {
            arrayList.add(line.trim());
        }
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        String[] tokens2 =arrayList.get(0).replaceAll("[()]", "").split("[\\s']+");
        funciones.put(tokens[1], arrayList.get(1));
        for(int i=2;i<tokens2.length;i++){
            param.add(tokens2[i]);
        }
        paramfunciones.put(tokens[1], param);
    }
    public String defun(String exp){
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        String expresion = funciones.get(tokens[0]);
        String expresion2 = funciones.get(tokens[0]);
        expresion= expresion.replaceAll("[()]", "");
        String[] tokens2 = expresion.split("[\\s']+");
        String pred =tokens2[0];
        for(int i=0; i<paramfunciones.get(tokens[0]).size();i++){
            expresion2=expresion2.replaceAll(paramfunciones.get(tokens[0]).get(i), tokens[i+1]);
        }
        if(pred.equals("+")||pred.equals("-")||pred.equals("/")||pred.equals("*")){
            return aritmetricas(expresion2);
        }
        else{
            return "";
        }


    }
    private synchronized void quote(String expresion){
        Pattern pattern = Pattern.compile("^[(][ ]*('|quote)[ ]*([(].+[)])[ ]*[)]$", Pattern.CASE_INSENSITIVE); //
         Matcher matcher = pattern.matcher(expresion);
        while(matcher.find()){
            System.out.println(matcher.group(2));
        }
    }
}
