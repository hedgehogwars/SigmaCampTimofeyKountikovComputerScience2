import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static String randomWord() throws FileNotFoundException {
        File input = new File("words.txt");
        Scanner file = new Scanner(input);
        Random random = new Random();
        int randomLine = random.nextInt(210627);//210626 is the number of lines, and since .nextInt goes from 0 to that number-1 we add one to have it properly access the word
        for(int i = 1;i<randomLine;i++){
            file.next();
        }
        return file.next();
    }
    public static void hangMan(String word,int guesses){
        boolean[] won = {false};//won is an array to allow methods to change it, I do not know of any better methods
        int guessesLeft;
        String[] letters = new String[26];
        String[] wordArray = word.split("");
        boolean[] lettersGuessedCorrect = new boolean[word.length()];
        for(guessesLeft = guesses; guessesLeft>=0;guessesLeft--){
            String letter;
            if(guessesLeft!=guesses){
                displayHangMan(lettersGuessedCorrect,word,wordArray,letters,guesses,guessesLeft);
            }
            letter = getGuess(letters,guessesLeft,word,won);

            if(won[0]){break;}
            for(int i = 0;i<wordArray.length;i++){
                if(wordArray[i].equals(letter)){lettersGuessedCorrect[i] = true;}
            }
            letters[guesses-guessesLeft] = letter;
        }
        if(won[0]){
            System.out.println("Congratulations, you correctly guessed the word! It was "+word+". You had "+guessesLeft+" guesses left.");
        }
        else{
            System.out.println("You lost, The word was "+word+", maybe try again?");
        }
    }

    private static String getGuess(String[] letters,int guessesLeft, String word,boolean[] won) {
        Scanner input = new Scanner(System.in);
        String letter;
        while(true){
            System.out.println();
            System.out.println("Enter a guess");
            System.out.println("You have "+guessesLeft+" guesses left");
            letter = input.next().toLowerCase();
            if(letter.equals(word)){
                won[0] = true;
                break;
            }
            if(letter.length() != 1||!Character.isLetter(letter.charAt(0))){
                System.out.println("That is not a letter, try again");
            }
            else if(Arrays.asList(letters).contains(letter)){
                System.out.println("You have already guessed this letter");
            }
            else{break;}
        }
    }

    private static void displayHangMan(boolean[] lettersGuessedCorrect, String word, String[] wordArray,String[] letters,int guesses,int guessesLeft) {
        System.out.println();
        for(int i = 0;i<wordArray.length;i++){
            if(lettersGuessedCorrect[i]){System.out.print(wordArray[i]);}
            else{System.out.print("_");}
        }
        System.out.println();
        System.out.println("You have guessed:");
        for(int i = 0; i<guesses-guessesLeft-1;i++) {
            System.out.print(letters[i] + ", ");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String word = randomWord();
        int guesses = (int)(((word.length()*2)/3)+.5);// the math is for having it be comparatively fair between the large and small words
        hangMan(word,guesses);
    }
}