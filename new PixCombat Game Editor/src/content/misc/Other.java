package content.misc;

import content.LocatedImage;
import javafx.scene.image.Image;

public class Other {

	public static final LocatedImage BUTTONICON_SCALE_UP 					= Other.loadImage("images/menu/IMG_Editor_Scale_Up.png");
	public static final LocatedImage BUTTONICON_SCALE_DOWN 				= Other.loadImage("images/menu/IMG_Editor_Scale_Down.png");
	public static final LocatedImage BUTTONICON_SCALE_RIGHT				= Other.loadImage("images/menu/IMG_Editor_Scale_Right.png");
	public static final LocatedImage BUTTONICON_SCALE_LEFT 				= Other.loadImage("images/menu/IMG_Editor_Scale_Left.png");
	
	public static final LocatedImage BUTTONICON_SCALE_UPRIGHT 				= Other.loadImage("images/menu/IMG_Editor_Scale_UpRight.png");
	public static final LocatedImage BUTTONICON_SCALE_UPLEFT 				= Other.loadImage("images/menu/IMG_Editor_Scale_UpLeft.png");
	public static final LocatedImage BUTTONICON_SCALE_DOWNRIGHT			= Other.loadImage("images/menu/IMG_Editor_Scale_DownRight.png");
	public static final LocatedImage BUTTONICON_SCALE_DOWNLEFT				= Other.loadImage("images/menu/IMG_Editor_Scale_DownLeft.png");
	
	public static final LocatedImage BUTTONICON_SCALE_UP_HOVERED			= Other.loadImage("images/menu/IMG_Editor_Scale_Up_Hovered.png");
	public static final LocatedImage BUTTONICON_SCALE_DOWN_HOVERED 		= Other.loadImage("images/menu/IMG_Editor_Scale_Down_Hovered.png");
	public static final LocatedImage BUTTONICON_SCALE_RIGHT_HOVERED		= Other.loadImage("images/menu/IMG_Editor_Scale_Right_Hovered.png");
	public static final LocatedImage BUTTONICON_SCALE_LEFT_HOVERED 		= Other.loadImage("images/menu/IMG_Editor_Scale_Left_Hovered.png");
	
	public static final LocatedImage BUTTONICON_SCALE_UPRIGHT_HOVERED 		= Other.loadImage("images/menu/IMG_Editor_Scale_UpRight_Hovered.png");
	public static final LocatedImage BUTTONICON_SCALE_UPLEFT_HOVERED 		= Other.loadImage("images/menu/IMG_Editor_Scale_UpLeft_Hovered.png");
	public static final LocatedImage BUTTONICON_SCALE_DOWNRIGHT_HOVERED	= Other.loadImage("images/menu/IMG_Editor_Scale_DownRight_Hovered.png");
	public static final LocatedImage BUTTONICON_SCALE_DOWNLEFT_HOVERED		= Other.loadImage("images/menu/IMG_Editor_Scale_DownLeft_Hovered.png");

	public static final LocatedImage BUTTONICON_PLAY 						= Other.loadImage("images/menu/IMG_MenuButton_Play_Unhovered.png");
	public static final LocatedImage BUTTONICON_PLAY_HOVERED 				= Other.loadImage("images/menu/IMG_MenuButton_Play_Hovered.png");
	public static final LocatedImage BUTTONICON_STOP						= Other.loadImage("images/menu/IMG_MenuButton_Stop_Unhovered.png");
	public static final LocatedImage BUTTONICON_STOP_HOVERED				= Other.loadImage("images/menu/IMG_MenuButton_Stop_Hovered.png");
	
	public static final LocatedImage BUTTONICON_PRE_IMAGE					= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Image.png");
	public static final LocatedImage BUTTONICON_PRE_IMAGE_HOVERED 			= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Image_Hovered.png");
	
	public static final LocatedImage BUTTONICON_PRE_BOX					= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Boxes.png");
	public static final LocatedImage BUTTONICON_PRE_BOX_HOVERED	 		= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Boxes_Hovered.png");
	
	public static final LocatedImage BUTTONICON_MAGIC						= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Magic.png");
	public static final LocatedImage BUTTONICON_MAGIC_HOVERED				= Other.loadImage("images/menu/IMG_Editor_Bonus_Pre_Magic_Hovered.png");

	public static final LocatedImage BUTTONICON_CLEAR						= Other.loadImage("images/menu/IMG_MenuButton_Clear_Unhovered.png");
	public static final LocatedImage BUTTONICON_CLEAR_HOVERED	 			= Other.loadImage("images/menu/IMG_MenuButton_Clear_Hovered.png");
	
	public static final LocatedImage BUTTONICON_RESET						= Other.loadImage("images/menu/IMG_MenuButton_Reset_Unhovered.png");
	public static final LocatedImage BUTTONICON_RESET_HOVERED				= Other.loadImage("images/menu/IMG_MenuButton_Reset_Hovered.png");
	
	public static final LocatedImage BUTTONICON_REF_RESET						= Other.loadImage("images/menu/IMG_Editor_Bonus_Ref_Image.png");
	public static final LocatedImage BUTTONICON_REF_RESET_HOVERED				= Other.loadImage("images/menu/IMG_Editor_Bonus_Ref_Image_Hovered.png");

	public static final LocatedImage BUTTONICON_BUTTON_REF						= Other.loadImage("images/menu/IMG_MenuButton_Ref_Unhovered.png");
	public static final LocatedImage BUTTONICON_BUTTON_REF_HOVERED				= Other.loadImage("images/menu/IMG_MenuButton_Ref_Hovered.png");

	public static final LocatedImage BUTTONICON_CURRENT_IMAGE_SAVED_FLAG				= Other.loadImage("images/menu/IMG_MenuBox_EditImage_Ref_Saved.png");

	
	

	
	
	public static final float PERMITTED_MIN_VALUE_DURATION 	= 0f;
	public static final float PERMITTED_MAX_VALUE_DURATION 	= 9999f;
	public static final float PERMITTED_MIN_VALUE_XPOS 		= -100f;
	public static final float PERMITTED_MAX_VALUE_XPOS 		= 100f;
	public static final float PERMITTED_MIN_VALUE_YPOS 		= -100f;
	public static final float PERMITTED_MAX_VALUE_YPOS 		= 100f;
	
	
	public static LocatedImage loadImage(String url) {
		try {
			LocatedImage img = new LocatedImage(new Image(url),url);
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
