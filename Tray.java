import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tray {

	public static void main(String args[]) {

		TrayIcon trayIcon = null;
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage("icon.gif");
			PopupMenu popup = new PopupMenu();

			MenuItem close = new MenuItem("Close");
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			MenuItem instructions = new MenuItem("Instructions");
			instructions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame("instructions");
					JLabel label = new JLabel(
							"Drag your champion to the top of your side of the scoreboard. Press open bracket ([) if you are blue side or close bracket (]) if you are red side. Then hold tab and press backslash (\\) to hear what item to build next!");
					frame.getContentPane().add(label, BorderLayout.CENTER);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			});

			MenuItem about = new MenuItem("About");
			about.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame("About");
					JLabel label = new JLabel("Visit freelo.tech");
					frame.getContentPane().add(label, BorderLayout.CENTER);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			});

			popup.add(instructions);
			popup.add(about);
			popup.add(close);

			trayIcon = new TrayIcon(image, "freelo builds", popup);
			KeyListener.main(null);
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}
		} else {
			System.err.println("Tray unavailable");
		}

	}

}