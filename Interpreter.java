import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Boolean continuar= true;
        Functions f= new Functions();
        int op=0;
        String exp= "";
        System.out.println("Bienvenido al intérpreter de Lisp");
        while(continuar){
            System.out.println("¿Qué desea Hacer?");
            System.out.println("1. Operaciones aritmétricas");
            op= teclado.nextInt();
            teclado.nextLine();
            if(op==1){
                System.out.println("Ingrese la operación");
                exp= teclado.nextLine();
                System.out.println(f.aritmetricas(exp));
            }
            else{
                continuar=false;
            }
        }
        
    }
    
}

   
