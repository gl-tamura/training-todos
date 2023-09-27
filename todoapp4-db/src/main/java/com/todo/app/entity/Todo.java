package com.todo.app.entity;
import java.time.LocalDateTime;

import lombok.Data;

/**
* タスク（TODO）
* @param id
* @param title
* @param done_flg
* @param time_limit
*/
@Data
public class Todo {
	
	//クラスフィールド
	private long id;			// id　通し番号
	private String title; 		//リストのタイトル
	private Integer done_flg;	//リストの済/未済　チェックボックスtrue/false
	private String time_limit;	//リストの期限　str型データ
	
	/**
	 * コンストラクタ
	 * @param id
	 * @param title
	 * @param done_flg
	 * @param time_limit
	 */
	public Todo(Long id, String title, Integer done_flg, String time_limit) {
		this.id = id;
		this.title = title;
		this.done_flg = done_flg;
		if (time_limit != null) {
			// 日付がYYYY-MM-DDではない場合、前10桁を日付とする
			if (time_limit.length() > 10) {
				this.time_limit = time_limit.substring(0, 10);
			}
		}
	}
	
	/**
	 * 日付変換処理（DB登録用）
	 * @return
	 */
	public LocalDateTime getTimeLimit() {
		if (this.time_limit == null || this.time_limit.length() == 0) {
			return null;
		}
		return LocalDateTime.parse(this.time_limit+"T00:00:00");
	}
}
