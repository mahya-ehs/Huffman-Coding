/**This is the main class
 * Please do the compilation and running process using the command line.
 * Use the command line to pass the file names!
 */
import java.io.*;
import java.util.*;

public class HuffmanEncode
{
    //a hash map which contains huffman codes
    private static HashMap<Character, String> hashMap = new HashMap<>();

    //the string that we've received from the file
    private static String data = "";

    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("there's no file as an input\nright usage: [java classname filename.txt]");
            System.exit(0);
        }

        File file = new File(args[0]);

        if (!file.exists())
        {
            System.out.println("there's no such file!\nPlease enter the right name");
            System.exit(0);
        }

        try
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
            {
                data += scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        //this is a map for storing the characters
        // and their frequencies as an object og QueueNode class
        HashMap<Character, Integer> hashMap;
        SortedQueue queue = new SortedQueue();

        hashMap = countFrequency(data);
        for (Map.Entry mapElement : hashMap.entrySet())
        {
            int count = (int) mapElement.getValue();
            char letter = (char) mapElement.getKey();
            queue.add(new QueueNode(count, letter));
        }
        makeHuffmanTree(queue);
        makeCanonicalHuffman();
    }

    //the frequency of each character of ascii table will be calculated here
    private static HashMap<Character, Integer> countFrequency(String str)
    {
        HashMap<Character, Integer> map = new HashMap<>();
        int size = 127;
        str = str.toLowerCase();
        int n = str.length();

        //ArrayList<Integer> freq = new ArrayList<>();
        int[] freq = new int[size];

        for (int i = 0; i < n; i++)
        {
            freq[str.charAt(i)]++;
        }

        for (int i = 0; i < n; i++)
        {
            if (freq[str.charAt(i)] != 0)
            {
                map.put(str.charAt(i), freq[str.charAt(i)]);
                freq[str.charAt(i)] = 0;
            }
        }
        return map;

    }

    //after we made a queue out of SortedQueue class,
    // we pop the last element of it to find the minimum
    private static void makeHuffmanTree(SortedQueue queue)
    {
        while (queue.size() > 1)
        {
            QueueNode left = queue.pop();
            QueueNode right = queue.pop();
            int sum = left.getCount() + right.getCount();
            queue.add(new QueueNode('*', sum, left, right));
        }

        QueueNode root = queue.front();
        encode(root, "");
    }

    //the recursive function of encoding tree
    private static void encode(QueueNode node, String path)
    {
        //we have traversed the whole tree
        if (node == null)
            return;

        //we find a node which is an actual letter (not an interval character)
        if ( node.isLeaf())
        {
            String str = path + (node.isRightChild() ? '1' : '0');
            hashMap.put(node.getLetter(),  path.length() > 0 ? path : "1");
        }
        //right is 1 and left is 0
        encode(node.getRightChild(), path + '1');
        encode(node.getLeftChild(), path + '0');
    }

    //a function for making canonical code
    // based on the length of the huffman code of the characters
    private static void makeCanonicalHuffman()
    {
        ArrayList<QueueNode> sortedHuffmanCode = new ArrayList<>();

        //adding the character and its path and length
        // to an array list as a new object of QueueNode class
        for (Map.Entry mapElement : hashMap.entrySet())
        {
            char character = (char) mapElement.getKey();
            String path = (String) mapElement.getValue();
            int length = path.length();
            sortedHuffmanCode.add(new QueueNode(path, length, character));
        }
        bubbleSort(sortedHuffmanCode);

        //in this loop, the canonical code of each character will be calculated
        for (int i = 0; i < sortedHuffmanCode.size(); i++)
        {
            String cano = "";

            //the first symbol
            if (i == 0)
            {
                int size = sortedHuffmanCode.get(i).getPathLength();
                for (int j = 0; j < size; j++)
                {
                    cano += "0";
                }
                sortedHuffmanCode.get(i).setCanonicalPath(cano);
            }
            else
            {
                //if the current symbol has a repeated length,
                // we have to add 1 to the previous symbol canonical code
                if ((sortedHuffmanCode.get(i - 1).getPathLength() == sortedHuffmanCode.get(i).getPathLength()))
                {
                    int bits = sortedHuffmanCode.get(i - 1).getPathLength();
                    sortedHuffmanCode.get(i).setCanonicalPath(binaryAdd(sortedHuffmanCode.get(i - 1).getCanonicalPath(), bits));
                }
                else
                {
                    int bits = sortedHuffmanCode.get(i).getPathLength();
                    String code = binaryAdd(sortedHuffmanCode.get(i - 1).getCanonicalPath(), bits-1);
                    int len1 = code.length();
                    int len2 = sortedHuffmanCode.get(i).getPathLength();
                    for (int j = 0; j < len2 - len1; j++)
                    {
                        code += "0";
                    }
                    sortedHuffmanCode.get(i).setCanonicalPath(code);
                }
            }
        }

        //writing the results in a file
        writeInFile(sortedHuffmanCode);

    }

    //adding 1 to a binary string
    private static String binaryAdd(String str, int bits)
    {
        int decimal = Integer.parseInt(str, 2);
        decimal ++;

        String binaryString = Integer.toBinaryString(decimal);
        if (binaryString.length() < bits)
            binaryString = String.format("%0" + bits + "d", Integer.parseInt(binaryString));

        return binaryString;
    }

    //ascending bubble sort
    private static void bubbleSort(ArrayList<QueueNode> arr)
    {
        int n = arr.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr.get(j).getPathLength() > arr.get(j + 1).getPathLength())
                {
                    // swap arr[j+1] and arr[j]
                    QueueNode temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
    }

    private static void writeInFile(ArrayList<QueueNode> list)
    {
        String result = "";
        ArrayList<Canonical> array = new ArrayList<>();

        //I used another class , Canonical class,  to send the data
        for (QueueNode queueNode : list)
        {
            array.add(new Canonical(queueNode.getCanonicalPath().length(), queueNode.getLetter()));
        }

        //replacing each character with its equivalent canonical huffman code
        for (int i = 0; i < data.length(); i++)
        {
            char symbol = Character.toLowerCase(data.charAt(i));
            String code = "";
            for (QueueNode queueNode : list)
            {
                if (queueNode.getLetter() == symbol)
                    code = queueNode.getCanonicalPath();

            }
            result += code;
        }
        System.out.println("Huffman codes :");
        System.out.println(hashMap);
        try
        {
            FileOutputStream fileOut = new FileOutputStream(new File("encoded-output.dat"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(array);
            objectOut.writeObject(result);
            objectOut.close();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

}
