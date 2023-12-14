/**a class for sending the data in a file**/

import java.io.Serializable;

public class Canonical implements Serializable
{
    private int length;
    private Character letter;

    public Canonical(int length, Character letter)
    {
        this.length = length;
        this.letter = letter;
    }

    public int getLength()
    {
        return length;
    }

    public Character getLetter()
    {
        return letter;
    }
}
