
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

public class Functions {
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();
    public HashMap<String, String> funciones = new HashMap<String, String>();
    private HashMap<String, ArrayList<String>> paramfunciones = new HashMap<String, ArrayList<String>>();
    private Stack<String> recursivo = new Stack<>();
    
    Addition add= new Addition();
    Division div= new Division();
    Multiplication mult = new Multiplication();
    Substraction sub= new Substraction();
    /** 
     * @param exp
     */
    public void setq(String exp) {//asigna un valor a una variable insertandola en un hashmap, key:variable, Valor: valor de la variable
        int valor;
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        String variable = tokens[1];
        valor= Integer.parseInt(tokens[2]);
        variables.put(variable, valor);
    }
    
    /** 
     * @param exp
     */
    public void cond(String exp) {//condicional
        String condicion;
        Boolean resultado1=false;
        Boolean resultado2=false;
        String[] tokens;
        String[] lines = exp.split("\\n");
        ArrayList<String> arrayList = new ArrayList<>();
        for (String line : lines) {//ingresa en el arraylist linea por linea
            arrayList.add(line.trim());
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        for(int i=1;i<arrayList.size();i++){
        	Pattern pattern = Pattern.compile("\\((.*)\\)\\s\"(.*)\"|^(T)\\s\"(.*)\"|\\((.*)\\)\\s'(.*)'");//crea un patron para la condicion y el mensaje
            Matcher matcher = pattern.matcher(arrayList.get(i));
            if (matcher.find()) {//inserta condicion-mensaje si encuentra un match al patron
                if (matcher.group(1) != null) {
                    arrayList2.add(matcher.group(1).trim());
                    arrayList2.add(matcher.group(2));
                } else if (matcher.group(3) != null) {
                    arrayList2.add(matcher.group(3).trim());
                    arrayList2.add(matcher.group(4));
                }
                else if (matcher.group(5) != null) {
                    arrayList2.add(matcher.group(5).trim());
                    arrayList2.add(matcher.group(6));
                }
            }
            else if(arrayList.get(i).startsWith("(T")){//default
                int startIdx = arrayList.get(i).indexOf('T') + 2;
                int endIdx = arrayList.get(i).lastIndexOf(')')-1;
                String message = arrayList.get(i).substring(startIdx, endIdx);
                arrayList2.add("T");
                arrayList2.add(message);
            }

        }
        for(int j=0;j<arrayList2.size();j+=2){
            condicion= arrayList2.get(j).replaceAll("[()]", "");
            tokens = condicion.split("[\\s']+");
            if(tokens[0].equals("=")||tokens[0].equals("<")||tokens[0].equals(">")){//mira si hay una variable que fue definida por setq
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
            
            
            switch(tokens[0]){//mira la condicion y devuelve el mensaje si es verdadero
                case "=":
                    if(equal(tokens[1],tokens[2])){
                        System.out.println(arrayList2.get(j+1));
                        j=arrayList2.size();
                    }
                    break;
                case ">":
                    if(greaterThan(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))){
                        System.out.println(arrayList2.get(j+1));
                        j=arrayList2.size();
                    }
                    break;
                case "<": 
                    if(lessThan(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))){
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


    /** 
     * @param exp
     * @return String
     */
    public String predicados(String exp){//Evalua los predicados, atom, list, equal, >, <
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


    /** 
     * @param objeto
     * @return boolean
     */
    public static boolean atom(Object objeto) {//mira si es un atomo
        if (objeto instanceof Integer || objeto instanceof Double || objeto instanceof String) {
            return true; // Es un átomo
        }
        return false; // No es un átomo
    }


    /** 
     * @param obj1
     * @param obj2
     * @return boolean
     */
    public static boolean equal(Object obj1, Object obj2) {// mira si son iguales
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
    /** 
     * @param obj1
     * @param obj2
     * @return boolean
     */
    public static boolean lessThan(Object obj1, Object obj2) {//evalua si es menor que
        if (obj1 instanceof Integer && obj2 instanceof Integer) {
            return ((Integer) obj1) < ((Integer) obj2);
        }
        return false;
    }
    /** 
     * @param obj1
     * @param obj2
     * @return boolean
     */
    public static boolean greaterThan(Object obj1, Object obj2) {//evalua si es mayor que
        if (obj1 instanceof Integer && obj2 instanceof Integer) {
            return ((Integer) obj1) > ((Integer) obj2);
        }
        return false;
    }
    

    /** 
     * @param exp
     * @return String
     */
    //Esta función se arregló remplazando todos los parentesis con espacios vacíos ya que antes no podia reconocer los operadores cuando estaban a la par de un parentesis porque tomaba el simbolo con todo y el parentesis
    public String aritmetricas(String exp){//Evalua funciones aritmetricas como prefix usando stack
        Stack<Integer> stack = new Stack<Integer>();

        exp = exp.substring(1, exp.length() - 1);// Se quitan los parentesis del inicio y del final
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("\\s+");

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

    /** 
     * @param strNum
     * @return boolean
     */
    public static boolean isNumeric(String strNum) {//revisa si el string es un numero
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

    /** 
     * @param exp
     */
    public void create_function (String exp){//crea la funcion definida por el usuario
        StringBuilder sb = new StringBuilder();
        ArrayList<String> param = new ArrayList<>();
        String[] lines = exp.split("\\n");
        ArrayList<String> arrayList = new ArrayList<>();
        for (String line : lines) {//agrega linea por linea
            arrayList.add(line.trim());
        }
        for(int i=1;i<lines.length;i++){
            sb.append(arrayList.get(i)+"\n");
        }
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        String[] tokens2 =arrayList.get(0).replaceAll("[()]", "").split("[\\s']+");
        funciones.put(tokens[1], sb.toString());//inserta en el hashmap de funciones, key: nombre de la funcion, valor: lo que hace la funcion
        for(int i=2;i<tokens2.length;i++){
            param.add(tokens2[i]);
        }
        paramfunciones.put(tokens[1], param);//inserta los parametros de la funcion a un hashmap, key: nombre de la funcion, valor: arraylist de los parametros
    }

    /** 
     * @param exp
     * @return String
     */
    public String ifcond(String exp){//if
        String condicion;
        Boolean resultado1=false;
        Boolean resultado2=false;
        String[] tokens;
        String[] lines = exp.split("\\n");
        ArrayList<String> arrayList = new ArrayList<>();
        for (String line : lines) {
            arrayList.add(line.trim());
        }
        condicion= arrayList.get(1).replaceAll("[()]", "");
        tokens = condicion.split("[\\s']+");
        String[] tokens2= arrayList.get(2).replaceAll("[()]", "").split("[\\s']+");
        String[] tokens3= arrayList.get(3).replaceAll("[()]", "").split("[\\s']+");
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
        boolean funcion=false;
        switch(tokens[0]){//evalua la condicional y devuelve el valor si es verdadero o falso
            case "=":
                if(equal(tokens[1],tokens[2])){
                    if(tokens2[0].equals("+")||tokens2[0].equals("-")||tokens2[0].equals("*")||tokens2[0].equals("/")||tokens2[0].equals("mod")||tokens2[0].equals("rem")){
                        return aritmetricas(arrayList.get(2).toString());
                    }
                    else if(tokens2[0].equals("atom")||tokens2[0].equals("list")||tokens2[0].equals("equal")||tokens2[0].equals(">")||tokens2[0].equals("<")){
                        return predicados(arrayList.get(2).toString());
                    }
                    else if(funciones.containsKey(tokens2[0])){
                        return defun(arrayList.get(2).toString());
                    }
                    else{
                        return arrayList.get(2).toString().replaceAll("[()]", "");
                    }
                }
                else{
                    
                    if(tokens3[0].equals("+")||tokens3[0].equals("-")||tokens3[0].equals("*")||tokens3[0].equals("/")||tokens3[0].equals("mod")||tokens3[0].equals("rem")){
                        return aritmetricas(arrayList.get(3).toString());
                    }
                    else if(tokens3[0].equals("atom")||tokens3[0].equals("list")||tokens3[0].equals("equal")||tokens3[0].equals(">")||tokens3[0].equals("<")){
                        return predicados(arrayList.get(3).toString());
                    }
                    else if(funciones.containsKey(tokens3[0])){
                        return defun(arrayList.get(3).toString());
                    }
                    else{
                        return arrayList.get(3).toString().replaceAll("[()]", "");
                    }
                }
            case ">":
                if(greaterThan(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))){
                    if(tokens2[0].equals("+")||tokens2[0].equals("-")||tokens2[0].equals("*")||tokens2[0].equals("/")||tokens2[0].equals("mod")||tokens2[0].equals("rem")){
                        return aritmetricas(arrayList.get(2).toString());
                    }
                    else if(tokens2[0].equals("atom")||tokens2[0].equals("list")||tokens2[0].equals("equal")||tokens2[0].equals(">")||tokens2[0].equals("<")){
                        return predicados(arrayList.get(2).toString());
                    }
                    else if(funciones.containsKey(tokens2[0])){
                        return defun(arrayList.get(2).toString());
                    }
                    else{
                        return arrayList.get(2).toString().replaceAll("[()]", "");
                    }
                }
                else{
                    if(tokens3[0].equals("+")||tokens3[0].equals("-")||tokens3[0].equals("*")||tokens3[0].equals("/")||tokens3[0].equals("mod")||tokens3[0].equals("rem")){
                        return aritmetricas(arrayList.get(3).toString());
                    }
                    else if(tokens3[0].equals("atom")||tokens3[0].equals("list")||tokens3[0].equals("equal")||tokens3[0].equals(">")||tokens3[0].equals("<")){
                        return predicados(arrayList.get(3).toString());
                    }
                    else if(funciones.containsKey(tokens3[0])){
                        return defun(arrayList.get(3).toString());
                    }
                    else{
                        return arrayList.get(3).toString().replaceAll("[()]", "");
                    }
                }
            case "<": 
                if(lessThan(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))){
                    if(tokens2[0].equals("+")||tokens2[0].equals("-")||tokens2[0].equals("*")||tokens2[0].equals("/")||tokens2[0].equals("mod")||tokens2[0].equals("rem")){
                        return aritmetricas(arrayList.get(2).toString());
                    }
                    else if(tokens2[0].equals("atom")||tokens2[0].equals("list")||tokens2[0].equals("equal")||tokens2[0].equals(">")||tokens2[0].equals("<")){
                        return predicados(arrayList.get(2).toString());
                    }
                    else if(funciones.containsKey(tokens2[0])){
                        return defun(arrayList.get(2).toString());
                    }
                    else{
                        return arrayList.get(2).toString().replaceAll("[()]", "");
                    }
                }
                else{
                    if(tokens3[0].equals("+")||tokens3[0].equals("-")||tokens3[0].equals("*")||tokens3[0].equals("/")||tokens3[0].equals("mod")||tokens3[0].equals("rem")){
                        return aritmetricas(arrayList.get(3).toString());
                    }
                    else if(tokens3[0].equals("atom")||tokens3[0].equals("list")||tokens3[0].equals("equal")||tokens3[0].equals(">")||tokens3[0].equals("<")){
                        return predicados(arrayList.get(3).toString());
                    }
                    else if(funciones.containsKey(tokens3[0])){
                        return defun(arrayList.get(3).toString());
                    }
                    else{
                        return arrayList.get(3).toString().replaceAll("[()]", "");
                    }
                }
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
                    if(tokens2[0].equals("+")||tokens2[0].equals("-")||tokens2[0].equals("*")||tokens2[0].equals("/")||tokens2[0].equals("mod")||tokens2[0].equals("rem")){
                        return aritmetricas(arrayList.get(2).toString());
                    }
                    else if(tokens2[0].equals("atom")||tokens2[0].equals("list")||tokens2[0].equals("equal")||tokens2[0].equals(">")||tokens2[0].equals("<")){
                        return predicados(arrayList.get(2).toString());
                    }
                    else if(funciones.containsKey(tokens2[0])){
                        return defun(arrayList.get(2).toString());
                    }
                    else{
                        return arrayList.get(2).toString().replaceAll("[()]", "");
                    }
                    
                }
                else{
                    if(tokens3[0].equals("+")||tokens3[0].equals("-")||tokens3[0].equals("*")||tokens3[0].equals("/")||tokens3[0].equals("mod")||tokens3[0].equals("rem")){
                        return aritmetricas(arrayList.get(3).toString());
                    }
                    else if(tokens3[0].equals("atom")||tokens3[0].equals("list")||tokens3[0].equals("equal")||tokens3[0].equals(">")||tokens3[0].equals("<")){
                        return predicados(arrayList.get(3).toString());
                    }
                    else if(funciones.containsKey(tokens3[0])){
                        return defun(arrayList.get(3).toString());
                    }
                    else{
                        return arrayList.get(3).toString().replaceAll("[()]", "");
                    }
                }
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
                    if(tokens2[0].equals("+")||tokens2[0].equals("-")||tokens2[0].equals("*")||tokens2[0].equals("/")||tokens2[0].equals("mod")||tokens2[0].equals("rem")){
                        return aritmetricas(arrayList.get(2).toString());
                    }
                    else if(tokens2[0].equals("atom")||tokens2[0].equals("list")||tokens2[0].equals("equal")||tokens2[0].equals(">")||tokens2[0].equals("<")){
                        return predicados(arrayList.get(2).toString());
                    }
                    else if(funciones.containsKey(tokens2[0])){
                        return defun(arrayList.get(2).toString());
                    }
                    else{
                        return arrayList.get(2).toString().replaceAll("[()]", "");
                    }
                    
                }
                else{
                    if(tokens3[0].equals("+")||tokens3[0].equals("-")||tokens3[0].equals("*")||tokens3[0].equals("/")||tokens3[0].equals("mod")||tokens3[0].equals("rem")){
                        return aritmetricas(arrayList.get(3).toString());
                    }
                    else if(tokens3[0].equals("atom")||tokens3[0].equals("list")||tokens3[0].equals("equal")||tokens3[0].equals(">")||tokens3[0].equals("<")){
                        return predicados(arrayList.get(3).toString());
                    }
                    else if(funciones.containsKey(tokens3[0])){
                        return defun(arrayList.get(3).toString());
                    }
                    else{
                        return arrayList.get(3).toString().replaceAll("[()]", "");
                    }
                }
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
                    if(tokens2[0].equals("+")||tokens2[0].equals("-")||tokens2[0].equals("*")||tokens2[0].equals("/")||tokens2[0].equals("mod")||tokens2[0].equals("rem")){
                        return aritmetricas(arrayList.get(2).toString());
                    }
                    else if(tokens2[0].equals("atom")||tokens2[0].equals("list")||tokens2[0].equals("equal")||tokens2[0].equals(">")||tokens2[0].equals("<")){
                        return predicados(arrayList.get(2).toString());
                    }
                    else if(funciones.containsKey(tokens2[0])){
                        return defun(arrayList.get(2).toString());
                    }
                    else{
                        return arrayList.get(2).toString().replaceAll("[()]", "");
                    }
                    
                }
                else{
                    if(tokens3[0].equals("+")||tokens3[0].equals("-")||tokens3[0].equals("*")||tokens3[0].equals("/")||tokens3[0].equals("mod")||tokens3[0].equals("rem")){
                        return aritmetricas(arrayList.get(3).toString());
                    }
                    else if(tokens3[0].equals("atom")||tokens3[0].equals("list")||tokens3[0].equals("equal")||tokens3[0].equals(">")||tokens3[0].equals("<")){
                        return predicados(arrayList.get(3).toString());
                    }
                    else if(funciones.containsKey(tokens3[0])){
                        return defun(arrayList.get(3).toString());
                    }
                    else{
                        return arrayList.get(3).toString().replaceAll("[()]", "");
                    }
                }
            }
            return "";

    }
    String exp3="";
    String exp4="";
    String resultado="";
    /** 
     * @param exp"
     * @return String
     */

    public String defun(String exp){//evalua la funcion
        String resultado="";
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        String expresion = funciones.get(tokens[0]);
        String expresion2 = funciones.get(tokens[0]);
        expresion= expresion.replaceAll("[()]", "");
        String[] tokens2 = expresion.split("[\\s']+");
        List<String> tokens2list =  Arrays.asList(tokens2);
        String pred =tokens2[0];
        ArrayList<String> parame= new ArrayList<>();
        ArrayList<String> paramenew= new ArrayList<>();
        for(int j=0; j<paramfunciones.get(tokens[0]).size();j++){
            if(funciones.containsKey(paramfunciones.get(tokens[0]).get(j))){//si un parametro es una funcion definida por el usuario
                parame= paramfunciones.get(tokens[0]);
                String subexp=paramfunciones.get(tokens[0]).get(j);
                exp4="("+subexp+" (";
                exp3="("+subexp+" (";
                for(int k =1;k<paramfunciones.get(subexp).size()+1;k++){
                    
                    if(k==paramfunciones.get(subexp).size()){
                        exp4+= tokens2[tokens2list.indexOf(subexp)+k];//es el ultimo parametro, no inserta un espacio
                        tokens2[tokens2list.indexOf(subexp)+k]=tokens2[tokens2list.indexOf(subexp)+k].replaceAll("\\b" +paramfunciones.get(tokens[0]).get(k)+"\\b" , tokens[k]);
                        exp3+= tokens2[tokens2list.indexOf(subexp)+k];
                    }
                    else{
                        exp4+= tokens2[tokens2list.indexOf(subexp)+k]+" ";//Agrega un espacio despues del parametro
                        tokens2[tokens2list.indexOf(subexp)+k]=tokens2[tokens2list.indexOf(subexp)+k].replaceAll("\\b" +paramfunciones.get(tokens[0]).get(k)+"\\b" , tokens[k]);
                        exp3+= tokens2[tokens2list.indexOf(subexp)+k]+" ";
                    }
                    

                }
                exp4+="))";
                exp3+="))";
                resultado =defun(exp3);//evalua lafuncion que está dentro de la funcion
                paramenew=parame;
                paramenew.remove(subexp);
                paramfunciones.replace(subexp, parame,paramenew);//remplaza el parametro de funcion por los parametros no funciones
                
                
            }
        }
        expresion2=expresion2.replaceAll("\\Q" + exp4 + "\\E",resultado );//remplaza la funcion que está adentro de la funcion por el resultado al ser evaluada
        for(int i=0; i<paramfunciones.get(tokens[0]).size();i++){//remplaza en la expresion los valores indicados por el usuario
            expresion2=expresion2.replaceAll("\\b" +paramfunciones.get(tokens[0]).get(i)+"\\b" , tokens[i+1]);
        }
        if(pred.equals("+")||pred.equals("-")||pred.equals("/")||pred.equals("*")||pred.equals("mod")||pred.equals("rem")){//en caso de que sea una expresion aritmetrica
            
            resultado= aritmetricas(expresion2);
        }
        else if(pred.equals("cond")){//en caso de que sea una condicional
            cond(expresion2);
            resultado= "";
        }
        else if(pred.equals("atom")||pred.equals("list")||pred.equals("equal")||pred.equals("<")||pred.equals(">")){//en caso de que sea un predicado
            resultado= predicados(expresion2);
        }
        else if(pred.equals("if")){
            resultado= ifcond(expresion2);
        }
        
        return resultado;
    
    }


    /** 
     * @param exp
     * @return String
     */
    public String quote(String exp){//devuelve la expresion entrada, no el valor de la expresion
        exp= exp.replaceAll("[()]", "");
        String[] tokens = exp.split("[\\s']+");
        return tokens[1].toString();
    }
}
