package com.example.firstcomposeactivity.accompanist;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CheckCLass {

    List<String> newList = new ArrayList<>();

    List<String> EMOJIS = new ArrayList<>();

    List<String> answerString = new ArrayList<>();

    public List<String> getEmoji() {


        String s = new String(Character.toChars(Integer.decode("0x1F1FF")));
        Log.i("Emoji conversion to uni", s);


        EMOJIS.add("U+1F44F");
        EMOJIS.add("U+1F525");
        EMOJIS.add("U+1F602");
        EMOJIS.add("U+2764");
        EMOJIS.add("U+1F622");
        EMOJIS.add("U+1F929");
        EMOJIS.add("U+1F644");
        EMOJIS.add("U+1F53D");


//        int a = Integer.parseInt("0x1F44F");


        for (int i = 0; i < EMOJIS.size(); i++) {

            newList.add(EMOJIS.get(i).replace("U+", "0x"));

        }


        Log.d("Emoji converted", "emojiUTF_16 1F44F: " + new StringBuilder().appendCodePoint(0x1F44F).toString());
        Log.d("Emoji converted", "emojiUTF_16 1F525: " + new StringBuilder().appendCodePoint(0x1F525).toString());
        Log.d("Emoji converted", "emojiUTF_16 1F602: " + new StringBuilder().appendCodePoint(0x1F602).toString());
        Log.d("Emoji converted", "emojiUTF_16 1F622: " + new StringBuilder().appendCodePoint(0x1F622).toString());
        Log.d("Emoji converted", "emojiUTF_16 1F644: " + new StringBuilder().appendCodePoint(0x1F644).toString());


//            Log.i("Emoji conversion to uni",toUnicode("U+1F44F"));


        for (int i = 0; i < newList.size(); i++) {
//            Log.d("aaa", String.valueOf(a));
//            Character.toChars(Integer.decode("0x1F44F")
//                Log.i("Emoji to any list",new StringBuilder().appendCodePoint(Integer.parseInt(newList.get(i),16)).toString());
                Log.i("FullEmoji any list full",new String(Character.toChars(Integer.decode(newList.get(i)))));

            answerString.add(new String(Character.toChars(Integer.decode(newList.get(i)))));

        }


        try {
            //I am assuming you are getting unicode from an inputbox
            for (int i = 0; i < EMOJIS.size(); i++) {
                //I am also assuming you are getting emoji in hexadecimal form `U+<hexdigits>`
                String unicodeHexEmoji = "U+";
                StringBuilder sb = new StringBuilder();

                //Firstly you want to encode emojis to unicode types by converting to byte array
                byte[] utf8Bytes = EMOJIS.get(i).getBytes("UTF-8"); // "\\uf0\\u9f\\u98\\u80"
                byte[] utf16Bytes = EMOJIS.get(i).getBytes("UTF-16"); // "\\ud83d\\ude00"


                Log.d("Emjo converted", "emojiUTF_16 bytes: " + utf16Bytes);

                //convert emoji to hex
                for (byte b : utf16Bytes) {
                    sb.append(String.format("%02x", b));
                }
                //we are converting our current emoji to hex just for the purpose of this example
                unicodeHexEmoji += sb; //yields "U+feffd83dde21";
                byte[] utfHexBytes = getByteFromHex(unicodeHexEmoji.replace("U+", "")); // "\\ud83d\\ude00"
                //NB: we removed "U+" because its only a prefix denoting that the string is a <hex>

                //Decoding our unicodes back to emoji string
                String emojiUTF_8 = new String(utf8Bytes, StandardCharsets.UTF_8);
                String emojiUTF_16 = new String(utf16Bytes, "UTF-16");
                String emojiUTF_hex = new String(utfHexBytes, StandardCharsets.UTF_16);
                Log.d("Emoji", "emojiUTF_8 : " + emojiUTF_8);
                Log.d("Emoji", "emojiUTF_16 : " + emojiUTF_16);
                Log.d("Emoji", "emojiUTF_hex : " + emojiUTF_hex);
                //output
                //emojiUTF-8 : 😀
                //emojiUTF-16 : 😀
                //emojiUTF-hex : 😡

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        return answerString;

    }

    public byte[] getByteFromHex(String hexString) {
        //To convert hex string to byte array, you need to first get the length
        //of the given string and include it while creating a new byte array.
        byte[] val = new byte[hexString.length() / 2];
        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hexString.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        return val;
    }


    public static String toUnicode(String text) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            int codePoint = text.codePointAt(i);
            // Skip over the second char in a surrogate pair
            if (codePoint > 0xffff) {
                i++;
            }
            String hex = Integer.toHexString(codePoint);
            sb.append("\\u");
            for (int j = 0; j < 4 - hex.length(); j++) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }


}
