package io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.ss.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.ss.handler.RowContentsHandler;
import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.ss.handler.RowListener;
import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.utils.Beans;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import io.github.dmitrymiyuzov.validator.xlsx.poiObjectMapper.SpreadsheetReadException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;


/**
 * Reader impletementation of {@link Workbook} for an OOXML .xlsx file. This implementation is
 * suitable for low memory sax parsing or similar.
 */
public class XlsxReader extends AbstractSpreadsheetReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(XlsxReader.class);


    // Constructor

    public XlsxReader() {
        super();
    }


    // SpreadsheetReader Impl
    // ------------------------------------------------------------------------

    @Override
    public <T> void read(Class<T> beanClz, InputStream is, RowListener<T> listener)
            throws SpreadsheetReadException {
        // Sanity checks
        if (!Beans.isInstantiableType(beanClz)) {
            throw new IllegalArgumentException("XlsxReader :: Invalid bean type passed !");
        }

        try {
            final OPCPackage opcPkg = OPCPackage.open(is);

            // XSSF Reader
            XSSFReader xssfReader = new XSSFReader(opcPkg);

            // Content Handler
            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable ssTable = new ReadOnlySharedStringsTable(opcPkg);
            SheetContentsHandler sheetHandler = new RowContentsHandler<>(beanClz, listener, 0);

            ContentHandler handler = new XSSFSheetXMLHandler(styles, ssTable, sheetHandler, true);

            // XML Reader
            XMLReader xmlParser = XMLHelper.newXMLReader();
            xmlParser.setContentHandler(handler);

            // Iterate over sheets
            XSSFReader.SheetIterator worksheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            for (int i = 0; worksheets.hasNext(); i++) {
                InputStream sheetInpStream = worksheets.next();

                String sheetName = worksheets.getSheetName();

                // Parse sheet
                xmlParser.parse(new InputSource(sheetInpStream));
                sheetInpStream.close();
            }
        } catch (Exception ex) {
            String errMsg = String.format("Error reading sheet data, to Bean %s : %s", beanClz, ex.getMessage());
            LOGGER.error(errMsg, ex);
            throw new SpreadsheetReadException(errMsg, ex);
        }
    }


    @Override
    public <T> void read(Class<T> beanClz, InputStream is, int sheetNo, RowListener<T> listener)
            throws SpreadsheetReadException {
        // Sanity checks
        if (!Beans.isInstantiableType(beanClz)) {
            throw new IllegalArgumentException("XlsxReader :: Invalid bean type passed !");
        }

        try {
            final OPCPackage opcPkg = OPCPackage.open(is);

            // XSSF Reader
            XSSFReader xssfReader = new XSSFReader(opcPkg);

            // Content Handler
            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable ssTable = new ReadOnlySharedStringsTable(opcPkg);
            SheetContentsHandler sheetHandler = new RowContentsHandler<T>(beanClz, listener, 0);

            ContentHandler handler = new XSSFSheetXMLHandler(styles, ssTable, sheetHandler, true);

            // XML Reader
            XMLReader xmlParser = XMLHelper.newXMLReader();
            xmlParser.setContentHandler(handler);

            // Iterate over sheets
            XSSFReader.SheetIterator worksheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            for (int i = 0; worksheets.hasNext(); i++) {
                InputStream sheetInpStream = worksheets.next();
                if (i != sheetNo) {
                    continue;
                }

                String sheetName = worksheets.getSheetName();

                // Parse Sheet
                xmlParser.parse(new InputSource(sheetInpStream));
                sheetInpStream.close();
            }

        } catch (Exception ex) {
            String errMsg = String.format("Error reading sheet %d, to Bean %s : %s", sheetNo, beanClz, ex.getMessage());
            LOGGER.error(errMsg, ex);
            throw new SpreadsheetReadException(errMsg, ex);
        }
    }

    /**
     * Метод предназначен для парсинга файла формата XLSX в список объектов переданного класса.
     *
     * @param file файл, из которого будут извлечены данные.
     * @param cls  класс, объекты которого будут созданы после извлечения данных.
     * @return возвращает ArrayList c полученными объектов.
     */
    public <T> List<T> parse(File file, Class<T> cls) {
        try (Workbook book = WorkbookFactory.create(file)) {
            Sheet sheet = book.getSheetAt(0);
            List<String> headers = StreamSupport.stream(sheet.getRow(0).spliterator(), false)
                    .map(Cell::getStringCellValue)
                    .toList();
            return StreamSupport.stream(sheet.spliterator(), false)
                    .skip(1) //пропуск строки с заголовками
                    .map(row -> StreamSupport.stream(row.spliterator(), false)
                            .map(Cell::getStringCellValue)
                            .toList())
                    .map(cellsValues -> IntStream.range(0, headers.size())
                            .boxed()
                            .collect(Collectors.toMap(headers::get, cellsValues::get)))
                    .map(mapViewEntity -> new ObjectMapper().convertValue(mapViewEntity, cls))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}