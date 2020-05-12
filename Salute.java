public class Salute {
    public static int solution(String s) {
        int salutes = 0;
        int movingFromLeftToRight = 0;
        for (int i = 0; i < (int) s.length(); i++) {
            if (s.charAt(i) == '>') movingFromLeftToRight++;
            else if (s.charAt(i) == '<') salutes += 2 * movingFromLeftToRight;
        }
        return salutes;
    }

    public static void main(String[] args) {
        System.out.println(solution(">----<"));
        System.out.println(solution("<<>><"));
    }
}
