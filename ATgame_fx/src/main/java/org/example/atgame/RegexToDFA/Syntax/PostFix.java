package org.example.atgame.RegexToDFA.Syntax;

import java.util.*;

public class PostFix {

    /** Operators precedence map. */
    public static final Map<Character, Integer> precedenceMap;
    static {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('(', 1);
        map.put('|', 2);
        map.put('.', 3); // explicit concatenation operator
        map.put('?', 4);
        map.put('*', 4);
        map.put('+', 4);
        map.put('^', 5);
        precedenceMap = Collections.unmodifiableMap(map);
    };

    /**
     * Get character precedence.
     *
     * @param c character
     * @return corresponding precedence
     */
    private static Integer getPrecedence(Character c) {
        Integer precedence = precedenceMap.get(c);
        return precedence == null ? 6 : precedence;
    }

    /**
     * Transform regular expression by inserting a '.' as explicit concatenation
     * operator.
     */
//    private static String formatRegEx(String regex) {
//        String res = new String();
//        List<Character> allOperators = Arrays.asList('|', '?', '+', '*', '^');
//        List<Character> binaryOperators = Arrays.asList('^', '|');
//
//        for (int i = 0; i < regex.length(); i++) {
//            Character c1 = regex.charAt(i);
//
//            if (i + 1 < regex.length()) {
//                Character c2 = regex.charAt(i + 1);
//
//                res += c1;
//
//                if (!c1.equals('(') && !c2.equals(')') && !allOperators.contains(c2) && !binaryOperators.contains(c1)) {
//                    res += '.';
//                }
//            }
//        }
//        res += regex.charAt(regex.length() - 1);
//
//        return res;
//    }
    private static String formatRegEx(String regex) {
        StringBuilder res = new StringBuilder();
        //List<Character> allOperators = Arrays.asList('|', '?', '+', '*', '^');
        //List<Character> binaryOperators = Arrays.asList('^', '|');

        for (int i = 0; i < regex.length() - 1; i++) {
            char c1 = regex.charAt(i);
            char c2 = regex.charAt(i + 1);

            res.append(c1);

            // Insert '.' only when:
            // c1 is not an operator or a left parenthesis
            // AND c2 is not an operator or a right parenthesis
            if ((Character.isLetterOrDigit(c1) || c1 == ')' || c1 == '*' || c1 == '+' || c1 == '?')
                    && (Character.isLetterOrDigit(c2) || c2 == '(')) {
                res.append('.');
            }
        }
        res.append(regex.charAt(regex.length() - 1));

        return res.toString();
    }

    /**
     * Convert regular expression from infix to postfix notation using
     * Shunting-yard algorithm.
     *
     * @param regex infix notation
     * @return postfix notation
     */
    public static String infixToPostfix(String regex) {
        String postfix = new String();

        Stack<Character> stack = new Stack<Character>();

        String formattedRegEx = formatRegEx(regex);

        for (Character c : formattedRegEx.toCharArray()) {
            switch (c) {
                case '(':
                    stack.push(c);
                    break;

                case ')':
                    while (!stack.peek().equals('(')) {
                        postfix += stack.pop();
                    }
                    stack.pop();
                    break;

                default:
                    while (stack.size() > 0) {
                        Character peekedChar = stack.peek();

                        Integer peekedCharPrecedence = getPrecedence(peekedChar);
                        Integer currentCharPrecedence = getPrecedence(c);

                        if (peekedCharPrecedence >= currentCharPrecedence) {
                            postfix += stack.pop();
                        } else {
                            break;
                        }
                    }
                    stack.push(c);
                    break;
            }

        }

        while (stack.size() > 0)
            postfix += stack.pop();

        return postfix;
    }
}