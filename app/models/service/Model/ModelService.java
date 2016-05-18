package models.service.Model;

import play.libs.F.Option;
import java.util.List;
import play.db.ebean.Model;

// モデル向けサービスのインターフェース
public interface ModelService<T extends Model> {

	public Option<List<T>> findWithPage(Integer pageSource);
    public Option<T> findById(Long id);
    public Option<T> save(T entry);
}
