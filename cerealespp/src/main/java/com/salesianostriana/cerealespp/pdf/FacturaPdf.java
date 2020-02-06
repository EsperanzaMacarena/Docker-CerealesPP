package com.salesianostriana.cerealespp.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.salesianostriana.cerealespp.model.LineaPedido;
import com.salesianostriana.cerealespp.model.Pedido;
import com.salesianostriana.cerealespp.service.LineaPedidoServicio;
import lombok.NoArgsConstructor;
/**
 * Clase que modela un PDF.
 * @author Esperanza M Escacena M
 *
 */
@Component
@NoArgsConstructor
public class FacturaPdf {
	@Autowired
	LineaPedidoServicio ls;
	/**
	 * Método que crea un PDF. Creará una tabla que contendrá la información de un pedido.
	 * @param p Pedido a mostrar.
	 * @return Archivo PDF.
	 */
	public ByteArrayInputStream generarPdf(Pedido p) {
		Document pdf=new Document(); //creo un documento PDF.
		ByteArrayOutputStream salida=new ByteArrayOutputStream();
		//Flujo de salida en el que los datos se escriben en un array de bytes
		
		try {
			//Antes de añadir elementos al pdf hay q dar estilo y crear las tablas necesarias.
			PdfPTable tabla=new PdfPTable(new float[] {3,2,2,2});//Creo una tabla y le paso las dimensiones de las celdas.
			tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);// Para el método agregar celda le digo que lo agregue en horizontal y con los elementos centrados.
			tabla.setSpacingAfter(6L); //Espacio entre un elemento y otro, en long
			tabla.addCell("Producto");
			tabla.addCell("Cantidad");
			tabla.addCell("Precio/U");
			tabla.addCell("Subtotal");
			tabla.setHeaderRows(1);//le digo cuantas filas son headers. 
			for(PdfPCell celda : tabla.getRow(0).getCells()) {
				celda.setBackgroundColor(BaseColor.LIGHT_GRAY); //pongo el header con fondo verde.
			}
			//imprimo tantas filas como lineas de pedido haya en el pedido
			for(LineaPedido linea : p.getLinea()) {
				tabla.addCell(linea.getProductoAnadido().getNombre());
				tabla.addCell(""+linea.getCantidad());
				tabla.addCell(linea.getPrecioUnidad()+"€");
				tabla.addCell(ls.calcularPrecioTotalLinea(linea)+"€");
			}
			
			Paragraph titulo=new Paragraph("PEDIDO "+p.getId());
			//dando estilo al título
			titulo.setSpacingAfter(18l);
			titulo.setFirstLineIndent(75l);
			titulo.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD));
			titulo.setAlignment(Element.ALIGN_CENTER);
			Paragraph cliente =new Paragraph(p.getCliente().getNomEmpresa());
			cliente.setSpacingAfter(10l);
			cliente.setFirstLineIndent(6l);
			cliente.setAlignment(Element.ALIGN_LEFT);
			tabla.setSpacingBefore(20L);
			//Instancio el pdf ahora que tengo la tabla.
			PdfWriter.getInstance(pdf, salida);
			pdf.open();//Abro el documento
			//inserto los elementos. 
			pdf.add(titulo);
			pdf.add(cliente);
			pdf.add(new Paragraph("Fecha"+ p.getFecha()));		
			pdf.add(tabla);
			pdf.add(new Paragraph("Subtotal: "+p.getPrecioSubtotal()+"€"));
			pdf.add(new Paragraph("Gastos de envío: "+p.getPrecioTransporte()+"€"));
			pdf.add(new Paragraph("Total: "+p.getPrecioTotal()+"€"));
			//Cierro el documento
			pdf.close();
			
		}catch(DocumentException error) {
			error.getCause();
		}
		return new ByteArrayInputStream(salida.toByteArray());//Salida: pdf en un array de bytes.
	}
}
