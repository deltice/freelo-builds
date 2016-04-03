import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class KeyListener implements NativeKeyListener {
	
	private boolean blueSide = true;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_BACK_SLASH) {
//			try {
//				BufferedImage image = new Robot()
//						.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));	
//				try {
//					ImageAnalyze ia = new ImageAnalyze(image, blueSide);
//					int champ = ia.getUserChamp();
//					int[] userItems = ia.getUserItems();
//					int[] enemy1 = null;
//					int[] enemy2 = null;
//					int[] enemy3 = null;
//					int[] enemy4 = null;
//					int[] enemy5 = null;
//					if (blueSide) {
//						enemy1 = ia.getPlayer6();
//						enemy2 = ia.getPlayer7();
//						enemy3 = ia.getPlayer8();
//						enemy4 = ia.getPlayer9();
//						enemy5 = ia.getPlayer10();
//					} else {
//						enemy1 = ia.getPlayer1();
//						enemy2 = ia.getPlayer2();
//						enemy3 = ia.getPlayer3();
//						enemy4 = ia.getPlayer4();
//						enemy5 = ia.getPlayer5();
//					}
//					DatabaseSearcher d = new DatabaseSearcher(champ, userItems, enemy1, enemy2, enemy3, enemy4, enemy5);
//					String itemName = d.findNextItem();
					VoiceManager vm = VoiceManager.getInstance();
			        Voice voice = vm.getVoice("kevin16");
			        voice.allocate();
//				voice.speak(itemName);
			        voice.speak("Infinity Edge");
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			} catch (HeadlessException e1) {
//				e1.printStackTrace();
//			} catch (AWTException e1) {
//				e1.printStackTrace();
//			}
		}
		if (e.getKeyCode() == NativeKeyEvent.VC_OPEN_BRACKET) {
			blueSide = true;
		}
		if (e.getKeyCode() == NativeKeyEvent.VC_CLOSE_BRACKET) {
			blueSide = false;
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
	}

	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(new KeyListener());
	}

}
