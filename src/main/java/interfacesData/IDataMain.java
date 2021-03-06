/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesData;

import java.net.UnknownHostException;
import java.util.Date;
import structData.ContactGroup;
import structData.Game;
import structData.User;
import java.awt.Image;
import java.util.HashSet;
import java.util.List;
import structData.Profile;

/**
 *
 * @author Irvin
 */
public interface IDataMain {
    
    /**
     * Modify the local profile
     * @param username : new username
     * @param password : new password
     * @param avatar : new avatar
     * @param lastName : new lastName
     * @param firstName : new firstName
     * @param borthDate : new birthDate
     */
    void editProfile(String username, String password, String avatar, String lastName, String firstName, Date borthDate);
    
    /**
     * Create a local account
     * @param idUser : unique ID of the user
     * @param login
     * @param username
     * @param ips : list of the IP adresses known by the user
     * @param password
     * @param contactList : list of the user's contacts
     * @param avatar
     * @param lastname
     * @param firstname
     * @param birthDate
     */
    void createAccount(String login, String username, HashSet ips, String password, List<ContactGroup> contactList, String avatar, String lastname, String firstname, Date birthDate);
    
    /**
     * Returns the statistics of an user
     * @return a profile
     */
    Profile getStatistics();
    
    /**
     * Notifies the away application that an user wants to join a game
     * @param g : Game the user wants to join
     */
    void notifGameChosen(Game g);
    
    /**
     * Notifies away applications that the local user disconnects and erases his session.
     */
    void askDisconnection();
    
    /**
     * Loads the saved data of the user and researches players.
     */
    void connection() throws UnknownHostException;
    
    /**
     * Add a new game to the list of games
     * @param g : game to add
     */
    Game newGame(Boolean newClassicType, String newName, 
            Boolean newHumanOpponent, int newTimePerShot, 
            Boolean newSpectator, Boolean newSpectatorChat);
}
