/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import interfacesData.IDataTable;
import java.util.Date;
import java.util.List;
import structData.Boat;
import structData.ChatMessage;
import structData.Position;

/**
 *
 * @author Irvin
 */
public class CDataTable implements IDataTable {
    
    private DataController controller;
    
    public CDataTable(DataController dc){
        super();
        controller = dc;
    }

    @Override
    public Boolean exit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void textMessage(ChatMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void textMessage(String content, Date time) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void coordinate(Position pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void coordinateShips(List<Boat> listBoat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
