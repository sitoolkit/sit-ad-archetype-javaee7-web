package org.sitoolkit.ad.archetype.tips.infrastructure.presentation.jsf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIInput;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.apache.commons.lang3.StringUtils;

public class ErrorStyleClassListener implements SystemEventListener {

    private static final String STYLE_CLASS_ATTRIBUTE = "styleClass";
    private static final String ERROR_STYLE_CLASS = "input-error";

    /**
     * システムイベントの発生元の{@code UIInput}でバリデーションエラーが発生していた場合、入力項目の{@code styleClass}
     * 属性に {@code inputError}を追加します。 バリデーションエラーが発生していなかった場合、入力項目の
     * {@code styleClass}属性から{@code inputError}を除去します。
     */
    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        UIInput input = (UIInput) event.getSource();
        if (input.isValid()) {
            removeErrorStyleClass(input.getAttributes());
        } else {
            addErrorStyleClass(input.getAttributes());
        }
    }

    /**
     * システムイベントの発生元のオブジェクトの型が{@code UIInput}またはそのサブクラスである場合にtrueを返却します。
     *
     * @param source
     *            システムイベントの発生元のオブジェクト
     */
    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof UIInput;
    }

    /**
     * 指定されたマップのキーが{@code styleClass}であるエントリーの値に文字列{@code inputError}を加えます。
     *
     * @param map
     *            マップ
     */
    private void addErrorStyleClass(Map<String, Object> map) {
        List<String> styleClasses = getStyleClassAsList(map);

        if (styleClasses.isEmpty()) {
            map.put(STYLE_CLASS_ATTRIBUTE, ERROR_STYLE_CLASS);
        } else if (!styleClasses.contains(ERROR_STYLE_CLASS)) {
            styleClasses.add(ERROR_STYLE_CLASS);
            map.put(STYLE_CLASS_ATTRIBUTE, StringUtils.join(styleClasses, " "));
        }
    }

    /**
     * 指定されたマップのキーが{@code styleClass}であるエントリーの値から文字列{@code inputError}を除きます。
     *
     * @param map
     *            マップ
     */
    private void removeErrorStyleClass(Map<String, Object> map) {
        List<String> styleClasses = getStyleClassAsList(map);

        int index = styleClasses.indexOf(ERROR_STYLE_CLASS);
        if (index != -1) {
            styleClasses.remove(index);
            map.put(STYLE_CLASS_ATTRIBUTE, StringUtils.join(styleClasses, " "));
        }
    }

    /**
     * 指定されたマップのキーが{@code styleClass}であるエントリーの値を、 半角スペースで分割し{@code List<String>}
     * に格納して返却します。
     *
     * @param map
     *            マップ
     */
    private List<String> getStyleClassAsList(Map<String, Object> map) {
        Object styleClass = map.get(STYLE_CLASS_ATTRIBUTE);
        if (styleClass == null) {
            return new ArrayList<String>();
        }

        List<String> retList = new ArrayList<String>();

        retList.addAll(Arrays.asList(styleClass.toString().split(" ")));

        return retList;
    }
}
