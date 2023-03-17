
import java.util.Scanner;
import java.util.Stack;

public class Interpreter {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        Functions f= new Functions();
        String exp= "";
        System.out.println("Bienvenido al intérpreter de Lisp, escriba 'fin' para terminar de usar el programa");
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        while(!exp.equals("fin")){
            StringBuilder sb = new StringBuilder();
            while (count >= 0) {
                System.out.print(">> ");
                String line = scanner.nextLine();
                sb.append(line);
                for (char c : line.toCharArray()) {
                    if (c == '(') {
                        count++;
                    } else if (c == ')') {
                        count--;
                    }
                }
                if (count == 0) {
                    break;
                } else {
                    sb.append("\n");
                }
            }
            exp = sb.toString();
            
            String exp2;
            exp2= exp.replaceAll("[()]", "");
            String[] tokens = exp2.split("[\\s]+");
            String pred = tokens[0];
            for(int i= 1;i<tokens.length;i++){
                if(tokens[i].equals("quote")||pred.equals("'")){
                    tokens[i]=f.quote(exp);
                }
            }
            if(pred.equals("atom")||pred.equals("list")||pred.equals("equal")||pred.equals("<")||pred.equals(">")){
                System.out.println(f.predicados(exp));
            }
            else if(pred.equals("setq")){
                f.setq(exp);
            }
            else if(pred.equals("+")||pred.equals("-")||pred.equals("/")||pred.equals("*")){
                System.out.println(f.aritmetricas(exp));
            }
            else if(pred.equals("cond")){
                f.cond(exp);
            }
            else if(pred.equals("defun")){
                f.create_function(exp);
            }
            else if(f.funciones.containsKey(pred)){
                System.out.println(f.defun(exp));
            }
            else if(pred.equals("quote")||pred.equals("'")){
                System.out.println(f.quote(exp));
            }
            else if(pred.equals("if")){
                System.out.print(f.ifcond(exp));
            }

        }
        
    }
    
}

   
