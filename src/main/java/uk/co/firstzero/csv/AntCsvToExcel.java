/*
Copyright (c) 2011, 2012 Parjanya Mudunuri

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

http://opensource.org/licenses/mit-license.php
 */

package uk.co.firstzero.csv;

import org.apache.log4j.Logger;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.util.Vector;

public class AntCsvToExcel extends Task {
    private static Logger logger = Logger.getLogger(AntCsvToExcel.class);
	private Vector<FileSet> fileSets = new Vector<FileSet>();
	private String outputFile;
	private char separator = ',';

	public AntCsvToExcel() { }


	/**
	 * Input file set
	 * @param fileset Input set of files
	 */
	public void addFileSet(FileSet fileset) {
		if (!fileSets.contains(fileset)) {
    	  fileSets.add(fileset);
      	}
	}

	/**
	 * CSV separator
	 * @param separator CSV separator
	 */
	public void setSeparator(char separator) {
		this.separator = separator;
	}


	/**
	 * Set the output excel file
	 * @param outputFile Output file to be written to
	 */
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}



	public void execute()  {
		DirectoryScanner ds;
        int count = 0;

		for (FileSet fileset : fileSets) {
			//Read in the control files
			ds = fileset.getDirectoryScanner(getProject());
        	String[] filesInSet = ds.getIncludedFiles();
            String oFile = this.outputFile;

            if (count > 1)
                oFile = new File(this.outputFile).getName() + "_" + count + ".xls";

            try {
                CsvToExcel csv = new CsvToExcel(filesInSet, oFile, ds.getBasedir().toString(), this.separator);
                csv.execute();
            } catch (Exception e) {
                logger.error("Unable to process ");
                logger.error(e);

            }

            ++count;
		}
	}
}
