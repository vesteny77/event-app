package main.guipresenters;

import main.controllers.OrganizerController;
import main.controllers.ProgramController;
import main.gui_interface.ICreateEventUI;
import main.gui_interface.IModifyEventUI;
import main.gui_interface.ISelectRoomUI;
import main.guilisteners.BackButtonListener;
import main.guilisteners.ConfirmSelectRoomButtonListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter class for selecting roms
 *
 * @author Steven Yuan
 */
public class SelectRoomUIPresenter
        implements BackButtonListener, ConfirmSelectRoomButtonListener {

    ProgramController programController;
    OrganizerController organizerController;
    ISelectRoomUI iSelectRoomUI;
    ICreateEventUI iCreateEventUI;
    IModifyEventUI iModifyEventUI;

    public SelectRoomUIPresenter(ISelectRoomUI selectRoomUI,
                                 ProgramController programController) {
        this.iSelectRoomUI = selectRoomUI;
        this.programController = programController;
        this.organizerController = new OrganizerController(programController);
        iSelectRoomUI.addBackButtonListener(this);
        iSelectRoomUI.addSelectButtonListener(this);
    }

    /**
     * Go to the previous screen
     */
    @Override
    public void onBackButtonClicked() {
        programController.saveForNext();
        if (iSelectRoomUI.getEventIndex() == -1) {
            iCreateEventUI = iSelectRoomUI.goToCreateEventUI();
            iCreateEventUI.getValuesFromSelectRoomUI(iSelectRoomUI.getEventTitle(),
                    iSelectRoomUI.getEventType(), iSelectRoomUI.getEventDuration(),
                    iSelectRoomUI.getEventCapacity(), iSelectRoomUI.getEventDate());
            iCreateEventUI.getRoomNumFromSelectRoomUI(iSelectRoomUI.getRoomNum());
            new CreateEventUIPresenter(iCreateEventUI, programController);
        }
        else {
            iModifyEventUI = iSelectRoomUI.goToModifyEventUI();
            iModifyEventUI.storeEventIndex(iSelectRoomUI.getEventIndex());
            iModifyEventUI.storeRoomNum(iSelectRoomUI.getRoomNum());
            new ModifyEventUIPresenter(iModifyEventUI, programController);
        }
    }

    /**
     * Return the index of the room chosen by the user, -1 if none is chosen
     * @param category constraints for rooms
     * @return room index
     */
    @Override
    public int onConfirmSelectRoomButtonClicked(ArrayList<String> category) {
        List<Integer> rooms = organizerController.getEventController().
                getSuggestedRooms(category);
        if (rooms.size() == 0) {
            iSelectRoomUI.selectRoomError();
        }
        else {
            iSelectRoomUI.selectRoomSuccessful();
            return rooms.get(iSelectRoomUI.getRoomIndex());
        }
        return -1;
    }
}
