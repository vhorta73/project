package app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import app.core.activationFunction.ActivationFunctionFactory;
import app.core.activationFunction.ActivationFunctionKey;
import app.core.neuralNetwork.NeuralNetworkPatternFactory;
import app.core.neuralNetwork.NeuralNetworkPatternKey;
import app.model.file.DataSetFileGUIReader;
import app.model.serializable.DataSetFileAttributes;
import app.model.serializable.FileAttributes;
import app.model.serializable.NeuralNetworkConfig;
import app.model.serializable.NeuralNetworkConfigImpl;
import app.model.serializable.ProjectData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

/**
 * The Main Application Controller.
 * 
 * @author Vasco
 *
 */
public class ControllerImpl implements Controller {

    /**
     * The Project File where all data is stored.
     */
    private File projectFile;
    
    /**
     * The Project details to be stored in file.
     */
    private ProjectData projectData;
    
    /**
     * Flag to indicate if all changes have been saved.
     */
    private boolean projectIsSaved = true;

    /**
     * The selected data set file with all its attributes.
     */
    private FileAttributes dataSetFileAttributes;
    
    /**
     * The Neural Network configuration set by the user.
     */
    private NeuralNetworkConfig neuralNetworkConfig;

    /**
     * Controller Constructor to initialise required objects.
     */
    public ControllerImpl() {
        neuralNetworkConfig = new NeuralNetworkConfigImpl();
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectSaved() {
        if ( projectIsSaved ) return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getProjectFile() {
        return projectFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProjectFile(File file) {
        this.projectFile = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeProject() {
        this.projectData = null;
        this.projectFile = null;
        this.projectIsSaved = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProjectData(ProjectData projectData) {
        this.projectData = projectData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveProject() {
        // TODO: Deal with exceptions
        if ( projectFile != null ) {
            if ( ! projectFile.exists() ) {
                try {
                    projectFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            if ( projectFile.canWrite() ) {
                FileOutputStream fout = null;
                ObjectOutputStream oos = null;
                try {
                    fout = new FileOutputStream(projectFile);
                    oos = new ObjectOutputStream(fout);
                    
                    // The list of objects that must be then read back 
                    // in the same order. Note: Changing this will result
                    // in older projects to not be readable anymore.
                    oos.writeObject(projectData);
                    oos.writeObject(dataSetFileAttributes);
                    oos.writeObject(neuralNetworkConfig);

                    projectIsSaved = true;

                } catch (IOException e) { 
                    e.printStackTrace();
                } finally {
                    try {
                        fout.close();
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectData getProjectData() {
        return projectData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadProject(File file) {
        if ( file != null ) {
            if ( file.exists() & file.canRead() ) {
                projectFile = file;
                FileInputStream fin = null;
                ObjectInputStream ois = null;
                try {
                    fin = new FileInputStream(file);
                    ois = new ObjectInputStream(fin);
                    try {

                        // The list of objects that must be then read back 
                        // in the same order in which they were saved. 
                        // Note: Changing this will result in older projects 
                        // to not be readable anymore.
                        projectData = (ProjectData) ois.readObject();
                        dataSetFileAttributes = (FileAttributes) ois.readObject();
                        neuralNetworkConfig = (NeuralNetworkConfig) ois.readObject();

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    projectIsSaved = true;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fin.close();
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setDataSetFile(File selectedFile) {
        this.dataSetFileAttributes = new DataSetFileAttributes();
        dataSetFileAttributes.setFilename(selectedFile.getAbsolutePath());
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setDataSetAttributes(Integer headerLines, Integer footerLines, String separator) {
        if ( dataSetFileAttributes != null ) {
            dataSetFileAttributes.setHeaderRows(headerLines);
            dataSetFileAttributes.setFooterRows(footerLines);
            dataSetFileAttributes.setSeparator(separator);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override public List<String> getNeuralNetworkPatternList() {
        List<String> list = new LinkedList<>();
        for( NeuralNetworkPatternKey key : NeuralNetworkPatternKey.values() ) {
            list.add(NeuralNetworkPatternFactory.getName(key));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override public String getNeuralNetworkPatternDescription(NeuralNetworkPatternKey neuralNetworkPattern) {
        return NeuralNetworkPatternFactory.getDescription(neuralNetworkPattern);
    }

    /**
     * {@inheritDoc}
     */
    @Override public String getNeuralNetworkPatternDescription(int neuralNetworkId) {
        int index = 0;
        for( NeuralNetworkPatternKey key : NeuralNetworkPatternKey.values() ) {
            if ( neuralNetworkId == index ) return NeuralNetworkPatternFactory.getDescription(key);
            index++;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override public List<String> getActivationFunctionList() {
        List<String> list = new LinkedList<>();
        for( ActivationFunctionKey key : ActivationFunctionKey.values() ) {
            list.add(ActivationFunctionFactory.getName(key));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override public List<String> getHeaderColumns() {
        // Cast to a super set for the non-serializable parsing capabilities.
        DataSetFileGUIReader dataSet = (DataSetFileGUIReader) dataSetFileAttributes;
        
        if ( dataSet == null ) return null;

        // Check if we have a header
        int headerRows = dataSet.getHeaderRows();
        
        // If we have header, return it.
        if ( headerRows > 0 ) {
            List<List<String>> headerColumns = dataSet.getHeaderDataRows();
            return headerColumns.get(headerRows-1);
        }
        // No header? Return first data row instead
        return dataSet.getFirstDataRows(1).get(0);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override public TableColumn<ObservableList, ?> getDataSetTableColumnHeader() {
        TableColumn<ObservableList, ?> allcols = new TableColumn<>();

        List<String> columnHeader = getHeaderColumns();
        if ( columnHeader != null ) {
            for(int i = 0 ; i < columnHeader.size(); i++) {
                int j = i;
                TableColumn<ObservableList,String> col = new TableColumn<>(columnHeader.get(i));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                   public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                       return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                allcols.getColumns().add(col);
            }
        }

        return allcols;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override public List<ObservableList> getDataSetTableRows(int dataRows) {
        // Cast to a super set for the non-serializable parsing capabilities.
        DataSetFileGUIReader dataSet = (DataSetFileGUIReader) dataSetFileAttributes;
        
        if ( dataSet == null ) return null;

        List<List<String>> firstDataRows = dataSet.getFirstDataRows(dataRows);

        List<ObservableList> rowList = new LinkedList<>();

        // TODO: The real thing
        for(int i = 0; i < firstDataRows.size(); i++ ) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.addAll(firstDataRows.get(i));
            rowList.add(row);
        }
        
        return rowList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NeuralNetworkConfig getNeuralNetworkConfig() {
        return neuralNetworkConfig;
    }
}
