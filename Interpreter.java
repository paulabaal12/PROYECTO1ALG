//Paula Barillas, Ana Paula Navas, Nicolle Gordillo
import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Boolean continuar= true;
        Functions f= new Functions();
        ArrayList<Object> lista= new ArrayList<>();
        int op=0;
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
            String[] tokens = exp2.split("[\\s']+");
            String pred = tokens[0];
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

        }
        
    }
    
}

   
