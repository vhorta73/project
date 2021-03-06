package app.view.menu.file.fxml;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.controller.Controller;
import app.controller.FXMLController;
import app.model.serializable.ProjectData;
import app.model.serializable.ProjectDetails;
import app.view.ApplicationDialogFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The Menu File New Project Controller, receives data from the user
 * and sends it down through the controller.
 * 
 * @author Vasco
 *
 */
public class FileNewProjectFXMLController implements FXMLController {

	/**
	 * The FileChooser title.
	 */
	private final String FILE_CHOOSER_TITLE = "New Project";

	/**
	 * The extension list to be shown when saving the new project.
	 */
	private final List<Map<String,String>> EXTENSION_LIST;

    /**
     * The Create button.
     */
    @FXML
    private Button projectCreateId;
    
    /**
     * The Project name.
     */
    @FXML
    private TextField projectNameId;
    
    /**
     * The Project abstract.
     */
    @FXML
    private TextField projectAbstractId;
    
    /**
     * The Project authors.
     */
    @FXML
    private TextField projectAuthorsId;
    
    /**
     * The Project summary.
     */
    @FXML
    private TextArea projectSummaryId;
    
    /**
     * The Project description.
     */
    @FXML
    private TextArea projectDescriptionId;

    /**
     * The main controller.
     */
    private final Controller mainController;

    /**
     * Constructor to instantiate the required main controller.
     * 
     * @param mainController
     */
    public FileNewProjectFXMLController(Controller mainController) {
        this.mainController = mainController;
        this.EXTENSION_LIST = new LinkedList<>();
        Map<String,String> extensionProject = new HashMap<>();
        extensionProject.put("Project File", "*.prj");
        this.EXTENSION_LIST.add(extensionProject);
    }
    
    /**
     * Create a new Project with data given by the user.
     */
    @FXML
    private void projectCreateAction() {
    	// Retrieves data supplied from the user.
    	ProjectData projectData = getProjectData();

    	// Asks user where to save this new project.
    	File file = ApplicationDialogFactory.saveFile(mainController, FILE_CHOOSER_TITLE, EXTENSION_LIST);

    	// If no file returned, the user has cancelled the operation.
    	if ( file != null ) {

    		// Set the project file
        	mainController.setProjectFile(file);
        	
        	// Set the project data details
        	mainController.setProjectData(projectData);

        	// Save the file
        	mainController.saveProject();

        	// Close the widget.
        	Stage stage = (Stage) projectNameId.getScene().getWindow();
        	stage.close();
    	}
    }
    
    /**
     * Cancels any change and closes the window.
     */
    @FXML
    private void projectCancelAction() {
    	closeWindow();
    }
    
    /**
     * If escape is pressed, close the window.
     * 
     * @param keyEvent
     */
    @FXML
    public void keyTypedAction(KeyEvent keyEvent) {
    	if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
    		closeWindow();
    	}
    }
    
    /**
     * Retrieves the data from the GUI into a structured
     * object ProjectData which is then returned
     * 
     * @return ProjectData
     */
    private ProjectData getProjectData() {
    	// TODO: Validate input data
        ProjectData projectData = new ProjectDetails();
        projectData.setProjectName(projectNameId.getText());
        projectData.setProjectAbstract(projectAbstractId.getText());
        projectData.setProjectAuthors(projectAuthorsId.getText());
        projectData.setProjectDescription(projectDescriptionId.getText());
        projectData.setProjectSummary(projectSummaryId.getText());
        return projectData;
    }
    
    /**
     * Closing the window and lose any changes made without warning.
     */
    private void closeWindow() {
    	Stage stage = (Stage) projectNameId.getScene().getWindow();
        stage.close();
    }
}
