package envasadoUnificado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import conector.Conexion;

public class Services {

	private String dataBase = "";
	private String dbProductos = "productos";
	//testing.productos

	public String getDbProductos() {
		return dbProductos;
	}

	public void setDbProductos(String dbProductos) {
		this.dbProductos = dbProductos;
	}

	public Services(){

	}


	public Connection abrirConexion() {
    	Conexion conexion = new Conexion();
		Connection cn;
		cn = conexion.Conectar();
		return cn;
	}


	public Statement newStatement(Connection cn) {
		Statement stm = null;
		try {
			stm = cn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error obteniendo conexión \n" + e);
		}
		return stm;
	}


	public ResultSet newRsStm(Statement stm, String query) {
		ResultSet rs = null;
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta (newRsStm)\n" + e);
		}
		return rs;
	}

	public ResultSet getResulSet(String query) {
		Connection cn = abrirConexion();
		Statement stm = newStatement(cn);
		ResultSet rs = newRsStm(stm, query);
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta (getResulSet)\n" + e);
		}
		return rs;
	}

	public PreparedStatement preparedStUserLogin (Connection cn, String query) {
	    //PreparedStatement prST = (PreparedStatement) cn.prepareStatement("Select name, password, user from pruebas_2022.usuarios where name=? and password=?");
	    PreparedStatement prST = null;
		try {
			prST = cn.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta (preparedStUserLogin)\n" + e);
		}
	    return prST;
	}


	public String[] validarProducto(int idProducto){

		String[] array = {};
		String query = ("SELECT * FROM "+dataBase+dbProductos+" where idProducto='"+idProducto+"'");

		ResultSet rs = getResulSet(query);
		array = new String [3];

   		try {
			while(rs.next()) {
				array[0] = rs.getString(1);  //capturo idProductos de pruebas_2022.productos
				array[1] = rs.getString(2); //capturo producto de pruebas_2022.productos
				array[2] = rs.getString(3);//capturo producto de pruebas_2022.productos
			}

		} catch (SQLException e) {
			e.printStackTrace();
   			JOptionPane.showMessageDialog(null,"Código de producto inválido \n\n "
   					+ "SQLException : services: validarProducto() \n" + e);
		}

		return array;
	}


	public boolean realizarInsertDB(String query) {

		boolean check = false;
		Connection cn = abrirConexion();
		Statement stm = newStatement(cn);

		try {
			stm = cn.createStatement();
			//rs = stm.executeQuery(query);
			stm.executeUpdate(query);
			check = true;
			cn.close();
			System.out.println("cn.close "+this.getClass());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}


	public String getNumPalletIntoDB(String tableDB, String fechaIngreso){

		String query = ("SELECT numPallet FROM "+dataBase+tableDB+" where fechaIngreso ='"+fechaIngreso+"' ");

		ResultSet rs = getResulSet(query);

		int numPallet = 0;
		String stringNumPallet = "null";

   		try {
			while(rs.next()) {
				numPallet = rs.getInt(1);  //capturo idProductos de pruebas_2022.productos
				stringNumPallet = Integer.toString(numPallet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
   			JOptionPane.showMessageDialog(null,"Error obteniendo numero de pallet  \n\n "
   					+ "SQLException : services: getNumPalletIntoDB() \n" + e);
		}

		return stringNumPallet;
	}





}
