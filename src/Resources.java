import org.powerbot.script.Random;
import org.powerbot.script.Tile;

/**
 * Created by Angel on 11/13/2015.
 */
public class Resources {
    private int[] NPCs = { 6748 };
    public static int[] runeIDs = { 556, 557, 559 };
    public static int[] talismanIDs = { 1438, 1440, 1442, 1446};
    public static int[] bankIDs = { 24101, 11748, 3194, 11744, 3193 };
    public static int runeEs = 1436;
    public static int falAltar = 14399, varAltar = 14405, alAltar = 14407, edAltar = 14409;
    public static int airPortal = 14841, earPortal = 14844, firPortal = 14845, bodPortal = 14846;
    public static int airAltarID = 14897, earAltarID = 14900, firAltarID = 14901, bodAltarID = 14902;
    public static Tile airAltarTile = new Tile(2842, 4833, 0), earAltarTile = new Tile(2658, 4839, 0), firAltarTile = new Tile(2584, 4840, 0), bodAltarTile = new Tile(2522, 4838, 0);
    public static Tile airPortalTile = new Tile(2841, 4830, 0), earPortalTile = new Tile(2657, 4830, 0), firPortalTile = new Tile(2576, 4848, 0), bodPortalTile = new Tile(2521, 4834, 0);
    public static Tile alEnd = new Tile(3312, 3253, 0), varEnd = new Tile(3305, 3472, 0), airEnd = new Tile(2841, 4830, 0), earEnd = new Tile(2657, 4830, 0), firEnd = new Tile(2576, 4848, 0), bodEnd = new Tile(2521, 4847, 0), edEnd = new Tile(3054, 3443, 0), falEnd = new Tile(2985, 3294, 0);
    public static Tile[] tileVarEar = {new Tile(3254, 3423, 0), new Tile(3256, 3427, 0), new Tile(3260, 3428, 0), new Tile(3265, 3429, 0), new Tile(3269, 3428, 0), new Tile(3274, 3428, 0), new Tile(3279, 3429, 0), new Tile(3283, 3430, 0), new Tile(3284, 3434, 0), new Tile(3284, 3439, 0), new Tile(3286, 3443, 0), new Tile(3286, 3448, 0), new Tile(3286, 3453, 0), new Tile(3289, 3456, 0), new Tile(3292, 3459, 0), new Tile(3295, 3462, 0), new Tile(3295, 3467, 0), new Tile(3299, 3469, 0), new Tile(3303, 3470, 0)};
    public static Tile[] tileAlFir = {new Tile(3382, 3268, 0), new Tile(3376, 3265, 0), new Tile(3369, 3265, 0), new Tile(3362, 3265, 0), new Tile(3355, 3265, 0), new Tile(3348, 3265, 0), new Tile(3341, 3265, 0), new Tile(3334, 3265, 0), new Tile(3327, 3265, 0), new Tile(3325, 3259, 0), new Tile(3325, 3252, 0), new Tile(3323, 3246, 0), new Tile(3319, 3241, 0), new Tile(3314, 3237, 0), new Tile(3310, 3242, 0), new Tile(3310, 3249, 0)};
    public static Tile[] tileEdBod = {new Tile(3091, 3491, 0), new Tile(3087, 3488, 0), new Tile(3083, 3485, 0), new Tile(3081, 3481, 0), new Tile(3080, 3477, 0), new Tile(3080, 3472, 0), new Tile(3080, 3467, 0), new Tile(3084, 3466, 0), new Tile(3086, 3462, 0), new Tile(3086, 3457, 0), new Tile(3084, 3453, 0), new Tile(3081, 3450, 0), new Tile(3077, 3451, 0), new Tile(3073, 3450, 0), new Tile(3072, 3446, 0), new Tile(3069, 3443, 0), new Tile(3065, 3440, 0), new Tile(3061, 3438, 0), new Tile(3059, 3438, 0), new Tile(3059, 3438, 0)};
    public static Tile[] tileFalAir = {new Tile(3012, 3359, 0), new Tile(3008, 3358, 0), new Tile(3008, 3353, 0), new Tile(3007, 3349, 0), new Tile(3007, 3344, 0), new Tile(3007, 3339, 0), new Tile(3007, 3334, 0), new Tile(3007, 3329, 0), new Tile(3007, 3324, 0), new Tile(3007, 3319, 0), new Tile(3006, 3315, 0), new Tile(3006, 3310, 0), new Tile(3004, 3306, 0), new Tile(3000, 3304, 0), new Tile(2998, 3300, 0), new Tile(2995, 3297, 0), new Tile(2991, 3295, 0)};
    public static Random rng = new Random();
}
