package ru.rzncenter.webcore.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Компонент транслитерации киррилических символов в латиницу
 */
@Component
public class CyrTranslation implements Translation {

    protected Map<Character, String> map = new HashMap<Character, String>();

    {
        map.put('А', "A");
        map.put('Б', "B");
        map.put('В', "V");
        map.put('Г', "G");
        map.put('Д', "D");
        map.put('Е', "E");
        map.put('Ё', "E");
        map.put('Ж', "Zh");
        map.put('З', "Z");
        map.put('И', "I");
        map.put('Й', "I");
        map.put('К', "K");
        map.put('Л', "L");
        map.put('М', "M");
        map.put('Н', "N");
        map.put('О', "O");
        map.put('П', "P");
        map.put('Р', "R");
        map.put('С', "S");
        map.put('Т', "T");
        map.put('У', "U");
        map.put('Ф', "F");
        map.put('Х', "H");
        map.put('Ц', "C");
        map.put('Ч', "Ch");
        map.put('Ш', "Sh");
        map.put('Щ', "Sh");
        map.put('Ъ', "'");
        map.put('Ы', "Y");
        map.put('Ь', "'");
        map.put('Э', "E");
        map.put('Ю', "U");
        map.put('Я', "Ya");
        map.put('а', "a");
        map.put('б', "b");
        map.put('в', "v");
        map.put('г', "g");
        map.put('д', "d");
        map.put('е', "e");
        map.put('ё', "e");
        map.put('ж', "zh");
        map.put('з', "z");
        map.put('и', "i");
        map.put('й', "i");
        map.put('к', "k");
        map.put('л', "l");
        map.put('м', "m");
        map.put('н', "n");
        map.put('о', "o");
        map.put('п', "p");
        map.put('р', "r");
        map.put('с', "s");
        map.put('т', "t");
        map.put('у', "u");
        map.put('ф', "f");
        map.put('х', "h");
        map.put('ц', "c");
        map.put('ч', "ch");
        map.put('ш', "sh");
        map.put('щ', "sh");
        map.put('ъ', "'");
        map.put('ы', "y");
        map.put('ь', "'");
        map.put('э', "e");
        map.put('ю', "u");
        map.put('я', "ya");
        map.put(' ', "_");

    }

    /**
     * Выполняет транслитерацию
     * @param input входная кирилическая строка
     * @return
     */
    public String execute(String input) {

        char[] chars = input.toCharArray();

        StringBuilder buffer = new StringBuilder();

        for(Character ch  : chars) {

            String fromMap = map.get(ch);

            if(fromMap != null) {

                buffer.append(fromMap);

            } else {

                buffer.append(ch);

            }

        }

        return buffer.toString();

    }


}
