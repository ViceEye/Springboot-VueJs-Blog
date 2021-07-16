package top.venja.common.utils;

import io.jsonwebtoken.Claims;
import top.venja.common.lang.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommonUtil {

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String generateShortUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * Ledger Type Int Enum
     */
    public enum LedgerType {

        COMMON(1),
        IN(2),
        OUT(3);

        private final int type;

        LedgerType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static List<Integer> getAllValues() {
            List<Integer> type = new ArrayList<>();
            for (LedgerType ledgerType : values()) {
                type.add(ledgerType.getType());
            }
            return type;
        }
    }

}
