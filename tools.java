package lyun.longzhi.components.actions;

public class tools {

    public static int replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            str = str.replaceAll("[\\pPââââ]", "");
            dest = str.replaceAll("\\s*|\t|\r|\n", "");
        }
        return dest.length();
    }
}
