/**you have to compile and run
 * the HuffmanEncode class first!
 * Do it with command line please to pass the file name
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDecode
{
    private static HashMap<Character, String> header = new HashMap<>();
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("there's no file as an input\nright usage: [java classname filename.dat]");
            System.exit(0);
        }

        String inputFile = args[0];
        ArrayList<Canonical> headerRow = null;
        String encodedStr = "";

        try
        {
            FileInputStream fileOut = new FileInputStream(new File(inputFile));
            ObjectInputStream ostream = new ObjectInputStream(fileOut);
            headerRow = (ArrayList<Canonical>) ostream.readObject();
            encodedStr = (String) ostream.readObject();
            ostream.close();

        }
        catch (IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }

        findCanonicalCode(headerRow, encodedStr);
    }

    //finding the canonical code of the characters in the header using their length
    private static void findCanonicalCode(ArrayList<Canonical> text, String encodedStr)
    {
        String code ="";

        for (int i = 0; i < text.size(); i++)
        {
            Canonical obj = text.get(i);
            if (i != 0)
            {
                Canonical prev = text.get(i - 1);
                if (obj.getLength() == prev.getLength())
                {
                    code = binaryAdd(code, obj.getLength());
                }
                else        // + 1 and append 0
                {
                    code = binaryAdd(code, obj.getLength() - 1);
                    for (int j = 0; j < (obj.getLength() - code.length()); j++)
                        code += "0";
                }
            }
            else
            {
                for (int j = 0; j < obj.getLength(); j++)
                    code += "0";
            }

            header.put(text.get(i).getLetter(), code);
        }
        decode(encodedStr);

    }
    private static String binaryAdd(String str, int bits)
    {
        int decimal = Integer.parseInt(str, 2);
        decimal ++;

        String binaryString = Integer.toBinaryString(decimal);
        if (binaryString.length() < bits)
            binaryString = String.format("%0" + bits + "d", Integer.parseInt(binaryString));

        return binaryString;
    }

    //a function for finding the equivalent code of each character length
    private static void decode(String text)
    {
        String str = "";
        String result = "";
        for (int i = 0; i < text.length(); i++)
        {
            str += text.charAt(i);
            Character ch = hasMeaning(str);
            if (ch != null)
            {
                result += ch;
                str = "";
            }
        }
        System.out.println(result);
        saveResult(result);
    }

    //if the traversed string is the canonical path of one of the characters
    private static Character hasMeaning(String str)
    {
        for (Map.Entry mapElement : header.entrySet())
        {
            String code = (String) mapElement.getValue();
            if (str.equals(code))
                return (Character) mapElement.getKey();
        }
        return null;
    }

    //saving the decoded file in another file
    private static void saveResult(String str)
    {
        try(FileWriter writer = new FileWriter("decoded-output.txt")) {
            writer.write(str);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
