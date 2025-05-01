package slagalica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("The format of the guesses will be 'Symbol Symbol Symbol Symbol' where ");
		System.out.println("Symbol is any of \"Skocko\", \"Tref\", \"Pik\", \"Srce\", \"Karo\", \"Zvezda\"");
		System.out.println("The format of your answers should be 'x y' where ");
		System.out.println("x - number of guessed symbols in the same position as in your combination");
		System.out.println("y - number of guessed symbols in a different position than in your combination but still contained by your combination");
		while(true) {
			List<String[]> lista = new ArrayList<>();
			setup(lista);

			String tacno[] = lista.get(new Random().nextInt(lista.size()));
			
			while(lista.size() > 1) {
				guess(lista, scanner, tacno);
			}
			if(lista.size() != 1) {
				System.out.println("Some of your answers were impossible, therefore I cannot guess the combination you're thinking of");
			}
			else  System.out.println("Your combination is " + lista.get(0)[0] + " " + lista.get(0)[1] + " " + lista.get(0)[2] + " " + lista.get(0)[3]);
			System.out.println("Would you like to go again? 0 - no, 1 - yes ");
			if(scanner.nextInt() == 0) break;
		}

		
		scanner.close();
	}
	
	private static void guess(List<String[]> lista, Scanner scanner, String[] tacno) {
		String[] s = pick(lista);
		

        System.out.print(s[0] + " " + s[1] + " " + s[2] + " " + s[3] + " : ");
        //int odg[] = compare(s, tacno);
        //System.out.println(odg[0] + " " + odg[1]);
        int odg[] = new int[2];
        odg[0] = scanner.nextInt();
        odg[1] = scanner.nextInt();
        eliminate(lista, odg, s);
	}

	private static void eliminate(List<String[]> lista, int[] odg, String[] s) {
		Iterator<String[]> iterator = lista.iterator();
	    
	    while (iterator.hasNext()) {
	        String[] elem = iterator.next();
	        if (!Arrays.equals(compare(elem, s), odg)) {
	            iterator.remove(); // Use the iterator to remove the element
	        }
	    }
		
	}

	private static String[] pick(List<String[]> lista) {
		int scores[] = new int[lista.size()];
		for(int i = 0; i < scores.length; i++) scores[i] = 0;
		for(int i = 0; i < lista.size(); i++) {
			scores[i] = eliminationCount(lista.get(i), lista);
		}
		List<Integer> izbori = new ArrayList<>();
		izbori.add(0);
		for(int i = 0; i < scores.length; i++) {
			if(scores[izbori.get(0)] < scores[i]) {
				izbori.clear();
				izbori.add(i);
			}
			else if(scores[izbori.get(0)] == scores[i]) {
				izbori.add(i);
			}
			
		}
		return lista.get(izbori.get(new Random().nextInt(izbori.size())));
		
	}

	private static int eliminationCount(String[] strings, List<String[]> lista) { 
		int x = 0;
		List<int[]> answers = generateAnswers(4);
		for(int[] a : answers) x += tryAnswerAndCombination(a, strings, lista);
		return x;
	}

	private static int tryAnswerAndCombination(int[] a, String[] strings, List<String[]> lista) {
		int x = 0;
		for(String[] s : lista) {
			if(!Arrays.equals(s, strings)) {
				int cmp[] = compare(s, strings);
				if((cmp[0] != a[0] || cmp[1] != a[1]) && !impossibleAnswer(strings, lista, a)) x++;
			}
		}
		return x;
	}

	private static boolean impossibleAnswer(String[] s, List<String[]> lista, int[] cmp) {
		if(cmp[0] == 3 && cmp[1] == 1) return true;
		else if(cmp[0] == 2 && cmp[1] == 2) return check(0, s) || check(1, s);
		else if(cmp[0] == 2 && cmp[1] == 1) return check(0, s);
		else if(cmp[0] == 1 && cmp[1] == 3) return check(0, s) || check(1, s) || check(2, s);
		else if(cmp[0] == 1 && cmp[1] == 2)	return check(0, s);
		else if(cmp[0] == 1 && cmp[1] == 1) return check(0, s);
		else if(cmp[0] == 0 && cmp[1] == 4) return check(0, s) || check(1, s);
		else if(cmp[0] == 0 && cmp[1] == 3) return check(0, s) || check(1, s);
		else if(cmp[0] == 0 && cmp[1] == 2) return check(0, s);
		else if(cmp[0] == 0 && cmp[1] == 1) return check(0, s);
		else return false;
	}

	private static boolean check(int i, String[] s) {
		Map<String, Integer> countMap = new HashMap<>();
        
        for (String element : s) {
            countMap.put(element, countMap.getOrDefault(element, 0) + 1);
        }
        
        boolean hasValueAppearedCaseTimes = false;
        int caseI = 0;
        switch(i) {
	        case 0:
		        caseI = 4;
		        break;
	        case 1:
	        	caseI = 3;
		        break;
	        case 2:
	        	caseI = 2;
		        break;
        }
        if(caseI == 4 || caseI == 3) {
        	for (int value : countMap.values()) {
                if (value == caseI) {
                    hasValueAppearedCaseTimes = true;
                    break;
                }
            }
        }
        else {
        	for (int value : countMap.values()) {
                if (value == 2) {
                	hasValueAppearedCaseTimes = true;
                } else {
                	hasValueAppearedCaseTimes = false;
                    break;
                }
            }
        }
        
		return hasValueAppearedCaseTimes;
	}

	private static int[] compare(String[] s, String[] strings) {
		int cmp[] = {0,0};
		int marki[] = new int[s.length];
		int markj[] = new int[s.length];
		for(int i = 0; i < marki.length; i++) {
			marki[i] = 0;
			markj[i] = 0;
		}
		for(int i = 0; i < s.length; i++) {
			if(s[i].equals(strings[i])){
				cmp[0]++;
				marki[i] = 1;
				markj[i] = 1;
			}
		}
		for(int i = 0; i < s.length; i++) {
			for(int j = 0; j < s.length; j++) {
				if(s[i].equals(strings[j]) && marki[i] == 0 && markj[j] == 0) {
					if(i != j) cmp[1]++;
					marki[i] = 1;
					markj[j] = 1;
					break;
				}
			}
		}
		return cmp;
	}

	private static List<int[]> generateAnswers(int maxValue) {
		List<int[]> combinations = new ArrayList<>();
        
        for (int i = 0; i <= maxValue; i++) {
            for (int j = 0; j <= maxValue - i; j++) {
                int[] combination = {i, j};
                combinations.add(combination);
            }
        }
        
        return combinations;
	}

	private static void setup(List<String[]> l) {
        String[] words = { "Skocko", "Tref", "Pik", "Srce", "Karo", "Zvezda" };
        generateCombinations(l, words, new String[4], 0);
    }

    private static void generateCombinations(List<String[]> l, String[] words, String[] currentCombination, int currentIndex) {
        if (currentIndex == currentCombination.length) {
            l.add(currentCombination.clone());
            return;
        }

        for (int i = 0; i < words.length; i++) {
            currentCombination[currentIndex] = words[i];
            generateCombinations(l, words, currentCombination, currentIndex + 1);
        }
    }
    

}

