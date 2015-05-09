/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;

import java.awt.*;
import java.util.*;

import javax.swing.JLabel;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	public String displayMessage;
	GLabel labelMessage = new GLabel("Welcome to FacePamphlet");
	GLabel labelName = new GLabel("");
	GLabel labelStatus = new GLabel("");
	GLabel labelNoImage = new GLabel("No Image");
	GLabel labelFriendLabel = new GLabel("Friends");
	ArrayList<GLabel> labelFriends = new ArrayList<GLabel>();
	GImage image = null;
	GRect imageFrame = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
	double xPosition;
	double yPosition;
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
		xPosition = this.getLocation().getX();
		yPosition = this.getLocation().getY();
//		System.out.println(getSize());
		
		labelMessage.setFont(MESSAGE_FONT);
		labelName.setColor(Color.BLUE);
		labelName.setFont(PROFILE_NAME_FONT);
		labelStatus.setFont(PROFILE_STATUS_FONT);
		labelFriendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		labelNoImage.setFont(PROFILE_IMAGE_FONT);
		image = new GImage("StanfordLogo.jpg");
		image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		
		add(labelMessage,300-labelMessage.getWidth()/2, APPLICATION_HEIGHT-BOTTOM_MESSAGE_MARGIN-labelMessage.getHeight());
		add(labelName,LEFT_MARGIN,TOP_MARGIN+labelName.getHeight());
		add(imageFrame,LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN+labelName.getHeight());
		add(labelNoImage,LEFT_MARGIN+IMAGE_WIDTH/2-labelNoImage.getWidth()/2,TOP_MARGIN+IMAGE_MARGIN+labelName.getHeight()+IMAGE_HEIGHT/2);
//		add(image,LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN+labelName.getHeight());
		add(labelStatus,LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN+labelName.getHeight()+IMAGE_HEIGHT+STATUS_MARGIN+labelStatus.getHeight());
//		add(labelFriendLabel,300,TOP_MARGIN+IMAGE_MARGIN+labelName.getHeight()-labelFriendLabel.getHeight());
		add(labelFriendLabel,300,TOP_MARGIN+IMAGE_MARGIN+labelName.getHeight());
		add(image,LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN+labelName.getHeight());
		image.setSize(0, 0);
	}
	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		// You fill this in
		labelMessage.setLabel(msg);
		labelMessage.setLocation(300-labelMessage.getWidth()/2, APPLICATION_HEIGHT-BOTTOM_MESSAGE_MARGIN-labelMessage.getHeight());
//		repaint();
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		// You fill this in
		if (profile == null) {
			labelName.setLabel("");
			labelStatus.setLabel("");
			labelFriendLabel.setLabel("");
			for (GLabel gLabel : labelFriends) {
				gLabel.setLabel("");
			}
			if (image != null) {
				image.setSize(0, 0);
				image = null;
			}
		}
		else {
			
			labelName.setLabel(profile.getName());
			labelFriendLabel.setLabel("Friends");
			
			for (GLabel gLabel : labelFriends) {
				gLabel.setLabel("");
			}
			
			Iterator it = profile.getFriends();
			int count = 0;
			while (it.hasNext()) {
				String friend = (String) it.next();
				
				if (count<labelFriends.size()) {
					labelFriends.get(count).setLabel(friend);
				}
				else {
					labelFriends.add(count, new GLabel(friend));
					labelFriends.get(count).setFont(PROFILE_FRIEND_FONT);
					add(labelFriends.get(count), 300, labelFriendLabel.getY()+(count+1)*labelFriends.get(count).getHeight());
				}
				
				count += 1;
			}
			
//			for (it; i < profile.friends.size(); i++) {
//				labelFriends.get(i).setFont(PROFILE_FRIEND_FONT);
//				labelFriends.get(i).setLabel(profile.friends.);
//			}
//			
			if (profile.getImage() != null) {
				if (image != null) {
					image.setSize(0, 0);
					image = null;
				}
				image = profile.getImage();
				image.setSize(IMAGE_WIDTH,IMAGE_HEIGHT);
				add(image,LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN+labelName.getHeight());
			}
			else {
				labelNoImage.setLabel("No Image");
				if (image != null) {
					image.setSize(0, 0);
					image = null;
				}
			}
			if (profile.getStatus().isEmpty()) {
				labelStatus.setLabel("No current status");
			}
			else {
				labelStatus.setLabel(profile.getName()+" is "+profile.getStatus());
			}
		}
		
		
	}

	
}
