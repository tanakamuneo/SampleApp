package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.libs.F.Option;
import play.*;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.*;
import utils.ConfigUtil;
import views.html.*;
import models.entity.Check;
import models.request.Check.ResultPostRequest;
import models.service.Check.CheckModelService;

public class ChecksController extends Application {

  public static Result index() {
	  // 要実装
	  // 文字列は、application.confで設定したcheckyou.setting.message.title、checkyou.setting.message.questionを渡す

      String title = ConfigUtil.get("checkyou.setting.message.title").get();
      String question = ConfigUtil.get("checkyou.setting.message.question").get();
      Form<ResultPostRequest> form = Form.form(ResultPostRequest.class);

      return ok(index.render(title,question,form));

  }




  public static Result result() {
          // 要実装
		  // 文字列は、application.confで設定したcheckyou.setting.message.title、checkyou.setting.message.questionを渡す

		  //値を受け取る
		  Form<ResultPostRequest> f = new Form(ResultPostRequest.class).bindFromRequest();

		  //受け取った値をString型に格納する
		  String x = f.data().get("name");


		    //　バリデーションチェック
	        // error.required
	        if(f.error("name") != null && f.error("name").message().contains("error.required")) {
	            return validationErrorIndexResult("名前欄が空です", f);
	        }


	        // error.pattern
	        // 要実装（返す文言："Twitter id形式ではありません")
	        //@****っていうのがtwitter　IDの形式


	        String regex = "^@";
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(x);
	        if(m.find()==false){
	        	return validationErrorIndexResult("Twitter id形式ではありません", f);
	        }

	        // error.maxLength
	        // 要実装（返す文言："文字数が15文字を超えています")
	        if(x.length()>15 && f.error("name").message().contains("error.maxLength")){
	        	return validationErrorIndexResult("文字数が15文字を超えています",f);
	        }


		  //コンストラクタを呼び出す インプットされた文字を保存している
		  Check check = new Check(x);

		  //診断結果を取得
		  String result = check.result().get();

		  Check check2 = new Check(x,result);

		  //保存する
		  check2.store();

	      String title =  x + ConfigUtil.get("checkyou.setting.message.resultTitle").get();



	   // TwitterID取得
	   // TwitterIDに基いて作成したCheckインスタンス保存
	   // 保存したOption型のCheckインスタンスが存在する場合、「Twitter名 +  checkYou.setting.message.resultTitle」, 保存したCheckインスタンスを返す
	   // 保存したOption型のCheckインスタンスが存在しない場合、Applicationクラスのfail呼び出し（redirect先：indexビュー、key:"error", message: "診断エラーです"
	 	      return ok(views.html.result.render(title,check2));

  }




	  // 診断結果ページシェア用
	    public static Result resultId(Long id) {
	        Option<Check> check        = new Check(id).unique();
	        return check.isDefined() ?
	            ok(result.render(
	                    check.get().name
	                    + ConfigUtil.get("checkYou.setting.message.resultTitle").getOrElse(""), check.get()))
	                : Application.fail(routes.ChecksController.index(), "error", "診断エラーです");
	    }



  private static Result validationErrorIndexResult(String message, Form<ResultPostRequest> form) {
	    flash("error", message);
	    return badRequest(index.render(
	        ConfigUtil.get("checkyou.setting.message.title").getOrElse(""),
	        ConfigUtil.get("checkyou.setting.message.question").getOrElse(""),
	        form));
  }




  public static Result recent(Integer page) {

	  //送信するtitle,messageを作る
	  String title = ConfigUtil.get("checkyou.setting.message.recentTitle").get();
	  String message = ConfigUtil.get("checkyou.setting.message.recentDescription").get();

	  //DBに入っているデータを取ってくる

	  CheckModelService cms = new CheckModelService();
	  List<Check> entryList = cms.findWithPage(page).get();

	  Option<List<Check>> result = new Check().entitiesList(page);
      Integer maxPage            = new Check().entitiesMaxPage(1);


	  //@(title: String, message: String, entryList: java.util.List[models.entity.Check], page: Integer, maxPage: Integer)
	  //なので、String,String,list,Integer,Integerを引数に入れたい
      return ok(views.html.check.recent.render(title,message,entryList,page,maxPage));
  }
}
