package envasadoUnificado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import EventosGUI.EventoDeTeclado;
import fecha.Fecha;



public class Envasado_Controller implements ActionListener{
	private String dataBase = "";

	private Envasado_View view = new Envasado_View();
	private Envasado_Model model = new Envasado_Model();
	private Services service = new Services();
	private Reports report = new Reports();

	private loteVtoManual_View loteVtoView = new loteVtoManual_View();
	private loteVtoManual_Controller loteVtoCtrl = new loteVtoManual_Controller(loteVtoView);

	private String planta = "null";
	private String usuario = "null";
	private int vidaUtil = 0;
	private String tableDB = "null";


	public String[] loteVencimiento = new String[2]; // utilizado para return loteVtoManual_Controller


	private String [] dependency;

	public Envasado_Controller(
		Envasado_View view, Envasado_Model model,
		int vidaUtil, String planta, String usuario){

		this.view = view;
		this.model = model;
		this.planta = planta;
		this.usuario = usuario;
		this.vidaUtil = vidaUtil;
		this.dataBase = dataBase;



		EventoDeTeclado tecla = new EventoDeTeclado();
		view.addKeyListener(tecla);

		view.field_id.addKeyListener(tecla);

		view.JBT_guardar.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	doInsertIntoDB();
            }
        });

		view.JBT_LtoVtoView.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	loteVtoManual_Controller();
            }
        });

		view.field_id.addActionListener(new ActionListener(){

			public void actionPerformed(KeyEvent e){
				tecla.codigo = e.getKeyCode();
			}

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (tecla.codigo == 10) {
					verificarId();
				}
			}
		});


    	loteVtoView.btn_volver.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	if( !(loteVtoView.field_ingresoLote.getText().equals("")
            			|| loteVtoView.calendar.getDate() == null))
            	{
            		loteVtoManual_Controller();
            		loteVtoView.dispose();
            	}
            	else {
           			JOptionPane.showMessageDialog(view, "Campos obligatorios son inválidos \n\n");
           			//loteVtoView.dispose();
            	}
            }
        });

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void iniciar() {
		view.setTitle("Vencimiento ");
		view.setLocationRelativeTo(null);
		model.setPlanta(planta);
		model.setUsuarioIngreso(usuario);
		setTitleView();
		setFechaCalcVenc();
		setLote();

	}


	public void setTitleView() {
		String bufferPlanta = model.getPlanta();
		String bufferUsuario = model.getUsuarioIngreso();
		view.setTitle("Planta:  "+ bufferPlanta +"  |   Usuario:  "+bufferUsuario+" ");
	}


	public void setCantidadModel() {
		String buffer = view.field_cantidad.getText();

		if( !(buffer.equals("")) ) {
			model.setCajasInformadas(
					Fecha.parserStringToInt(buffer)
					);
		} else {
			model.setCajasInformadas(-1);
		}
	}


	public void setFechaCalcVenc() {
		String vencimiento = Fecha.calcularVencimiento(vidaUtil);
		model.setVencimiento(vencimiento);
		view.lbl_setearVenc.setText(model.getVencimiento());
	}

	public void setFechaCalcVenc(String vencimiento) {
		model.setVencimiento(vencimiento);
		view.lbl_setearVenc.setText(model.getVencimiento());
	}

	public void setLote() {
		String lote = Fecha.calcularLote();
		int bufferLote = Fecha.parserStringToInt(lote);
		model.setLote(bufferLote);
		view.lbl_setearLote.setText(lote);
	}

	public void setLote(String lote) {
		model.setLote(Fecha.parserStringToInt(lote));
		view.lbl_setearLote.setText(lote);
	}


	public void setProductoLabel() {
		String bufferString = model.getProducto();
		view.label6.setText(bufferString);
	}

	public void setFechaIngresoModel() {
		model.setFechaIngreso(Fecha.fechaIngreso());
	}

	public void setFlagDisponibleModel() {
		model.setFlagDisponible(1);
	}

	public void setFlagAjusteModel() {
		model.setFlagAjuste(0);
	}


	private String getTableDBModel() {
		return model.getTableDB();
	}

	public void verificarId() {
		System.out.println("Data base target: "+dataBase);

		String field = view.field_id.getText();
		int bufferId = Fecha.parserStringToInt(field);
		String [] depcy = service.validarProducto(bufferId);

   		if (depcy[0]==null) { // valido textfield id sino hubo resultados en consulta
   			JOptionPane.showMessageDialog(null, "Código de producto inválido \n\n "
   					+ "Envasado_Controller: doInjection() ");

   			model.setIdProducto(-1);
   			model.setProducto(null);
   			model.setTableDB(null);
   			setProductoLabel();

   		} else {
   			dependency = depcy;
   			int id = Fecha.parserStringToInt(dependency[0]);
   			model.setIdProducto(id);
   			model.setProducto(dependency[1]);
   			model.setTableDB(dependency[2]);
   			setProductoLabel();
   		}
	}


	private boolean checkBeforeInsertDB() {

		setCantidadModel();

		boolean check = false;
		int bufferCajas = model.getCajasInformadas();
		int bufferId = model.getIdProducto();

		if ( !(bufferId == -1
				|| bufferCajas <= 0
				|| view.field_id.getText().equals("")
				|| view.field_cantidad.getText().equals("") ))
		{
			check = true;
		}

		return check;
	}


	private void ejecutarQuerySQL(){

		this.tableDB = getTableDBModel();
		String fechaIngreso = model.getFechaIngreso();
		String usuarioIngreso = usuario;
		int idProducto = model.getIdProducto();
		String planta = model.getPlanta();
		String producto = model.getProducto();
		int lote = model.getLote();
		String vencimiento = model.getVencimiento();
		int cajasInformadas = model.getCajasInformadas();
		int flagDisponible = model.getFlagDisponible();
		int flagAjuste = model.getFlagAjuste();
		String date = Fecha.calculateDateTime();

		String query = "INSERT into "+dataBase+tableDB+" "
		+ "(fechaIngreso, usuarioIngreso, idProducto, planta, producto, lote, "
		+ "vencimiento, cajasInformadas, flagDisponible, flagAjuste, dateIngreso) "
		+ "values ('"+fechaIngreso+"', '"+usuarioIngreso+"', '"+idProducto+"', "
				+ "'"+planta+"', '"+producto+"', '"+lote+"', "
				+ "'"+vencimiento+"', '"+cajasInformadas+"', "
				+ "'"+flagDisponible+"', '"+flagAjuste+"', '"+date+"')";

		service.realizarInsertDB(query);
	}


	private void doInsertIntoDB() {

		if(checkBeforeInsertDB()) {
			setFechaIngresoModel();
			setFlagDisponibleModel();
			setFlagAjusteModel();
			ejecutarQuerySQL();
			setInformacionIngresadaView();
			setNumPalletModel();
			report.reportEnvasado(model);

		} else {
			view.label6.setText("Alguno de los campos obligatorios es inválido");
		}

		view.field_cantidad.setText(null);
	}


	private void setNumPalletView() {
		view.info_pallets.setText(
				service.getNumPalletIntoDB(
						model.getTableDB(),
						model.getFechaIngreso())
				);
	}


	private void setCantidadView() {
		view.info_cantidad.setText(
				Integer.toString(
						model.getCajasInformadas()
						));
	}


	private void setDescripcionView() {
		view.info_descripcion.setText(
				model.getProducto()
				);
	}


	private void setInformacionIngresadaView() {
		setNumPalletView();
		setCantidadView();
		setDescripcionView();
	}

	private void setNumPalletModel() {
		model.setNumPallet(view.info_pallets.getText());
	}


	public void setLoteVencimientoIntoArray() {
		loteVencimiento[0] = loteVtoView.field_ingresoLote.getText();
		loteVencimiento[1] = Fecha.formatterCalendarDate((loteVtoView.calendar.getDate()));
	}


	public String[] getLoteVencimientoIntoArray() {
		return loteVencimiento;
	}


	private void loteVtoManual_Controller() {

    	loteVtoCtrl.iniciar();
    	loteVtoView.setVisible(true);

    	setLoteVencimientoIntoArray();
    	setLote(loteVencimiento[0]);
    	setFechaCalcVenc(loteVencimiento[1]);
	}


}
