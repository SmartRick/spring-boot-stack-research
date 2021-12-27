package cn.smartrick.constant;

/**
 * @Date: 2021/12/23 10:35
 * @Author: SmartRick
 * @Description: TODO
 */
public class AnalyzerType {
    /**
     * 会将文本做最细粒度的拆分，
     * 例如「中华人民共和国国歌」会被拆分为
     * 「中华人民共和国、中华人民、中华、华人、人民共和国、人民、人、民、共和国、共和、和、国国、国歌」
     * 会穷尽各种可能的组合
     */
    public static final String MAX_WORD = "ik_max_word";

    /**
     * 会将文本做最粗粒度的拆分，
     * 例如「中华人民共和国国歌」会被拆分为「中华人民共和国、国歌」
     */
    public static final String SMART_WORD = "ik_smart";

}
