package countries;

public class Kiegeszitok {

    public static int charCount(String s, char c){
        int db = 0;
        for (int i=0; i<s.length(); i++){
            if (s.charAt(i) == c){
                db++;
            }
        }
        return db;
    }

    public static int vowelCount(String s){
        int db = 0;
        db += charCount(s, 'a');
        db += charCount(s, 'e');
        db += charCount(s, 'i');
        db += charCount(s, 'o');
        db += charCount(s, 'u');
        return db;
    }
    
}
