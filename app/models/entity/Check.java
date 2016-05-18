package models.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.Constraint;
import play.libs.F.Option;
import models.service.Check.CheckModelService;
import models.service.Check.CheckService;
import play.db.ebean.Model;
import play.data.validation.*;
import play.data.format.*;


@Entity
public class Check extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String result;

    @Formats.DateTime(pattern="yyyy/MM/dd")
    public Date created;

    @Formats.DateTime(pattern="yyyy/MM/dd")
    public Date modified;

    @Transient  // 永続化しないフィールドを定義。Transient付けないとDBのフィールドとして処理されようとしてエラーになる
    private CheckModelService checkModelService = new CheckModelService();

    @Transient
    private CheckService checkService = new CheckService();


    // コンストラクタ設定
    public Check() {}

    public Check(String name) {
        this.name = name;
    }

    // 検索用オブジェクト生成のためのコンストラクタ
    /*
      使用例. id=10のデータを取り出す
      Check check = new Check(10).method();
    */
    public Check(Long id) {
        this.id = id;
    }

    public Check(String name, String result) {
        this.name = name;
        this.result = result;
        this.created = new Date();
        this.modified = new Date();
    }

    // 診断結果を取得
    public Option<String> result() {
          //　要実装
    	return checkService.getResult(name);
    }


    public Option<Check> unique() {
        return checkModelService.findById(id);
    }

    public Option<Check> store() {
        return checkModelService.save(this);
    }

    // ページ結果を取得
    public Integer entitiesMaxPage(Integer value) {
        // 要実装
        // CheckModelServiceのgetMaxPage呼び出し。最大ページ数取得できない場合、valueを返す
    	return checkModelService.getMaxPage().get();
    }

    // 指定ページの一覧
    public Option<List<Check>> entitiesList(Integer page) {
//　　　// 要実装
//　　　// CheckModelServiceクラスのメソッド呼び出し
    	return checkModelService.findWithPage(page);
    }

}