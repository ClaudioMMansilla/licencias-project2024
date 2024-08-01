import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Bienvenido, \n");
		int menuppal = 0;
		boolean retorno = false;
	    String eleccion = null; 

	    do {
	       System.out.println("********** Menu de Opciones **********\n\n"); 	
		   System.out.println("1: Consultar inventario");
		   System.out.println("2: Ingreso | Egreso de mercadería");
		   System.out.println("3: Dar alta un producto en Base de Datos");       	
		   System.out.println("4: Eliminar producto en Base de Datos");          	
		   System.out.println("99: Salir del sistema \n");
		   menuppal = sc.nextInt();     
				    
		   switch (menuppal) {
	        	case 1: // consultar stock
	        		do {
	        			System.out.println("Realizando consulta, aguarda por favor\n\n");
	        			retorno = SELECTtest.consultabd();
	        			if(retorno)
	        			{
	        				System.out.println("\n-> Consulta finalizada\n");	

	        			} else {

	        				System.out.println("Ocurrio un error, operación no realizada\n");
	        			}

	        			System.out.println("¿Quiere repetir la consulta? s|n\n");
	        			eleccion = br.readLine();

	        		}while ( eleccion.equals("s") || eleccion.equals("S") ); 
	        		System.out.flush();  
	        		retorno = false;
			    break;
			        	
				case 2: menuppal = 2;
					 do {
						System.out.println("Cargando módulo, aguarda por favor");
//						UPDATEtest.uptadebd();
						System.out.println("Actualizacion ejecutada");
						System.out.println("------------------------------------------ \n");  	
				        System.out.println("Quieres ingresar otro producto? s|n");
				        eleccion = br.readLine(); 
				     }while (eleccion.equals("s")); 
					 System.out.println("Actualizacion finalizada"); 
			    break;
			        	
	        	case 3: menuppal = 3;	
	        		  do  {
			          	System.out.println("Cargando módulo, aguarda por favor");
//			          	INSERT_test.insertbd();
						System.out.println("Alta ejecutada");
			          	System.out.println("------------------------------------------ \n");  
				        System.out.println("Quieres ingresar nueva Alta? s|n");
				        eleccion = br.readLine(); 
				     }while (eleccion.equals("s")); 
	        		  System.out.println("Alta de producto finalizada");
			    break;
			        	
				case 4: menuppal = 4;	
				      do  {			
			          	System.out.println("Cargando módulo, aguarda por favor");
			          	System.out.println("ATENCION, UD. ESTA POR ELIMINAR UN PRODUCTO DE LA BASE DE DATOS EN FORMA DEFINITIVA");
//			          	DELETEtest.deletebd();
			          	System.out.println("Baja ejecutada");
			          	System.out.println("------------------------------------------ \n");  
				        System.out.println("Desea repetir la consulta? s|n");
				        eleccion = br.readLine(); 
				     }while (eleccion.equals("s")); 
				     System.out.println("Baja de producto finalizada");
			    break;
		        }
		    
		    }while (menuppal != 99);     
	    	sc.close();
	}

}
