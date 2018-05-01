package content.misc;

import content.LocatedImage;
import javafx.scene.image.Image;

public class Other {

	public static final Image BUTTONICON_SCALE_UP 					= Other.loadImage("images/menu/IMG_Editor_Scale_Up.png");
	public static final Image BUTTONICON_SCALE_DOWN 				= Other.loadImage("images/menu/IMG_Editor_Scale_Down.png");
	public static final Image BUTTONICON_SCALE_RIGHT				= Other.loadImage("images/menu/IMG_Editor_Scale_Right.png");
	public static final Image BUTTONICON_SCALE_LEFT 				= Other.loadImage("images/menu/IMG_Editor_Scale_Left.png");
	
	public static final Image BUTTONICON_SCALE_UPRIGHT 				= Other.loadImage("images/menu/IMG_Editor_Scale_UpRight.png");
	public static final Image BUTTONICON_SCALE_UPLEFT 				= Other.loadImage("images/menu/IMG_Editor_Scale_UpLeft.png");
	public static final Image BUTTONICON_SCALE_DOWNRIGHT			= Other.loadImage("images/menu/IMG_Editor_Scale_DownRight.png");
	public static final Image BUTTONICON_SCALE_DOWNLEFT				= Other.loadImage("images/menu/IMG_Editor_Scale_DownLeft.png");
	
	public static final Image BUTTONICON_SCALE_UP_HOVERED			= Other.loadImage("images/menu/IMG_Editor_Scale_Up_Hovered.png");
	public static final Image BUTTONICON_SCALE_DOWN_HOVERED 		= Other.loadImage("images/menu/IMG_Editor_Scale_Down_Hovered.png");
	public static final Image BUTTONICON_SCALE_RIGHT_HOVERED		= Other.loadImage("images/menu/IMG_Editor_Scale_Right_Hovered.png");
	public static final Image BUTTONICON_SCALE_LEFT_HOVERED 		= Other.loadImage("images/menu/IMG_Editor_Scale_Left_Hovered.png");
	
	public static final Image BUTTONICON_SCALE_UPRIGHT_HOVERED 		= Other.loadImage("images/menu/IMG_Editor_Scale_UpRight_Hovered.png");
	public static final Image BUTTONICON_SCALE_UPLEFT_HOVERED 		= Other.loadImage("images/menu/IMG_Editor_Scale_UpLeft_Hovered.png");
	public static final Image BUTTONICON_SCALE_DOWNRIGHT_HOVERED	= Other.loadImage("images/menu/IMG_Editor_Scale_DownRight_Hovered.png");
	public static final Image BUTTONICON_SCALE_DOWNLEFT_HOVERED		= Other.loadImage("images/menu/IMG_Editor_Scale_DownLeft_Hovered.png");

	public static final Image BUTTONICON_PLAY 						= Other.loadImage("images/menu/IMG_MenuButton_Play_Unhovered.png");
	public static final Image BUTTONICON_PLAY_HOVERED 				= Other.loadImage("images/menu/IMG_MenuButton_Play_Hovered.png");
	public static final Image BUTTONICON_STOP						= Other.loadImage("images/menu/IMG_MenuButton_Stop_Unhovered.png");
	public static final Image BUTTONICON_STOP_HOVERED				= Other.loadImage("images/menu/IMG_MenuButton_Stop_Hovered.png");
	
	public static final Image BUTTONICON_PRE_IMAGE					= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Image.png");
	public static final Image BUTTONICON_PRE_IMAGE_HOVERED 			= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Image_Hovered.png");
	
	public static final Image BUTTONICON_PRE_BOX					= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Boxes.png");
	public static final Image BUTTONICON_PRE_BOX_HOVERED	 		= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Boxes_Hovered.png");
	
	public static final Image BUTTONICON_MAGIC						= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Magic.png");
	public static final Image BUTTONICON_MAGIC_HOVERED				= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Magic_Hovered.png");

	
	public static final float PERMITTED_MIN_VALUE_DURATION 	= 0f;
	public static final float PERMITTED_MAX_VALUE_DURATION 	= 9999f;
	public static final float PERMITTED_MIN_VALUE_XPOS 		= -100f;
	public static final float PERMITTED_MAX_VALUE_XPOS 		= 100f;
	public static final float PERMITTED_MIN_VALUE_YPOS 		= -100f;
	public static final float PERMITTED_MAX_VALUE_YPOS 		= 100f;
	
	
	public static LocatedImage loadImage(String url) {
		try {
			LocatedImage img = new LocatedImage(url);
			return img;
		} catch (Exception e) {
			System.out.println("No Image");
			return null;
		}
	}
	
	public static Image loadLocatedImage(String url) {
		try {
			Image img = new Image(url);
			return img;
		} catch (Exception e) {
			return null;
		}
	}
	
}
