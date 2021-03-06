package uk.co.firstzero

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileTree

import uk.co.firstzero.xml.ModifyPath
import uk.co.firstzero.xml.XMLCleanTask
import uk.co.firstzero.xml.XMLDiffTask

import uk.co.firstzero.csv.CsvDiffTask
import uk.co.firstzero.csv.CsvToExcelTask

import uk.co.firstzero.sql.ReadBlobTask

import uk.co.firstzero.webdav.PullTask
import uk.co.firstzero.webdav.PushTask

/**
 * Plugin for CsvDiffTask
 */
class CsvDiffPluginExtension {
	String keyColumns
	FileTree controlDirectory
	String testDirectory
	String resultDirectory
	String separator = ','
}

/**
 * Plugin for CsvToExcelTask
 */
class CsvToExcelPluginExtension {
	String outputFile
	FileTree inputFiles
	String separator = ','
}

/**
 * Plugin for ReadBlobTask
 */
class ReadBlobPluginExtension {
	String className;
	String jdbcUrl;
	String user;
	String password;
	String extension;
	String sql;
	String outputDirectory;
	boolean unzip;
}

/**
 * Plugin for PullTask
 */
class PullPluginExtension {
	String user;
	String password;
	String url;
	String file;
	String outFile;
	String proxyUser;
	String proxyPassword;
	String proxyHost;
	boolean overwrite;
	int proxyPort;
}

/**
 * Plugin for PushTask
 */
class PushPluginExtension {
	String user
	String password
	String url
	String proxyUser
	String proxyPassword
	String proxyHost
	int proxyPort
	boolean overwrite
	boolean createDirectoryStructure
	FileTree tree
}

/**
 * Plugin for XMLCleanTask
 */
class XMLCleanPluginExtension {
	String renamePattern	//Example //date or //date#_#//price
	FileTree inputDirectory
	String outputDirectory
	List<ModifyPath> modifyPaths
}

/**
 * Plugin for XMLDiffTask
 */
class XMLDiffPluginExtension {
	String resultDirectory;
	String separator = ","
	FileTree controlDirectory
	String testDirectory
}

/**
 * Declarative class for Gradle plugin
 */
class AddOnJavaAntTasksPlugin implements Plugin<Project> {
    /**
     *
     * @param target
     */
	void apply(Project target) {
		target.extensions.create('csvDiffArgs', CsvDiffPluginExtension)
		target.task('csvDiffTask', group:'AddOnJavaAntTasks', description: 'Diffs two directories containing csv files. Each directory must have the same name and number of files as the other', type: CsvDiffTask)
		
		target.extensions.create('csvToExcelArgs', CsvToExcelPluginExtension)
		target.task('csvToExcelTask', group:'AddOnJavaAntTasks', description:'Converts a set of csv files into 1 Excel file. Each csv file is a sheet within excel', type: CsvToExcelTask)

		target.extensions.create('readBlobArgs', ReadBlobPluginExtension)
		target.task('readBlobTask', group:'AddOnJavaAntTasks', description: 'Extracts Blobs from Database.SQL should contain a string name and then blob', type: ReadBlobTask)

		target.extensions.create('pullArgs', PullPluginExtension)
		target.task('pullTask', group:'AddOnJavaAntTasks', description:'Downloads files from a WEBDAV site, proxy configuration is supported', type: PullTask)

		target.extensions.create('pushArgs', PushPluginExtension)
		target.task('pushTask', group:'AddOnJavaAntTasks', description:'Pushes files to a WEBDAV site, proxy configuration is supported', type: PushTask)

		target.extensions.create('xmlCleanArgs', XMLCleanPluginExtension)
		target.task('xmlCleanTask', group:'AddOnJavaAntTasks', description:'Strips out fluff from the XML and manipulating the XML. The use case for this is, before comparison, sometimes XMLs need to be cleaned and renamed. The cleaned xmls can be diffed using xmlunittask.', type: XMLCleanTask)

		target.extensions.create('xmlDiffArgs', XMLDiffPluginExtension)
		target.task('xmlDiffTask', group:'AddOnJavaAntTasks', description:'Diffs two directories containing xml files. Each directory must have the same name and number of files as the other', type: XMLDiffTask)

	}
}
