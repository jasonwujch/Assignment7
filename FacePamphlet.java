/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import acmx.export.javax.swing.JTextField;

import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.*;
import java.sql.DatabaseMetaData;

import javax.swing.*;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;

import com.sun.org.apache.bcel.internal.generic.FASTORE;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {
	
	FacePamphletDatabase database;
	FacePamphletProfile currentProfile = null;
	
	JTextField tFieldName = new JTextField(TEXT_FIELD_SIZE);
	JTextField tFieldChangeStatus = new JTextField(TEXT_FIELD_SIZE);
	JTextField tFieldChangePicture = new JTextField(TEXT_FIELD_SIZE);
	JTextField tFieldAddFriend = new JTextField(TEXT_FIELD_SIZE);
	JButton buttonChangeStatus = new JButton("Change Status");
	JButton buttonChangePicture = new JButton("Change Picture");
	JButton buttonAddFriend = new JButton("Add Friend");
	JButton buttonAdd = new JButton("Add");
	JButton buttonDelete = new JButton("Delete");
	JButton buttonLookup = new JButton("Lookup");
	JLabel labelName = new JLabel("Name");
	
	private FacePamphletCanvas canvas = new FacePamphletCanvas();
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		// You fill this in
		
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		setTitle("FacePamphlet");
		
		add(tFieldChangeStatus, WEST);
		add(buttonChangeStatus,WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		add(tFieldChangePicture, WEST);
		add(buttonChangePicture,WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		add(tFieldAddFriend, WEST);
		add(buttonAddFriend,WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(labelName,NORTH);
		add(tFieldName, NORTH);
		add(buttonAdd, NORTH);
		add(buttonDelete, NORTH);
		add(buttonLookup, NORTH);
		
		add(canvas);
		
		
		Listener al = new Listener();
		
		tFieldName.addActionListener(al);
		tFieldChangeStatus.addActionListener(al);
		tFieldChangePicture.addActionListener(al);
		tFieldAddFriend.addActionListener(al);
		buttonAdd.addActionListener(al);
		buttonDelete.addActionListener(al);
		buttonLookup.addActionListener(al);
		buttonChangeStatus.addActionListener(al);
		buttonChangePicture.addActionListener(al);
		buttonAddFriend.addActionListener(al);
		
		database = new FacePamphletDatabase();
		
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
	private class Listener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			// You fill this in as well as add any additional methods
			String name = "";
			FacePamphletProfile profile = null;
			
			if (e.getSource().equals(buttonAdd) && !tFieldName.getText().trim().isEmpty()) {
				name = tFieldName.getText().trim();
				if (database.containsProfile(name)) {
					profile = database.getProfile(name);
					currentProfile = profile;
					canvas.displayProfile(profile);
					canvas.showMessage("A profile with the name "+name+" already exists");
				}
				else {
					profile = new FacePamphletProfile(tFieldName.getText().trim());
					database.addProfile(profile);
					currentProfile = profile;
					canvas.displayProfile(profile);
					canvas.showMessage("New profile created");
				}
			}
			else if (e.getSource().equals(buttonDelete) && !tFieldName.getText().trim().isEmpty()) {
				name = tFieldName.getText().trim();
				currentProfile = null;
				if (database.containsProfile(name)) {
					database.deleteProfile(name);
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Profile of "+name+" deleted");
				}
				else {
					canvas.displayProfile(currentProfile);
					canvas.showMessage("A profile with the name "+name+" does not exist");
				}
			}
			else if (e.getSource().equals(buttonLookup) && !tFieldName.getText().trim().isEmpty()) {
				name = tFieldName.getText().trim();
				if (database.containsProfile(name)) {
					profile = database.getProfile(name);
					currentProfile = profile;
					canvas.displayProfile(profile);
					canvas.showMessage("Displaying "+name);
				}
				else {
					currentProfile = null;
					canvas.displayProfile(currentProfile);
					canvas.showMessage("A profile with the name "+name+" does not exist");
				}
							}
			else if ( (e.getSource().equals(buttonChangeStatus) || e.getSource().equals(tFieldChangeStatus)) && !tFieldChangeStatus.getText().isEmpty() ) {
				if (currentProfile != null) {
					currentProfile.setStatus(tFieldChangeStatus.getText());
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Status updated to "+currentProfile.getStatus());
				}
				else {
					canvas.showMessage("Please select a profile to change status");
				}
			}
			else if ( (e.getSource().equals(buttonChangePicture) || e.getSource().equals(tFieldChangePicture)) && !tFieldChangePicture.getText().trim().isEmpty() ) {
				String filename = tFieldChangePicture.getText().trim();
				if (currentProfile != null) {
					GImage image = null;
					 try {
					 image = new GImage(filename);
					
					 } catch (ErrorException ex) {
					 // Code that is executed if the filename cannot be opened.
						 canvas.showMessage("Unable to open image file: "+filename);
					 }
					 if (image!=null) {
						currentProfile.setImage(image);
						canvas.displayProfile(currentProfile);
						canvas.showMessage("Picture updated");
					}
				}
				else {
					canvas.showMessage("Please select a profile to change picture");
				}
			}
			else if ( (e.getSource().equals(buttonAddFriend) || e.getSource().equals(tFieldAddFriend)) && !tFieldAddFriend.getText().trim().isEmpty() ) {
				String friend = tFieldAddFriend.getText().trim();
				if (currentProfile != null) {
					if (database.containsProfile(friend)) {
						if (friend.equals(currentProfile.getName())) {
							canvas.showMessage("One cannot friend oneself sadly :(");
						}
						else if (!currentProfile.friends.contains(friend)) {
							currentProfile.addFriend(friend);
							database.getProfile(friend).addFriend(currentProfile.getName());
							canvas.displayProfile(currentProfile);
							canvas.showMessage(friend+" added as a friend");
						}
						else {
							canvas.showMessage(currentProfile.getName()+" already has "+friend+" as a friend");
						}
					}
					else {
						canvas.showMessage(friend+" does not exist");
					}
				}
				else {
					canvas.showMessage("Please select a profile to add friend");
				}
			}
	    	
		}
		
	}
	
    

}
