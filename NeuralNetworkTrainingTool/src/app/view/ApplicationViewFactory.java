package app.view;

import app.controller.Controller;
import app.view.menu.file.FileEditProjectController;
import app.view.menu.file.FileNewProjectController;

/**
 * The Application menu views.
 * 
 * @author Vasco
 *
 */
public abstract class ApplicationViewFactory {
    
    /**
     * Starting the File New Project View, using passed Controller.
     * 
     * @param mainController Controller
     */
    public static void startFileNewProjectController(Controller mainController) {
        new FileNewProjectController(mainController);
    }

    /**
     * Starts the File Edit Project View, passing the Controller to 
     * show what is currently loaded and allowing editing.
     * 
     * @param mainController Controller
     */
    public static void startFileEditProjectController(Controller mainController) {
        new FileEditProjectController(mainController);
    }

    public static void startFileLoadProjectController(Controller mainController) {
        // TODO Auto-generated method stub
    }

    public static void startFileSaveProjectController(Controller mainController) {
        // TODO Auto-generated method stub
    }

    public static void startFileCloseProjectController(Controller mainController) {
        // TODO Auto-generated method stub
    }

    public static void startFileImportNeuralNetworkConfigController(Controller mainController) {
        // TODO Auto-generated method stub
    }

    public static void startFileExportNeuralNetworkConfigController(Controller mainController) {
        // TODO Auto-generated method stub
    }

    public static void startFileExportEncogController(Controller mainController) {
        // TODO Auto-generated method stub
    }

    public static void startFileExportReportController(Controller mainController) {
        // TODO Auto-generated method stub
    }

    public static void startFilePreferencesController(Controller mainController) {
        // TODO Auto-generated method stub
    }

}
