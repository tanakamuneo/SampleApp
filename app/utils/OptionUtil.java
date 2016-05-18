package utils;

import java.util.List;

import play.libs.F;

//Option用ユーティリティクラス
public class OptionUtil {

 public static <A> F.Option<A> apply(A value) {
     if(value != null) {
         return F.Option.Some(value);
     } else {
         return F.Option.None();
     }
 }

 public static <String> F.Option<String> applyWithString(String value) {
     if(value != null && !value.equals("")) {
         return F.Option.Some(value);
     } else {
         return F.Option.None();
     }
 }

 public static <T> F.None<T> none() {
     return new F.None<T>();
 }

 public static <A> F.Option<List<A>> apply(List<A> value) {
     if(value != null && value.size() != 0) {
         return F.Option.Some(value);
     } else {
         return F.Option.None();
     }
 }
}
