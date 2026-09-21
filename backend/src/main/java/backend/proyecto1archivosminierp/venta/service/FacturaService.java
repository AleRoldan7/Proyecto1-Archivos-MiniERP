package backend.proyecto1archivosminierp.venta.service;

import backend.proyecto1archivosminierp.excepciones.OperacionInvalida;
import backend.proyecto1archivosminierp.venta.dto.DetalleVentaResponseDTO;
import backend.proyecto1archivosminierp.venta.dto.VentaResponseDTO;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class FacturaService {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarFactura(VentaResponseDTO ventaResponseDTO) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document documento = new Document(PageSize.LETTER, 36, 36, 54, 36);

        try {
            PdfWriter.getInstance(documento, byteArrayOutputStream);
            documento.open();

            Font tituloEmpresa = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(30, 41, 59));
            Font subtitulo = new Font(Font.HELVETICA, 11, Font.NORMAL, new Color(100, 116, 139));
            Font negrita = new Font(Font.HELVETICA, 10, Font.BOLD, new Color(15, 23, 42));
            Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(30, 41, 59));

            Paragraph empresa = new Paragraph("MiniERP", tituloEmpresa);
            documento.add(empresa);

            Paragraph eslogan = new Paragraph("Sistema de Gestión Empresarial", subtitulo);
            eslogan.setSpacingAfter(12f);
            documento.add(eslogan);

            // Número de factura y fecha (alineado a la derecha)
            Paragraph numeroFactura = new Paragraph(
                    "FACTURA No. " + ventaResponseDTO.getIdVenta(),
                    new Font(Font.HELVETICA, 14, Font.BOLD, new Color(79, 70, 229)));
            numeroFactura.setAlignment(Element.ALIGN_RIGHT);
            documento.add(numeroFactura);

            Paragraph fecha = new Paragraph(
                    "Fecha: " + (ventaResponseDTO.getFechaVenta() != null
                            ? ventaResponseDTO.getFechaVenta().format(FORMATO_FECHA)
                            : "—"),
                    subtitulo);
            fecha.setAlignment(Element.ALIGN_RIGHT);
            fecha.setSpacingAfter(20f);
            documento.add(fecha);

            // Línea separadora
            documento.add(new Paragraph(" "));
            Paragraph linea = new Paragraph("__________________________________________________", subtitulo);
            linea.setSpacingAfter(12f);
            documento.add(linea);

            // ---------- 2. DATOS DEL CLIENTE ----------
            Paragraph tituloCliente = new Paragraph("DATOS DEL CLIENTE", negrita);
            tituloCliente.setSpacingAfter(6f);
            documento.add(tituloCliente);

            documento.add(new Paragraph("Cliente: " + valorSeguro(ventaResponseDTO.getNombreCliente()), normal));
            documento.add(new Paragraph("ID Cliente: " + valorSeguro(ventaResponseDTO.getIdCliente()), normal));
            documento.add(new Paragraph(" "));

            // ---------- 3. TABLA DE DETALLE ----------
            Paragraph tituloProductos = new Paragraph("DETALLE DE PRODUCTOS", negrita);
            tituloProductos.setSpacingAfter(6f);
            documento.add(tituloProductos);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{4f, 1.5f, 2f, 2.5f});

            // Encabezados
            Color colorEncabezado = new Color(79, 70, 229);
            tabla.addCell(celdaEncabezado("Producto", colorEncabezado));
            tabla.addCell(celdaEncabezado("Cantidad", colorEncabezado));
            tabla.addCell(celdaEncabezado("Precio Unit.", colorEncabezado));
            tabla.addCell(celdaEncabezado("Subtotal", colorEncabezado));

            // Filas
            if (ventaResponseDTO.getDetalles() != null) {
                for (DetalleVentaResponseDTO detalle : ventaResponseDTO.getDetalles()) {
                    tabla.addCell(celdaNormal(valorSeguro(detalle.getDescripcionProducto())));
                    tabla.addCell(celdaCentrada(String.valueOf(detalle.getCantidad())));
                    tabla.addCell(celdaDerecha("Q " + formatearMoneda(detalle.getPrecioUnitario())));
                    tabla.addCell(celdaDerecha("Q " + formatearMoneda(detalle.getSubtotal())));
                }
            }

            tabla.setSpacingAfter(20f);
            documento.add(tabla);

            // ---------- 4. TOTALES ----------
            PdfPTable tablaTotales = new PdfPTable(2);
            tablaTotales.setWidthPercentage(45);
            tablaTotales.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaTotales.setWidths(new float[]{3f, 2f});

            tablaTotales.addCell(celdaEtiqueta("Subtotal:"));
            tablaTotales.addCell(celdaValor("Q " + formatearMoneda(ventaResponseDTO.getSubtotal())));

            tablaTotales.addCell(celdaEtiqueta("IVA (12%):"));
            tablaTotales.addCell(celdaValor("Q " + formatearMoneda(ventaResponseDTO.getIvaVenta())));

            tablaTotales.addCell(celdaEtiquetaNegrita("TOTAL:"));
            tablaTotales.addCell(celdaValorNegrita("Q " + formatearMoneda(ventaResponseDTO.getTotal())));

            documento.add(tablaTotales);

            // ---------- PIE ----------
            Paragraph pie = new Paragraph(
                    "\n\nGracias por su compra",
                    new Font(Font.HELVETICA, 9, Font.ITALIC, new Color(148, 163, 184)));
            pie.setAlignment(Element.ALIGN_CENTER);
            documento.add(pie);
        } catch (DocumentException e) {
            throw new OperacionInvalida("No se pudo generar la factura en PDF");
        } finally {
            documento.close();
        }

        return byteArrayOutputStream.toByteArray();
    }

    private PdfPCell celdaEncabezado(String texto, Color color) {
        Font fuente = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBackgroundColor(color);
        celda.setPadding(8f);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setBorderColor(Color.WHITE);
        return celda;
    }

    private PdfPCell celdaNormal(String texto) {
        Font fuente = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(30, 41, 59));
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setPadding(6f);
        celda.setBorderColor(new Color(226, 232, 240));
        return celda;
    }

    private PdfPCell celdaCentrada(String texto) {
        PdfPCell celda = celdaNormal(texto);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        return celda;
    }

    private PdfPCell celdaDerecha(String texto) {
        PdfPCell celda = celdaNormal(texto);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return celda;
    }

    private PdfPCell celdaEtiqueta(String texto) {
        Font fuente = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(71, 85, 105));
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBorder(Rectangle.NO_BORDER);
        celda.setPadding(4f);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return celda;
    }

    private PdfPCell celdaValor(String texto) {
        Font fuente = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(30, 41, 59));
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBorder(Rectangle.NO_BORDER);
        celda.setPadding(4f);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return celda;
    }

    private PdfPCell celdaEtiquetaNegrita(String texto) {
        Font fuente = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(15, 23, 42));
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBorder(Rectangle.NO_BORDER);
        celda.setPadding(6f);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return celda;
    }

    private PdfPCell celdaValorNegrita(String texto) {
        Font fuente = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(79, 70, 229));
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBorder(Rectangle.NO_BORDER);
        celda.setPadding(6f);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return celda;
    }

    private String valorSeguro(Object valor) {
        return valor != null ? valor.toString() : "—";
    }

    private String formatearMoneda(BigDecimal valor) {
        if (valor == null) {
            return "0.00";
        }
        return String.format("%.2f", valor);
    }

}
