# Huffman Coding
I've done this project as a part of Data Structure Course. It's made up of main parts: decoding and encoding.

## Project Overview
**Encoding:**

1.First a file name is passed to the arguments of the encoding class which is called "HuffmanEncode".

2.After that, we count the frequency of each letter and we'll save each character with its 
frequency as an object of the "QueueNode" class in a hashmap.

3.The next step is to sort the hashmap in the ascending form, based on their frequencies.

4.Once the hashmap is sorted, we'll be able to encode each character.

5.The encoding process will be done in "encode" method; it's a recursive methode which left nodes are known as "0"
and right ones are "1". So the result of encoding each character will be a string made up of 0s and 1s.

6.Based on the length of each huffman code ,which has been made in the previous step, the canonical code will be
constructed.

7.The last step is to write header and the result of encoding in a binary file. I put the result of encoding in a file
named "encoded-output". The header is an arraylist of "Canonical" objects which contains each character with its 
canonical code length.

**Decoding Part**

1.First a file name is passed to the arguments of the encoding class which is called "HuffmanDecode".

2.Then we'll get the canonical code of each character in header by its length

3.After that, we start traversing the text and replacing each meaningful string with a character.

## Usage
compile : `javac classname.java`

run for encode : `java classname demo.txt`

run for decode : `java classname encoded-output.dat`


## Contact
Created by [mahya.ehsanimehr@gmail.com](mailto:mahya.ehsanimehr@gmail.com) - feel free to contact me!