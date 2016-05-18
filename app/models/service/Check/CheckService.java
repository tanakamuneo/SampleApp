package models.service.Check;

import utils.ConfigUtil;
import utils.OptionUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static play.libs.F.*;


//診断サービスクラス
public class CheckService {

	// 診断結果取得
    public Option<String> getResult(String name) {
        // 要実装
        // 診断リスト取得（診断リストが存在しない場合、"2.1.3 Java"を返す）
    	List<String> versions = ConfigUtil.getByList("checkyou.setting.answer")
    			.getOrElse(new ArrayList<String>(){{add("2.1.3 Java");}});
        // Collectionsクラスのshuffleメソッドでランダムに診断リスト並び替え
    	Collections.shuffle(versions);
    	return getResultText(name, versions.get(0));
    }



    // 取得した診断結果を整形（nameとversionはそのまま使用可、連結した文字列はtoString()メソッド使用）
    // 診断結果
    // 正常ケース：nameさんにオススメなPlay frameworkのバージョンは、versionです。
    // 異常ケース①：name-versionです。
    // 異常ケース②：nameさんにオススメなPlay frameworkのバージョンは、version.
    // 異常ケース③：name-version.
    private Option<String> getResultText(String name, String version) {
        StringBuilder result = new StringBuilder();
        // 要実装
        result.append(name);
        result.append("さんにオススメなPlay frameworkのバージョンは、 ");
        result.append(version);
        result.append("です。");
        String str = new String(result);

        return OptionUtil.applyWithString(str);

    }
}


