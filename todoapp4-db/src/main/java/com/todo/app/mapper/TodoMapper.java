package com.todo.app.mapper;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.todo.app.entity.Todo;

/**
 * TODOテーブルアクセスクラス
 */
@Repository
public class TodoMapper {

	// DBアクセスオブジェクト
	private JdbcTemplate jdbcTemplate;
	// DBスキーマ名
	private String schemaName = "";

    public TodoMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        try {
            this.schemaName = this.jdbcTemplate.getDataSource().getConnection().getSchema();
        }
        catch (Exception e) {
        	schemaName = "TODO";
        }
    }
	
	/**
	 * 全件取得
	 * @return
	 * @throws SQLException 
	 */
	public List<Todo> selectAll() {
		// SQL生成
		String sql = 
				"""
				  SELECT 
				    id
				,  title 
				, done_flg 
				, time_limit 
				FROM
				    {0}.TODO
				""";
		// スキーマ名を置換
		sql = sql.replace("{0}", this.schemaName);
		// 全件取得を行う
		return jdbcTemplate.query(sql, (rs, rowNum) -> 
			new Todo(
	                rs.getLong("id"),
	                rs.getString("title"),
	                rs.getInt("done_flg"),
	                rs.getString("time_limit")
					));
    }

    /**
     * 追加処理
     * @param todo
     * @return true or false
     */
    public Boolean add(Todo todo) {
		// SQL生成
		String sql = 
				"""
				INSERT INTO {0}.TODO (
				     id
					,title 
					,done_flg 
					,time_limit
				)
				SELECT
				  (MAX(ID) + 1)
				 ,:title
				 ,0
				 ,NULL
				FROM {0}.TODO
				""";
		// スキーマ名を置換
		sql = sql.replace("{0}", this.schemaName);
		try {
			// 追加処理
			if (jdbcTemplate.update(sql,
	                todo.getTitle()) == 1) {
				return true;
			}
		} catch(Exception e) {
			return false;
		}
		return false;
    }

    /**
     * 更新処理
     * @param todo
     * @return true or false
     */
    public Boolean update(Todo todo) {
		// SQL生成
		String sql = 
				"""
				UPDATE {0}.TODO
				SET
				     title      = :title 
				    ,done_flg   = :done_flg
				    ,time_limit = :time_limit
				WHERE
				    id = :id
				""";
		// スキーマ名を置換
		sql = sql.replace("{0}", this.schemaName);
		// 更新処理
		if (jdbcTemplate.update(sql,
                todo.getTitle(), 
                todo.getDone_flg(), 
                todo.getTimeLimit(),
                todo.getId()) == 1) {
			return true;
		}
		return false;
    }

    /**
     * 削除処理
     * @param todo
     * @return true or false
     */
    public Boolean delete(Todo todo) {
		// SQL生成
		String sql = 
				"""
				DELETE {0}.TODO
				WHERE
				    id = :id
				""";
		// スキーマ名を置換
		sql = sql.replace("{0}", this.schemaName);
		// 削除処理
		if (jdbcTemplate.update(sql,
                todo.getId()) <= 0) {
			return true;
		}
		return false;
    }
}