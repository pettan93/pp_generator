import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ImageGenerator {

    // do not touch these
    private static List<String> fontPaths = new ArrayList<>();
    private static final String fs = File.separator;
    private static final String resouces_path = "resources";


    // definitely touch these!
    private static final String alphabet_out = "alphabet_13";
    private static final int samples = 50;

    // alphabet settings
    private static final String font = "OpenSans-Regular.ttf";
    private static final int size = 32;
    private static final int fontSize = 25;
    private static final Boolean all_fonts = Boolean.FALSE;
    private static final Boolean upperCase = Boolean.FALSE;
    private static final Boolean noise = Boolean.FALSE;
    private static final int noiseLevel = 2;                // 1 - 10
    private static final Boolean grid = Boolean.FALSE;
    private static final int gridSize = 10;                  // 1 - 10 px
    private static final int rotationApmlitude = 30;
    private static final int scaleApmlitude = 30;
    private static final Boolean randomUpperCase = Boolean.FALSE;

    static List<String> captchas = new ArrayList<>();

    private static HashMap<Character, ArrayList> generatedMap = new HashMap<>();


    static {
        if (all_fonts)
            fontPaths.addAll(loadFonts());
        else
            fontPaths.add(resouces_path + fs + "fonts" + fs + font);
    }

    private static CaptchaGenerator generator = new CaptchaGenerator(1, 1, null, fontSize, grid, gridSize, rotationApmlitude, scaleApmlitude);


    public static void main(String[] args) throws IOException {



        // pokud jde o volání z Pythonu
        if (args.length > 0) {
            argsCall(args);
            System.exit(0);
        }

        generator = new CaptchaGenerator(1, 1, null, fontSize, grid, gridSize, rotationApmlitude, scaleApmlitude);


        makeDir(resouces_path + fs + "output" + fs + alphabet_out);

        // generate lowercase alphabet
        generateAlphabet(resouces_path + fs + "output" + fs + alphabet_out + fs + "lowercase" + fs, false, samples);

        // mkay.. generate also uppercase alphabet
        if (upperCase)
            generateAlphabet(resouces_path + fs + "output" + fs + alphabet_out + fs + "uppercase" + fs, true, samples);


        captchas.add("hello nsa project");
        captchas.add("i love apple brand");
        captchas.add("manageiq");
        captchas.add("legless lego legolass");
        captchas.add("leglessness");
        captchas.add("alphabet");
        captchas.add("abcdefghijklmnopqrstuvwxyz");
        captchas.add("virtual machine");
        captchas.add("fedora");
        captchas.add("rip in peace");
        captchas.add("love");
        captchas.add("mendel university in brno");
        captchas.add("red alert two");
        captchas.add("zerglings at the gates");
        int i = 0;


        clean(resouces_path + fs + "output" + fs + alphabet_out);


    }




    public static BufferedImage generateFromTask(Task task) {
        return generate(task.letter.toString());
    }

    private static BufferedImage generate(String str) {

        generator.setWidth(str.length() * size);
        generator.setHeight(size);
        generator.setFontPath(fontPaths.get(new Random().nextInt(fontPaths.size())));

        generator.setup();

        char[] charArray = str.toCharArray();

        // pokud generuju captcha texty pro abecedu, pro kterou jsem si nageneroval i velke pismena, chci lowercase a uppercase pismena nahodne namichane
        if (randomUpperCase) {
            Random rand = new Random();
            for (int i = 0; i < charArray.length; i++) {
                int randomNum = rand.nextInt(10) + 1;
                if (randomNum > 5)
                    charArray[i] = Character.toUpperCase(charArray[i]);
            }
        }


        BufferedImage bi = generator.createCaptcha(charArray);

        if (noise)
            bi = addNoise(bi, noiseLevel);

//        File outfile = new File(outputFile);
//        try {
//            ImageIO.write(bi, format, outfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        generatedImages.add(bi);


        return bi;


    }




    private static BufferedImage addNoise(BufferedImage bf, int power) {

        Random rand = new Random();

        Color myWhite = new Color(0, 0, 0); // Color white
        int rgb = myWhite.getRGB();

        for (int i = 0; i < bf.getHeight(); i++) {
            for (int j = 0; j < bf.getWidth(); j++) {
                int randomNum = rand.nextInt(10) + 1;
                if (randomNum > (10 - power))
                    bf.setRGB(j, i, rgb);
            }
        }

        return bf;
    }

    /**
     * Vygeneruje abecedu
     *
     * @param outDir  Zadejte kořenový adresář pri vygenerování abecedy
     * @param samples Počet vzorků na každý znak
     */
    private static void generateAlphabet(String outDir, Boolean upperCase, int samples) {

        System.out.println("Generuji abecedu do [" + outDir + "] počet vzorků na písmeno [" + samples + "]");


        char[] alphabet = upperCase ? "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray() : "abcdefghijklmnopqrstuvwxyz".toCharArray();

        File root = makeDir(outDir);

        for (Character c : alphabet) {

            if (!generatedMap.containsKey(c))
                generatedMap.put(c, new ArrayList());

            File charFolder = makeDir(root.getPath() + "/" + c);

            for (int i = 0; i < samples; i++) {
                System.out.println("Generuji písmeno [" + c + "] vzorek [" + i + "]");
                BufferedImage bi = generate(Character.toString(c));
                generatedMap.get(c).add(bi);


            }
        }
    }


    /**
     * Pro zavolani přes java -jar
     *
     * @param args argumenty pro generování, dle pořadí
     *             0. - řetězec ,který se má vygenerovat
     *             1. - název souboru, který se má vytvořit (včetně přípony)
     *             2. - cesta k souboru s fontem
     *             3. - výška (nepovinné - defaultně 60px)
     *             4. - šířka (nepovinné - defaultně (počet znaků řetězce * 60px))
     *             5. - velikost písma (nepovinné - defaultně 60px)
     *             6. - aplituda rotace (nepovinné - defaultně 30)
     *             7. - aplituda změnvy velikosto (nepovinné - defaultně 40)
     *             8. - vykreslit mřížku (nepovinné - defaultně False)
     *             9. - velikost mřížky (nepovinné - defaultně 10px)
     */
    private static void argsCall(String[] args) {
        String str = args[0];
        String outputFile = args[1];
        String fontFile = args[2];

        int height = (!args[3].equals("d")) ? Integer.parseInt(args[3]) : 60;
        int width = (!args[4].equals("d")) ? Integer.parseInt(args[4]) : str.length() * 60;
        int fontSize = (!args[5].equals("d")) ? Integer.parseInt(args[5]) : 60;
        int rotationAmplitude = (!args[6].equals("d")) ? Integer.parseInt(args[6]) : 30;
        int scaleAmplitude = (!args[7].equals("d")) ? Integer.parseInt(args[7]) : 40;
        boolean showGrid = (!args[8].equals("d")) && Boolean.parseBoolean(args[8]);
        int gridSize = (!args[9].equals("d")) ? Integer.parseInt(args[9]) : 10;


        String format = outputFile.substring(outputFile.lastIndexOf(".") + 1, outputFile.length());

        CaptchaGenerator generator = new CaptchaGenerator(width, height, fontFile, fontSize, showGrid, gridSize, rotationAmplitude, scaleAmplitude);

        char[] charArray = str.toCharArray();

        generator.setup();
        BufferedImage bi = generator.createCaptcha(charArray);

        File outfile = new File(outputFile);
        try {
            ImageIO.write(bi, format, outfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static File makeDir(String path) {
        File theDir = new File(path);
        if (theDir.exists())
            delDir(path);

        if (!theDir.exists()) {
            try {
                theDir.mkdirs();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        return theDir;
    }


    public static void delDir(String path) {
        File f = new File(path);
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delDir(c.getPath());
        }
        if (!f.delete())
            try {
                throw new FileNotFoundException("Failed to delete file: " + f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    /**
     * Vymaže všechny soubory a adresáře v daném umistění kromě
     * sobouru zip
     *
     * @param source_folder
     */
    public static void clean(String source_folder) {
        for (File file : new File(source_folder).listFiles()) {
            if (file.getName().contains("zip")) {
            } else {
                file.delete();
                if (file.isDirectory())
                    delDir(file.getPath());
            }

        }
    }

    public static ArrayList<String> loadFonts() {
        File fontDir = new File(resouces_path + fs + "fonts");

        ArrayList<String> fontPaths = new ArrayList<>();

        File[] listOfFiles = fontDir.listFiles();

        for (File font : listOfFiles) {
            fontPaths.add(font.getPath());
        }

        return fontPaths;
    }


}
