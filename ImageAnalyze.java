import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImageAnalyze {

	private final int SIZE = 34;
	private final int[] PLAYER_1_X = new int[] { 715, 749, 783, 817, 851, 885 };
	private final int PLAYER_1_Y = 332;
	private final int[] PLAYER_2_X = PLAYER_1_X;
	private final int PLAYER_2_Y = 407;
	private final int[] PLAYER_3_X = PLAYER_1_X;
	private final int PLAYER_3_Y = 483;
	private final int[] PLAYER_4_X = PLAYER_1_X;
	private final int PLAYER_4_Y = 558;
	private final int[] PLAYER_5_X = PLAYER_1_X;
	private final int PLAYER_5_Y = 634;
	private final int[] PLAYER_6_X = new int[] { 1287, 1321, 1355, 1389, 1423, 1457 };
	private final int PLAYER_6_Y = 332;
	private final int[] PLAYER_7_X = PLAYER_6_X;
	private final int PLAYER_7_Y = 407;
	private final int[] PLAYER_8_X = PLAYER_6_X;
	private final int PLAYER_8_Y = 483;
	private final int[] PLAYER_9_X = PLAYER_6_X;
	private final int PLAYER_9_Y = 558;
	private final int[] PLAYER_10_X = PLAYER_6_X;
	private final int PLAYER_10_Y = 634;

	private final int[] player1;
	private final int[] player2;
	private final int[] player3;
	private final int[] player4;
	private final int[] player5;
	private final int[] player6;
	private final int[] player7;
	private final int[] player8;
	private final int[] player9;
	private final int[] player10;
	
	private final int[] userItems;
	private final int userChamp;
	private final int CHAMP_SIZE = 56;
	
	private final static int[] ignore = new int[] { 1001, 1004, 1006, 1011, 1018, 1026, 1027, 1028, 1029, 1031, 1033,
			1036, 1037, 1038, 1039, 1041, 1042, 1043, 1051, 1052, 1053, 1054, 1055, 1056, 1057, 1058, 1082, 1083, 1300,
			1301, 1302, 1303, 1305, 1306, 1307, 1308, 1310, 1311, 1312, 1313, 1315, 1316, 1317, 1318, 1320, 1321, 1322,
			1323, 1325, 1326, 1327, 1328, 1330, 1331, 1332, 1333, 2003, 2009, 2015, 2031, 2032, 2033, 2043, 2053, 2138,
			2139, 2140, 3006, 3009, 3010, 3020, 3024, 3028, 3034, 3035, 3043, 3044, 3047, 3052, 3067, 3070, 3077, 3082,
			3086, 3096, 3097, 3098, 3101, 3105, 3108, 3111, 3113, 3114, 3117, 3123, 3133, 3134, 3136, 3140, 3144, 3145,
			3155, 3158, 3191, 3196, 3197, 3198, 3200, 3211, 3240, 3301, 3302, 3303, 3599, 3706, 3711, 3715, 3751,
			3801 };

	public ImageAnalyze(BufferedImage image, boolean blueSide) throws IOException {

		player1 = new int[6];
		player2 = new int[6];
		player3 = new int[6];
		player4 = new int[6];
		player5 = new int[6];
		player6 = new int[6];
		player7 = new int[6];
		player8 = new int[6];
		player9 = new int[6];
		player10 = new int[6];

		// player 1
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_1_X[i] + x, PLAYER_1_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player1[i] = nsf.getItemId();
		}
		// player 2
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_2_X[i] + x, PLAYER_2_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player2[i] = nsf.getItemId();
		}
		// player 3
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_3_X[i] + x, PLAYER_3_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player3[i] = nsf.getItemId();

		}
		// player 4
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_4_X[i] + x, PLAYER_4_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player4[i] = nsf.getItemId();

		}
		// player 5
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_5_X[i] + x, PLAYER_5_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player5[i] = nsf.getItemId();
		}
		// player 6
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_6_X[i] + x, PLAYER_6_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player6[i] = nsf.getItemId();
		}
		// player 7
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_7_X[i] + x, PLAYER_7_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player7[i] = nsf.getItemId();
		}
		// player 8
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_8_X[i] + x, PLAYER_8_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player8[i] = nsf.getItemId();
		}
		// player 9
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_9_X[i] + x, PLAYER_9_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player9[i] = nsf.getItemId();
		}
		// player 10
		for (int i = 0; i < 6; i++) {
			BufferedImage item = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					item.setRGB(x, y, image.getRGB(PLAYER_10_X[i] + x, PLAYER_10_Y + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "res");
			player10[i] = nsf.getItemId();
		}
		
		if (blueSide) {
			userItems = player1;
			BufferedImage item = new BufferedImage(CHAMP_SIZE, CHAMP_SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < CHAMP_SIZE; x++) {
				for (int y = 0; y < CHAMP_SIZE; y++) {
					item.setRGB(x, y, image.getRGB(482 + x, 321 + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "champs");
			userChamp = nsf.getItemId();
		} else {
			userItems = player6;
			BufferedImage item = new BufferedImage(CHAMP_SIZE, CHAMP_SIZE, BufferedImage.TYPE_3BYTE_BGR);
			for (int x = 0; x < CHAMP_SIZE; x++) {
				for (int y = 0; y < CHAMP_SIZE; y++) {
					item.setRGB(x, y, image.getRGB(1052 + x, 322 + y));
				}
			}
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(item, "champs");
			userChamp = nsf.getItemId();
		}

	}

	public int[] getUserItems() {
		return userItems;
	}

	public int getUserChamp() {
		return userChamp;
	}

	public int[] getPlayer1() {
		return player1;
	}

	public int[] getPlayer2() {
		return player2;
	}

	public int[] getPlayer3() {
		return player3;
	}

	public int[] getPlayer4() {
		return player4;
	}

	public int[] getPlayer5() {
		return player5;
	}

	public int[] getPlayer6() {
		return player6;
	}

	public int[] getPlayer7() {
		return player7;
	}

	public int[] getPlayer8() {
		return player8;
	}

	public int[] getPlayer9() {
		return player9;
	}

	public int[] getPlayer10() {
		return player10;
	}

	public static boolean isComplete(int id) {
		return (Arrays.binarySearch(ignore, id) < 0);
	}

	public static void main(String[] args) throws IOException {
		ImageAnalyze ia = new ImageAnalyze(ImageIO.read(new File("Screen01.png")), true);
		int[] player1 = ia.player1;
		for (int i = 0; i < player1.length; i++) {
			System.out.println(player1[i]);
		}
		int[] userItems = ia.userItems;
		for (int i = 0; i < player1.length; i++) {
			System.out.println(userItems[i]);
		}
		System.out.println(ia.userChamp);
	}

}
