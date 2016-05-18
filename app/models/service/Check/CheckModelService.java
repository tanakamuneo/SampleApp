package models.service.Check;

import java.util.List;
import models.entity.Check;
import models.service.Model.ModelService;
import play.db.ebean.Model.Finder;
import utils.OptionUtil;
import play.libs.F;
import play.libs.F.Option;
import play.libs.F.None;

public class CheckModelService implements ModelService<Check> {

	final static Integer LIMIT = 5;  // １ページあたりの表示件数

    public static CheckModelService use() {
        return new CheckModelService();
    }



      @Override
      public Option<Check> findById(Long id) {
          Finder<Long, Check> find = new Finder<Long, Check>(Long.class, Check.class);
          return OptionUtil.apply(find.byId(id));
      }


      @Override
      public Option<Check> save(Check entry) {
          Option<Check> entryOp = OptionUtil.apply(entry);
          if(entryOp.isDefined()) {
              entry.save();
              if(OptionUtil.apply(entry.id).isDefined()) {
                  return OptionUtil.apply(entry);
              }
          }
          return new None<Check>();
      }

   // 最大ページ数を取得
      public Option<Integer> getMaxPage() {
          Finder<Long, Check> find = new Finder<Long, Check>(Long.class, Check.class);
          // 要実装
          // getTotalPageCountを使用して最大ページ数取得
          return OptionUtil.apply(
        		  find.order().asc("created").findPagingList(LIMIT).getTotalPageCount()
        		  );
      }

   // ページ番号で取得
      @Override
      public Option<List<Check>> findWithPage(Integer pageSource) {

          // Ebeanではページが0から始まるためページ番号調整
          Integer pageNum = (pageSource - 1 < 0)? 0 : pageSource - 1;

          // 要実装
          // findPagingListを使用し, 指定したページ番号、指定ページ表示件数（LIMIT）、作成日昇順のOption<Check>のListを取得

          Finder<Long, Check> finder = new Finder<Long,Check>(Long.class, Check.class);
          return OptionUtil.apply(
        		  finder.orderBy("created").findPagingList(LIMIT).getPage(pageNum).getList()
        		  );

      }
}


