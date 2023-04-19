// first i can consider making xor with cycles and maybe do padding to match the cycles
// second i may consider doing 2 dimentional shifts like the vigenaire cipher
// third i may consider doing a rotor machine in which each cycle i rotate the key by a certain shift depending on what mathematical formula i decide
// as long as i can keep track of the encryption, i can decrypt anything

// encrypt 1100 1001 by using 01
// 1100 1001    1100 1001   1100 1001
// xor
// 01           01 (+1)     01
//   01           10 (+2)     01
//      01           11 (+3)     01
//        01           11 (+4)     01
// 1001 1100    1010 0110   see below
// [first]      [second]    [third]
// normalMethod Vigenere    RotationMachine

// probability is 1 / 2^n+i where i (power rule probability distribution) is the number of flips, so i highly recommend actually flipping more than a bit at each iteration https://www.desmos.com/calculator/pjdqp9v3ig

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class NewEncrypter
{
    private static final String KEY = "PqA3s^";

    private static int[] rotator(int key_length, int key_bit_number, int iterations)
    {
        // generates the rotation machine requires by the algorithm
        int[] gears = new int[iterations];
        Random rand = new Random(key_length);
        for (int i = 0; i < iterations; i++)
        {
            gears[i] = rand.nextInt(2*key_bit_number+1)-key_bit_number;
        }
        return gears;
    }

    private static int getShift(int[] array, int index)
    {
        // getShift(key_bits[], myGears[i])
        if (index < 0)
            index += array.length;
        return array[index];
    }

    // private static int[] encoder(String string) throws UnsupportedEncodingException
    // {
    //     ByteBuffer bytes = ByteBuffer.wrap(string.getBytes("utf-8"));
    //     // you must specify a charset
    //     IntBuffer ints = bytes.asIntBuffer();
    //     int numInts = ints.remaining();
    //     int[] result = new int[numInts];
    //     ints.get(result);
    //     return ints.array();
    // }

    private static String decodeString(int[] passBits)
    {
        byte[] bytes = new byte[passBits.length / 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bytes[i] |= (passBits[(i * 8) + j] << (7 - j));
            }
        }
        return new String(bytes);
    }

    private static int[] encodeString(String password)
    {
        // gets the integer array of the bits of the passord
        byte[] bytes = password.getBytes();
        int[] bits = new int[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bits[(i * 8) + j] = (bytes[i] >> (7 - j)) & 1;
            }
        }
        return bits;
    }

    private static int not(int bit)
    {
        if (bit == 0)
            return 1;
        else
            return 0;
    }

    // private static int getPadding(int keyLength, int passLength)
    // {
    //     if (keyLength < passLength)
    //         return passLength%keyLength;
    //     else if (keyLength == passLength)
    //         return 0;
    //     else
    //         return -keyLength%passLength;
    // }

    private static int getMultiple(int keyLength, int passLength) {
        int n = 1;
        while (n * keyLength < passLength) {
            n++;
        }
        return n;
    }
    
    public static String encrypt(String password)
    {
        // to be used by other methods...
        int[] pass = encodeString(password), key = encodeString(KEY), newKey = new int[key.length*getMultiple(key.length, pass.length)], newPass = new int[pass.length]; // the new key array length must be a multiple of key length not added with the padding, but the operation must be up till the padded area
        int iterations = getMultiple(key.length, pass.length), index, round=0;
        int[] myGears = rotator(KEY.length(), key.length, iterations);
        // now i have the gears to get the shifts i need in the index array
        for (int i = 0; i < iterations; i++)
        {
            index = getShift(key, myGears[i]);
            key[index] = not(key[index]);
            for (int j = 0; j < key.length; j++)
            {
                newKey[j + round] = key[j];
                if ((j+round)%8 == 0 && (j+round) <= pass.length-1) // for each MSB in the newKey as long, as we're still concerned with the password bits, do:
                {
                    // bit mask for 00H till 7FH
                    int msb = newKey[j+round]^pass[j+round];
                    if (msb == 1)
                        newKey[j+round] = not(newKey[j+round]);
                }
            }
            round = round+key.length;
        }
        // now i have the new toggled key
        for (int i = 0; i < pass.length; i++)
            newPass[i] = pass[i]^newKey[i];
        // these are the new password bits which will be encoded
        return decodeString(newPass);
    }

    public static String decrypt(String encryptedPassword)
    {
        return encrypt(encryptedPassword);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String password = "i have a neat pass";
        String encoded = encrypt(password);
        String decoded = decrypt(encoded);
        int[] passBits = encodeString(password), encodedBits=  encodeString(encoded), decodedBits = encodeString(decoded), KEYBits = encodeString(KEY);

        System.out.println();
        System.out.println(String.format("decoded pass is %s : %b", decoded, decoded.equals(password)));

        System.out.println();
        System.out.print("[PassBits]: ");
        for (int i = 0; i < passBits.length; i++)
            System.out.print(passBits[i]);
        System.out.println();
        System.out.print("[encodedPassBits]: ");
        for (int i = 0; i < encodedBits.length; i++)
            System.out.print(encodedBits[i]);
        System.out.println();
        System.out.print("[decodedPassBits]: ");
        for (int i = 0; i < decodedBits.length; i++)
            System.out.print(decodedBits[i]);
        System.out.println();
        System.out.print("[KEYBits]: ");
        for (int i = 0; i < KEYBits.length; i++)
            System.out.print(KEYBits[i]);
        
        System.out.println();
        System.out.println("[The Passowrds]: ");
        System.err.println(encoded);
        System.err.println(decoded);
    }
}
