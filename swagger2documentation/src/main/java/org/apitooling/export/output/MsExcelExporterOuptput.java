package org.apitooling.export.output;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MsExcelExporterOuptput implements ExporterOuptput {
	
	private WritableWorkbook workbook;
	
	public MsExcelExporterOuptput(WritableWorkbook workbook) {
		super();
		this.workbook = workbook;
	}

	@Override
	public void toFile(File output) throws IOException {
		//1. Create an Excel file
        try {
        	//Using the already provided output file
            //workbook.setOutputFile(output);
        	
            workbook.write();

        } catch (IOException cause) {
        	throw new IOException(cause);
        } finally {

            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException cause) {
                	throw new IOException(cause);
                } catch (WriteException cause) {
                	throw new IOException(cause);
                }
            }


        }		
	}
	
	public String asString() {
		return "";//template.toString();
	}
	
	@Override
	public String toString() {
		return this.asString();
	}

}
